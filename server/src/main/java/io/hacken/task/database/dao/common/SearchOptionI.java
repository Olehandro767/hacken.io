package io.hacken.task.database.dao.common;

import java.util.Optional;

public interface SearchOptionI<T> {

    String getSearchBy();

    T getValue();

    default <K> Optional<K> getValue(Class<K> type) {
        try {
            return Optional.of(type.cast(this.getValue()));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    default String getStringValue() {
        return this.getValue().toString();
    }
}
