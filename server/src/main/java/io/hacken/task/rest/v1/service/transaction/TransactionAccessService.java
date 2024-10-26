package io.hacken.task.rest.v1.service.transaction;

import io.hacken.task.database.dao.transaction.AcceptedTransactionDao;
import io.hacken.task.database.model.AcceptedTransaction;
import io.hacken.task.exception.FieldDoesNotExistException;
import io.hacken.task.rest.v1.converter.transaction.TransactionSearchJsonConverter;
import io.hacken.task.rest.v1.dto.request.transaction.TransactionSearchJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static io.hacken.task.configuration.DefaultValues.DEFAULT_PAGE_LENGTH;
import static io.hacken.task.database.dao.transaction.search.AcceptedTransactionComplexSearch.*;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;

@Service
public class TransactionAccessService {

    private final AcceptedTransactionDao acceptedTransactionDao;
    private final TransactionSearchJsonConverter searchJsonConverter;

    @Autowired
    public TransactionAccessService(AcceptedTransactionDao acceptedTransactionDao,
                                    TransactionSearchJsonConverter searchJsonConverter) {
        this.acceptedTransactionDao = acceptedTransactionDao;
        this.searchJsonConverter = searchJsonConverter;
    }

    public Page<AcceptedTransaction> readTransactions(int page) {
        return this.acceptedTransactionDao.findAll(PageRequest.of(page, DEFAULT_PAGE_LENGTH, by(DESC, "id")));
    }

    public Page<AcceptedTransaction> search(String searchBy, String value, int page) {
        var pagination = PageRequest.of(page, DEFAULT_PAGE_LENGTH, by(DESC, "id"));
        return switch (searchBy) {
            case SEARCH_BY_TO -> this.acceptedTransactionDao.findByTo(value, pagination);
            case SEARCH_BY_FROM -> this.acceptedTransactionDao.findByFrom(value, pagination);
            case SEARCH_BY_GAS -> this.acceptedTransactionDao.findByGas(value, pagination);
            case SEARCH_BY_GAS_PRICE -> this.acceptedTransactionDao.findByGasPrice(value, pagination);
            case SEARCH_BY_TRANSACTION_HASH -> this.acceptedTransactionDao.findByTransactionHash(value, pagination);
            case SEARCH_BY_DATE -> this.acceptedTransactionDao.findByDate(parse(value, ISO_DATE_TIME), pagination);
            default -> throw new FieldDoesNotExistException('"' + searchBy + "\" does not exists");
        };
    }

    public Page<AcceptedTransaction> complexSearch(TransactionSearchJson json) {
        var options = this.searchJsonConverter.convertToOptions(json);
        return this.acceptedTransactionDao.complexSearch(prepareComplexSearch(json.getPage(), options));
    }
}
