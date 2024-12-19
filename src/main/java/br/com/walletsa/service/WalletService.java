package br.com.walletsa.service;

import br.com.walletsa.exception.BusinessException;
import br.com.walletsa.exception.NotFoundException;
import br.com.walletsa.model.dto.pagination.PaginationRequestDTO;
import br.com.walletsa.model.dto.pagination.PaginationResponseDTO;
import br.com.walletsa.model.dto.wallet.WalletRequestDTO;
import br.com.walletsa.model.dto.wallet.WalletResponseDTO;
import br.com.walletsa.model.entity.Wallet;
import br.com.walletsa.model.mapper.PaginationMapper;
import br.com.walletsa.model.mapper.WalletMapper;
import br.com.walletsa.repository.CostumerRepository;
import br.com.walletsa.repository.WalletRepository;
import br.com.walletsa.repository.base.BaseRepository;
import br.com.walletsa.service.base.BaseQueryService;
import br.com.walletsa.service.base.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class WalletService extends BaseQueryService<Wallet> {

    private final CostumerRepository costumerRepository;
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final PaginationMapper<WalletResponseDTO> paginationMapper;
    private final MessageService messageService;

    @Override
    public BaseRepository<Wallet, Long> getRepository() {
        return walletRepository;
    }

    @Transactional
    public WalletResponseDTO save(Long constumerId, WalletRequestDTO dto) {
        return costumerRepository.findById(constumerId)
                .map((costumer) -> {
                            if (walletRepository.findByCostumerIdAndCurrency(constumerId, dto.getCurrency()).isPresent()) {
                                throw new BusinessException(messageService.getMessage("wallet.already.registered"));
                            }

                            var wallet = walletMapper.toEntity(dto);
                            wallet.setCostumer(costumer);
                            wallet.setWalletNumber(generateWalletNumber());
                            return walletMapper.toDTO(save(wallet));
                })
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("costumer.not.found")));
    }

    public PaginationResponseDTO<WalletResponseDTO> findAllWallets(PaginationRequestDTO paginationRequestDTO) {
        var wallets = findAll(paginationRequestDTO)
                .map(walletMapper::toDTO);

        return paginationMapper.toDTO(wallets);
    }

    public Integer generateWalletNumber() {
        var random = new Random();
        return random.nextInt(999999999) + 1;
    }

    public WalletResponseDTO getById(Long id) {
        return walletRepository.findById(id)
                .map(walletMapper::toDTO)
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("wallet.not.found")));
    }

    public void update(Long id, WalletRequestDTO dto) {
        walletRepository.findById(id)
                .map((costumer) -> save(walletMapper.toEntity(dto, costumer)))
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("wallet.not.found")));
    }

    @Transactional
    public void delete(Long id) {
        walletRepository.findById(id)
                .ifPresentOrElse(
                        walletRepository::delete,
                        () -> {throw new NotFoundException(messageService.getMessage("wallet.not.found"));}
                );
    }

}
