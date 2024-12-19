package br.com.walletsa.service.base;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public String getMessage(@NonNull String code) {
        return getMessage(code, Locale.ENGLISH, (Object) null);
    }

    public String getMessage(@NonNull String code, @NonNull Locale locale, Object... args) {
        return messageSource.getMessage(code, args, locale);
    }

}