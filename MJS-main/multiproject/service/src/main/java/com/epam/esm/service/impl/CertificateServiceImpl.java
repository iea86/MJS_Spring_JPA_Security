package com.epam.esm.service.impl;

import com.epam.esm.entity.util.CountInString;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.entity.*;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validator.CertificateBusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.time.Duration;
import java.util.*;

@Service
public class CertificateServiceImpl implements CertificateService {

    private static final double DEFAULT_PRICE = -1.0;
    private static final Duration DEFAULT_DURATION = Duration.ofDays(-1);

    private CertificateRepository certificateRepository;
    private TagRepository tagRepository;
    private CertificateBusinessValidator certificateBusinessValidator;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository, CertificateBusinessValidator certificateBusinessValidator) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.certificateBusinessValidator = certificateBusinessValidator;
    }

    @Override
    public Page<Certificate> getCertificates(SearchCriteria searchCriteria, Pageable pageable) {
        int countElementsInString = CountInString.getCountOfElInStr(searchCriteria.getTag());
        return certificateRepository.findCertificatesByNameAndDescription(searchCriteria.getName(),
                searchCriteria.getDescription(),
                searchCriteria.getTag(),
                countElementsInString, pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    @Override
    public Long create(Certificate entity) throws BusinessValidationException {
            certificateBusinessValidator.validateIfExistsByName(entity.getName());
            List<Tag> tags = entity.getTags();
            if (!tags.isEmpty()) {
                List<Tag> tagsExisting = tagRepository.findAll();
                tags.forEach(t -> {
                    Optional<Tag> result = tagsExisting
                            .stream()
                            .filter(num -> num.getName().equals((t.getName())))
                            .findAny();
                    result.ifPresentOrElse(r -> t.setId(r.getId()), () -> tagRepository.save(t));
                });
            }
            return certificateRepository.save(entity).getId();
    }

    @Override
    public Certificate get(Long id) {
        return certificateRepository.findCertificateById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    @Override
    public void update(Certificate entity) throws BusinessValidationException {
        certificateBusinessValidator.validateIfExistsById(entity.getId());
        Certificate certificateToUpdate = certificateRepository.findCertificateById(entity.getId());
        if (!entity.getDuration().equals(DEFAULT_DURATION)) {
            certificateToUpdate.setDuration(entity.getDuration());
        }
        if (entity.getPrice() != DEFAULT_PRICE) {
            certificateToUpdate.setPrice(entity.getPrice());
        }
        if (entity.getDescription() != null) {
            certificateToUpdate.setDescription(entity.getDescription());
        }
        if (entity.getName() != null) {
            certificateToUpdate.setName(entity.getName());
        }
        if (!entity.getTags().isEmpty()) {
            certificateToUpdate.setTags(entity.getTags());
        }
        certificateToUpdate.setLastUpdateDate(entity.getLastUpdateDate());
        certificateRepository.save(certificateToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    @Override
    public void delete(Long id) throws BusinessValidationException {
        certificateBusinessValidator.validateIfExistsById(id);
        certificateRepository.deleteById(id);
    }

    @Override
    public List<Certificate> getCertificates() {
        return certificateRepository.findAll();
    }
}
