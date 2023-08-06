package com.epam.esm.validator;

import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.exception.BusinessValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class CertificateBusinessValidator {
    private MessageSource messageSource;
    private CertificateRepository certificateRepository;

    @Autowired
    public CertificateBusinessValidator(MessageSource messageSource, CertificateRepository certificateRepository) {
        this.messageSource = messageSource;
        this.certificateRepository = certificateRepository;
    }

    public void validateIfExistsById(Long id) throws BusinessValidationException {
        if (certificateRepository.findCertificateById(id) == null) {
            String errorMessage = messageSource.getMessage("label.not.found.certificate", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }

    public void validateIfExistsByName(String name) throws BusinessValidationException {
        if (certificateRepository.findByName(name)!=null) {
            String errorMessage = messageSource.getMessage("label.already.exists.certificate", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }

    public void validateIfExists(String name) throws BusinessValidationException {
        if (certificateRepository.findByName(name) == null) {
            String errorMessage = messageSource.getMessage("label.not.found.certificate", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }
}
