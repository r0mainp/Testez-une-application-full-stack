package com.openclassrooms.starterjwt.security.jwt;

import static org.mockito.Mockito.*;
import static org.springframework.security.core.userdetails.User.withUsername;
import static org.junit.jupiter.api.Assertions.*;


import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;

public class AuthTokenFilterTest {

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoFilterInternal() throws Exception {
        String jwt = "valid.jwt.token";
        String username = "testUser";
        UserDetails userDetails = withUsername(username).password("password").authorities("ROLE_USER").build();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtUtils.validateJwtToken(jwt)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(jwt)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtUtils).validateJwtToken(jwt);
        verify(jwtUtils).getUserNameFromJwtToken(jwt);
        verify(userDetailsService).loadUserByUsername(username);

        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(currentAuth);
        assertEquals(username, ((UserDetails) currentAuth.getPrincipal()).getUsername());
    }
}
