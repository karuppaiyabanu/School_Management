package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SubjectDTO;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.model.Subject;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.SubjectRepository;
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
public class SubjectServiceTest {

    private static final String STANDARD_ID = "1";
    private static final String SUBJECT_ID = "sub-1";
    @Mock
    StandardRepository standardRepository;
    @Mock
    SubjectRepository subjectRepository;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private SubjectService subjectService;

    @Test
    void testCreate() {

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setStandard(STANDARD_ID);
        subjectDTO.setName("");

        Standard mockStandard = mock(Standard.class);
        mockStandard.setId(STANDARD_ID);
        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.of(mockStandard));

        Subject mockSubject = mock(Subject.class);
        when(subjectRepository.save(any(Subject.class))).thenReturn(mockSubject);

        ResponseDTO response = subjectService.create(subjectDTO);

        assertNotNull(response);
        Object data = response.getData();
        assertEquals(data, mockSubject);
        assertEquals(Constants.CREATED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.CREATED.name(), response.getStatusValue());

    }

    @Test
    public void testRetrieve() {
        Subject subject = mock(Subject.class);

        List<Subject> subjects = Stream.ofNullable(subject).toList();
        when(subjectRepository.findAll()).thenReturn(subjects);

        ResponseDTO response = subjectService.retrieve();

        Object data = response.getData();
        assertEquals(data, subjects);
        assertEquals(Constants.SUCCESS, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue());
    }


    @Test
    public void testUpdate() {
        Subject subject = mock(Subject.class);
        SubjectDTO subjectDTO = mock(SubjectDTO.class);

        when(subjectRepository.findById(SUBJECT_ID)).thenReturn(Optional.ofNullable(subject));
        assert subject != null;
        when(subjectRepository.save(subject)).thenReturn(subject);

        ResponseDTO response = subjectService.update(SUBJECT_ID, subjectDTO);

        assertEquals(response.getData(), subject);
    }

    @Test
    public void testRemove() {

        when(subjectRepository.existsById(SUBJECT_ID)).thenReturn(true);

        ResponseDTO responseDTO = subjectService.remove(SUBJECT_ID);
        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertEquals(SUBJECT_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }

}
