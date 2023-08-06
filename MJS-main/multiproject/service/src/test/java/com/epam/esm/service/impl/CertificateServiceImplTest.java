package com.epam.esm.service.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.SearchCriteria;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.CertificateBusinessValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CertificateServiceImplTest {

    private final static Long CERTIFICATE_ID = 1L;
    private final static String CERTIFICATE_NAME = "certificate";
    private final static String CERTIFICATE_DESCRIPTION = "certificate_description";
    private final static String TAG = "tag";
    private final static int COUNT = 1;


    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private CertificateBusinessValidator certificateBusinessValidator;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagService tagService;

    @InjectMocks
    private CertificateServiceImpl certificatesService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() throws BusinessValidationException {
        Certificate certificate = createCertificate();
        when(certificateRepository.save(certificate)).thenReturn(certificate);
        Long id = certificatesService.create(certificate);
        assertEquals(CERTIFICATE_ID, id);
    }

    @Test
    public void testDelete() throws BusinessValidationException {
        doNothing().when(certificateBusinessValidator).validateIfExistsById(CERTIFICATE_ID);
        doNothing().when(certificateRepository).deleteById(CERTIFICATE_ID);
        certificatesService.delete(CERTIFICATE_ID);
    }

    @Test
    public void testRead() {
        Certificate certificate = createCertificate();
        when(certificateRepository.findCertificateById(CERTIFICATE_ID)).thenReturn(certificate);
        Certificate certificateActual = certificatesService.get(CERTIFICATE_ID);
        assertEquals(certificate, certificateActual);
    }

    @Test
    public void testGetCertificates() {
        List<Certificate> certificates = createListOfCertificates();
        when(certificateRepository.findAll()).thenReturn(certificates);
        List<Certificate> actualCertificates = certificatesService.getCertificates();
        verify(certificateRepository, times(1)).findAll();
        assertEquals(certificates, actualCertificates);
    }

    @Test(expected = Exception.class)
    public void whenExceptionWhileGettingCertificates() {
        when(certificateRepository.findAll()).thenThrow(Exception.class);
        certificatesService.getCertificates();
    }

    @Test
    public void getAllCertificates() {
        List<Certificate> certificates = createListOfCertificates();
        when(certificateRepository.findAll()).thenReturn(certificates);
        List<Certificate> actualCertificates = certificatesService.getCertificates();
        verify(certificateRepository, times(1)).findAll();
        assertEquals(certificates, actualCertificates);
    }

    @Test
    public void getCertificates() {
        Page<Certificate> certificates = mock(Page.class);
        when(certificateRepository.findCertificatesByNameAndDescription(CERTIFICATE_NAME, CERTIFICATE_DESCRIPTION, TAG, COUNT, null)).thenReturn(certificates);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setName(CERTIFICATE_NAME);
        searchCriteria.setDescription(CERTIFICATE_DESCRIPTION);
        searchCriteria.setTag(TAG);
        Page<Certificate> actualCertificates = certificatesService.getCertificates(searchCriteria, null);
        verify(certificateRepository, times(1)).findCertificatesByNameAndDescription(CERTIFICATE_NAME, CERTIFICATE_DESCRIPTION, TAG, COUNT, null);
        assertEquals(certificates, actualCertificates);
    }

    @Test
    public void testUpdate() throws BusinessValidationException {
        Certificate certificate = createCertificate();
        when(certificateRepository.findCertificateById(CERTIFICATE_ID)).thenReturn(certificate);
        certificatesService.update(certificate);
        verify(certificateRepository, times(1)).save(certificate);
    }


    private Certificate createCertificate() {
        Certificate certificate = new Certificate();
        certificate.setId(1L);
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(0L);
        tag.setName("tag");
        tags.add(tag);
        certificate.setTags(tags);
        Duration duration = Duration.ofDays(1);
        certificate.setDuration(duration);
        certificate.setPrice(10.0);
        certificate.setName("name");
        certificate.setName("description");
        return certificate;
    }

    private List<Certificate> createListOfCertificates() {
        List<Certificate> certificates = new ArrayList<>();
        Certificate certificate1 = new Certificate();
        certificate1.setId(1L);
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(0L);
        tag.setName("tag");
        tags.add(tag);
        certificate1.setTags(tags);
        certificates.add(certificate1);
        Certificate certificate2 = new Certificate();
        certificate2.setId(2L);
        certificate2.setTags(tags);
        certificates.add(certificate2);
        return certificates;
    }

}