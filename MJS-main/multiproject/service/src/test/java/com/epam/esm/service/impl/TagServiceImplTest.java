package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.validator.TagBusinessValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TagServiceImplTest {

    private final static Long TAG_ID = 1L;

    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagBusinessValidator tagBusinessValidator;

    @InjectMocks
    private TagServiceImpl tagService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteTag() throws BusinessValidationException {
        when(tagRepository.findTagById(TAG_ID)).thenReturn(new Tag());
        doNothing().when(tagBusinessValidator).validateIfExistsById(TAG_ID);
        doNothing().when(tagRepository).deleteById(TAG_ID);
        tagService.delete(TAG_ID);
        verify(tagRepository, times(1)).deleteById(TAG_ID);
    }

    @Test
    public void testRead() {
        Tag tag = createTag();
        when(tagRepository.findTagById(TAG_ID)).thenReturn(tag);
        Tag tagActual = tagService.get(TAG_ID);
        assertEquals(tag, tagActual);
        verify(tagRepository, times(1)).findTagById(TAG_ID);
    }

    @Test
    public void testCreate() throws BusinessValidationException {
        Tag tag = createTag();
        doNothing().when(tagBusinessValidator).validateIfExistsByName(tag.getName());
        when(tagRepository.save(tag)).thenReturn(tag);
        tagService.create(tag);
        verify(tagRepository, times(1)).save(tag);
    }

    @Test(expected = BusinessValidationException.class)
    public void whenCreateExistingTag() throws BusinessValidationException {
        Tag tag = createTag();
        doThrow(BusinessValidationException.class).when(tagBusinessValidator).validateIfExistsByName(tag.getName());
        tagService.create(tag);
    }

    @Test
    public void testUpdate()  {
        Tag tag = createTag();
        tagService.update(tag);
    }

    @Test
    public void testFind() {
        Tag tag = createTag();
        when(tagRepository.findByName("name")).thenReturn(tag);
        tagService.find(tag);
        verify(tagRepository, times(1)).findByName(tag.getName());
    }

    @Test
    public void testGetAllTags() {
        List<Tag> tags = createListOfTag();
        when(tagRepository.findAll()).thenReturn(tags);
        tagService.getAllTags();
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    public void testThWidelyUsedTag() {
        Tag tag = createTag();
        when(tagRepository.findTopByCertificatesMatches()).thenReturn(tag);
        tagService.getThWidelyUsedTag();
        verify(tagRepository, times(1)).findTopByCertificatesMatches();
    }

    @Test
    public void testGetTags() {
        Page<Tag> tags = mock(Page.class);
        when(tagRepository.findAllBy(null)).thenReturn(tags);
        tagService.getAllTags(null);
        verify(tagRepository, times(1)).findAllBy(null);
    }

    private Tag createTag() {
        Tag tag = new Tag();
        tag.setId(0L);
        tag.setName("tag");
        return tag;
    }

    private List<Tag> createListOfTag() {
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(0L);
        tag1.setName("tag1");
        tags.add(tag1);
        Tag tag2 = new Tag();
        tag2.setId(1L);
        tag2.setName("tag2");
        tags.add(tag2);
        return tags;
    }
}