import {Component, EventEmitter, Output} from "@angular/core";
import {UserInfo} from "../../model/UserInfo";
import {HttpService} from "../HttpService";


@Component({
  selector: 'main-form-component',
  template: require('./main_form.component.html'),
  styles: [require('./main_form.component.css')],
})

export class MainFormComponent {
  @Output() exit = new EventEmitter<void>();

  userInfo: UserInfo | null = null;

  loadUserInfoButtonEnabled: boolean = false;
  clientInfoFormComponentEnable: boolean = false;
  loadClientInfoListButtonEnable: boolean = false;

  editClientId: number | null = null;

  constructor(private httpService: HttpService) {
    this.loadUserInfo();
  }

  switchBetweenUserAndClientButtonClicked (client: boolean) {
    this.loadUserInfoButtonEnabled = !client;
    this.loadClientInfoListButtonEnable = client;
  }

  loadUserInfo() {
    this.httpService.get("/auth/userInfo").toPromise().then(result => {
      this.userInfo = UserInfo.copy(result.json());
    }, error => {
      this.userInfo = null;
    });
  }

  openAddNewClientModal() {
    this.editClientId = null;
    this.clientInfoFormComponentEnable = true;
  }

  close(listEdited: boolean) {
    this.editClientId = null;
    this.clientInfoFormComponentEnable = false;
    if (listEdited==true) {
      this.switchBetweenUserAndClientButtonClicked(false);
    }
  }

  editClient(clientId: number) {
    this.editClientId = clientId;
    this.clientInfoFormComponentEnable = true;
  }
}