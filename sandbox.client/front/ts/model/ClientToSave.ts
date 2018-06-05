import {Gender} from "./Gender";
import {Charm} from "./Charm";
import {ClientAddress} from "./ClientAddress";
import {ClientPhone} from "./ClientPhone";

export class ClientToSave {
  public id: number;
  public surname: string;
  public name: string;
  public patronymic: string;
  public gender: Gender;
  public birth_day: Date;
  public charm: Charm;
  public addressFact: ClientAddress = new ClientAddress();
  public addressReg: ClientAddress = new ClientAddress();
  public homePhone: ClientPhone = new ClientPhone();
  public workPhone: ClientPhone = new ClientPhone();
  public mobilePhone: ClientPhone = new ClientPhone();

  public assign(o: any): ClientToSave {
    this.id = o.id;
    this.surname = o.surname;
    this.name = o.name;
    this.patronymic = o.patronymic;
    this.gender = o.gender;
    this.birth_day = o.birth_day;
    this.charm = o.charm;
    this.addressFact = o.addressFact;
    this.addressReg = o.addressReg;
    this.homePhone = o.homePhone;
    this.workPhone = o.workPhone;
    this.mobilePhone = o.mobilePhone;
    return this;
  }

  public static copy(a: any): ClientToSave {
    let ret = new ClientToSave();
    ret.assign(a);
    return ret;
  }
}