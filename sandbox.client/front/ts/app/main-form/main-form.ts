import {Component, EventEmitter, OnDestroy, Output} from "@angular/core";
import {UserInfo} from "../../model/UserInfo";
import {HttpService} from "../HttpService";
import {PhoneType} from "../../model/PhoneType";
import {AccountService} from "../services/AccountService";
import {Subscription} from "rxjs/Subscription";
import random = require("core-js/fn/number/random");
import {Charm} from "../../model/Charm";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {ModalInfoComponent} from "./components/modal-info/modal-info";

@Component({
  selector: 'main-form-component',
  template: require('./main-form.html'),
  styles: [require('./main-form.css')]
})
export class MainFormComponent implements OnDestroy{

  @Output() exit = new EventEmitter<void>();
  subscription:Subscription;

  userInfo: UserInfo | null = null;

  loadUserInfoButtonEnabled: boolean = true;
  loadUserInfoError: string | null;

  isEditMode = false;

  constructor(private httpService: HttpService, private dialog: MatDialog) { }

  handleAddAccClick = function() {
    console.log("main handle add");
    this.openModal();
  };

  handleEditAccClick = function(accountInfo) {
    console.log("main handle edit");
    console.log(accountInfo.id);
    this.openModal(accountInfo);
  };

  openModal(accountInfo) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {accountInfo: accountInfo};

    this.dialog.open(ModalInfoComponent, dialogConfig);
  }

  loadUserInfoButtonClicked() {
    this.loadUserInfoButtonEnabled = false;
    this.loadUserInfoError = null;

    this.httpService.get("/auth/userInfo").toPromise().then(result => {
      this.userInfo = UserInfo.copy(result.json());
      let phoneType: PhoneType | null = this.userInfo.phoneType;
      console.log(phoneType);
    }, error => {
      console.log(error);
      this.loadUserInfoButtonEnabled = true;
      this.loadUserInfoError = error;
      this.userInfo = null;
    });
  }

  checkHealthButtonClicked() {
    this.httpService.get("/accounts/ok").toPromise().then(response => {
      console.log(response)
    }, error => {
      console.log(error)
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
