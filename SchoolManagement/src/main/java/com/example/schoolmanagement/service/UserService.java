package com.example.schoolmanagement.service;


import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.UserLoginRequestDTO;
import com.example.schoolmanagement.dto.UserSignUpRequestDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.UserInfo;
import com.example.schoolmanagement.repository.UserInfoRepository;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.UtilService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(final UserInfoRepository userInfoRepository, final PasswordEncoder passwordEncoder, final JwtService jwtService, final AuthenticationManager authenticationManager) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public ResponseDTO create(final UserSignUpRequestDTO userSignUpRequestDTO) {
        validateUserDTO(userSignUpRequestDTO);
        final UserInfo user = UserInfo.builder().name(userSignUpRequestDTO.getUsername()).email(userSignUpRequestDTO.getEmail()).password(passwordEncoder.encode(userSignUpRequestDTO.getPassword())).roles(userSignUpRequestDTO.getRoles()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.userInfoRepository.save(user)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO login(final UserLoginRequestDTO userLoginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getUsername(), userLoginRequestDTO.getPassword()));

        if (authentication.isAuthenticated()) {
            String accessToken = jwtService.generateToken(userLoginRequestDTO.getUsername());
            return ResponseDTO.builder().message(Constants.SUCCESS).data(accessToken).statusValue(HttpStatus.OK.getReasonPhrase()).build();
        }
        return ResponseDTO.builder().message(Constants.CREDENTIALS_MISMATCH).message(userLoginRequestDTO.getUsername()).statusValue(HttpStatus.UNAUTHORIZED.getReasonPhrase()).build();
    }

    private UserSignUpRequestDTO validateUserDTO(final UserSignUpRequestDTO userSignUpRequestDTO) {
        if (!UtilService.emailValidation(userSignUpRequestDTO.getEmail())) {
            throw new InputMismatchException(Constants.REGEX_MISS_MATCH);
        }
        Optional<UserInfo> user = this.userInfoRepository.findByEmail(userSignUpRequestDTO.getEmail());
        if (user.isPresent()) {
            throw new BadRequestServiceException(Constants.EXISTING_EMAIL);
        }
        return userSignUpRequestDTO;
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.userInfoRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveById(final Integer id) {
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.userInfoRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND))).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeById(final Integer id) {
        if (!this.userInfoRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.NO_DATA_FOUND);
        }
        this.userInfoRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.REMOVED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
