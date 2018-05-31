package kz.greetgo.sandbox.controller.model;

public class ClientAddress {
  AddressType type;
  String street;
  String house;
  String apartment;

  public ClientAddress () {}

  public ClientAddress (AddressType type, String street, String house, String apartment) {
    this.type = type;
    this.street = street;
    this.house = house;
    this.apartment = apartment;
  }
}