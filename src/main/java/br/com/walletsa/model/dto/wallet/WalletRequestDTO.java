package br.com.walletsa.model.dto.wallet;

import br.com.walletsa.model.enums.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class WalletRequestDTO {

    @Min(value = 0, message="Balance Should be greater than zero")
    @NotNull
    private double balance;

    @NotNull
    private CurrencyEnum currency;

}
