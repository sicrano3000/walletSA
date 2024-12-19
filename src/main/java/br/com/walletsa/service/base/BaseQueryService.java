package br.com.walletsa.service.base;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract implementation for the “interface” {@link BaseQueryInterfaceService} to extend it in classes so that
 * avoid method injection and overwriting {@link BaseQueryInterfaceService#getRsqlService()}.
 * <p>
 * Not injecting the repository due to the need to use specific methods within the
 * repositories, which is why overwriting is necessary within the specific service.
 */
public abstract non-sealed class BaseQueryService<E> implements BaseQueryInterfaceService<E, Long> {

    @Autowired
    private RsqlService rsqlService;

    @Override
    public RsqlService getRsqlService() {
        return rsqlService;
    }

}