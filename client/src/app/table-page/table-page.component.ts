import { Component } from "@angular/core";
import { database_entity } from "../types";
import { HttpRequestService } from "../http-request.service";
import { FormsModule } from "@angular/forms";

@Component({
  selector: "app-table-page",
  standalone: true,
  imports: [FormsModule],
  templateUrl: "./table-page.component.html",
  styleUrl: "./table-page.component.css",
})
export class TablePageComponent {
  public readonly SEARCH_STATE = {
    NO_SEARCH: 1,
    SIMPLE_SEARCH: 2,
    COMPLEX_SEARCH: 3,
  };
  public readonly availableOptions = [
    { key: "to", value: 'Search by "TO"' },
    { key: "from", value: 'Search by "FROM"' },
    { key: "gas", value: 'Search by "Gas"' },
    { key: "gasPrice", value: 'Search by "Gas Price"' },
    { key: "transactionHash", value: 'Search by "Transaction Hash"' },
    { key: "transactionMethod", value: 'Search by "Transaction Method"' },
    { key: "date", value: 'Search by "Date"' },
  ];
  public searchState = this.SEARCH_STATE.NO_SEARCH;
  public simpleSearchContext = {
    typeValue: "",
    searchBy: "",
    value: "",
    dateValue: "",
  };
  public complexSearchContext = {
    toValue: "",
    fromValue: "",
    gas: "",
    gasPrice: "",
    transactionHash: "",
    transactionMethod: "",
    date: "",
  };
  public transactionsData: database_entity[] = [];
  public simpleSearchType: string = "";
  public showPreviousPageButton = false;
  public showNextPageButton = false;
  private page: number = 0;

  public constructor(
    private readonly _httpRequestService: HttpRequestService,
  ) {}

  public updateTable(): void {}

  public simpleSearchToggle(target: any): void {}

  public pagination(page: number): void {}

  public clickOnSimpleSearch(): void {}

  public clickOnComplexSearch(): void {}
}
