import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class HttpRequestService {
  private readonly HOST_PREFIX = location.host.startsWith("localhost:4200")
    ? "http://localhost:8080"
    : location.origin;

  public constructor(private readonly _http: HttpClient) {}

  private emptyBody(): any {
    return {};
  }

  public toggleToStartReceivingTransactions(): Observable<Object> {
    return this._http.put(
      `${this.HOST_PREFIX}/api/v1/transaction/behaviour/start"`,
      this.emptyBody(),
      {
        headers: {},
      },
    );
  }

  public toggleToStopReceivingTransactions(): Observable<Object> {
    return this._http.put(
      `${this.HOST_PREFIX}/api/v1/transaction/behaviour/stop`,
      this.emptyBody(),
      {
        headers: {},
      },
    );
  }

  public readDbData(page: number): void {}

  public complexDbDataReading(): void {}
}
