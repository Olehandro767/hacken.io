package io.hacken.task.database.dao.transaction.search;

import io.hacken.task.database.dao.common.SearchOptionI;

import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionComplexSearch.SEARCH_BY_FROM;

public record AcceptedTransactionSearchByFrom(String value) implements SearchOptionI<String> {

    @Override
    public String getSearchBy() {
        return SEARCH_BY_FROM;
    }

    @Override
    public String getValue() {
        return this.value();
    }

    @Override
    public String getStringValue() {
        return this.value();
    }

    public static AcceptedTransactionSearchByFrom findByFrom(String value) {
        return new AcceptedTransactionSearchByFrom(value);
    }
}
