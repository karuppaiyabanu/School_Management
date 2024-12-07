package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StandardDTO;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.repository.SchoolRepository;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StandardServiceTest {

    private static final String SCHOOL_ID = "school-1";
    private static final String STANDARD_NAME = "Standard 1";
    private static final double FEES = 1000.0;
    private static final String STANDARD_ID = "";
    @Mock
    StandardRepository standardRepository;
    @Mock
    SchoolRepository schoolRepository;
    @InjectMocks
    StandardService standardService;
    @Mock
    private AuthenticationService authenticationService;


    @Test
    public void testCreate() {

        StandardDTO standardDTO = new StandardDTO();
        standardDTO.setSchoolId(SCHOOL_ID);
        standardDTO.setStandardName(STANDARD_NAME);
        standardDTO.setFees(FEES);

        School mockSchool = mock(School.class);
        mockSchool.setId(SCHOOL_ID);
        when(schoolRepository.findById(SCHOOL_ID)).thenReturn(Optional.of(mockSchool));

        Standard mockStandard = mock(Standard.class);
        when(standardRepository.save(any(Standard.class))).thenReturn(mockStandard);

        ResponseDTO response = standardService.create(standardDTO);

        Object data = response.getData();
        assertEquals(data, mockStandard);
        assertEquals(Constants.CREATED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue());

    }

    @Test
    public void testRetrieve() {

        Standard standard = mock(Standard.class);

        List<Standard> standards = Stream.ofNullable(standard).toList();
        when(standardRepository.findAll()).thenReturn(standards);

        ResponseDTO response = standardService.retrieve();

        Object data = response.getData();
        assertEquals(data, standards);
        assertEquals(Constants.RETRIEVED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue());
    }

    @Test
    public void testRetrieveById() {
        Standard standard = mock(Standard.class);

        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.ofNullable(standard));

        ResponseDTO responseDTO = standardService.retrieveById(STANDARD_ID);

        Object data = responseDTO.getData();
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }


    @Test
    public void testRemove() {

        when(standardRepository.existsById(STANDARD_ID)).thenReturn(true);

        ResponseDTO responseDTO = standardService.remove(STANDARD_ID);
        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertEquals(STANDARD_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }

    @Test
    public void testUpdate() {
        StandardDTO standardDTO = mock(StandardDTO.class);

        Standard standard = mock(Standard.class);

        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.ofNullable(standard));
        assert standard != null;
        when(standardRepository.save(standard)).thenReturn(standard);

        ResponseDTO response = standardService.update(STANDARD_ID, standardDTO);

        assertEquals(response.getData(), standard);

    }
}

