package br.com.walletsa.service;

import br.com.walletsa.exception.BusinessException;
import br.com.walletsa.exception.NotFoundException;
import br.com.walletsa.model.dto.transaction.TransactionRequestDTO;
import br.com.walletsa.model.dto.transaction.TransactionResponseDTO;
import br.com.walletsa.model.entity.Transaction;
import br.com.walletsa.model.entity.Wallet;
import br.com.walletsa.model.enums.TransactionTypeEnum;
import br.com.walletsa.model.mapper.TransactionMapper;
import br.com.walletsa.repository.CostumerRepository;
import br.com.walletsa.repository.TransactionRepository;
import br.com.walletsa.repository.WalletRepository;
import br.com.walletsa.repository.base.BaseRepository;
import br.com.walletsa.service.base.BaseQueryService;
import br.com.walletsa.service.base.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService extends BaseQueryService<Transaction> {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final CostumerRepository costumerRepository;
    private final TransactionMapper transactionMapper;
    private final MessageService messageService;

    @Override
    public BaseRepository<Transaction, Long> getRepository() {
        return transactionRepository;
    }

    public TransactionResponseDTO credit(Long walletId, TransactionRequestDTO dto) {
        return walletRepository.findById(walletId)
                .map((wallet) -> saveCredit(wallet, dto))
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("wallet.not.found")));
    }

    private void validMinimumAmount(TransactionRequestDTO dto) {
        if (dto.getAmount() < 10) {
            throw new BusinessException(messageService.getMessage("transaction.minimum.amount"));
        }
    }

    private void validCredit(TransactionRequestDTO dto) {
        validMinimumAmount(dto);
        var isCredit = Objects.equals(dto.getTransactionType(), TransactionTypeEnum.CREDIT);

        if (!isCredit) {
            throw new BusinessException(messageService.getMessage("transaction.only.credit"));
        }
    }

    private TransactionResponseDTO saveCredit(Wallet wallet, TransactionRequestDTO dto) {
        validCredit(dto);

        var transaction = transactionMapper.toEntity(dto);
        transaction.setWallet(creditAmount(wallet, dto));
        transaction.setToCostumer(null);
        transactionRepository.save(transaction);

        return transactionMapper.toDTO(transaction);
    }

    private Wallet creditAmount(Wallet wallet, TransactionRequestDTO dto) {
        wallet.credit(dto.getAmount());

        return walletRepository.save(wallet);
    }

    public TransactionResponseDTO debit(Long walletId, TransactionRequestDTO dto) {
        return walletRepository.findById(walletId)
                .map((wallet) -> saveDebit(wallet, dto))
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("wallet.not.found")));
    }

    private void validDebit(TransactionRequestDTO dto, double balance) {
        validMinimumAmount(dto);
        var isDebit = Objects.equals(dto.getTransactionType(), TransactionTypeEnum.DEBIT);
        var newBalance = balance - dto.getAmount();

        if (!isDebit) {
            throw new BusinessException(messageService.getMessage("transaction.only.debit"));
        }

        if (newBalance < 0) {
            throw new BusinessException(messageService.getMessage("transaction.debit.negative"));
        }
    }

    private TransactionResponseDTO saveDebit(Wallet wallet, TransactionRequestDTO dto) {
        validDebit(dto, wallet.getBalance());

        var transaction = transactionMapper.toEntity(dto);
        transaction.setWallet(debitAmount(wallet, dto));
        transaction.setToCostumer(null);
        transactionRepository.save(transaction);

        return transactionMapper.toDTO(transaction);
    }

    private Wallet debitAmount(Wallet wallet, TransactionRequestDTO dto) {
        wallet.debit(dto.getAmount());

        return walletRepository.save(wallet);
    }

    public List<TransactionResponseDTO> getByWalletId(Long walletId) {
        return transactionRepository.findByWalletId(walletId)
                .stream()
                .map(transactionMapper::toDTO)
                .toList();
    }

    public List<TransactionResponseDTO> getByCostumerId(Long costumerId) {
        return transactionRepository.findByCostumeId(costumerId)
                .stream()
                .map(transactionMapper::toDTO)
                .toList();
    }

    public TransactionResponseDTO transferToCostumer(Long walletId, String cpf, TransactionRequestDTO dto) {
        return walletRepository.findById(walletId)
                .map((wallet) -> saveTransfer(wallet, cpf, dto))
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("wallet.not.found")));
    }

    public TransactionResponseDTO saveTransfer(Wallet wallet, String cpf, TransactionRequestDTO dto) {
        validTransfer(dto, wallet.getBalance());

        var costumer = costumerRepository.findByCpf(cpf)
                .orElseThrow(() -> new BusinessException(messageService.getMessage("costumer.not.found")));
        var walletCostumer = walletRepository.findByCostumerIdAndCurrency(costumer.getId(), wallet.getCurrency())
                .orElseThrow(() -> new BusinessException(messageService.getMessage("wallet.currency.not.found")));

        saveCreditAndDebit(wallet, dto, walletCostumer.getId());

        dto.setTransactionType(TransactionTypeEnum.TRANSFER);
        var transaction = transactionMapper.toEntity(dto);
        transaction.setWallet(wallet);
        transaction.setToCostumer(costumer);
        transactionRepository.save(transaction);

        return transactionMapper.toDTO(transaction);
    }

    private void saveCreditAndDebit(Wallet wallet, TransactionRequestDTO dto, Long walletCostumerId) {
        dto.setTransactionType(TransactionTypeEnum.DEBIT);
        saveDebit(wallet, dto);

        var walletCostumer = walletRepository.findById(walletCostumerId)
                .orElseThrow(() -> new BusinessException(messageService.getMessage("wallet.not.found")));

        dto.setTransactionType(TransactionTypeEnum.CREDIT);
        saveCredit(walletCostumer, dto);
    }

    private void validTransfer(TransactionRequestDTO dto, double balance) {
        validMinimumAmount(dto);
        var isTransfer = Objects.equals(dto.getTransactionType(), TransactionTypeEnum.TRANSFER);
        var newBalance = balance - dto.getAmount();

        if (!isTransfer) {
            throw new BusinessException(messageService.getMessage("transaction.only.transfer"));
        }

        if (newBalance < 0) {
            throw new BusinessException(messageService.getMessage("transaction.debit.negative"));
        }
    }

}
