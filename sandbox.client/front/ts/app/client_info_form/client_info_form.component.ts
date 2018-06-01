import {Component} from "@angular/core";
import {HttpService} from "../HttpService";
import {CharacterType} from "../../model/Charm";
import {Sex} from "../../model/Sex";
import {ClientAddress} from "../../model/ClientAddress";
import {AddressType} from "../../model/AddressType";
import {ClientInfoNew} from "../../model/ClientInfoNew";
import {ClientPhone} from "../../model/ClientPhone";
import {PhoneType} from "../../model/PhoneType";

@Component({
  selector: 'client-info-form-component',
  template: require('./client_info_form.component.html'),
  styles: [require('./client_info_form.component.css')],
})

export class ClientInfoFormComponent {

  characters: CharacterType[] = [CharacterType.ANXIOUS, CharacterType.CYCLOID,
    CharacterType.DEMONSTRATIVE, CharacterType.DYSTHYMIC, CharacterType.EXCITABLE,
    CharacterType.PEDANTIC, CharacterType.STUCK, CharacterType.HYPERTENSIVE];

  constructor(private httpService: HttpService) {}


  close() {
    document.getElementById("myModal").style.display = "none"
  }

  add() {
    let surname: string = (document.getElementById("surname") as HTMLInputElement).value;

    let name: string = (document.getElementById("name") as HTMLInputElement).value;

    let patronymic: string = (document.getElementById("patronymic") as HTMLInputElement).value;

    let sex: Sex;
    if ((document.getElementById("sex_male") as HTMLInputElement).checked) {
      sex = Sex.MALE;
    } else if ((document.getElementById("sex_female") as HTMLInputElement).checked) {
      sex = Sex.FEMALE;
    }

    let age = +(document.getElementById("age") as HTMLInputElement).value;

    let character: string = (document.getElementById("character") as HTMLSelectElement).value;

    let registrationAddress = new ClientAddress();
    let ra_street = (document.getElementById("ra_street") as HTMLInputElement).value;
    let ra_house = (document.getElementById("ra_house") as HTMLInputElement).value;
    let ra_apartment = (document.getElementById("ra_apartment") as HTMLInputElement).value;
    registrationAddress.apartment = ra_street;
    registrationAddress.house = ra_house;
    registrationAddress.apartment = ra_apartment;
    registrationAddress.type = AddressType.REG;

    let homeAddress = new ClientAddress();
    let la_street = (document.getElementById("la_street") as HTMLInputElement).value;
    let la_house = (document.getElementById("la_house") as HTMLInputElement).value;
    let la_apartment = (document.getElementById("la_apartment") as HTMLInputElement).value;
    homeAddress.apartment = la_street;
    homeAddress.house = la_house;
    homeAddress.apartment = la_apartment;
    homeAddress.type = AddressType.FACT;

    let home_phone = new ClientPhone();
    home_phone.number = (document.getElementById("home_phone") as HTMLInputElement).value;
    home_phone.phoneType = PhoneType.HOME;

    let work_phone = new ClientPhone();
    work_phone.number = (document.getElementById("work_phone") as HTMLInputElement).value;
    work_phone.phoneType = PhoneType.WORK;

    let mobile_phone1 = new ClientPhone();
    mobile_phone1.number = (document.getElementById("mobile_phone1") as HTMLInputElement).value;
    mobile_phone1.phoneType = PhoneType.MOBILE;

    let mobile_phone2 = new ClientPhone();
    mobile_phone2.number = (document.getElementById("mobile_phone2") as HTMLInputElement).value;
    mobile_phone2.phoneType = PhoneType.MOBILE;

    let mobile_phone3 = new ClientPhone();
    mobile_phone3.number = (document.getElementById("mobile_phone3") as HTMLInputElement).value;
    mobile_phone3.phoneType = PhoneType.MOBILE;

    let newClientInfo = new ClientInfoNew();
    newClientInfo.name = name;
    newClientInfo.surname = surname;
    newClientInfo.sex = sex;
    newClientInfo.age = age;
    newClientInfo.character = character as CharacterType;
    newClientInfo.homeAddress = homeAddress;
    newClientInfo.registrationAddress = registrationAddress;
    newClientInfo.clientPhones = [home_phone, work_phone, mobile_phone1, mobile_phone2, mobile_phone3];

  }
}