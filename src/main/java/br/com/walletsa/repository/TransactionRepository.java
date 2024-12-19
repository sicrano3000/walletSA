package br.com.walletsa.repository;

import br.com.walletsa.model.entity.Transaction;
import br.com.walletsa.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends BaseRepository<Transaction, Long> {

    List<Transaction> findByWalletId(Long walletId);

    @Query(value = "SELECT * " +
                   "FROM transaction t " +
                   "inner join wallet w on w.id = t.wallet_id " +
                   "inner join costumer c on c.id = w.costumer_id " +
                   "where c.id = :costumeId", nativeQuery = true)
    List<Transaction> findByCostumeId(Long costumeId);

}
