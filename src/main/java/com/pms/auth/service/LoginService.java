package com.pms.auth.service;

import com.pms.auth.dto.JwtTokenResponseDTO;
import com.pms.auth.dto.Login;
import com.pms.auth.dto.RepresentativeResponse;

import java.util.List;

public interface LoginService {

    JwtTokenResponseDTO login(Login login);

    List<RepresentativeResponse> getAllRepresentatives();
}
