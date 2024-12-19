package br.com.walletsa.model.mapper;

import br.com.walletsa.model.dto.wallet.WalletRequestDTO;
import br.com.walletsa.model.dto.wallet.WalletResponseDTO;
import br.com.walletsa.model.entity.Wallet;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {WalletMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletMapper {

    Wallet toEntity(WalletRequestDTO dto);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    Wallet toEntity(WalletRequestDTO dto, @MappingTarget Wallet entity);

    @Mappings({
            @Mapping(source = "id", target = "id")
    })
    WalletResponseDTO toDTO(Wallet entity);

}
