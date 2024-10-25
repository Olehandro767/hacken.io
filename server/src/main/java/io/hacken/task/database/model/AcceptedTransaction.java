package io.hacken.task.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.LocalDateTime;

import static org.hibernate.type.SqlTypes.LONGVARCHAR;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accepted_transactions")
public class AcceptedTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JdbcTypeCode(LONGVARCHAR)
    @Column(name = "sent_to", nullable = false)
    private String sentTo;
    @JdbcTypeCode(LONGVARCHAR)
    @Column(name = "sent_from", nullable = false)
    private String sentFrom;
    @JdbcTypeCode(LONGVARCHAR)
    @Column(name = "gas", nullable = false)
    private String gas;
    @JdbcTypeCode(LONGVARCHAR)
    @Column(name = "gas_price", nullable = false)
    private String gasPrice;
    @JdbcTypeCode(LONGVARCHAR)
    @Column(name = "block_number", nullable = false)
    private String blockNumber;
    @JdbcTypeCode(LONGVARCHAR)
    @Column(name = "transaction_hash", nullable = false)
    private String transactionHash;
    @JdbcTypeCode(LONGVARCHAR)
    @Column(name = "transaction_method", nullable = false)
    private String transactionMethod;
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}
