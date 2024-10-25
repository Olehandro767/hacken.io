package io.hacken.task.database.dao.transaction;

import io.hacken.task.database.dao.AbstractDaoImpl;
import io.hacken.task.database.dao.transaction.search.AcceptedTransactionComplexSearch;
import io.hacken.task.database.model.AcceptedTransaction;
import io.hacken.task.database.repository.AcceptedTransactionRepositoryI;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TemporalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;

import static io.hacken.task.configuration.DefaultValues.DEFAULT_PAGE_LENGTH;

@Service
public class AcceptedTransactionDao extends AbstractDaoImpl<AcceptedTransaction> {

    private final EntityManager entityManager;
    private final AcceptedTransactionRepositoryI repository;

    @Autowired
    private AcceptedTransactionDao(AcceptedTransactionRepositoryI repository,
                                   EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    protected JpaRepository<AcceptedTransaction, Long> getRepository() {
        return this.repository;
    }

    public Page<AcceptedTransaction> findByTo(String value, Pageable pageable) {
        return this.repository.findBySentTo(value, pageable);
    }

    public Page<AcceptedTransaction> findByFrom(String value, Pageable pageable) {
        return this.repository.findBySentFrom(value, pageable);
    }

    public Page<AcceptedTransaction> findByGas(String value, Pageable pageable) {
        return this.repository.findByGas(value, pageable);
    }

    public Page<AcceptedTransaction> findByGasPrice(String value, Pageable pageable) {
        return this.repository.findByGasPrice(value, pageable);
    }

    public Page<AcceptedTransaction> findByTransactionHash(String value, Pageable pageable) {
        return this.repository.findByTransactionHash(value, pageable);
    }

    public Page<AcceptedTransaction> findByTransactionMethod(String value, Pageable pageable) {
        return this.repository.findByTransactionMethod(value, pageable);
    }

    public Page<AcceptedTransaction> findByDate(LocalDateTime value, Pageable pageable) {
        return this.repository.findByDate(value, pageable);
    }

    public Page<AcceptedTransaction> complexSearch(AcceptedTransactionComplexSearch search) {
        var params = new Object[]{search.to(), search.from(), search.gas(), search.gasPrice(),
                search.transactionHash(), search.transactionMethod(), search.date()};
        var fields = new String[]{"table.sentTo LIKE ?0 ", "table.sentFrom  LIKE ?1 ", "table.gas LIKE ?2 ", "table.gasPrice LIKE ?3 ",
                "table.transactionHash LIKE ?4 ", "table.transactionMethod LIKE ?5 ", "table.date BETWEEN ?6 AND ?7 "};
        var paginationJpqlBuilder = new StringBuilder("SELECT count(table) FROM AcceptedTransaction table WHERE");
        var jpqlBuilder = new StringBuilder("SELECT table FROM AcceptedTransaction table WHERE ");

        for (int i = 0; i < fields.length; i++) {
            if (params[i] != null) {
                jpqlBuilder.append(fields[i]);
                paginationJpqlBuilder.append(fields[i]);
            }
        }

        var query = this.entityManager.createQuery(jpqlBuilder.toString(), AcceptedTransaction.class);
        var countQuery = this.entityManager.createQuery(paginationJpqlBuilder.toString(), Long.class);

        for (int i = 0; i < params.length; i++) {
            var param = params[i];

            if (param != null) {
                if (i == 6) {
                    var localDateNow = LocalDateTime.now();
                    var castedParam = (LocalDateTime) param;
                    var now = Calendar.getInstance();
                    var end = Calendar.getInstance();
                    now.set(localDateNow.getYear(), localDateNow.getMonthValue() - 1, localDateNow.getDayOfMonth());
                    end.set(castedParam.getYear(), castedParam.getMonthValue() - 1, castedParam.getDayOfMonth());
                    query.setParameter(i, now, TemporalType.TIMESTAMP);
                    countQuery.setParameter(i, now, TemporalType.TIMESTAMP);
                    query.setParameter(i + 1, end, TemporalType.TIMESTAMP);
                    countQuery.setParameter(i + 1, end, TemporalType.TIMESTAMP);
                    break;
                } else {
                    query.setParameter(i, param);
                }
            }
        }

        var result = query.setFirstResult(search.page() * DEFAULT_PAGE_LENGTH)
                .setMaxResults(DEFAULT_PAGE_LENGTH)
                .getResultList();

        return new PageImpl<>(result, Pageable.ofSize(result.size()),
                (long) Math.ceil(countQuery.getSingleResult() / (double) DEFAULT_PAGE_LENGTH));
    }
}
