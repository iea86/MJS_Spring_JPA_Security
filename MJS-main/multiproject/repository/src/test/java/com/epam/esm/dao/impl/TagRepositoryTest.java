package com.epam.esm.dao.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {InitTestDataBaseConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class TagRepositoryTest {

    private final static Long TAG_ID= 1L;
    private final static Long TAG_ID_CREATED= 6L;
    private final static Long TAG_ID_TO_DELETE = 2L;
    private final static String TAG_NAME= "#test1";
    private final static int TAGS_COUNT= 5;

    @Autowired
    TagRepository tagRepository;

    @Test
    @Sql(scripts = {"/data.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindAll() {
        List<Tag> tags= tagRepository.findAll();
        assertEquals(TAGS_COUNT, tags.size());
    }

    @Test
    @Sql(scripts = {"/data.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindById() {
        Tag tag = tagRepository.findTagById(TAG_ID);
        assertEquals(TAG_NAME, tag.getName());
    }

    @Test
    @Sql(scripts = {"/data.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSave() {
        Tag tag=createTag();
        Tag tagCreated= tagRepository.save(tag);
        assertEquals(TAG_ID_CREATED, tagCreated.getId());
    }

    @Test
    @Sql(scripts = {"/data.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteById() {
        tagRepository.deleteById(TAG_ID_TO_DELETE);
        assertNull(tagRepository.findTagById(TAG_ID_TO_DELETE));
    }

    private Tag createTag() {
        String name = "name";
        Tag tag = new Tag();
        tag.setName(name);
        return tag;
    }
}
