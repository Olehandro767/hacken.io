package io.hacken.task.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractDaoImpl<T> implements DaoI<T> {

    protected abstract JpaRepository<T, Long> getRepository();

    @Override
    public T getById(long id) {
        return this.getRepository().getReferenceById(id);
    }

    @Override
    public T save(T entity) {
        return this.getRepository().save(entity);
    }
}