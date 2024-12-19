package br.com.walletsa.model.mapper;

import br.com.walletsa.model.dto.costumer.CostumerRequestDTO;
import br.com.walletsa.model.dto.costumer.CostumerResponseDTO;
import br.com.walletsa.model.entity.Costumer;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CostumerMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CostumerMapper {

    Costumer toEntity(CostumerRequestDTO dto);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    Costumer toEntity(CostumerRequestDTO dto, @MappingTarget Costumer entity);

    @Mappings({
            @Mapping(source = "id", target = "id")
    })
    CostumerResponseDTO toDTO(Costumer entity);

}
