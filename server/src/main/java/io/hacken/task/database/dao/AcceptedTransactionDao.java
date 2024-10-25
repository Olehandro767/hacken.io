package io.hacken.task.database.dao;

import io.hacken.task.database.model.AcceptedTransaction;
import io.hacken.task.database.repository.AcceptedTransactionRepositoryI;
import org.springframework.data.jpa.repository.JpaRepository;

public class AcceptedTransactionDao extends AbstractDaoImpl<AcceptedTransaction> {

    private final AcceptedTransactionRepositoryI repository;

    private AcceptedTransactionDao() {
        this.repository = null; // FIXME
    }

    @Override
    protected JpaRepository<AcceptedTransaction, Long> getRepository() {
        return this.repository;
    }
}
