package br.com.walletsa.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class EntityValidationException extends RuntimeException {

    private final List<String> fieldErrors;

    public EntityValidationException(String fieldErrors) {
        this.fieldErrors = Arrays.asList(fieldErrors);
    }

    public EntityValidationException(List<String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public EntityValidationException(List<String> fieldErrors, Throwable cause) {
        super(cause);
        this.fieldErrors = fieldErrors;
    }

}
