import {Component, EventEmitter, Output} from "@angular/core";
import {UserInfo} from "../../model/UserInfo";
import {HttpService} from "../HttpService";
import {PhoneType} from "../../model/PhoneType";
import {ClientInfo} from "../../model/ClientInfo";
import {ClientInfoView} from "../../model/ClientInfoView";

@Component({
  selector: 'main-form-component',
  template: require('./main_form.component.html'),
  styles: [require('./main_form.component.css')],
})

export class MainFormComponent {
  @Output() exit = new EventEmitter<void>();

  currentPage: number = 1;
  pagesSize: number = 0;

  userInfo: UserInfo | null = null;
  clientInfoViewList: ClientInfoView[] | null = null;

  loadUserInfoButtonEnabled: boolean = false;
  loadClientInfoListButtonEnable: boolean = false;

  constructor(private httpService: HttpService) {}

  switchBetweenUserAndClient (client: boolean) {
    this.loadUserInfoButtonEnabled = !client;
    this.loadClientInfoListButtonEnable = client;

    if (client) {
      this.userInfo = null;
      this.loadClientInfoList();
    } else {
      this.clientInfoViewList = null;
      this.loadUserInfo();
    }

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

  loadClientInfoList() {
    this.loadPage(this.currentPage);
    this.loadPagesSize();
  }

  paginationPageButtonClicked(page: number) {
    if (this.currentPage == page) return;
    this.currentPage = page;
    this.loadPage(page)
  }

  loadPage (page: number) {
    this.clientInfoViewList = [];
    this.httpService.post("/auth/clientInfoListViewPart", {"from": page*10-10,"to": page*10 }).toPromise().then(result => {
      for (let res of result.json()) {
        this.clientInfoViewList.push(ClientInfo.copy(res))
      }
    })
  }

  loadPagesSize () {
    this.httpService.get("/auth/totalClientsNumber").toPromise().then(result => {
      this.pagesSize = Math.ceil((result.json() as number)/10);
      if (this.pagesSize < this.currentPage) {
        this.currentPage -= 1;
        this.loadPage(this.currentPage);
      }
    });
  }

  deleteClientInfoByIndex (clientInfo: ClientInfo) {
    let ID = clientInfo.id;

    this.httpService.post("/auth/deleteClientInfo", {"ID": ID}).toPromise().then(result => {
      this.loadPagesSize();
      this.loadPage(this.currentPage);
    });
  }

  openAddNewClientModal() {
    document.getElementById("myModal").style.display="block"
  }
}