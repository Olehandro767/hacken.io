package io.hacken.task.rest.v1.controller.transaction;

import io.hacken.task.rest.v1.converter.transaction.AcceptedTransactionConverter;
import io.hacken.task.rest.v1.dto.request.transaction.TransactionSearchJson;
import io.hacken.task.rest.v1.dto.request.transaction.TransactionSimpleSearchJson;
import io.hacken.task.rest.v1.dto.response.PageResponse;
import io.hacken.task.rest.v1.dto.response.transaction.TransactionRecord;
import io.hacken.task.rest.v1.service.transaction.TransactionAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.hacken.task.rest.RestApiVersionDeclarations.API_V1_PREFIX;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(API_V1_PREFIX + "/transaction/access")
public class TransactionAccessRestController {

    private final AcceptedTransactionConverter transactionConverter;
    private final TransactionAccessService transactionAccessService;

    @Autowired
    public TransactionAccessRestController(AcceptedTransactionConverter transactionConverter,
                                           TransactionAccessService transactionAccessService) {
        this.transactionConverter = transactionConverter;
        this.transactionAccessService = transactionAccessService;
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<TransactionRecord>> read(@RequestParam int page) {
        var result = this.transactionAccessService.readTransactions(page);
        return ok(new PageResponse<>(this.transactionConverter.convert(result), page, result.getTotalPages()));
    }

    @PostMapping("/read/simply")
    public ResponseEntity<PageResponse<TransactionRecord>> read(@RequestBody TransactionSimpleSearchJson json) {
        var result = this.transactionAccessService.search(json.getSearchBy(), json.getValue(), json.getPage());
        return ok(new PageResponse<>(this.transactionConverter.convert(result), json.getPage(), result.getTotalPages()));
    }

    @PostMapping("/read")
    public ResponseEntity<PageResponse<TransactionRecord>> read(@RequestBody TransactionSearchJson json) {
        var result = this.transactionAccessService.complexSearch(json);
        return ok(new PageResponse<>(this.transactionConverter.convert(result), json.getPage(), result.getTotalPages()));
    }
}
