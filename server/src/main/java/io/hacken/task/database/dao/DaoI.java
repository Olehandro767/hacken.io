package io.hacken.task.database.dao;

public interface DaoI<T> {

    T getById(long id);

    T save(T entity);
}
