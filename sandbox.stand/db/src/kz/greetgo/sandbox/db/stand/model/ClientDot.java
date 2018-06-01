package kz.greetgo.sandbox.db.stand.model;

import kz.greetgo.sandbox.controller.model.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class ClientDot {
  public int id;
  public String name;
  public String surname;
  public String patronymic;
  public Gender gender;
  public Date birth_day;
  public Charm charm;
  public ClientAddress[] client_addr;
  public ClientPhone[] client_phones;
  public ClientAccount[] client_accounts;

  public ClientRecords toClientRecords () {
    ClientRecords clientRecords = new ClientRecords();
    clientRecords.name = this.name;
    clientRecords.surname = this.surname;
    clientRecords.patronymic = this.patronymic;
    clientRecords.age = getAge();
    clientRecords.middle_balance = getMiddleBalance();
    clientRecords.max_balance = getMaxBalance();
    clientRecords.min_balance = getMinBalance();
    return clientRecords;
  }

  public ClientDetail toClientDetail() {
    ClientDetail clientDetail = new ClientDetail();
    clientDetail.name = this.name;
    clientDetail.surname = this.surname;
    clientDetail.patronymic = this.patronymic;
    clientDetail.birth_day = this.birth_day;
    clientDetail.charm = this.charm;
    clientDetail.clientAddresses = this.client_addr;
    clientDetail.clientPhones = this.client_phones;
    clientDetail.gender = this.gender;
    return clientDetail;
  }

  private int getAge() {
    LocalDate birth_day_local = birth_day.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate current_date_local = (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return Period.between(birth_day_local, current_date_local).getYears();
  }

  private float getMiddleBalance() {
    float middle_balance = 0;
    for (ClientAccount account : client_accounts) middle_balance += account.money;
    return middle_balance/client_accounts.length;
  }

  private float getMaxBalance() {
    float max_balance = -1;
    for (ClientAccount account : client_accounts)
      if (account.money > max_balance) max_balance = account.money;
    return max_balance;
  }

  private float getMinBalance() {
    float min_balance = -1;
    for (ClientAccount account : client_accounts)
      if (account.money < min_balance) min_balance = account.money;
    return min_balance;
  }
}
