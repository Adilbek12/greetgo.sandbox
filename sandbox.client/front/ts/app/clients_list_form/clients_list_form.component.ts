import {Component, ElementRef, EventEmitter, Output, ViewChild} from "@angular/core";
import {HttpService} from "../HttpService";
import {Page} from "../../model/Page";
import {ClientRecords} from "../../model/ClientRecords";
import {SortBy} from "../../model/SortBy";
import {PageParam} from "../../model/PageParam";
import {SortDirection} from "../../model/SortDirection";

@Component({
  selector: 'clients-list-form-component',
  template: require('./clients_list_form.component.html'),
  styles: [require('./clients_list_form.component.css')],
})

export class ClientsListFormComponent {

  @Output() editClient = new EventEmitter<number>();
  @ViewChild('myModule') module: ElementRef;
  @ViewChild('searchInput') searchInput: ElementRef;

  page: Page = new Page();
  pageParam: PageParam = new PageParam();
  currentPage: number = 1;
  sortIndex: number = -1;
  numberOfItemInPage: number = 10;

  constructor(private httpService: HttpService) {
    this.loadClientRecordsList(this.currentPage);
  }

  loadClientRecordsList(page: number) {
    this.currentPage = page;
    this.pageParam.from = this.currentPage*this.numberOfItemInPage - this.numberOfItemInPage;
    this.pageParam.to = this.currentPage*this.numberOfItemInPage;
    this.loadPage();
  }

  paginationPageButtonClicked(page: number) {
    if (this.currentPage == page) return;
    this.loadClientRecordsList(page);
  }

  loadPage() {

    this.httpService.post("/client/records", {
      "page":JSON.stringify(this.pageParam)
    }).toPromise().then(result => {
      this.page = Page.copy(result.json());
    })
  }

  filterPage() {
    let search_text: string = this.searchInput.nativeElement.value;
    if (search_text == "") this.pageParam.filter = null;
    else this.pageParam.filter = search_text;
    this.currentPage = 1;
    this.loadClientRecordsList(this.currentPage);
  }

  sortPage(index: number) {

    if (this.sortIndex == index) {
      this.sortIndex = index+100;
      this.pageParam.sortDirection = SortDirection.DESCENDINGLY;
      this.loadPage();
      return;
    }
    else if (this.sortIndex == index+100) {
      this.sortIndex = -1;
      this.pageParam.sortBy = SortBy.NONE;
      this.pageParam.sortDirection = SortDirection.NONE;
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
    this.pageParam.sortDirection = SortDirection.ASCENDING;
    this.pageParam.sortBy = sortBy;
    this.loadPage();
  }

  pagesCount(): number {
    let pagesCount = Math.ceil(this.page.pagesCount / this.numberOfItemInPage);
    if (this.currentPage > pagesCount) {
      this.currentPage = pagesCount;
      this.loadClientRecordsList(this.currentPage);
    }
    return pagesCount;
  }

  deleteClientInfoById (clientRecords: ClientRecords) {
    let ID = clientRecords.id;
    this.httpService.post("/client/remove", {
      "clientId": ID
    }).toPromise().then(() => {
      this.loadPage();
    });
  }

  range(from: number, to: number, step: number) {
    let rangeList: number[] = new Array<number>();
    let i = from;
    while (i < to) {
      rangeList.push(i);
      i += step;
    }
    return rangeList;
  }

  numberOfItemInPageSelected() {
    this.numberOfItemInPage = +(document.getElementById("4324") as HTMLSelectElement).value;
    this.loadClientRecordsList(this.currentPage);
  }

  editClientButtonClicked(client: ClientRecords) {
    this.editClient.emit(client.id);
  }
}