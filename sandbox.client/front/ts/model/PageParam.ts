import {SortBy} from "./SortBy";
import {SortDirection} from "./SortDirection";

export class PageParam {
  public from: number;
  public to: number;
  public sortBy: SortBy | null;
  public sortDirection: SortDirection | null;
  public filter: string | null;
}