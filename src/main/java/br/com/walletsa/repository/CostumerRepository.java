package br.com.walletsa.repository;

import br.com.walletsa.model.entity.Costumer;
import br.com.walletsa.repository.base.BaseRepository;

import java.util.Optional;

public interface CostumerRepository extends BaseRepository<Costumer, Long> {

    Optional<Costumer> findByCpf(String cpf);

}
