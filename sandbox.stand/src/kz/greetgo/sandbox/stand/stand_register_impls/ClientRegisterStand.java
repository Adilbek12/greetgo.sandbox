package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.account.AccountRegister;
import kz.greetgo.sandbox.controller.register.client.ClientRegister;
import kz.greetgo.sandbox.db.stand.beans.StandDb;
import kz.greetgo.sandbox.db.stand.model.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static kz.greetgo.sandbox.stand.util.Constants.DUMB_ID;

@Bean
public class ClientRegisterStand implements ClientRegister {

  public BeanGetter<StandDb> db;
  public BeanGetter<AccountRegister> accountRegister;

  @Override
  public ClientInfoModel getClientInfo(int clientId) {
    ClientInfoModel clientInfoModel = new ClientInfoModel();

    if (clientId != DUMB_ID) {
      clientInfoModel.clientInfo = db.get().clientStorage.get(clientId).toClient();

      AddressDot factAddDot = getAddressDot(clientId, AddressType.FACT);
      if(factAddDot != null) {
        clientInfoModel.factAddress = factAddDot.toAddress();
      }

      AddressDot regAddDot = getAddressDot(clientId, AddressType.REG);
      if(regAddDot != null) {
        clientInfoModel.regAddress = regAddDot.toAddress();
      }

      clientInfoModel.phones = getPhones(clientId);
    }

    return clientInfoModel;
  }

  @Override
  public AccountInfo createNewClient(InfoForm createForm) {

//    int newClientId = db.get().clientStorage.size() + 1;
//    ClientDot newClient = new ClientDot(newClientId, name, surname, patronymic,
//      Gender.valueOf(gender), new Date(birthDate), charmId);
//
//    db.get().clientStorage.put(newClientId, newClient);
//
//    addNewAddress(db.get().addressStorage.size() + 1, newClient.id, AddressType.FACT, streetFact, houseFact, flatFact);
//    addNewAddress(db.get().addressStorage.size() + 1, newClient.id, AddressType.REG, streetReg, houseReg, flatReg);
//
//    addDefaultAccount(newClient.id);
//
//    createNewPhone(PhoneType.HOME, phoneHome, newClient.id);
//    createNewPhone(PhoneType.WORK, phoneWork, newClient.id);
//    createNewPhone(PhoneType.MOBILE, phoneMobile1, newClient.id);
//    createNewPhone(PhoneType.MOBILE, phoneMobile2, newClient.id);
//    createNewPhone(PhoneType.MOBILE, phoneMobile3, newClient.id);
//
//    AccountInfo newAccountInfo = new AccountInfo();
//
//    newAccountInfo.fullName = String.format("%s %s %s", name, surname, patronymic);
//    newAccountInfo.charm = db.get().charmStorage.get(charmId).name;
//    newAccountInfo.id = newClientId;
//
//    return newAccountInfo;
    return null;
  }

  @Override
  public AccountInfo editClient(InfoForm editForm) {

//    ClientDot clientDot = db.get().clientStorage.get(clientId);
//    if (clientDot == null) {
//      throw new NullPointerException("no such client id:" + clientId);
//    }
//
//    clientDot.name = name;
//    clientDot.surname = surname;
//    clientDot.patronymic = patronymic;
//    clientDot.birthDate = new Date(birthDate);
//    clientDot.gender = Gender.valueOf(gender);
//    clientDot.charmId = charmId;
//
//    updateAddress(AddressType.FACT, clientId, streetFact, houseFact, flatFact);
//    updateAddress(AddressType.REG, clientId, streetReg, houseReg, flatReg);
//
//    updatePhone(PhoneType.HOME, clientId, phoneHome, -1);
//    updatePhone(PhoneType.WORK, clientId, phoneWork, -1);
//    updatePhone(PhoneType.MOBILE, clientId, phoneMobile1, 0);
//    updatePhone(PhoneType.MOBILE, clientId, phoneMobile2, 1);
//    updatePhone(PhoneType.MOBILE, clientId, phoneMobile3, 2);
//
////  TODO: phone edit
//
//    return accountRegister.get().getAccountInfo(clientId);
    return null;
  }

  private void updatePhone(PhoneType type, int clientId, String number, int mobileIndex) {
    PhoneDot phone = getPhoneDot(type, clientId, mobileIndex);
    phone.number = number;
  }


  private void updateAddress(AddressType type, int clientId, String street, String house, String flat) {
    AddressDot addressDot = getAddressDot(clientId, type);

    if (addressDot == null) {
      addressDot = new AddressDot(db.get().addressStorage.size() + 1, clientId, type, street, house, flat);
      db.get().addressStorage.put(addressDot.id, addressDot);
      return;
    }

    addressDot.street = street;
    addressDot.house = house;
    addressDot.flat = flat;
  }


  @Override
  public AccountInfo deleteClient(int clientId) {
    Client client = db.get().clientStorage.get(clientId).toClient();
    AccountInfo accountInfo = new AccountInfo();

    if (client == null) {
      throw new NullPointerException("client does not exist id:" + clientId);
    }
    db.get().clientStorage.remove(clientId);

    removeAllAccounts(clientId);
    removeAllAddresses(clientId);
    removeAllPhones(clientId);

    accountInfo.id = clientId;
    return accountInfo;
  }

  private void createNewPhone(PhoneType type, String number, int clientId) {
    if (number == null || number.isEmpty() || clientId == DUMB_ID) {
      return;
//      throw new NullPointerException("add number clientId:" + clientId);
    }

    int newPhoneId = db.get().phoneStorage.size() + 1;
    db.get().phoneStorage.put(newPhoneId, new PhoneDot(newPhoneId, clientId, number, type));
  }


  private void addDefaultAccount(int clientId) {
    int newAccountId = db.get().accountStorage.size() + 1;

    db.get().accountStorage.put(newAccountId,
      new AccountDot(newAccountId, clientId, 0f, "KZT!@#$",
        new java.sql.Timestamp(Date.from(Instant.now()).getTime())));

  }

  private void addNewAddress(int id, int clientId, AddressType type, String street, String house, String flat) {
    db.get().addressStorage.put(id, new AddressDot(id, clientId, type, street, house, flat));
  }

  private void removeAllAddresses(int clientId) {
    db.get().addressStorage.values().removeIf(address -> address.clientId == clientId);
  }

  private void removeAllPhones(int clientId) {
    db.get().phoneStorage.values().removeIf(phone -> phone.clientId == clientId);
  }

  private void removeAllAccounts(int clientId) {
    db.get().accountStorage.values().removeIf(account -> account.clientId == clientId);
  }


  private AddressDot getAddressDot(int clientId, AddressType addressType) {
    for (AddressDot addressDot : db.get().addressStorage.values()) {
      if (addressDot.clientId == clientId && addressDot.addressType == addressType) {
        return addressDot;
      }
    }

    return null;
  }

  private List<Phone> getPhones(int clientId) {
    List<Phone> result = new ArrayList<>();

    for (PhoneDot phoneDot : db.get().phoneStorage.values()) {
      if (phoneDot.clientId == clientId)
        result.add(phoneDot.toPhone());
    }

    return result;
  }

  private PhoneDot getPhoneDot(PhoneType type, int clientId, int mobileIndex) {
    if(type != PhoneType.MOBILE) {
      for (PhoneDot phoneDot : db.get().phoneStorage.values()) {
        if (phoneDot.clientId == clientId && phoneDot.type == type) {

          return phoneDot;
        }
      }
    } else {
      int mobileCounter = 0;
      for (PhoneDot phoneDot : getMobilePhoneDots(clientId, mobileIndex)) {
        if (mobileCounter++ == mobileIndex) {
          return phoneDot;
        }
      }
    }
    throw new NullPointerException("no such phone. clientId" + clientId
      +", type:"+type+", mobileIndex:"+mobileIndex);
  }

  private List<PhoneDot> getMobilePhoneDots(int clientId, int mobileIndex) {
    List<PhoneDot> mobiles = new ArrayList<>();
    for (PhoneDot phoneDot : db.get().phoneStorage.values()) {
      if (phoneDot.clientId == clientId && phoneDot.type == PhoneType.MOBILE) {
        mobiles.add(phoneDot);
      }
    }

    return mobiles;
  }
}
