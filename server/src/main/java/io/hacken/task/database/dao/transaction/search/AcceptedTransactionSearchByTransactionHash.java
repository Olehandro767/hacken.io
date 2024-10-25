package io.hacken.task.database.dao.transaction.search;

import io.hacken.task.database.dao.common.SearchOptionI;

import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionComplexSearch.SEARCH_BY_TRANSACTION_HASH;

public record AcceptedTransactionSearchByTransactionHash(String value) implements SearchOptionI<String> {

    @Override
    public String getSearchBy() {
        return SEARCH_BY_TRANSACTION_HASH;
    }

    @Override
    public String getValue() {
        return this.value();
    }

    @Override
    public String getStringValue() {
        return this.value();
    }

    public static AcceptedTransactionSearchByTransactionHash findByTransactionHash(String value) {
        return new AcceptedTransactionSearchByTransactionHash(value);
    }
}
