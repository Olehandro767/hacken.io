package io.hacken.task.database.repository;

import io.hacken.task.database.model.AcceptedTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AcceptedTransactionRepositoryI extends JpaRepository<AcceptedTransaction, Long> {

    Page<AcceptedTransaction> findBySentTo(String value, Pageable pageable);

    Page<AcceptedTransaction> findBySentFrom(String value, Pageable pageable);

    Page<AcceptedTransaction> findByGas(String value, Pageable pageable);

    Page<AcceptedTransaction> findByGasPrice(String value, Pageable pageable);

    Page<AcceptedTransaction> findByTransactionHash(String value, Pageable pageable);

    Page<AcceptedTransaction> findByTransactionMethod(String value, Pageable pageable);

    Page<AcceptedTransaction> findByDate(LocalDateTime value, Pageable pageable);
}
