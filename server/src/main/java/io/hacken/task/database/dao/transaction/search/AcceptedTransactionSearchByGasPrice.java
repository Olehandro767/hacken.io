package io.hacken.task.database.dao.transaction.search;

import io.hacken.task.database.dao.common.SearchOptionI;

import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionComplexSearch.SEARCH_BY_GAS_PRICE;

public record AcceptedTransactionSearchByGasPrice(String value) implements SearchOptionI<String> {

    @Override
    public String getSearchBy() {
        return SEARCH_BY_GAS_PRICE;
    }

    @Override
    public String getValue() {
        return this.value();
    }

    @Override
    public String getStringValue() {
        return this.value();
    }

    public static AcceptedTransactionSearchByGasPrice findByGasPrice(String value) {
        return new AcceptedTransactionSearchByGasPrice(value);
    }
}
