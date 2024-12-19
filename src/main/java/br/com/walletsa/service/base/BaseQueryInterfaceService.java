package br.com.walletsa.service.base;

import br.com.walletsa.model.dto.pagination.PaginationRequestDTO;
import br.com.walletsa.repository.base.BaseRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Objects;

/**
 * It is recommended to use the {@link BaseQueryService} if the intention is only
 * performing queries in the database
 */
public sealed interface BaseQueryInterfaceService<E, ID> permits BaseQueryService {

    /**
     * Method for implementing repository capture, such as {@link BaseQueryInterfaceService} is an interface,
     * It is not possible to use automatic injection of services for this purpose unless these methods are used so that the
     * default implementation don't have any problems
     */
    BaseRepository<E, ID> getRepository();
    RsqlService getRsqlService();

    private PageRequest paginationToPageRequest(PaginationRequestDTO paginationRequestDTO) {
        if (Objects.isNull(paginationRequestDTO)) {
            return PageRequest.of(0, 10);
        }

        Integer pageIndex = Objects.requireNonNullElse(paginationRequestDTO.getPageIndex(), 0);
        Integer pageSize = Objects.requireNonNullElse(paginationRequestDTO.getPageSize(), 10);
        return PageRequest.of(pageIndex, pageSize);
    }

    default Page<E> findAll(PaginationRequestDTO paginationRequestDTO) {
        try {
            final var pageRequest = paginationToPageRequest(paginationRequestDTO);
            final var spec = getRsqlService().<E>getPaginationSpecification(paginationRequestDTO);

            return getRepository().findAll(spec, pageRequest);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Error when performing a paged search. Check the sent query: " + paginationRequestDTO.getQuery(), e);
        }
    }

    default E save(E entity) {
        return getRepository().save(entity);
    }

    default List<E> saveAll(List<E> entities) {
        return getRepository().saveAll(entities);
    }

}