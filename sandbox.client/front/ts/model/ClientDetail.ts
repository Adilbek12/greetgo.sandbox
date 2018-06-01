import {Gender} from "./Gender";
import {Charm} from "./Charm";
import {ClientAddress} from "./ClientAddress";
import {ClientPhone} from "./ClientPhone";

export class ClientDetail {
  public name: string;
  public surname: string;
  public patronymic: string;
  public gender: Gender;
  public birth_day: Date;
  public charm: Charm;
  public clientAddresses: ClientAddress[];
  public clientPhones: ClientPhone[];

  public assign(o: any): ClientDetail {
    this.name = o.name;
    this.surname = o.surname;
    this.patronymic = o.patronymic;
    this.gender = o.gender;
    this.birth_day = o.birth_day;
    this.charm = o.charm;
    this.clientAddresses = o.clientAddresses;
    this.clientPhones = o.clientPhones;
    return this;
  }

  public static copy(a: any): ClientDetail {
    let ret = new ClientDetail();
    ret.assign(a);
    return ret;
  }
}