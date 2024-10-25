package io.hacken.task.database.repository;

import io.hacken.task.database.model.AcceptedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcceptedTransactionRepositoryI extends JpaRepository<AcceptedTransaction, Long> {
}
