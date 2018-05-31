import {Sex} from "./Sex";
import {ClientAddress} from "./ClientAddress";
import {ClientPhone} from "./ClientPhone";

export class ClientInfo {
  public id: number;
  public name: string;
  public surname: string;
  public sex: Sex;
  public age: number;
  public balance: number;
  public maxBalance: number;
  public minBalance: number;
  public homeAddress: ClientAddress;
  public registrationAddress: ClientAddress;
  public clientPhones: ClientPhone[];

  public assign(o: any): ClientInfo {
    this.id = o.id;
    this.name = o.name;
    this.surname = o.surname;
    this.sex = o.sex;
    this.age = o.age;
    this.balance = o.balance;
    this.maxBalance = o.maxBalance;
    this.minBalance = o.minBalance;
    this.homeAddress = o.homeAddress;
    this.registrationAddress = o.registrationAddress;
    this.clientPhones = o.clientPhones;
    return this;
  }

  public static copy(a: any): ClientInfo {
    let ret = new ClientInfo();
    ret.assign(a);
    return ret;
  }
}