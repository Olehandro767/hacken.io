package io.hacken.task.rest.v1.converter.transaction;

import io.hacken.task.database.dao.common.SearchOptionI;
import io.hacken.task.rest.v1.dto.request.transaction.TransactionSearchJson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByDate.findByDate;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByFrom.findByFrom;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByGas.findByGas;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByGasPrice.findByGasPrice;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByTo.findByTo;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByTransactionHash.findByTransactionHash;

@Service
public class TransactionSearchJsonConverter {

    public SearchOptionI<Object>[] convertToOptions(TransactionSearchJson json) {
        var options = new ArrayList<SearchOptionI<?>>();

        if (json.getTo() != null && !json.getTo().isBlank()) {
            options.add(findByTo(json.getTo()));
        }
        if (json.getFrom() != null && !json.getFrom().isBlank()) {
            options.add(findByFrom(json.getFrom()));
        }
        if (json.getGas() != null && !json.getGas().isBlank()) {
            options.add(findByGas(json.getGas()));
        }
        if (json.getGasPrice() != null && !json.getGasPrice().isBlank()) {
            options.add(findByGasPrice(json.getGasPrice()));
        }
        if (json.getTransactionHash() != null && !json.getTransactionHash().isBlank()) {
            options.add(findByTransactionHash(json.getTransactionHash()));
        }
        if (json.getDate() != null) {
            options.add(findByDate(json.getDate()));
        }

        return options.toArray(new SearchOptionI[] {});
    }
}
