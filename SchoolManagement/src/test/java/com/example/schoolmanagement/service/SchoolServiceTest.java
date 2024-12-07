package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolDTO;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SchoolServiceTest {

    private static final String EXISTING_ID = "107c4014-59a8-4807-b890-eb5a456ab198";
    private static final String NAME = " School Name";
    private static final String ADDRESS = " Address";
    private static final String PHONE = "0123654789";
    private static final String USER_NAME = "testUser";


    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private SchoolService schoolService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {

        SchoolDTO schoolDTO = new SchoolDTO();
        schoolDTO.setName(NAME);
        schoolDTO.setAddress(ADDRESS);
        schoolDTO.setPhone(PHONE);
        School school = new School();
        school.setName(NAME);
        school.setAddress(ADDRESS);
        school.setPhone(PHONE);

        when(schoolRepository.save(any(School.class))).thenReturn(school);

        ResponseDTO responseDTO = schoolService.create(schoolDTO);

        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatusValue());

    }

    @Test
    public void testRetrieve() {
        School school = mock(School.class);

        List<School> schools = school == null ? new ArrayList<>() : java.util.List.of(school);

        when(schoolRepository.findAll()).thenReturn(schools);

        ResponseDTO responseDTO = schoolService.retrieve();

        assertEquals(Constants.SUCCESS, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    public void testRetrieveById() {

        School school = new School();

        when(schoolRepository.findById(EXISTING_ID)).thenReturn(Optional.of(school));
        ResponseDTO responseDTO = schoolService.retrieveById(EXISTING_ID);

        assertNotNull(responseDTO);
        assertEquals(Constants.SUCCESS, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }


    @Test
    public void testRemove() {
        when(schoolRepository.existsById(EXISTING_ID)).thenReturn(true);

        ResponseDTO responseDTO = schoolService.remove(EXISTING_ID);

        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertEquals(EXISTING_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }


    @Test
    void testUpdate_Success() {

        School existingSchool = new School();
        existingSchool.setId(EXISTING_ID);
        existingSchool.setName(NAME);
        existingSchool.setAddress(ADDRESS);
        existingSchool.setPhone(PHONE);

        when(schoolRepository.findById(EXISTING_ID)).thenReturn(java.util.Optional.of(existingSchool));
        when(schoolRepository.save(existingSchool)).thenReturn(existingSchool);

        SchoolDTO schoolDTO = new SchoolDTO();
        schoolDTO.setName(NAME);
        schoolDTO.setAddress(ADDRESS);
        schoolDTO.setPhone(PHONE);
        ResponseDTO response = schoolService.update(EXISTING_ID, schoolDTO);

        assertEquals(HttpStatus.OK.getReasonPhrase(), response.getStatusValue());
        assertEquals(Constants.UPDATED, response.getMessage());
        assertNotNull(response.getData(), "Response data should not be null");
        assertEquals(NAME, existingSchool.getName());
        assertEquals(ADDRESS, existingSchool.getAddress());
        assertEquals(PHONE, existingSchool.getPhone());

        verify(schoolRepository, times(1)).save(existingSchool);
    }


}
