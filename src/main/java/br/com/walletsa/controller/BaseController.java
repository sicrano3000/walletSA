package br.com.walletsa.controller;

import br.com.walletsa.model.dto.base.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<Response<T>> responseAcepted(T result) {
        var body = Response.<T>builder()
                .status(HttpStatus.ACCEPTED.getReasonPhrase())
                .code(HttpStatus.ACCEPTED.value())
                .result(result)
                .build();

        return ResponseEntity
                .ok(body);
    }

    protected <T> ResponseEntity<Response<T>> responseOk(T result) {
        var body = Response.<T>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .result(result)
                .build();

        return ResponseEntity
                .ok(body);
    }

    protected <T> ResponseEntity<Response<T>> responseCreated(T result) {
        var body = Response.<T>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .code(HttpStatus.CREATED.value())
                .result(result)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(body);
    }

    protected ResponseEntity<Response<Void>> responseNoContent() {
        return ResponseEntity
                .noContent()
                .build();
    }

}
