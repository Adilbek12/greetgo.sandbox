package kz.greetgo.sandbox.db.stand.model;

import kz.greetgo.sandbox.controller.model.*;

import java.util.ArrayList;

public class ClientInfoDot {

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

  public ClientInfo toClientInfo() {
    ClientInfo ret = new ClientInfo();
    ret.id = id;
    ret.name = name;
    ret.surname = surname;
    ret.sex = sex;
    ret.age = age;
    ret.balance = balance;
    ret.maxBalance = balance;
    ret.minBalance = minBalance;
    ret.homeAddress = homeAddress;
    ret.registrationAddress = registrationAddress;
    ret.clientPhones = clientPhones;
    return ret;
  }

  public ClientInfoView toClientInfoView() {
    ClientInfoView ret = new ClientInfoView();
    ret.id = id;
    ret.name = name;
    ret.surname = surname;
    ret.age = age;
    ret.balance = balance;
    ret.maxBalance = balance;
    ret.minBalance = minBalance;
    return ret;
  }
}
