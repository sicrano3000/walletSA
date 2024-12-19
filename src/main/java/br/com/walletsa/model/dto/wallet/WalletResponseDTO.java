package br.com.walletsa.model.dto.wallet;

import br.com.walletsa.model.entity.Costumer;
import br.com.walletsa.model.enums.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class WalletResponseDTO {

    @ApiModelProperty(value = "Id of Wallet.")
    private Long id;

    @ApiModelProperty(value = "Balance.")
    private double balance;

    @ApiModelProperty(value = "Wallet Number.")
    private Integer walletNumber;

    @ApiModelProperty(value = "Currency.")
    private CurrencyEnum currency;

    @ApiModelProperty(value = "Costumer.")
    private Costumer costumer;

}
