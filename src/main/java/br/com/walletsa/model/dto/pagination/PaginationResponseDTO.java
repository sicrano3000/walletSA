package br.com.walletsa.model.dto.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class PaginationResponseDTO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -571624768330633674L;

    private List<T> content;
    private Integer pageSize;
    private Integer pageIndex;
    private Integer totalPages;
    private Integer contentSize;
    private Long totalElements;
    private Boolean first;
    private Boolean last;
    private Boolean empty;

}
