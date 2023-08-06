package com.epam.esm.validator;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.exception.BusinessValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class TagBusinessValidator {

    private MessageSource messageSource;
    private TagRepository tagRepository;

    @Autowired
    public TagBusinessValidator(MessageSource messageSource, TagRepository tagRepository) {
        this.messageSource = messageSource;
        this.tagRepository = tagRepository;
    }

    public void validateIfExistsById(Long id) throws BusinessValidationException {
        if (tagRepository.findTagById(id) == null) {
            String errorMessage = messageSource.getMessage("label.not.found.tag", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }

    public void validateIfExistsByName(String name) throws BusinessValidationException {
        if (tagRepository.findByName(name) != null) {
            String errorMessage = messageSource.getMessage("label.already.exists.tag", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }
}
