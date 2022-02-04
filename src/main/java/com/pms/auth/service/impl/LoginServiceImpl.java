package com.pms.auth.service.impl;

import com.pms.auth.dto.JwtTokenResponseDTO;
import com.pms.auth.dto.Login;
import com.pms.auth.dto.RepresentativeResponse;
import com.pms.auth.entity.User;
import com.pms.auth.model.MyUserDetails;
import com.pms.auth.repository.UserRepository;
import com.pms.auth.service.LoginService;
import com.pms.auth.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsServiceImpl userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTUtils jwtUtils;

    @Override
    public JwtTokenResponseDTO login(Login login) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );

            MyUserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());

            String token = jwtUtils.createToken(userDetails);

            return new JwtTokenResponseDTO(token, jwtUtils.extractExpiration(token));

        } catch (Exception e){
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    @Override
    public List<RepresentativeResponse> getAllRepresentatives() {
        Iterable<User> userIterable = userRepository.findAll();

        List<RepresentativeResponse> list = new ArrayList<>();

        userIterable.forEach(user -> {
            list.add(new RepresentativeResponse(user.getId(), user.getUsername()));
        });

        return list;
    }
}
