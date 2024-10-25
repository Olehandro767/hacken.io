package io.hacken.task.database.model;

import java.time.LocalDateTime;

public class AcceptedTransaction {

    private long id;
    private String to;
    private String from;
    private String gas;
    private String gasPrice;
    private String blockNumber;
    private String transactionHash;
    private String transactionMethod;
    private LocalDateTime date;
}
