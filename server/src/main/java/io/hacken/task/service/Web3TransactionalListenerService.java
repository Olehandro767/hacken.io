package io.hacken.task.service;

import io.reactivex.disposables.Disposable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

import static java.lang.String.format;

@Service
public class Web3TransactionalListenerService {

    private final Web3j web3;
    private Disposable transactionListenerContext = null;

    @Autowired
    public Web3TransactionalListenerService(
            @Value("${hacken.application.credentials.grove-city-chain-prefix}") String groveCityChainPrefix,
            @Value("${hacken.application.credentials.grove-city-application-id}") String apiId
    ) throws IOException {
        this.web3 = Web3j.build(new HttpService(format("https://%s.rpc.grove.city/v1/%s", groveCityChainPrefix, apiId)));
        this.checkConnection();
    }

    private void checkConnection() throws IOException {
        this.web3.web3ClientVersion().send();
    }

    private boolean isListenerStillListen() {
        return this.transactionListenerContext != null && !this.transactionListenerContext.isDisposed();
    }

    public synchronized boolean deployTransactionListener() {
        if (!this.isListenerStillListen()) {
            this.transactionListenerContext = this.web3.pendingTransactionFlowable().subscribe(transaction -> {
                transaction.getHash();
                transaction.getRaw(); // TODO should be a method
                transaction.getBlockNumber();
                transaction.getFrom();
                transaction.getTo();
                transaction.getGasRaw();
                transaction.getGasPriceRaw();
                // TODO
            });

            // TODO log
            return true;
        }

        // TODO log
        return false;
    }

    public boolean stopTransactionListener() {
        if (this.isListenerStillListen()) {
            this.transactionListenerContext.dispose();
            // TODO log
            return true;
        }

        // TODO log
        return false;
    }
}
