import { Component, OnInit } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { TablePageComponent } from "./table-page/table-page.component";
import { HttpRequestService } from "./http-request.service";

@Component({
  selector: "app-root",
  standalone: true,
  imports: [RouterOutlet, TablePageComponent],
  templateUrl: "./app.component.html",
  styleUrl: "./app.component.css",
})
export class AppComponent implements OnInit {
  public toggle: boolean = false;

  public constructor(
    private readonly _httpRequestService: HttpRequestService,
  ) {}

  private async findSplashScreen(): Promise<HTMLElement> {
    const result = document.getElementById("splash-screen-block");

    if (result) {
      return result;
    }

    throw new Error("splash-screen not found");
  }

  private handleErrors(errors: any): void {
    console.log(errors);
  }

  public ngOnInit(): void {
    this._httpRequestService.checkReceivingTransactionsStatus().subscribe({
      next: (result) => {
        this.toggle = result.enabled;
        this.findSplashScreen().then((element) => {
          element.classList.add("close-splash-screen");
          setTimeout(() => element.remove(), 510);
        });
      },
      error: this.handleErrors,
    });
  }

  public onToggleChange(target: any): void {
    this.toggle = target.checked;

    this.toggle
      ? this._httpRequestService
          .toggleToStartReceivingTransactions()
          .subscribe({ error: this.handleErrors })
      : this._httpRequestService.toggleToStopReceivingTransactions().subscribe({
          error: this.handleErrors,
        });
  }
}
