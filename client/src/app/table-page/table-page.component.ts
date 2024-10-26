import { Component } from "@angular/core";
import { database_entity, page } from "../types";
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
    { key: "date", value: 'Search by "Date"' },
  ];
  public searchState = this.SEARCH_STATE.NO_SEARCH;
  public simpleSearchContext = {
    searchBy: this.availableOptions[0].key,
    value: "",
    dateValue: "",
  };
  public complexSearchContext = {
    toValue: "",
    fromValue: "",
    gas: "",
    gasPrice: "",
    transactionHash: "",
    date: "",
  };
  public transactionsData: database_entity[] = [];
  public simpleSearchType: string = "";
  public showPreviousPageButton = false;
  public showNextPageButton = false;
  private page: number = 0;

  public constructor(private readonly _httpRequestService: HttpRequestService) {
    this.fetchData();
  }

  private commonDataHandler(data: page<database_entity>) {
    this.page = data.currentPage;
    this.showNextPageButton = this.page < data.totalPages - 1;
    this.showPreviousPageButton = this.page > 0;
    this.transactionsData = data.collection;
  }

  private fetchData(): void {
    switch (this.searchState) {
      case this.SEARCH_STATE.SIMPLE_SEARCH: {
        if (this.simpleSearchContext.searchBy === "date") {
          // TODO
        }
        const value = this.simpleSearchContext.value;

        this._httpRequestService
          .sipleTransactionSearch({
            page: this.page,
            searchBy: this.simpleSearchContext.searchBy,
            value,
          })
          .subscribe({
            next: (data) => this.commonDataHandler(data),
          });
        break;
      }
      case this.SEARCH_STATE.COMPLEX_SEARCH: {
        // this._httpRequestService
        //   .complexTransactionSearch({
        //     page: this.page,
        //     date: "",
        //   })
        //   .subscribe({
        //     next: this.commonDataHandler,
        //   });
        break;
      }
      default: {
        this._httpRequestService.readDbData(this.page).subscribe({
          next: (data) => this.commonDataHandler(data),
        });
        break;
      }
    }
  }

  public updateTable(): void {
    this.fetchData();
  }

  public clickOnPreviousPage(): void {
    this.page--;
    this.fetchData();
  }

  public clickOnNextPage(): void {
    this.page++;
    this.fetchData();
  }

  public changeSearchType(value: number) {
    this.searchState = value;
  }

  public simpleSearchToggle(target: any): void {}

  public clickOnSimpleSearch(): void {
    this.fetchData();
  }

  public clickOnComplexSearch(): void {}
}
