package br.com.walletsa.model.dto.transaction;

import br.com.walletsa.model.entity.Costumer;
import br.com.walletsa.model.entity.Wallet;
import br.com.walletsa.model.enums.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TransactionResponseDTO {

    @ApiModelProperty(value = "Id of Transaction.")
    private Integer id;

    @ApiModelProperty(value = "Type of Transaction.")
    private TransactionTypeEnum transactionType;

    @ApiModelProperty(value = "Date of Transaction.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime transactionDate;

    @ApiModelProperty(value = "Amount.")
    private double amount;

    @ApiModelProperty(value = "Description.")
    private String description;

    @ApiModelProperty(value = "Wallet.")
    private Wallet wallet;

    @ApiModelProperty(value = "To Costumer.")
    private Costumer toCostumer;

}
