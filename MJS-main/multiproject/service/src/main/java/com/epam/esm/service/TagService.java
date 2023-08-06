package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService extends ManagementService<Tag> {
    /**
     * Return all tags satisfy pagination
     *
     * @param pageable defined on front-end
     * @return Page<Tag>
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    Page<Tag> getAllTags(Pageable pageable) throws ServiceException;

    /**
     * Check whether this already exists in db
     *
     * @param tag looking for
     * @return Tag
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    Tag find(Tag tag) throws ServiceException, BusinessValidationException;

    /**
     * Return all tags
     *
     * @return List<Tag>
     */
    List<Tag> getAllTags() throws ServiceException;

    /**
     * Get the widely used tag
     *
     * @return Tag
     */
    Tag getThWidelyUsedTag();

}
