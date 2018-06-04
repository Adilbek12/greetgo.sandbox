import {SortBy} from "./SortBy";

export class PageParam {
  public from: number;
  public to: number;
  public sortBy: SortBy | null;
  public filter: string | null;
}