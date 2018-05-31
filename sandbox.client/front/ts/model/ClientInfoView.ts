export class ClientInfoView {
  public id: number;
  public name: string;
  public surname: string;
  public age: number;
  public balance: number;
  public maxBalance: number;
  public minBalance: number;

  public assign(o: any): ClientInfoView {
    this.id = o.id;
    this.name = o.name;
    this.surname = o.surname;
    this.age = o.age;
    this.balance = o.balance;
    this.maxBalance = o.maxBalance;
    this.minBalance = o.minBalance;
    return this;
  }

  public static copy(a: any): ClientInfoView {
    let ret = new ClientInfoView();
    ret.assign(a);
    return ret;
  }
}