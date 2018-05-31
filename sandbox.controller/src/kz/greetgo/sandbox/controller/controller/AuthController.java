package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.*;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.AuthRegister;
import kz.greetgo.sandbox.controller.security.NoSecurity;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.List;

/**
 * как составлять контроллеры написано
 * <a href="https://github.com/greetgo/greetgo.mvc/blob/master/greetgo.mvc.parent/doc/controller_spec.md">здесь</a>
 */
@Bean
@Mapping("/auth")
public class AuthController implements Controller {

  public BeanGetter<AuthRegister> authRegister;

  @AsIs
  @NoSecurity
  @Mapping("/login")
  public String login(@Par("accountName") String accountName, @Par("password") String password) {
    return authRegister.get().login(accountName, password);
  }

  @ToJson
  @Mapping("/info")
  public AuthInfo info(@ParSession("personId") String personId) {
    return authRegister.get().getAuthInfo(personId);
  }

  @ToJson
  @Mapping("/userInfo")
  public UserInfo userInfo(@ParSession("personId") String personId) {
    return authRegister.get().getUserInfo(personId);
  }

  @ToJson
  @Mapping("/clientInfoViewList")
  public List<ClientInfoView> clientInfoViewList() {
    return authRegister.get().getClientInfoViewList();
  }

  @ToJson
  @Mapping("/clientInfoListViewListPart")
  public List<ClientInfoView> clientInfoViewListPart(@Par("from") int from, @Par("to") int to) {
    return authRegister.get().getClientInfoViewList(from, to);
  }

  @ToJson
  @Mapping("/deleteClientInfo")
  public void deleteClientInfo(@Par("clientId") String clientId) {
    authRegister.get().removeClientInfo(clientId);
  }

  @ToJson
  @Mapping("/totalClientsNumber")
  public int totalClientsNumber() {
    return authRegister.get().getTotalClientsNumber();
  }

  @ToJson
  @Mapping("/editClientInfo")
  public void editClientInfo (@Par("editedClientInfo") ClientInfoEdited editedClientInfo) {
    authRegister.get().editClientInfo(editedClientInfo);
  }

  @ToJson
  @Mapping("/sortedClientInfoViewListPart")
  public List<ClientInfoView> sortedClientInfoViewListPart(@Par("from") int from, @Par("to") int to, @Par("sortBy") SortBy sortBy) {
    return authRegister.get().getSortedClientInfoViewList(from, to, sortBy);
  }
}
