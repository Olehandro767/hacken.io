package io.hacken.task.rest.v1.dto.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSimpleSearchJson {

    private String searchBy;
    private String value;
    private int page;
}
