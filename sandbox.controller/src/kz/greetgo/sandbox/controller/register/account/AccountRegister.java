package kz.greetgo.sandbox.controller.register.account;

import kz.greetgo.sandbox.controller.model.AccountInfo;
import kz.greetgo.sandbox.controller.model.Client;

import java.util.List;

public interface AccountRegister {
  List<AccountInfo> getAllAccountInfo();
  AccountInfo getAccountInfo(int clientId);
}
