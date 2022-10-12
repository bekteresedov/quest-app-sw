package com.questApp.service.impl;

import com.questApp.data.entity.User;
import com.questApp.data.repository.UserRepository;
import com.questApp.security.JwtUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailsServiceImpl implements UserDetailsService {


    final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepository.findByUserName(username);
        return JwtUserDetails.createjJwtUserDetails(user);
    }

    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {

        User user=userRepository.findById(userId).get();
        return JwtUserDetails.createjJwtUserDetails(user);
    }
}
