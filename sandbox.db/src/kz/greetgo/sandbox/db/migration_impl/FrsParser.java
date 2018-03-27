package kz.greetgo.sandbox.db.migration_impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import kz.greetgo.sandbox.db.migration_impl.model.Account;
import kz.greetgo.sandbox.db.migration_impl.model.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class FrsParser {

  private InputStream inputStream;
  private FrsTableWorker frsTableWorker;

  public FrsParser(InputStream inputStream, FrsTableWorker frsTableWorker) {
    this.inputStream = inputStream;
    this.frsTableWorker = frsTableWorker;
  }

  public int parseAndSave() throws IOException {
    int recordsCount = 0;
    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    String line;
    while ((line = br.readLine()) != null) {
      Account account = new Account();
      Transaction transaction = new Transaction();

      JsonParser jsonParser = new JsonFactory().createParser(line);

      //Skip START_OBJECT
      jsonParser.nextToken();

      //loop through the JsonTokens
      String type = null;
      while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
        String name = jsonParser.getCurrentName();
        switch (name.trim()) {
          case "type":
            jsonParser.nextToken();
            type = jsonParser.getText();
            account.type = type;
            transaction.type = type;
            break;
          case "client_id":
            jsonParser.nextToken();
            account.clientId = jsonParser.getText();
            break;
          case "account_number":
            jsonParser.nextToken();
            account.account_number = jsonParser.getText();
            transaction.account_number = jsonParser.getText();
            break;
          case "registered_at":
            jsonParser.nextToken();
            account.registeredAt = jsonParser.getText();
            break;
          case "money":
            jsonParser.nextToken();
            transaction.money = new BigDecimal(jsonParser.getText().replace("_", ""));
            break;
          case "finished_at":
            jsonParser.nextToken();
            transaction.finishedAt = jsonParser.getText();
            break;
          case "transaction_type":
            jsonParser.nextToken();
            transaction.transaction_type = jsonParser.getText();
            break;
        }
      }
      if ("transaction".equals(type)) frsTableWorker.addToBatch(transaction);
      else if ("new_account".equals(type)) frsTableWorker.addToBatch(account);
      jsonParser.close();
      recordsCount++;
    }
    return recordsCount;
  }
}