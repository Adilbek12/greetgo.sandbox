import {Sex} from "./Sex";
import {ClientAddress} from "./ClientAddress";
import {ClientPhone} from "./ClientPhone";

export class ClientInfoEdited {
  public id: number;
  public name: string;
  public surname: string;
  public sex: Sex;
  public age: number;
  public homeAddress: ClientAddress | null;
  public registrationAddress: ClientAddress;
  public clientPhones: ClientPhone[];
}