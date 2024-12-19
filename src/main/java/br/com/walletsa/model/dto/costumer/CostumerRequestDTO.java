package br.com.walletsa.model.dto.costumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CostumerRequestDTO {

    @ApiModelProperty(value = "Name.")
    @Pattern(regexp = "[a-zA-Z0-9]{1,100}", message = "Name can't be Empty")
    private String name;

    @ApiModelProperty(value = "Cpf.")
    @Pattern(regexp = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}", message = "Cpf not is valid")
    private String cpf;

    @ApiModelProperty(value = "Phone.")
    @Pattern(regexp = "[0-9]{9}", message = "Number entered is not an valid number")
    private String phone;

    @ApiModelProperty(value = "Age.")
    @Min(18)
    private Integer age;

    @ApiModelProperty(value = "Email.")
    @Email(message = "Email format is wrong")
    @NotNull(message = "Email should not be null")
    private String email;

}
