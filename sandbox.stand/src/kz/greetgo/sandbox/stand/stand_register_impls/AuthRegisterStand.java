package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.errors.AuthError;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.AuthRegister;
import kz.greetgo.sandbox.controller.register.model.SessionInfo;
import kz.greetgo.sandbox.controller.register.model.UserParamName;
import kz.greetgo.sandbox.controller.security.SecurityError;
import kz.greetgo.sandbox.db.stand.beans.StandDb;
import kz.greetgo.sandbox.db.stand.model.ClientInfoDot;
import kz.greetgo.sandbox.db.stand.model.PersonDot;
import kz.greetgo.util.ServerUtil;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Bean
public class AuthRegisterStand implements AuthRegister {

  private final Path storageDir = new File("build/user_param_storage").toPath();

  private File getUserParamFile(UserParamName name) {
    return storageDir.resolve(name.name() + ".txt").toFile();
  }

  @Override
  public void saveParam(String personId, UserParamName name, String value) {
    File paramFile = getUserParamFile(name);
    if (value == null) {
      if (paramFile.exists()) paramFile.delete();
      return;
    }
    paramFile.getParentFile().mkdirs();
    try (PrintStream out = new PrintStream(paramFile, "UTF-8")) {
      out.print(value);
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getParam(String personId, UserParamName name) {
    File paramFile = getUserParamFile(name);
    if (!paramFile.exists()) return null;
    try {
      return ServerUtil.streamToStr(new FileInputStream(paramFile));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public BeanGetter<StandDb> db;

  @Override
  public String login(String accountName, String password) {
    if (!"111".equals(password)) {
      throw new AuthError("Пароль 111");
    }

    StringBuilder err = new StringBuilder();
    err.append("Use one of: ");

    for (PersonDot personDot : db.get().personStorage.values()) {
      if (!personDot.disabled) err.append(personDot.accountName).append(", ");
      if (accountName == null) continue;
      if (accountName.equals(personDot.accountName)) {
        if (personDot.disabled) throw new AuthError("Account " + accountName + " is disabled");
        return "token:personId=" + personDot.id;
      }
    }

    err.setLength(err.length() - 2);

    throw new AuthError(err.toString());
  }

  private final ThreadLocal<SessionInfo> sessionInfoThreadLocal = new InheritableThreadLocal<>();

  @Override
  public void checkTokenAndPutToThreadLocal(String token) {
    sessionInfoThreadLocal.set(null);
    if (token == null) throw new SecurityError("token is null");
    if (!token.startsWith("token:")) throw new SecurityError("Token does not contain prefix `token:`");
    String personId = null;
    for (String pair : token.substring("token:".length()).split(",")) {
      String[] split = pair.split("=");
      if (split.length != 2) throw new SecurityError("Left token = [[" + token + "]]");
      if (split[0].equals("personId")) {
        personId = split[1];
        continue;
      }
      throw new SecurityError("Unknown token field [[" + split[0] + "]]. Token = [[" + token + "]]");
    }

    if (personId == null) throw new SecurityError("No personId in token");
    sessionInfoThreadLocal.set(new SessionInfo(personId));
  }

  @Override
  public void cleanTokenThreadLocal() {
    sessionInfoThreadLocal.set(null);
  }

  @Override
  public SessionInfo getSessionInfo() {
    return sessionInfoThreadLocal.get();
  }

  @Override
  public AuthInfo getAuthInfo(String personId) {
    PersonDot personDot = db.get().personStorage.get(personId);
    if (personDot == null) throw new NullPointerException("personDot == null for id = " + personId);
    AuthInfo ret = new AuthInfo();
    ret.pageSize = 7;
    ret.appTitle = personDot.accountName + " - Sandbox STAND";
    return ret;
  }

  @Override
  public UserInfo getUserInfo(String personId) {
    return db.get().personStorage.get(personId).toUserInfo();
  }

  @Override
  public ClientInfo getClientInfo(String clientId) {
    List<ClientInfoDot> clientsStorage = db.get().clientsStorage;
    for (ClientInfoDot clientInfoDot : clientsStorage)
      if (clientInfoDot.id.equals(clientId)) return clientInfoDot.toClientInfo();
    return null;
  }

  @Override
  public List<ClientInfoView> getClientInfoViewList() {
    List<ClientInfoDot> clientsStorage = db.get().clientsStorage;
    List<ClientInfoView> clientInfoViews = new ArrayList<>();
    for (ClientInfoDot clientInfoDot : clientsStorage) clientInfoViews.add(clientInfoDot.toClientInfoView());
    return clientInfoViews;
  }

  @Override
  public List<ClientInfoView> getClientInfoViewList(int from, int to) {
    List<ClientInfoDot> clientsStorage = db.get().clientsStorage;
    List<ClientInfoView> clientInfoViews = new ArrayList<>();
    for (int i = from; i < to; i++) {
      if (i > clientsStorage.size()) break;
      clientInfoViews.add(clientsStorage.get(i).toClientInfoView());
    }
    return clientInfoViews;
  }

  @Override
  public int getTotalClientsNumber() {
    return db.get().clientsStorage.size();
  }

  @Override
  public void editClientInfo(ClientInfoEdited clientInfoEdited) {
    List<ClientInfoDot> clientsStorage = db.get().clientsStorage;
    for (ClientInfoDot clientInfoDot : clientsStorage) {
      if (clientInfoDot.id != clientInfoEdited.id) continue;
      clientInfoDot.name = clientInfoEdited.name;
      clientInfoDot.surname = clientInfoEdited.surname;
      clientInfoDot.sex = clientInfoEdited.sex;
      clientInfoDot.name = clientInfoEdited.name;
      clientInfoDot.age = clientInfoEdited.age;
      for (ClientPhone clientPhone : clientInfoDot.clientPhones)
        if (!clientInfoEdited.clientPhones.contains(clientPhone))clientInfoDot.clientPhones.remove(clientPhone);
      for (ClientPhone clientPhone : clientInfoEdited.clientPhones)
        if (!clientInfoDot.clientPhones.contains(clientPhone))clientInfoDot.clientPhones.add(clientPhone);
      clientInfoDot.homeAddress = clientInfoEdited.homeAddress;
      clientInfoDot.registrationAddress = clientInfoEdited.registrationAddress;
      break;
    }
  }

  @Override
  public void removeClientInfo(String clientId) {
    List<ClientInfoDot> clientsStorage = db.get().clientsStorage;
    for (ClientInfoDot clientInfoDot : clientsStorage)
      if (clientInfoDot.id.equals(clientId)) {
        clientsStorage.remove(clientInfoDot);
        return;
      }
  }

  @Override
  public List<ClientInfoView> getSortedClientInfoViewList (int from, int to, SortBy sortBy) {
    ArrayList<ClientInfoDot> clientsStorage = (ArrayList) db.get().clientsStorage;
    List<ClientInfoDot> sortedClientInfoList = (ArrayList<ClientInfoDot>) clientsStorage.clone();
    Comparator<ClientInfoDot> clientInfoComparator = null;
    switch (sortBy) {
      case NAME:
        clientInfoComparator = Comparator.comparing(o -> o.name);
        break;
      case SURNAME:
        clientInfoComparator = Comparator.comparing(o -> o.surname);
        break;
      case AGE:
        clientInfoComparator = Comparator.comparing(o -> o.age);
        break;
      case BALANCE:
        clientInfoComparator = Comparator.comparing(o -> o.balance);
        break;
      case MIN_BALANCE:
        clientInfoComparator = Comparator.comparing(o -> o.minBalance);
        break;
      case MAX_BALANCE:
        clientInfoComparator = Comparator.comparing(o -> o.maxBalance);
    }
    Collections.sort(sortedClientInfoList, clientInfoComparator);
    List<ClientInfoView> clientInfoViews = new ArrayList<>();
    for (ClientInfoDot clientInfoDot : sortedClientInfoList) clientInfoViews.add(clientInfoDot.toClientInfoView());
    return clientInfoViews;
  }
}
