package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.UserInfoUserDetailsDTO;
import com.example.schoolmanagement.model.UserInfo;
import com.example.schoolmanagement.repository.UserInfoRepository;
import com.example.schoolmanagement.util.Constants;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    private final UserInfoRepository repository;

    public UserInfoUserDetailsService(final UserInfoRepository userInfoRepository) {
        this.repository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = this.repository.findByName(username);
        return userInfo.map(UserInfoUserDetailsDTO::new).orElseThrow(() -> new UsernameNotFoundException(Constants.NO_DATA_FOUND));
    }

}
