package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionDTO;
import com.example.schoolmanagement.model.Section;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTest {

    private static final String STANDARD_ID = "";
    private static final String SECTION_ID = "";
    private static final String SECTION_NAME = "";

    @Mock
    SectionRepository sectionRepository;

    @Mock
    StandardRepository standardRepository;

    @InjectMocks
    SectionService sectionService;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    public void create() {

        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setStandardId(STANDARD_ID);
        sectionDTO.setName(SECTION_NAME);

        Standard standard = mock(Standard.class);
        standard.setId(STANDARD_ID);

        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.of(standard));

        Section section = mock(Section.class);
        when(sectionRepository.save(any(Section.class))).thenReturn(section);

        ResponseDTO response = sectionService.create(sectionDTO);

        Object data = response.getData();
        assertEquals(data, section);
        assertEquals(Constants.CREATED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue());


    }

    @Test
    public void testRetrieve() {

        Section section = mock(Section.class);

        List<Section> sections = Stream.ofNullable(section).toList();
        when(sectionRepository.findAll()).thenReturn(sections);

        ResponseDTO response = sectionService.retrieve();

        Object data = response.getData();
        assertEquals(data, sections);
        assertEquals(Constants.RETRIEVED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue());
    }

    @Test
    public void testRetrieveById() {
        Section section = mock(Section.class);

        when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.ofNullable(section));

        ResponseDTO responseDTO = sectionService.retrieveById(SECTION_ID);

        Object data = responseDTO.getData();
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }


    @Test
    public void testRemove() {

        when(sectionRepository.existsById(SECTION_ID)).thenReturn(true);

        ResponseDTO responseDTO = sectionService.remove(SECTION_ID);
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals(SECTION_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }

    @Test
    public void testUpdate() {

        SectionDTO sectionDTO = mock(SectionDTO.class);

        Section section = mock(Section.class);

        when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.ofNullable(section));
        assert section != null;
        when(sectionRepository.save(section)).thenReturn(section);

        ResponseDTO response = sectionService.update(SECTION_ID, sectionDTO);

        assertEquals(response.getData(), section);


    }
}