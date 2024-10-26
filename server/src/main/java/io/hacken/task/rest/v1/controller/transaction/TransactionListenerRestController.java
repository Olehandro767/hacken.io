package io.hacken.task.rest.v1.controller.transaction;

import io.hacken.task.rest.v1.dto.response.transaction.TransactionListenerStateRecord;
import io.hacken.task.rest.v1.service.transaction.Web3TransactionalListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.hacken.task.rest.RestApiVersionDeclarations.API_V1_PREFIX;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(API_V1_PREFIX + "/transaction/listener/control")
public class TransactionListenerRestController {

    private final Web3TransactionalListenerService transactionalListenerService;

    @Autowired
    public TransactionListenerRestController(Web3TransactionalListenerService transactionalListenerService) {
        this.transactionalListenerService = transactionalListenerService;
    }

    @PutMapping("/start")
    public ResponseEntity<String> start() {
        return this.transactionalListenerService.deployTransactionListener()
                ? ok().body(OK.getReasonPhrase()) : badRequest().body("Already started");
    }

    @PutMapping("/stop")
    public ResponseEntity<String> stop() {
        this.transactionalListenerService.stopTransactionListener();
        return this.transactionalListenerService.deployTransactionListener()
                ? ok().body(OK.getReasonPhrase()) : badRequest().body("Already stopped");
    }

    @GetMapping("/read/state")
    public ResponseEntity<TransactionListenerStateRecord> readState() {
        return ok(new TransactionListenerStateRecord(this.transactionalListenerService.isListenerStillListening()));
    }
}
