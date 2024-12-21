package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.UserLoginRequestDTO;
import com.example.schoolmanagement.dto.UserSignUpRequestDTO;
import com.example.schoolmanagement.exception.ConflictException;
import com.example.schoolmanagement.exception.ResourceNotFoundException;
import com.example.schoolmanagement.exception.UserNotFoundException;
import com.example.schoolmanagement.model.UserInfo;
import com.example.schoolmanagement.repository.UserInfoRepository;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.UtilService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


    @Transactional
    public ResponseDTO create(final UserSignUpRequestDTO userSignUpRequestDTO) {
        validateUserDTO(userSignUpRequestDTO);
        final UserInfo user = UserInfo.builder().name(userSignUpRequestDTO.getUsername()).email(userSignUpRequestDTO.getEmail()).password(passwordEncoder.encode(userSignUpRequestDTO.getPassword())).roles(userSignUpRequestDTO.getRoles()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.userInfoRepository.save(user)).statusValue(HttpStatus.CREATED.name()).build();
    }

    public ResponseDTO login(final UserLoginRequestDTO userLoginRequestDTO) throws UserNotFoundException {
        try {
            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getUsername(), userLoginRequestDTO.getPassword()));

            if (authentication.isAuthenticated()) {
                String accessToken = jwtService.generateToken(userLoginRequestDTO.getUsername());
                return ResponseDTO.builder().message(Constants.SUCCESS).data(accessToken).statusValue(HttpStatus.OK.getReasonPhrase()).build();
            } else {
                return ResponseDTO.builder().message(Constants.CREDENTIALS_MISMATCH).statusValue(HttpStatus.UNAUTHORIZED.getReasonPhrase()).build();
            }
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException(Constants.CREDENTIALS_MISMATCH);
        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }


    private void validateUserDTO(final UserSignUpRequestDTO userSignUpRequestDTO) {
        if (!UtilService.emailValidation(userSignUpRequestDTO.getEmail())) {
            throw new InputMismatchException(Constants.REGEX_MISS_MATCH);
        }
        Optional<UserInfo> user = this.userInfoRepository.findByEmail(userSignUpRequestDTO.getEmail());
        if (user.isPresent()) {
            throw new ConflictException(Constants.EXISTING_EMAIL);
        }
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.userInfoRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveById(final Integer id) {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.userInfoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND))).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeById(final Integer id) {
        if (!this.userInfoRepository.existsById(id)) {
            throw new ResourceNotFoundException(Constants.DATA_NOT_FOUND);
        }
        this.userInfoRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.REMOVED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public  ResponseDTO welcome(){
        return ResponseDTO.builder()
                .message(Constants.SUCCESS)
                .data("Welcome")
                .statusValue(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
