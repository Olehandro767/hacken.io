import { Component } from "@angular/core";
import { database_entity } from "../types";
import { HttpRequestService } from "../http-request.service";

@Component({
  selector: "app-table-page",
  standalone: true,
  imports: [],
  templateUrl: "./table-page.component.html",
  styleUrl: "./table-page.component.css",
})
export class TablePageComponent {
  public transactionsData: database_entity[] = [];

  public constructor(
    private readonly _httpRequestService: HttpRequestService,
  ) {}

  public updateTable(): void {}

  public pagination(page: number): void {}
}
