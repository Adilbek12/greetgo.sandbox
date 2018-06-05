import {Component, EventEmitter, Output} from "@angular/core";
import {UserInfo} from "../../model/UserInfo";
import {HttpService} from "../HttpService";
import {PhoneType} from "../../model/PhoneType";


@Component({
  selector: 'main-form-component',
  template: require('./main_form.component.html'),
  styles: [require('./main_form.component.css')],
})

export class MainFormComponent {
  @Output() exit = new EventEmitter<void>();

  userInfo: UserInfo | null = null;

  loadUserInfoButtonEnabled: boolean = false;
  loadClientInfoListButtonEnable: boolean = false;

  editClientId: number = -1;
  clientInfoFormComponentEnable: boolean = false;

  constructor(private httpService: HttpService) {
    this.loadUserInfo();
  }

  switchBetweenUserAndClient (client: boolean) {
    this.loadUserInfoButtonEnabled = !client;
    this.loadClientInfoListButtonEnable = client;
  }

  loadUserInfo() {

    this.httpService.get("/auth/userInfo").toPromise().then(result => {
      this.userInfo = UserInfo.copy(result.json());
      let phoneType: PhoneType | null = this.userInfo.phoneType;
      console.log(phoneType);
    }, error => {
      console.log(error);
      this.userInfo = null;
    });
  }

  openAddNewClientModal() {
    this.editClientId = -1;
    this.clientInfoFormComponentEnable = true;
  }

  close() {
    this.editClientId = -1;
    this.clientInfoFormComponentEnable = false;
    this.switchBetweenUserAndClient(false);
  }

  editClient(clientId: number) {
    this.editClientId = clientId;
    this.clientInfoFormComponentEnable = true;
  }
}