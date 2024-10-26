package io.hacken.task.rest.v1.dto.response.transaction;

public record TransactionRecord(long id, String to, String from, String gas,
                                String gasPrice, String blockNumber, String transactionHash, String date) {
}
