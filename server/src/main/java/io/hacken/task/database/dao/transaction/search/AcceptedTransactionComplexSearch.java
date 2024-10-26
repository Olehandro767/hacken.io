package io.hacken.task.database.dao.transaction.search;

import io.hacken.task.database.dao.common.SearchOptionI;

import java.time.LocalDateTime;

public record AcceptedTransactionComplexSearch(int page, String to, String from, String gas, String gasPrice,
                                               String transactionHash, LocalDateTime date) {

    public static final String SEARCH_BY_TO = "to";
    public static final String SEARCH_BY_FROM = "from";
    public static final String SEARCH_BY_GAS = "gas";
    public static final String SEARCH_BY_GAS_PRICE = "gasPrice";
    public static final String SEARCH_BY_TRANSACTION_HASH = "transactionHash";
    public static final String SEARCH_BY_DATE = "date";

    public static AcceptedTransactionComplexSearch prepareComplexSearch(int page, SearchOptionI<?>... options) {
        String to = null;
        String from = null;
        String gas = null;
        String gasPrice = null;
        String transactionHash = null;
        LocalDateTime date = null;

        for (SearchOptionI<?> option : options) {
            switch (option.getSearchBy()) {
                case SEARCH_BY_TO -> to = option.getStringValue();
                case SEARCH_BY_FROM -> from = option.getStringValue();
                case SEARCH_BY_GAS -> gas = option.getStringValue();
                case SEARCH_BY_GAS_PRICE -> gasPrice = option.getStringValue();
                case SEARCH_BY_TRANSACTION_HASH -> transactionHash = option.getStringValue();
                case SEARCH_BY_DATE -> date = option.getValue(LocalDateTime.class)
                        .orElseThrow(() -> new IllegalArgumentException("illegal argument"));
                default -> throw new IllegalArgumentException("illegal argument");
            }
        }

        return new AcceptedTransactionComplexSearch(page, to, from, gas, gasPrice,
                transactionHash, date);
    }
}
