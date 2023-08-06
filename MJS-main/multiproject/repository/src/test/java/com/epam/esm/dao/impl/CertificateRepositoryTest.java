package com.epam.esm.dao.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {InitTestDataBaseConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class CertificateRepositoryTest {

    private final static String CERTIFICATE_NAME= "test_name1";
    private final static Long CERTIFICATE_ID= 4L;
    private final static Long CERTIFICATE_ID_TO_DELETE = 1L;
    private final static int CERTIFICATES_COUNT= 3;

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    TagRepository tagRepository;

    @Test
    @Sql(scripts = {"/data.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindAll() {
        List<Certificate> certificates= certificateRepository.findAll();
        assertEquals(CERTIFICATES_COUNT, certificates.size());
    }

    @Test
    @Sql(scripts = {"/data.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindById() {
        Certificate certificate = certificateRepository.findCertificateById(1L);
        assertEquals(CERTIFICATE_NAME, certificate.getName());
    }

    @Test
    @Sql(scripts = {"/data.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindByName() {
        Certificate certificate = certificateRepository.findCertificateById(1L);
        assertEquals(CERTIFICATE_NAME, certificate.getName());
    }

    @Test
    @Sql(scripts = {"/data.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSave() {
        Certificate certificate=createCertificate();
        Tag tag = tagRepository.save(certificate.getTags().get(0));
        Certificate certificateCreated= certificateRepository.save(certificate);
        assertEquals(CERTIFICATE_ID, certificateCreated.getId());
    }

    @Test
    @Sql(scripts = {"/data.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteById() {
        certificateRepository.deleteById(CERTIFICATE_ID_TO_DELETE);
        assertNull(certificateRepository.findCertificateById(1L));
    }

    private Certificate createCertificate() {
        String name = "name";
        String description = "description";
        double price = 10.0;
        Duration duration = Duration.ofDays(1);
        Certificate certificate = new Certificate();
        certificate.setName(name);
        certificate.setDescription(description);
        certificate.setPrice(price);
        certificate.setDuration(duration);
        Tag tag = new Tag();
        List<Tag> tags = new ArrayList<>();
        tag.setName("newTag");
        tags.add(tag);
        certificate.setTags(tags);
        return certificate;
    }

}