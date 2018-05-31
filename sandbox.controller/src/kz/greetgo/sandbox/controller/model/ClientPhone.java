package kz.greetgo.sandbox.controller.model;

public class ClientPhone {
  PhoneType type;
  String number;

  public ClientPhone() {}

  public ClientPhone(PhoneType type, String number) {
    this.type = type;
    this.number = number;
  }
}