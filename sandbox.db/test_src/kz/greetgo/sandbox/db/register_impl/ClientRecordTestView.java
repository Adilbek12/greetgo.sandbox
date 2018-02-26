package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.report.ClientRecordView;

import java.util.ArrayList;
import java.util.List;

public class ClientRecordTestView implements ClientRecordView {
  public String[] headers;
  public List<ClientRecord> rows = new ArrayList<>();

  @Override
  public void start(String[] headers) throws Exception {
    this.headers = headers;
  }

  @Override
  public void appendRow(ClientRecord record) throws Exception {
    this.rows.add(record);
  }

  @Override
  public void finish() throws Exception {

  }
}
