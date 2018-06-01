package kz.greetgo.sandbox.controller.model;

import java.util.Date;

public class ClientToSave {
  public int id;
  public String surname;
  public String name;
  public String patronymic;
  public Gender gender;
  public Date birth_day;
  public Charm charm;
  public ClientAddress[] clientAddresses;
  public ClientPhone[] clientPhones;
}
