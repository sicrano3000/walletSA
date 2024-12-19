package br.com.walletsa.model.mapper;

import br.com.walletsa.model.dto.transaction.TransactionRequestDTO;
import br.com.walletsa.model.dto.transaction.TransactionResponseDTO;
import br.com.walletsa.model.entity.Transaction;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {TransactionMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {

    Transaction toEntity(TransactionRequestDTO dto);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    Transaction toEntity(TransactionRequestDTO dto, @MappingTarget Transaction entity);

    @Mappings({
            @Mapping(source = "id", target = "id")
    })
    TransactionResponseDTO toDTO(Transaction entity);

}
