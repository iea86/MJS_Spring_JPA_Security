package com.epam.esm.service.impl;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagBusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    private TagBusinessValidator tagBusinessValidator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagBusinessValidator tagBusinessValidator) {
        this.tagRepository = tagRepository;
        this.tagBusinessValidator = tagBusinessValidator;
    }

    @Override
    public Page<Tag> getAllTags(Pageable pageable) {
        return tagRepository.findAllBy(pageable);
    }

    @Override
    public Tag find(Tag tag) {
        return tagRepository.findByName(tag.getName());
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getThWidelyUsedTag()  {
        return tagRepository.findTopByCertificatesMatches();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public Long create(Tag entity) throws BusinessValidationException {
        tagBusinessValidator.validateIfExistsByName(entity.getName());
        return tagRepository.save(entity).getId();
    }

    @Override
    public Tag get(Long id) {
        return tagRepository.findTagById(id);
    }

    @Override
    public void update(Tag entity) {
    }

    @Override
    public void delete(Long id) throws BusinessValidationException {
        tagBusinessValidator.validateIfExistsById(id);
        tagRepository.deleteById(id);
    }
}
