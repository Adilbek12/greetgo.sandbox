package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.ClientDetail;
import kz.greetgo.sandbox.controller.model.ClientRecords;
import kz.greetgo.sandbox.controller.model.ClientToSave;
import kz.greetgo.sandbox.controller.model.SortBy;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.stand.beans.StandDb;
import kz.greetgo.sandbox.db.stand.model.ClientDot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Bean
public class ClientRegisterStand implements ClientRegister {

  private BeanGetter<StandDb> db;

  @Override
  public ClientDetail getClientDetail(int clientId) {
    ClientDot clientDot = getClient(clientId);
    if (clientDot == null) return null;
    else return clientDot.toClientDetail();
  }

  @Override
  public void saveClient(ClientToSave clientToSave) {
    ClientDot clientDot;
    if (clientToSave.id == -1) clientDot = new ClientDot();
    else clientDot = getClient(clientToSave.id);
    assert clientDot != null;
    clientDot.name = clientToSave.name;
    clientDot.surname = clientToSave.surname;
    clientDot.patronymic = clientToSave.patronymic;
    clientDot.gender = clientToSave.gender;
    clientDot.birth_day = clientToSave.birth_day;
    clientDot.client_addr = clientToSave.clientAddresses;
    clientDot.client_phones = clientToSave.clientPhones;
    clientDot.charm = clientToSave.charm;
  }

  @Override
  public void removeClient(int clientId) {
    List<ClientDot> clientDots = db.get().clientsStorage;
    clientDots.remove(getClient(clientId));
  }

  @Override
  public int getClientRecordsCount() {
    List<ClientDot> clientDots = db.get().clientsStorage;
    return clientDots.size();
  }

  @Override
  public List<ClientRecords> getClientRecords(int from, int to) {
    List<ClientDot> temp = db.get().clientsStorage;
    List<ClientRecords> clientRecords = new ArrayList<>();
    for (int i = from; i<=to; i++)
      if (i >= temp.size()) break;
      else clientRecords.add(temp.get(i).toClientRecords());
    return clientRecords;
  }

  @Override
  public List<ClientRecords> getClientRecords(int from, int to, SortBy sortBy) {
    List<ClientDot> temp = db.get().clientsStorage;
    List<ClientRecords> clientRecords = new ArrayList<>();
    for (ClientDot clientDot : temp) clientRecords.add(clientDot.toClientRecords());
    Comparator<ClientRecords> comparator = null;
    switch (sortBy) {
      case NAME:
        comparator = Comparator.comparing(o -> o.name);
        break;
      case SURNAME:
        comparator = Comparator.comparing(o -> o.surname);
        break;
      case PATRONYMIC:
        comparator = Comparator.comparing(o -> o.patronymic);
        break;
      case AGE:
        comparator = Comparator.comparing(o -> o.age);
        break;
      case MIDDLE_BALANCE:
        comparator = Comparator.comparing(o -> o.middle_balance);
        break;
      case MIN_BALANCE:
        comparator = Comparator.comparing(o -> o.min_balance);
        break;
      case MAX_BALANCE:
        comparator = Comparator.comparing(o -> o.max_balance);
        break;
    }
    clientRecords.sort(comparator);
    return clientRecords;
  }

  private ClientDot getClient(int clientId) {
    List<ClientDot> clientDots = db.get().clientsStorage;
    for (ClientDot clientDot : clientDots)
      if (clientDot.id == clientId) return clientDot;
    return null;
  }
}
