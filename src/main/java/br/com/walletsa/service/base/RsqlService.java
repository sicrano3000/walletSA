package br.com.walletsa.service.base;

import br.com.walletsa.model.dto.pagination.PaginationRequestDTO;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import io.github.perplexhub.rsql.RSQLCustomPredicate;
import io.github.perplexhub.rsql.RSQLJPASupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * {@link RsqlService} service responsible for translating the RSQL query language.
 * <p>
 * Documentação em:
 * <ul>
 *     <li><a href="https://www.baeldung.com/rest-api-search-language-rsql-fiql">rest api search language rsql fiql</a></li>
 *     <li><a href="https://github.com/jirutka/rsql-parser">rsql parser</a></li>
 * </ul>
 */
@Service
public class RsqlService {

    public <T> Specification<T> getPaginationSpecification(PaginationRequestDTO paginationRequestDTO) {
        Specification<T> spec = RSQLJPASupport.toSpecification("");

        if (Objects.isNull(paginationRequestDTO)) {
            return spec;
        }

        if (StringUtils.isNotEmpty(paginationRequestDTO.getQuery())) {
            spec = spec.and(RSQLJPASupport.toSpecification(paginationRequestDTO.getQuery(), getCustomPredications()));
        }

        if (StringUtils.isNotEmpty(paginationRequestDTO.getSort())) {
            spec = spec.and(RSQLJPASupport.toSort(paginationRequestDTO.getSort()));
        }

        return spec;
    }

    private List<RSQLCustomPredicate<?>> getCustomPredications() {
        return List.of(getRsqlBeetwenDateCustomPredication());
    }

    private RSQLCustomPredicate<String> getRsqlBeetwenDateCustomPredication() {
        return new RSQLCustomPredicate<>(new ComparisonOperator("=btd=", true), String.class, input -> {
            String input1 = (String) input.getArguments().get(0);
            String input2 = (String) input.getArguments().get(1);

            LocalDateTime date1 = input1.contains("T") ? LocalDateTime.parse(input1)
                    : LocalDateTime.of(LocalDate.parse(input1), LocalTime.of(0, 0));

            LocalDateTime date2 = input2.contains("T") ? LocalDateTime.parse(input2)
                    : LocalDateTime.of(LocalDate.parse(input2), LocalTime.of(23, 59));

            return input.getCriteriaBuilder().between(input.getPath().as(LocalDateTime.class), date1, date2);
        });
    }

}
