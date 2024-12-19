package br.com.walletsa.model.dto.transaction;

import br.com.walletsa.model.enums.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TransactionRequestDTO {

    @NotNull
    private TransactionTypeEnum transactionType;

    @NotNull
    private double amount;

    @NotNull
    private String description;

}
