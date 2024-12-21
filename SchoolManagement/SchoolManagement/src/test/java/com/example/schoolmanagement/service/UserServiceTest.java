package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.UserSignUpRequestDTO;
import com.example.schoolmanagement.model.UserInfo;
import com.example.schoolmanagement.repository.UserInfoRepository;
import com.example.schoolmanagement.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "abc12@gmail.com";
    private static final String ROLES = "role";
    private static final int USER_ID = 1;
    @Mock
    UserInfoRepository userInfoRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserService userService;

    @Test
    public void testCreate() {

        UserSignUpRequestDTO userObject = new UserSignUpRequestDTO();
        userObject.setUsername(USER_NAME);
        userObject.setPassword(PASSWORD);
        userObject.setEmail(EMAIL);
        userObject.setRoles(ROLES);

        UserInfo user = mock(UserInfo.class);
        when(userInfoRepository.save(any(UserInfo.class))).thenReturn(user);

        ResponseDTO response = userService.create(userObject);

        assertNotNull(response);
        assertEquals(user, response.getData());
        assertEquals(Constants.CREATED, response.getMessage());
        assertEquals(HttpStatus.CREATED.name(), response.getStatusValue());
    }

    @Test
    public void testRetrieve() {

        UserInfo userInfo = mock(UserInfo.class);
        List<UserInfo> users = Stream.ofNullable(userInfo).toList();
        when(userInfoRepository.findAll()).thenReturn(users);

        ResponseDTO response = userService.retrieve();

        Object data = response.getData();
        assertEquals(data, users);
        assertEquals(Constants.RETRIEVED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue());
    }

    @Test
    public void testRetrieveById() {
        UserInfo user = mock(UserInfo.class);
        when(userInfoRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user));

        ResponseDTO responseDTO = userService.retrieveById(USER_ID);

        Object data = responseDTO.getData();
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    public void testRemove() {

        when(userInfoRepository.existsById(USER_ID)).thenReturn(true);

        ResponseDTO responseDTO = userService.removeById(USER_ID);
        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertEquals(USER_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }
}
