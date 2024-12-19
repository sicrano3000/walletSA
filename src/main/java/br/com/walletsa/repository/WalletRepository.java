package br.com.walletsa.repository;

import br.com.walletsa.model.entity.Wallet;
import br.com.walletsa.model.enums.CurrencyEnum;
import br.com.walletsa.repository.base.BaseRepository;

import java.util.Optional;

public interface WalletRepository extends BaseRepository<Wallet, Long> {

    Optional<Wallet> findByCostumerIdAndCurrency(Long costumeId, CurrencyEnum currency);

}
