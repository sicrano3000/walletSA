package br.com.walletsa.exception;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public class BusinessException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Collection<String> messages = new LinkedList<>();

    private Integer code;

    public BusinessException() {
        super();
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(Collection<String> messages) {
        super();
        this.messages = messages;
    }

    public BusinessException(String message) {
        super(message);
        this.messages.add(message);
    }

    public BusinessException(Collection<String> messages, Integer code) {
        super();
        this.messages = messages;
        this.code = code;
    }

    public BusinessException(String message, Integer code) {
        super(message);
        this.messages.add(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.messages.add(message);
    }

    public BusinessException(Throwable cause, Collection<String> messages) {
        super(cause);
        this.messages = messages;
    }

    public BusinessException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.messages.add(message);
        this.code = code;
    }

    public BusinessException(Throwable cause, Collection<String> messages, Integer code) {
        super(cause);
        this.messages = messages;
        this.code = code;
    }

    public Collection<String> getMessages() {
        return messages;
    }

    public Integer getCode() {
        return code;
    }

}
