package com.epam.esm.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Locale;
@Component
public class PaginationValidator {

    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static void validatePagination(Pageable pageable, Locale locale) {
        if (pageable.getPageNumber() < 0) {
            String errorMessage = messageSource.getMessage("label.page.number.cannot.be.negative", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (pageable.getPageSize()<=0) {
            String errorMessage = messageSource.getMessage("label.page.size.cannot.be.negative", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (pageable.getPageSize()>=10000000) {
            String errorMessage = messageSource.getMessage("label.incorrect.entered.size", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (pageable.getPageNumber()>=10000000) {
            String errorMessage = messageSource.getMessage("label.incorrect.entered.page", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
