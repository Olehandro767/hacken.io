package io.hacken.task.rest.v1.converter.transaction;

import io.hacken.task.database.model.AcceptedTransaction;
import io.hacken.task.rest.v1.dto.response.transaction.TransactionRecord;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

@Service
public class AcceptedTransactionConverter {

    public TransactionRecord convert(AcceptedTransaction entity) {
        return new TransactionRecord(entity.getId(), entity.getSentTo(), entity.getSentFrom(), entity.getGas(),
                entity.getGasPrice(), entity.getBlockNumber(), entity.getTransactionHash(),
                ofPattern("yyyy-MM-dd HH:mm").format(entity.getDate()));
    }

    public List<TransactionRecord> convert(Streamable<AcceptedTransaction> ptr) {
        return ptr.stream().map(this::convert).toList();
    }
}
