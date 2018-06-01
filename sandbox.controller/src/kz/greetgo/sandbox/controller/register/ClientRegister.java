package kz.greetgo.sandbox.controller.register;


import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.sandbox.controller.model.ClientDetail;
import kz.greetgo.sandbox.controller.model.ClientRecords;
import kz.greetgo.sandbox.controller.model.ClientToSave;
import kz.greetgo.sandbox.controller.model.SortBy;

import java.util.List;

/**
 * Работа с клиентами
 */
public interface ClientRegister {

  /**
   * Предоставляет детальную информацию о клиенте
   *
   * @param clientId идентификатор клиента
   * @return детальная информация о клиенте
   */
  ClientDetail getClientDetail(int clientId);

  /**
   * Сохраняет или изменяет информацию о клиенте
   *
   * @param clientToSave моделька для сохранения клиента
   */
  void saveClient(@Par("clientToSave") ClientToSave clientToSave);

  /**
   * Удалает клиент из списка
   *
   * @param clientId идентификатор клиента
   */
  void removeClient(@Par("clientId") int clientId);

  /**
   * Дает общее количество клиентов
   *
   * @return количество клиентов
   */
  int getClientRecordsCount();

  /**
   * Дает отрезок списка клиентов (для пагинаций)
   *
   * @param from от индекса
   * @param to до индекса
   * @return документаций о клиентах
   */
  List<ClientRecords> getClientRecords(@Par("from") int from, @Par("to") int to);

  /**
   * Дает сортированый отрезок списка клиентов (для пагинаций)
   *
   * @param from от индекса
   * @param to до индекса
   * @param sortBy сортировка по параметру
   * @return документаций о клиентах
   */
  List<ClientRecords> getClientRecords(@Par("from") int from, @Par("to") int to, @Par("sortBy") SortBy sortBy);
}
