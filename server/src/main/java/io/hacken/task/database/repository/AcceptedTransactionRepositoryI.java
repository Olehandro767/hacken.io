package io.hacken.task.database.repository;

import io.hacken.task.database.model.AcceptedTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AcceptedTransactionRepositoryI extends JpaRepository<AcceptedTransaction, Long> {

    Page<AcceptedTransaction> findBySentToContaining(String value, Pageable pageable);

    Page<AcceptedTransaction> findBySentFromContaining(String value, Pageable pageable);

    Page<AcceptedTransaction> findByGasContaining(String value, Pageable pageable);

    Page<AcceptedTransaction> findByGasPriceContaining(String value, Pageable pageable);

    Page<AcceptedTransaction> findByTransactionHashContaining(String value, Pageable pageable);

    Page<AcceptedTransaction> findByDate(LocalDateTime value, Pageable pageable);
}
