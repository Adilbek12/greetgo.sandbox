package kz.greetgo.sandbox.controller.model;

import java.util.ArrayList;

public class ClientInfo {
  public String id;
  public String name;
  public String surname;
  public Sex sex;
  public int age;
  public float balance;
  public float maxBalance;
  public float minBalance;
  public ClientAddress homeAddress;
  public ClientAddress registrationAddress;
  public ArrayList<ClientPhone> clientPhones;
}
