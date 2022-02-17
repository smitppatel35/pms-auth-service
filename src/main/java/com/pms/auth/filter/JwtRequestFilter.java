package com.pms.auth.filter;

import com.pms.auth.model.MyUserDetails;
import com.pms.auth.service.impl.MyUserDetailsServiceImpl;
import com.pms.auth.utils.JWTUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        try {
            if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
                jwtToken = requestHeader.substring(7);
                username = jwtUtils.extractUsername(jwtToken);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                try {
                    MyUserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                    if (jwtUtils.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } catch (UsernameNotFoundException e) {
                    logger.warn(e.getMessage());
                }
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", new Exception("Unable to get JWT Token"));
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", new Exception("JWT Token has expired"));
        } catch (BadCredentialsException e){
            request.setAttribute("exception", new Exception("Invalid Credentials"));
        } catch (Exception e){
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);

    }
}
