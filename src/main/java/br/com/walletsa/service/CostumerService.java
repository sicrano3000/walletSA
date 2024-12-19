package br.com.walletsa.service;

import br.com.walletsa.exception.BusinessException;
import br.com.walletsa.exception.NotFoundException;
import br.com.walletsa.model.dto.costumer.CostumerRequestDTO;
import br.com.walletsa.model.dto.costumer.CostumerResponseDTO;
import br.com.walletsa.model.dto.pagination.PaginationRequestDTO;
import br.com.walletsa.model.dto.pagination.PaginationResponseDTO;
import br.com.walletsa.model.entity.Costumer;
import br.com.walletsa.model.mapper.CostumerMapper;
import br.com.walletsa.model.mapper.PaginationMapper;
import br.com.walletsa.repository.CostumerRepository;
import br.com.walletsa.repository.base.BaseRepository;
import br.com.walletsa.service.base.BaseQueryService;
import br.com.walletsa.service.base.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CostumerService extends BaseQueryService<Costumer> {

    private final CostumerRepository costumerRepository;
    private final CostumerMapper costumerMapper;
    private final PaginationMapper<CostumerResponseDTO> paginationMapper;
    private final MessageService messageService;

    @Override
    public BaseRepository<Costumer, Long> getRepository() {
        return costumerRepository;
    }

    @Transactional
    public CostumerResponseDTO save(CostumerRequestDTO dto) {
        if (costumerRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new BusinessException(messageService.getMessage("costumer.already.registered"));
        }

        var costumer = costumerMapper.toEntity(dto);

        return costumerMapper.toDTO(save(costumer));
    }

    public PaginationResponseDTO<CostumerResponseDTO> findAllCostumers(PaginationRequestDTO paginationRequestDTO) {
        var costumer = findAll(paginationRequestDTO)
                .map(costumerMapper::toDTO);

        return paginationMapper.toDTO(costumer);
    }

    public CostumerResponseDTO getById(Long id) {
        return costumerRepository.findById(id)
                .map(costumerMapper::toDTO)
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("costumer.not.found")));
    }

    public void update(Long id, CostumerRequestDTO dto) {
        costumerRepository.findById(id)
                .map((costumer) -> save(costumerMapper.toEntity(dto, costumer)))
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("costumer.not.found")));
    }

    @Transactional
    public void delete(Long id) {
        costumerRepository.findById(id)
                .ifPresentOrElse(
                        costumerRepository::delete,
                        () -> {throw new NotFoundException(messageService.getMessage("costumer.not.found"));}
                );
    }

}

