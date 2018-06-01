package kz.greetgo.sandbox.db.stand.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.db.stand.model.ClientDot;
import kz.greetgo.sandbox.db.stand.model.PersonDot;
import kz.greetgo.util.RND;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Bean
public class StandDb implements HasAfterInject {
  public final Map<String, PersonDot> personStorage = new HashMap<>();
  public final List<ClientDot> clientsStorage = new ArrayList<>();
  public final List<Charm> charms = new ArrayList<>();
  public final List<ClientPhone> phones = new ArrayList<>();
  public final List<ClientAddress> addresses = new ArrayList<>();
  public final List<ClientAccount> accounts = new ArrayList<>();

  Random random = new Random();

  @Override
  public void afterInject() throws Exception {
    appendCharms();
    appendAddresses();
    appendClientAccounts();

    appendClientInfoDotList();

    try (BufferedReader br = new BufferedReader(
      new InputStreamReader(getClass().getResourceAsStream("StandDbInitData.txt"), "UTF-8"))) {

      int lineNo = 0;

      while (true) {
        String line = br.readLine();
        if (line == null) break;
        lineNo++;
        String trimmedLine = line.trim();
        if (trimmedLine.length() == 0) continue;
        if (trimmedLine.startsWith("#")) continue;

        String[] splitLine = line.split(";");

        String command = splitLine[0].trim();
        switch (command) {
          case "PERSON":
            appendPerson(splitLine, line, lineNo);
            break;
          case "CLIENT_INFO":
            break;
          default:
            throw new RuntimeException("Unknown command " + command);
        }
      }
    }
  }

  @SuppressWarnings("unused")
  private void appendCharms() {
    for (int i = 0; i < 100; i++) {
      Charm charm = new Charm();
      charm.description = RND.intStr(10);
      charm.energy = random.nextFloat();
      charm.description = RND.intStr(20);
      charms.add(charm);
    }
  }

  @SuppressWarnings("unused")
  private void appendAddresses() {
    for (int i = 0; i < 100; i++) {
      ClientAddress clientAddress = new ClientAddress();
      clientAddress.type = 1 == random.nextInt(1) ? AddressType.FACT : AddressType.REG;
      clientAddress.street = RND.intStr(5);
      clientAddress.house = RND.intStr(5);
      clientAddress.flat = RND.intStr(5);
      addresses.add(clientAddress);
    }
  }

  @SuppressWarnings("unused")
  private void appendPhones() {
    for(int i = 0; i < 100; i++) {
      ClientPhone clientPhone = new ClientPhone();
      clientPhone.type = 1 == random.nextInt(1) ? PhoneType.HOME : PhoneType.MOBILE;
      clientPhone.number = RND.intStr(10);
      phones.add(clientPhone);
    }
  }

  @SuppressWarnings("unused")
  private void appendClientAccounts() {
    for (int i = 0; i < 100; i++) {
      ClientAccount clientAccount = new ClientAccount();
      clientAccount.money = random.nextFloat()*random.nextInt(10000);
      clientAccount.number = RND.intStr(10);
      clientAccount.registered_at = null;
      accounts.add(clientAccount);
    }
  }

  @SuppressWarnings("unused")
  private void appendClientInfoDotList() {
    for (int i = 0; i < 100; i++) {
      ClientDot clientDot = new ClientDot();
      clientDot.id = i;
      clientDot.name = RND.str(10);
      clientDot.surname = RND.str(10);
      clientDot.gender = Gender.MALE;
      clientDot.patronymic = RND.str(10);
      clientDot.birth_day = RND.dateYears(1900, 2018);
      clientDot.charm = charms.get(random.nextInt(charms.size()));
      clientDot.client_addr = new ClientAddress[2];
      clientDot.client_addr[0] = addresses.get(random.nextInt(addresses.size()));
      clientDot.client_addr[1] = addresses.get(random.nextInt(addresses.size()));
      clientDot.client_phones = new ClientPhone[5];
      clientDot.client_phones[0] = phones.get(random.nextInt(phones.size()));
      clientDot.client_phones[1] = phones.get(random.nextInt(phones.size()));
      clientDot.client_phones[2] = phones.get(random.nextInt(phones.size()));
      clientDot.client_phones[3] = phones.get(random.nextInt(phones.size()));
      clientDot.client_phones[4] = phones.get(random.nextInt(phones.size()));
      clientsStorage.add(clientDot);
    }
  }

  @SuppressWarnings("unused")
  private void appendPerson(String[] splitLine, String line, int lineNo) {
    PersonDot p = new PersonDot();
    p.id = splitLine[1].trim();
    String[] ap = splitLine[2].trim().split("\\s+");
    String[] fio = splitLine[3].trim().split("\\s+");
    p.accountName = ap[0];
    p.password = ap[1];
    p.surname = fio[0];
    p.name = fio[1];
    if (fio.length > 2) p.patronymic = fio[2];
    personStorage.put(p.id, p);
  }
}
