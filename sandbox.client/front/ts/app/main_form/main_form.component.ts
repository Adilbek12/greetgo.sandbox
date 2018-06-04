import {Component, ElementRef, EventEmitter, Output, ViewChild} from "@angular/core";
import {UserInfo} from "../../model/UserInfo";
import {HttpService} from "../HttpService";
import {PhoneType} from "../../model/PhoneType";
import {ClientRecords} from "../../model/ClientRecords";
import {SortBy} from "../../model/SortBy";
import {PageParam} from "../../model/PageParam";
import {Page} from "../../model/Page";


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

  page: Page = new Page();
  pageParam: PageParam = new PageParam();
  currentPage: number = 1;
  sortIndex: number = -1;
  numberOfItemInPage: number = 10;

  @ViewChild("modal") modal: ElementRef;

  constructor(private httpService: HttpService) {
  }

  switchBetweenUserAndClient (client: boolean) {
    this.loadUserInfoButtonEnabled = !client;
    this.loadClientInfoListButtonEnable = client;

    if (client) {
      this.userInfo = null;
      this.loadClientRecordsList();
    }
    else {
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

  loadClientRecordsList() {
    this.pageParam.from = 0;
    this.pageParam.to = 10;
    this.currentPage = 1;
    this.loadPage();
  }

  paginationPageButtonClicked(page: number) {
    if (this.currentPage == page) return;
    this.currentPage = page;
    this.pageParam.from = this.currentPage*this.numberOfItemInPage - this.numberOfItemInPage;
    this.pageParam.to = this.currentPage*this.numberOfItemInPage;
    this.loadPage()
  }

  loadPage() {

    this.httpService.post("/client/records", {
      "page":JSON.stringify(this.pageParam)
    }).toPromise().then(result => {
      this.page = Page.copy(result.json());
    })
  }

  filterPage() {
    let search_text = (document.getElementById("search_input") as HTMLInputElement).value;
    if (search_text == "") this.pageParam.filter = null;
    else this.pageParam.filter = search_text;
    this.loadPage()
  }

  sortPage(index: number) {

    if (this.sortIndex == index) {
      this.sortIndex = -1;
      this.pageParam.sortBy = SortBy.NONE;
      this.loadPage();
      return;
    }

    let sortBy: SortBy;
    switch (index) {
      case 0: sortBy = SortBy.NAME;
        break;
      case 1: sortBy = SortBy.SURNAME;
        break;
      case 2: sortBy = SortBy.AGE;
        break;
      case 3: sortBy = SortBy.MIDDLE_BALANCE;
        break;
      case 4: sortBy = SortBy.MAX_BALANCE;
        break;
      case 5: sortBy = SortBy.MIN_BALANCE;
        break;
    }

    this.sortIndex = index;
    this.pageParam.sortBy = sortBy;
    this.loadPage();
  }

  pagesCount(): number {
    return Math.ceil(this.page.pagesCount / this.numberOfItemInPage);
  }

  deleteClientInfoById (clientRecords: ClientRecords) {
    let ID = clientRecords.id;
    this.httpService.post("/client/delete", {
      "clientId": ID
    }).toPromise().then(() => {
      this.loadPage();
    });
  }

  editClient(client: ClientRecords) {
    document.getElementById("myModal").style.display="block";
  }

  openAddNewClientModal() {
    document.getElementById("myModal").style.display="block";
  }
}