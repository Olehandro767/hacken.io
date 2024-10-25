package io.hacken.task.database.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DaoI<T> {

    T getById(long id);

    T save(T entity);

    Page<T> findAll(Pageable pageable);
}
