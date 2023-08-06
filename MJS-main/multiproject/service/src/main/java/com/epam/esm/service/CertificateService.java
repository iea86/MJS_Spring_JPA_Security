package com.epam.esm.service;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.SearchCriteria;
import com.epam.esm.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CertificateService extends ManagementService<Certificate> {
    /**
     * Get list of certificates which satisfy search criteria
     *
     * @param searchCriteria defined by user
     * @return List<Certificate>
     * satisfy search criteria
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    Page<Certificate> getCertificates(SearchCriteria searchCriteria, Pageable pageable) throws ServiceException;

 /**
     * Get list of all certificates
     *
     * @return List<Certificate>
     *
     */
    List<Certificate> getCertificates() throws ServiceException;

}
