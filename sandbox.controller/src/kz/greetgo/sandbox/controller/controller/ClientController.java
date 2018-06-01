package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.sandbox.controller.model.ClientDetail;
import kz.greetgo.sandbox.controller.model.ClientRecords;
import kz.greetgo.sandbox.controller.model.ClientToSave;
import kz.greetgo.sandbox.controller.model.SortBy;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.List;


@Bean
@Mapping("/client")
public class ClientController implements Controller {

  public BeanGetter<ClientRegister> clientRegister;

  @ToJson
  @Mapping("/detail")
  public ClientDetail detail(@Par("clientId") int clientId) {
    return clientRegister.get().getClientDetail(clientId);
  }

  @ToJson
  @Mapping("/save")
  void save(@Par("clientToSave") ClientToSave clientToSave) {
    clientRegister.get().saveClient(clientToSave);
  }

  @ToJson
  @Mapping("/remove")
  void remove(@Par("clientId") int clientId) {
    clientRegister.get().removeClient(clientId);
  }

  @ToJson
  @Mapping("/recordsCount")
  int recordsCount() {
    return clientRegister.get().getClientRecordsCount();
  }

  @ToJson
  @Mapping("/records")
  List<ClientRecords> records(@Par("from") int from, @Par("to") int to) {
    return clientRegister.get().getClientRecords(from, to);
  }

  @ToJson
  @Mapping("/sortedRecords")
  List<ClientRecords> records(@Par("from") int from, @Par("to") int to, @Par("sortBy") SortBy sortBy) {
    return clientRegister.get().getClientRecords(from, to, sortBy);
  }
}
