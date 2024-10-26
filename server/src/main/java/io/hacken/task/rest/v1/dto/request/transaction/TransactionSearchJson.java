package io.hacken.task.rest.v1.dto.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSearchJson {

    private int page;
    private String to;
    private String from;
    private String gas;
    private String gasPrice;
    private String transactionHash;
    private String date;

    public LocalDateTime getDate() {
        return LocalDateTime.parse(this.date, DateTimeFormatter.ISO_DATE_TIME);
    }
}
