package com.pms.auth.controller;

import com.pms.auth.dto.Login;
import com.pms.auth.dto.RepresentativeResponse;
import com.pms.auth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AppController {

    @Autowired
    private LoginService loginService;

    // hello
    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return ResponseEntity.ok("Hello");
    }

    /**
     * Provides Login route for pharmacy stock management system
     *
     * @param login
     * @return JWT token with expiry date of token
     */

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody Login login) {
        try {
            return new ResponseEntity<>(loginService.login(login), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid Credentials", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("users")
    public ResponseEntity<?> getAllRepresentatives() {
        List<RepresentativeResponse> rep = loginService.getAllRepresentatives();

        return ResponseEntity.ok(rep);
    }
}
