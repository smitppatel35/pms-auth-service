package com.pms.auth.config;

import com.pms.auth.entity.User;
import com.pms.auth.repository.UserRepository;
import com.pms.auth.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatabaseTestConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AppUtils utils;

    // Inserting test data for testing
    @PostConstruct
    public void insertDummyData(){

        user1();
        user2();
    }

    private void user2() {
        List<String> list = new ArrayList<>();
        list.add("REP");

        User user = new User();

        user.setId(utils.generateRandomUUID());
        user.setUsername("rep2");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setAccountNonExpired(true);
        user.setEnabled(true);
        user.setAuthorities(list);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        userRepository.save(user);
    }

    private void user1() {
        List<String> list = new ArrayList<>();
        list.add("REP");

        User user = new User();

        user.setId(utils.generateRandomUUID());
        user.setUsername("rep6");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setAccountNonExpired(true);
        user.setEnabled(true);
        user.setAuthorities(list);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        userRepository.save(user);
    }

}
