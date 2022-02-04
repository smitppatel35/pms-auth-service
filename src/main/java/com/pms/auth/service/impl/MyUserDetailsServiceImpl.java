package com.pms.auth.service.impl;

import com.pms.auth.entity.User;
import com.pms.auth.model.MyUserDetails;
import com.pms.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new MyUserDetails(user);
    }

    public MyUserDetails loadUserById(String id) throws UsernameNotFoundException{
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return new MyUserDetails(user.get());
        }else{
            throw new UsernameNotFoundException("User Not Found.");
        }
    }
}
