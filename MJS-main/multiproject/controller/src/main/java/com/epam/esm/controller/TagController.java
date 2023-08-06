package com.epam.esm.controller;

import com.epam.esm.response.InfoResponse;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.DataGenerator;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("tags")
public class TagController {

    private TagService tagService;
    private DataGenerator dataGeneratorService;
    private MessageSource messageSource;

    @Autowired
    public TagController(TagService tagService, DataGenerator dataGeneratorService, MessageSource messageSource) {
        this.tagService = tagService;
        this.dataGeneratorService = dataGeneratorService;
        this.messageSource = messageSource;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('role_admin')")
    public HttpEntity<List<TagDTO>> getTags(Pageable pageable, Locale locale) throws ServiceException, BusinessValidationException {
        PaginationValidator.validatePagination(pageable, locale);
        List<TagDTO> tagsDTO = tagService.getAllTags(pageable)
                .stream()
                .map(TagConverter::convertToDto)
                .collect(Collectors.toList());
        addTagsHATEOAS(tagsDTO, locale, pageable);
        return ResponseEntity.ok(tagsDTO);
    }

    @GetMapping("/{tagId}")
    @PreAuthorize("hasAuthority('role_admin')")
    public HttpEntity<TagDTO> getTag(@PathVariable Long tagId, Locale locale, Pageable pageable) throws ServiceException, BusinessValidationException {
        TagDTO tagDTO = TagConverter.convertToDto(tagService.get(tagId));
        addTagHATEOAS(tagDTO, locale, pageable);
        return ResponseEntity.ok(tagDTO);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('role_admin')")
    public InfoResponse addTag(@RequestBody TagDTO tagDTO, Locale locale) throws ServiceException, BusinessValidationException {
        TagValidator.validateTag(tagDTO, locale);
        Tag tag = TagConverter.convertToEntity(tagDTO);
        Long tagId = tagService.create(tag);
        String message = messageSource.getMessage("label.tag.created", null, locale);
        return new InfoResponse(
                HttpStatus.CREATED.value(),
                message + ":" + tagId,
                HttpStatus.CREATED.toString());
    }

    @DeleteMapping("/{tagId}")
    @PreAuthorize("hasAuthority('role_admin')")
    public InfoResponse deleteTag(@PathVariable Long tagId, Locale locale) throws ServiceException, BusinessValidationException {
        tagService.delete(tagId);
        String message = messageSource.getMessage("label.tag.deleted", null, locale);
        return new InfoResponse(
                HttpStatus.NO_CONTENT.value(),
                message + ":" + tagId,
                HttpStatus.NO_CONTENT.toString());
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('role_admin')")
    public HttpEntity<TagDTO> getStats(Locale locale, Pageable pageable) throws ServiceException, BusinessValidationException {
        Tag tag = tagService.getThWidelyUsedTag();
        TagDTO tagDTO = TagConverter.convertToDto(tag);
        addTagHATEOAS(tagDTO, locale, pageable);
        return ResponseEntity.ok(tagDTO);
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('role_admin')")
    public InfoResponse generateTags(@RequestParam(value = "count") int count, Locale locale) throws ServiceException {
        dataGeneratorService.generateTags(count);
        int numberOfRows = tagService.getAllTags().size();
        String message = messageSource.getMessage("label.tags.total.rows", null, locale);
        return new InfoResponse(
                HttpStatus.OK.value(),
                message + ":" + numberOfRows,
                HttpStatus.OK.toString());
    }

    private void addTagsHATEOAS(List<TagDTO> tagsDTO, Locale locale, Pageable pageable) throws ServiceException, BusinessValidationException {
        for (TagDTO tagDTO : tagsDTO) {
            tagDTO.add(linkTo(methodOn(TagController.class).getTag(tagDTO.getId(), locale, pageable)).withSelfRel());
            tagDTO.add(linkTo(methodOn(TagController.class).deleteTag(tagDTO.getId(), locale)).withRel("delete"));
        }
    }

    private void addTagHATEOAS(TagDTO tagDTO, Locale locale, Pageable pageable) throws ServiceException, BusinessValidationException {
        tagDTO.add(linkTo(methodOn(TagController.class).getTag(tagDTO.getId(), locale, pageable)).withSelfRel());
        tagDTO.add(linkTo(methodOn(TagController.class).deleteTag(tagDTO.getId(), locale)).withRel("delete"));
        tagDTO.add(linkTo(methodOn(TagController.class).getTags(pageable, locale)).withRel("tags"));
    }
}
