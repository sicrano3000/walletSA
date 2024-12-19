package br.com.walletsa.model.dto.costumer;

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
public class CostumerResponseDTO {

    @ApiModelProperty(value = "Id of Costumer.")
    private Long id;

    @ApiModelProperty(value = "Name.")
    private String name;

    @ApiModelProperty(value = "Cpf.")
    private String cpf;

    @ApiModelProperty(value = "Phone.")
    private String phone;

    @ApiModelProperty(value = "Age.")
    private Integer age;

    @ApiModelProperty(value = "Email.")
    private String email;

}
