package io.hacken.task.rest.v1;

import io.hacken.task.service.Web3TransactionalListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.hacken.task.rest.RestApiVersionDeclarations.API_V1_PREFIX;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(API_V1_PREFIX + "/transaction/behaviour")
public class TransactionBehaviourRestController {

    private final Web3TransactionalListenerService transactionalListenerService;

    @Autowired
    public TransactionBehaviourRestController(Web3TransactionalListenerService transactionalListenerService) {
        this.transactionalListenerService = transactionalListenerService;
    }

    @PutMapping("/start")
    public ResponseEntity<String> start() {
        return this.transactionalListenerService.deployTransactionListener()
                ? ok().body(OK.getReasonPhrase()) : badRequest().body("Already started");
    }

    @PutMapping("/stop")
    private ResponseEntity<String> stop() {
        this.transactionalListenerService.stopTransactionListener();
        return this.transactionalListenerService.deployTransactionListener()
                ? ok().body(OK.getReasonPhrase()) : badRequest().body("Already stopped");
    }
}
