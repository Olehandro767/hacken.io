package io.hacken.task.database.dao.transaction.search;

import io.hacken.task.database.dao.common.SearchOptionI;

import java.time.LocalDateTime;

import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionComplexSearch.SEARCH_BY_FROM;
import static java.time.format.DateTimeFormatter.ofPattern;

public record AcceptedTransactionSearchByDate(LocalDateTime value) implements SearchOptionI<LocalDateTime> {

    @Override
    public String getSearchBy() {
        return SEARCH_BY_FROM;
    }

    @Override
    public LocalDateTime getValue() {
        return this.value();
    }

    @Override
    public String getStringValue() {
        return ofPattern("yyyy-MM-dd HH:mm").format(this.value());
    }

    public static AcceptedTransactionSearchByDate findByDate(LocalDateTime value) {
        return new AcceptedTransactionSearchByDate(value);
    }
}
