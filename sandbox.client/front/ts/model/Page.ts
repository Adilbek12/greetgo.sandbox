import {ClientRecords} from "./ClientRecords";

export class Page {
  public clientRecordsList: ClientRecords[];
  public pagesCount: number;

  public assign(o: any): Page {
    this.clientRecordsList = o.clientRecordsList;
    this.pagesCount = o.pagesCount;
    return this;
  }

  public static copy(a: any): Page {
    let ret = new Page();
    ret.assign(a);
    return ret;
  }
}