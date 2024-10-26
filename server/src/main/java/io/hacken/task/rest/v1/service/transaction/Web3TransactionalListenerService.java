package io.hacken.task.rest.v1.service.transaction;

import io.hacken.task.database.dao.transaction.AcceptedTransactionDao;
import io.hacken.task.database.model.AcceptedTransaction;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.lang.String.format;

@Slf4j
@Service
public class Web3TransactionalListenerService {

    private final Web3j web3;
    private final AcceptedTransactionDao acceptedTransactionDao;

    private Disposable transactionListenerContext = null;

    @Autowired
    public Web3TransactionalListenerService(
            @Value("${hacken.application.credentials.grove-city-chain-prefix}") String groveCityChainPrefix,
            @Value("${hacken.application.credentials.grove-city-application-id}") String apiId,
            AcceptedTransactionDao acceptedTransactionDao
    ) throws IOException {
        this.acceptedTransactionDao = acceptedTransactionDao;
        this.web3 = Web3j.build(new HttpService(format("https://%s.rpc.grove.city/v1/%s", groveCityChainPrefix, apiId)));
        this.checkConnection();
    }

    private void checkConnection() throws IOException {
        this.web3.web3ClientVersion().send();
    }

    public boolean isListenerStillListening() {
        return this.transactionListenerContext != null && !this.transactionListenerContext.isDisposed();
    }

    public synchronized boolean deployTransactionListener() {
        if (!this.isListenerStillListening()) {
            this.transactionListenerContext = this.web3.pendingTransactionFlowable()
                    .subscribe(transaction -> this.acceptedTransactionDao.save(AcceptedTransaction.builder()
                            .transactionHash(transaction.getHash())
                            .transactionMethod(transaction.getRaw()) // TODO should be a method
                            .blockNumber(transaction.getBlockNumberRaw())
                            .sentFrom(transaction.getFrom())
                            .sentTo(transaction.getTo())
                            .gas(transaction.getGasRaw())
                            .gasPrice(transaction.getGasPriceRaw())
                            .date(LocalDateTime.now())
                            .build()));

            log.info("deployTransactionListener: Listener started");
            return true;
        }

        log.info("deployTransactionListener: Already started");
        return false;
    }

    public boolean stopTransactionListener() {
        if (this.isListenerStillListening()) {
            this.transactionListenerContext.dispose();
            log.info("deployTransactionListener: Listener stopped");
            return true;
        }

        log.info("deployTransactionListener: Already stopped");
        return false;
    }
}
