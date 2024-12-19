package br.com.walletsa.exception;

import br.com.walletsa.model.dto.base.Response;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Optional;

@Log4j2
@ControllerAdvice
public class BaseExceptionHandler {

    //==== Handlers gerais ======//

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> defaultErrorHandler(HttpServletRequest req, Exception ex){
        return responseErro(ex, ex instanceof EntityNotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<String>> businessExceptionHandler(final NotFoundException ex, final WebRequest request){
        return responseErro(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<String>> businessExceptionHandler(final BusinessException ex, final WebRequest request){
        return responseErro(ex, HttpStatus.BAD_REQUEST);
    }

    //==== Handlers de exceções de validação e erro do banco de dados ======//

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<String>> onConstraintValidationException(ConstraintViolationException e){
        log.error("[" + e.getClass().getName() + "]", e);
        var msg = new StringBuilder();
        for (@SuppressWarnings("rawtypes") ConstraintViolation violation : e.getConstraintViolations()) {
            msg.append(violation.getPropertyPath().toString()).append(" - ").append(violation.getMessage()).append("; ");
        }

        return responseErro(msg.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> onMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("[" + e.getClass().getName() + "]", e);
        var msg = new StringBuilder();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            msg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("; ");
        }

        return responseErro(msg.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<Response<String>> onEntityValidationException(EntityValidationException e){
        log.error("[" + e.getClass().getName() + "]", e);
        var msg = new StringBuilder();
        for (String fieldError : e.getFieldErrors()) {
            msg.append(fieldError).append("; ");
        }

        return responseErro(msg.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response<String>> onDataIntegrityViolationException(DataIntegrityViolationException e){
        log.error("[" + e.getClass().getName() + "]", e);
        var mensagem = getMessage(e);
        if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException){
            var cause = (org.hibernate.exception.ConstraintViolationException)e.getCause();
            if(cause!=null && cause.getCause() != null && cause.getCause().getMessage().contains("duplicate key")) {
                mensagem = "Registro duplicado. Chave: "+ cause.getConstraintName();
            }
        }

        return responseErro(mensagem, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<String>> invalidFormatException(final HttpMessageNotReadableException e) {
        if(e.getCause() instanceof InvalidFormatException) {
            final var message = Optional.ofNullable(e.getCause().getMessage()).orElse(e.getClass().getSimpleName());
            var formats = message.split("format");
            var campos = message.split("\\[\"");
            var sb = new StringBuilder().append("Formato do Campo Inválido. ")
                    .append("Campo: ").append(campos[1].replace("\"])", ""))
                    .append(" - Formato: ").append(formats[1]);
            return responseErro(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        return responseErro(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //==== Métodos utilitários ======//

    private String getMessage(Exception erro) {
        var message = (!(erro instanceof BusinessException) ? "Ocorreu um erro inesperado: " : "") + erro.getMessage();

        if (erro.getCause()!=null) {
            message = message + "; Causa: " + ExceptionUtils.getRootCauseMessage(erro);
        }

        return message;
    }

    private ResponseEntity<Response<String>> responseErro(Exception erro, HttpStatus status) {
        Response<String> response = null;

        if (erro instanceof BusinessException businessException) {
            response = new Response<>(status.getReasonPhrase(), status.value(), businessException.getMessages(), null);
        } else {
            response = new Response<>(status.getReasonPhrase(), status.value(), Collections.singletonList(erro.getMessage()), null);
        }

        log.error("[" + erro.getClass().getName() + "]", erro);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    private ResponseEntity<Response<String>> responseErro(String mensagem, HttpStatus status) {
        Response<String> response = new Response<>(status.getReasonPhrase(), status.value(), Collections.singletonList(mensagem), null);

        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

}
