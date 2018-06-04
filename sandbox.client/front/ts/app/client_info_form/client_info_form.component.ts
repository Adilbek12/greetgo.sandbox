import {Component} from "@angular/core";
import {HttpService} from "../HttpService";
import {ClientAddress} from "../../model/ClientAddress";
import {AddressType} from "../../model/AddressType";
import {ClientPhone} from "../../model/ClientPhone";
import {PhoneType} from "../../model/PhoneType";
import {Gender} from "../../model/Gender";
import {ClientToSave} from "../../model/ClientToSave";
import {Charm} from "../../model/Charm";

@Component({
  selector: 'client-info-form-component',
  template: require('./client_info_form.component.html'),
  styles: [require('./client_info_form.component.css')],
})

export class ClientInfoFormComponent {

  charms: Charm[] = Charm.getRandomCharms();

  nameInput = this.getElementById("name");
  surnameInput = this.getElementById("surname");
  patronymicInput = this.getElementById("patronymic");
  birthDayInput = this.getElementById("birth_day");
  charmInput = (document.getElementById("charm") as HTMLSelectElement);

  raStreetInput = this.getElementById("ra_street");
  raHouseInput = this.getElementById("ra_house");
  raFlatInput = this.getElementById("ra_flat");

  haStreetInput = this.getElementById("ha_street");
  haHouseInput = this.getElementById("ha_house");
  haFlatInput = this.getElementById("ha_flat");

  homePhoneInput = this.getElementById("home_phone");
  workPhoneInput = this.getElementById("work_phone");
  mobilePhone1Input = this.getElementById("mobile_phone1");
  mobilePhone2Input = this.getElementById("mobile_phone2");
  mobilePhone3Input = this.getElementById("mobile_phone3");

  constructor(private httpService: HttpService) {}

  close() {
    document.getElementById("myModal").style.display = "none"
  }

  getElementById(id: string): HTMLInputElement {
    return (document.getElementById("surname") as HTMLInputElement);
  }

  add() {

    let gender: Gender;
    if ((document.getElementById("male") as HTMLInputElement).checked) {
      gender = Gender.MALE;
    } else if ((document.getElementById("female") as HTMLInputElement).checked) {
      gender = Gender.FEMALE;
    }


    let registrationAddress = new ClientAddress();
    registrationAddress.street = this.raStreetInput.value;
    registrationAddress.house = this.raHouseInput.value;
    registrationAddress.flat = this.raFlatInput.value;
    registrationAddress.type = AddressType.REG;

    let homeAddress = new ClientAddress();
    homeAddress.street = this.haStreetInput.value;
    homeAddress.house = this.haHouseInput.value;
    homeAddress.flat = this.haFlatInput.value;
    homeAddress.type = AddressType.FACT;

    let homePhone = new ClientPhone();
    homePhone.number = this.homePhoneInput.value;
    homePhone.type = PhoneType.HOME;

    let workPhone = new ClientPhone();
    workPhone.number = this.workPhoneInput.value;
    workPhone.type = PhoneType.WORK;

    let mobilePhone1 = new ClientPhone();
    mobilePhone1.number = this.mobilePhone1Input.value;
    mobilePhone1.type = PhoneType.MOBILE;

    let mobilePhone2 = new ClientPhone();
    mobilePhone2.number = this.mobilePhone2Input.value;
    mobilePhone2.type = PhoneType.MOBILE;

    let mobilePhone3 = new ClientPhone();
    mobilePhone3.number = this.mobilePhone3Input.value;
    mobilePhone3.type = PhoneType.MOBILE;

    let clientToSave = new ClientToSave();
    clientToSave.id = -1;
    clientToSave.name = this.nameInput.value;
    clientToSave.surname = this.surnameInput.value;
    clientToSave.patronymic = this.patronymicInput.value;
    clientToSave.gender = gender;
    clientToSave.birth_day = this.birthDayInput.value;
    clientToSave.charm = this.charms[this.charmInput.selectedIndex];
    clientToSave.clientAddresses = [homeAddress, registrationAddress];
    clientToSave.clientPhones = [homePhone, workPhone, mobilePhone1, mobilePhone2, mobilePhone3];

    this.httpService.post("/client/create", {
      "client": JSON.stringify(clientToSave)
    }).toPromise().then(result => {
      this.close()
    })
  }
}