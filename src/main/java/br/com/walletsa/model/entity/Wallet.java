package br.com.walletsa.model.entity;

import br.com.walletsa.model.enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(name = "idx_costumer_wallet_id", columnList = "costumer_id")})
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private double balance;

    @Column
    private Integer walletNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;

    public void credit(double amount) {
        this.balance = this.balance + amount;
    }

    public void debit(double amount) {
        this.balance = this.balance - amount;
    }

}
