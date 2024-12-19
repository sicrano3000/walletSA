package br.com.walletsa.model.entity;

import br.com.walletsa.model.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(name = "idx_transaction_wallet_id", columnList = "wallet_id"),
                  @Index(name = "idx_transaction_costumer_id", columnList = "costumer_id")})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum transactionType;

    @CreationTimestamp
    @Column
    private LocalDateTime transactionDate;

    @Column
    private double amount;

    @Column
    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "costumer_id")
    private Costumer toCostumer;

}
