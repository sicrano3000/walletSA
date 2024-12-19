package br.com.walletsa.controller;

import br.com.walletsa.model.dto.base.Response;
import br.com.walletsa.model.dto.pagination.PaginationRequestDTO;
import br.com.walletsa.model.dto.pagination.PaginationResponseDTO;
import br.com.walletsa.model.dto.wallet.WalletRequestDTO;
import br.com.walletsa.model.dto.wallet.WalletResponseDTO;
import br.com.walletsa.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Controller - Wallet")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/wallet", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class WalletController extends BaseController {

    private final WalletService walletService;

    @PostMapping("/{constumerId}")
    @ApiOperation(value = "Save wallet.", notes = "wallet")
    public ResponseEntity<Response<WalletResponseDTO>> save(final @PathVariable Long constumerId,
                                                            final @RequestBody @Valid WalletRequestDTO dto) {
        return responseCreated(walletService.save(constumerId, dto));
    }

    @GetMapping
    @ApiOperation(value = "Return all wallet.", notes = "wallet")
    public ResponseEntity<Response<PaginationResponseDTO<WalletResponseDTO>>> findAll(@RequestParam(value = "query", required = false) String query,
                                                                                      @RequestParam(value = "pagesize", defaultValue = "10", required = false) Integer pageSize,
                                                                                      @RequestParam(value = "pageindex", defaultValue = "0", required = false) Integer pageIndex,
                                                                                      @RequestParam(value = "sort", defaultValue = "id, ASC", required = false) String sort) {
        var wallets = walletService.findAllWallets(PaginationRequestDTO.of(query, pageSize, pageIndex, sort));
        return responseOk(wallets);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Return wallet for ID.", notes = "wallet")
    public ResponseEntity<Response<WalletResponseDTO>> findById(final @PathVariable Long id) {
        var wallet = walletService.getById(id);
        return responseOk(wallet);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update wallet.", notes = "wallet")
    public ResponseEntity<Response<Void>> update(final @PathVariable Long id,
                                                 final @RequestBody @Valid WalletRequestDTO dto) {
        walletService.update(id, dto);
        return responseNoContent();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete wallet.", notes = "wallet")
    public ResponseEntity<Response<Void>> delete(final @PathVariable Long id) {
        walletService.delete(id);
        return responseNoContent();
    }

}
