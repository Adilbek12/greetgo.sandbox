package kz.greetgo.sandbox.db.register_impl.jdbc.callback;

import kz.greetgo.sandbox.controller.model.ClientFilter;
import kz.greetgo.sandbox.db.register_impl.jdbc.SqlExecuteConnection;

public abstract class ClientFilterCallback<ConnectionReturnType, RsReturnType> extends SqlExecuteConnection<ConnectionReturnType, RsReturnType> {

  ClientFilter filter;

  @Override
  public void update() {}

  @Override
  public void set() {}

  @Override
  public void insert() {}

  @Override
  public void values() {}

  @Override
  public void where() {
    sql.append("WHERE client.actual=1 ");
    if (filter.fio != null) {
      if (!filter.fio.isEmpty()) {
        sql.append("AND (client.name LIKE ? OR client.surname LIKE ? OR client.patronymic LIKE ?) ");
        params.add("%" + filter.fio + "%");
        params.add("%" + filter.fio + "%");
        params.add("%" + filter.fio + "%");
      }
    }
  }
}
