package com.uni.tech.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.uni.tech.entity.User;
import com.uni.tech.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String pin)throws UsernameNotFoundException {
        User user = userRepo.findByPin(pin);
        if(user==null){
            throw new UsernameNotFoundException("User not exists by Pin");
        }
        // Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("User"));
        //   return new org.springframework.security.core.userdetails.User(pin,user.getPassword(),authorities);

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getPin())
                        .password(user.getPassword())
                        .roles(roles.toArray(new String[0]))
                        .build();
        return userDetails;
    }
}