import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from "@angular/core";
import {HttpService} from "../HttpService";
import {ClientToSave} from "../../model/ClientToSave";
import {Charm} from "../../model/Charm";
import {Gender} from "../../model/Gender";

@Component({
  selector: 'client-info-form-component',
  template: require('./client_info_form.component.html'),
  styles: [require('./client_info_form.component.css')],
})
export class ClientInfoFormComponent implements OnInit{
  @Input() clientId: number;
  @Output() onClose = new EventEmitter<boolean>();

  @ViewChild('birthDayInput') birthDayInput: ElementRef;

  title = "Новый клиент";
  buttonTitle = "Добавить";

  charms: Array<Charm> = new Array<Charm>();
  wrongMessageEnable: boolean = false;
  clientToSave: ClientToSave = new ClientToSave();

  constructor(private httpService: HttpService) {
    this.loadCharms();

  }

  ngOnInit(): void {
    if (this.clientId != null) {
      this.title = "Изменить данные клиента";
      this.buttonTitle = "Изменить";
      let clientId = this.clientId as number;
      this.httpService.get("/client/detail", {"clientId": clientId}).toPromise().then(result => {
        this.clientToSave = ClientToSave.copy(result.json());
        console.log(this.clientToSave);
      })
    }
    else {
      this.clientToSave = new ClientToSave();
      this.clientToSave.gender = Gender.MALE;
      this.clientToSave.charmId = 1;
    }
    this.setMaxDate();
  }

  loadCharms() {
    this.httpService.get("/client/getCharms").toPromise().then(result => {
      for (let res of result.json())
        this.charms.push(Charm.copy(res));
      console.log(this.charms);
    })
  }

  checkClient(): boolean {
    let re1 = /^[a-zA-Z_]*$/;
    let re2 = /^[a-zA-Z0-9_]*$/;
    return this.checkData(this.clientToSave.addressReg) &&
      this.clientToSave.addressReg.house.match(re2) &&
      this.clientToSave.addressReg.flat.match(re2) &&
      this.clientToSave.addressReg.street.match(re2) &&
      this.checkClientPhones() &&
      this.checkData(this.clientToSave.name) &&
      this.clientToSave.name.match(re1) != null &&
      this.checkData(this.clientToSave.surname) &&
      this.clientToSave.surname.match(re1) != null &&
      this.checkData(this.clientToSave.birth_day) &&
      this.checkData(this.clientToSave.gender) &&
      this.checkClientPhones();
  }

  checkData(element: any): boolean {
    return (element != null && element != "")
  }

  checkClientPhones(): boolean {
    let re = /^[0-9]{8}[0-9]*$/;
    return this.checkData(this.clientToSave.mobilePhone.number) &&
      this.clientToSave.mobilePhone.number.match(re)!=null &&
      this.checkData(this.clientToSave.workPhone.number) &&
      this.clientToSave.workPhone.number.match(re)!=null &&
      this.checkData(this.clientToSave.homePhone.number) &&
      this.clientToSave.homePhone.number.match(re)!=null;
  }

  saveButtonClicked () {
    if (this.checkClient()) {
      this.wrongMessageEnable = false;
      this.saveClient();
    }
    else {
      this.wrongMessageEnable = true;
    }
  }

  saveClient() {
    this.httpService.post("/client/save", {
      "clientToSave": JSON.stringify(this.clientToSave)
    }).toPromise().then(result => {
      this.onClose.emit(true);
    })
  }

  closeButtonClicked(clientSaved: boolean) {
    this.onClose.emit(clientSaved);
  }

  setBDate(dateText) {
    this.clientToSave.birth_day = new Date(dateText);
  }

  setMaxDate() {
    let today = new Date();
    let dd:string = today.getDate().toString();
    let mm:string = (today.getMonth()+1).toString(); //January is 0!
    let yyyy: string = today.getFullYear().toString();
    if(dd.length<10){
      dd='0'+dd
    }
    if(mm.length<10){
      mm='0'+mm
    }

    this.birthDayInput.nativeElement.setAttribute("max", yyyy+'-'+mm+'-'+dd);
  }
}