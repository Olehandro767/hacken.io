package io.hacken.task.rest.v1.converter.transaction;

import io.hacken.task.database.dao.common.SearchOptionI;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByDate.findByDate;

import io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByDate;
import io.hacken.task.rest.v1.dto.request.TransactionSearchJson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByFrom.findByFrom;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByGas.findByGas;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByGasPrice.findByGasPrice;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByTo.findByTo;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByTransactionHash.findByTransactionHash;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionSearchByTransactionMethod.findByTransactionMethod;

@Service
public class TransactionSearchJsonConverter {

    public SearchOptionI[] convertToOptions(TransactionSearchJson json) {
        var options = new ArrayList<SearchOptionI<?>>();

        if (json.getTo() != null) {
            options.add(findByTo(json.getTo()));
        }
        if (json.getFrom() != null) {
            options.add(findByFrom(json.getFrom()));
        }
        if (json.getGas() != null) {
            options.add(findByGas(json.getGas()));
        }
        if (json.getGasPrice() != null) {
            options.add(findByGasPrice(json.getGasPrice()));
        }
        if (json.getTransactionHash() != null) {
            options.add(findByTransactionHash(json.getTransactionHash()));
        }
        if (json.getTransactionMethod() != null) {
            options.add(findByTransactionMethod(json.getTransactionMethod()));
        }
        if (json.getDate() != null) {
            options.add(findByDate(json.getDate()));
        }

        return options.toArray(new SearchOptionI[0]);
    }
}
