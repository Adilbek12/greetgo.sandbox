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
  public clientAddresses: ClientAddress[];
  public clientPhones: ClientPhone[];
}