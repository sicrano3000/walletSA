package br.com.walletsa.controller;

import br.com.walletsa.model.dto.base.Response;
import br.com.walletsa.model.dto.transaction.TransactionRequestDTO;
import br.com.walletsa.model.dto.transaction.TransactionResponseDTO;
import br.com.walletsa.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Controller - Transaction")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class TransactionController extends BaseController {

    private final TransactionService transactionService;

    @PatchMapping("/credit/{walletId}")
    @ApiOperation(value = "Credit transaction.", notes = "transaction")
    public ResponseEntity<Response<TransactionResponseDTO>> credit(final @PathVariable Long walletId,
                                                                   final @RequestBody @Valid TransactionRequestDTO dto) {
        return responseAcepted(transactionService.credit(walletId, dto));
    }

    @PatchMapping("/debit/{walletId}")
    @ApiOperation(value = "Debit transaction.", notes = "transaction")
    public ResponseEntity<Response<TransactionResponseDTO>> debit(final @PathVariable Long walletId,
                                                                  final @RequestBody @Valid TransactionRequestDTO dto) {
        return responseAcepted(transactionService.debit(walletId, dto));
    }

    @GetMapping("/wallet/{walletId}")
    @ApiOperation(value = "Return wallet for ID.", notes = "wallet")
    public ResponseEntity<Response<List<TransactionResponseDTO>>> findByWalletId(final @PathVariable Long walletId) {
        return responseOk(transactionService.getByWalletId(walletId));
    }

    @GetMapping("/costumer/{costumerId}")
    @ApiOperation(value = "Return wallet for ID.", notes = "wallet")
    public ResponseEntity<Response<List<TransactionResponseDTO>>> findByCostumerId(final @PathVariable Long costumerId) {
        return responseOk(transactionService.getByCostumerId(costumerId));
    }

    @PatchMapping("/transfer/{walletId}/{cpf}")
    @ApiOperation(value = "Transfer transaction into costumer.", notes = "transaction")
    public ResponseEntity<Response<TransactionResponseDTO>> transferToCostumer(final @PathVariable Long walletId,
                                                                               final @PathVariable String cpf,
                                                                               final @RequestBody @Valid TransactionRequestDTO dto) {
        return responseAcepted(transactionService.transferToCostumer(walletId, cpf, dto));
    }

    //TODO validar para que cada cliente só tenha 1 carteira de cada tipo

}
