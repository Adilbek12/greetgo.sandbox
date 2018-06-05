import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
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
  @Output() onClose = new EventEmitter<void>();

  charms: Array<Charm> = new Array<Charm>();
  wrongMessageEnable: boolean = false;
  clientToSave: ClientToSave = new ClientToSave();

  constructor(private httpService: HttpService) {
    this.loadCharms();
  }

  ngOnInit(): void {
    console.log("NG ON INIT | CLIENT TO SAVE ID= " + this.clientId);
    if (this.clientId != -1) {
      this.httpService.post("/client/get", {"clientId": this.clientId}).toPromise().then(result => {
        this.clientToSave = ClientToSave.copy(result.json());
      })
    }
    else
      this.clientToSave = new ClientToSave();
  }

  genderSelected(selectedIndex: number) {
    switch (selectedIndex) {
      case 1:
        this.clientToSave.gender = Gender.FEMALE;
        break;
      case 0:
        this.clientToSave.gender = Gender.MALE;
        break;
    }
  }

  loadCharms() {
    this.httpService.get("/client/getCharms").toPromise().then(result => {
      for (let res of result.json())
        this.charms.push(Charm.copy(res));
    })
  }

  checkClient(): boolean {
    return this.checkData(this.clientToSave.addressReg) &&
      this.checkClientPhones() &&
      this.checkData(this.clientToSave.name) &&
      this.checkData(this.clientToSave.surname) &&
      this.checkData(this.clientToSave.birth_day) &&
      this.checkData(this.clientToSave.gender) &&
      this.checkClientPhones();
  }

  checkData(element: any): boolean {
    return (element != null && element != "")
  }

  checkClientPhones(): boolean {
    return this.checkData(this.clientToSave.mobilePhone.number) &&
      this.checkData(this.clientToSave.workPhone.number) &&
      this.checkData(this.clientToSave.homePhone.number);
  }

  charmSelected() {
    console.log("CHARM SELECTED!");
  }

  add() {
    console.log("CHECK!");
    if (this.checkClient()) {
      console.log("SUCCESS!");
      this.wrongMessageEnable = false;
      this.saveClient();
    }
    else {
      console.log("FAILED!");
      this.wrongMessageEnable = true;
    }
  }

  saveClient() {
    this.httpService.post("/client/save", {
      "clientToSave": JSON.stringify(this.clientToSave)
    }).toPromise().then(result => {
      this.close()
    })
  }

  close() {
    this.onClose.emit();
  }
}