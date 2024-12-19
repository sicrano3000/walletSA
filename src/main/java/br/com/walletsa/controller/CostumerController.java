package br.com.walletsa.controller;

import br.com.walletsa.model.dto.base.Response;
import br.com.walletsa.model.dto.costumer.CostumerRequestDTO;
import br.com.walletsa.model.dto.costumer.CostumerResponseDTO;
import br.com.walletsa.model.dto.pagination.PaginationRequestDTO;
import br.com.walletsa.model.dto.pagination.PaginationResponseDTO;
import br.com.walletsa.service.CostumerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Controller - Costumer")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/costumer", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class CostumerController extends BaseController {

    private final CostumerService costumerService;

    @PostMapping
    @ApiOperation(value = "Save costumer.", notes = "costumer")
    public ResponseEntity<Response<CostumerResponseDTO>> save(final @RequestBody @Valid CostumerRequestDTO dto) {
        return responseCreated(costumerService.save(dto));
    }

    @GetMapping
    @ApiOperation(value = "Return all costumer.", notes = "costumer")
    public ResponseEntity<Response<PaginationResponseDTO<CostumerResponseDTO>>> findAll(@RequestParam(value = "query", required = false) String query,
                                                                                        @RequestParam(value = "pagesize", defaultValue = "10", required = false) Integer pageSize,
                                                                                        @RequestParam(value = "pageindex", defaultValue = "0", required = false) Integer pageIndex,
                                                                                        @RequestParam(value = "sort", defaultValue = "id, ASC", required = false) String sort) {
        var costumer = costumerService.findAllCostumers(PaginationRequestDTO.of(query, pageSize, pageIndex, sort));
        return responseOk(costumer);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Return costumer for ID.", notes = "costumer")
    public ResponseEntity<Response<CostumerResponseDTO>> findById(final @PathVariable Long id) {
        var costumer = costumerService.getById(id);
        return responseOk(costumer);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update costumer.", notes = "costumer")
    public ResponseEntity<Response<Void>> update(final @PathVariable Long id,
                                                 final @RequestBody @Valid CostumerRequestDTO dto) {
        costumerService.update(id, dto);
        return responseNoContent();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete costumer.", notes = "costumer")
    public ResponseEntity<Response<Void>> delete(final @PathVariable Long id) {
        costumerService.delete(id);
        return responseNoContent();
    }

}
