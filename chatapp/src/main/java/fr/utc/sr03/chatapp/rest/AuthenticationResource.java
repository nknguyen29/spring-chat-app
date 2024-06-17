package fr.utc.sr03.chatapp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.utc.sr03.chatapp.model.AuthenticationRequest;
import fr.utc.sr03.chatapp.model.AuthenticationResponse;
import fr.utc.sr03.chatapp.model.HttpUserDetails;
import fr.utc.sr03.chatapp.service.HttpUserDetailsService;
import fr.utc.sr03.chatapp.service.JwtTokenService;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationResource {

    private final HttpUserDetailsService httpUserDetailsService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResource(final HttpUserDetailsService httpUserDetailsService,
            final JwtTokenService jwtTokenService,
            final AuthenticationManager authenticationManager) {
        this.httpUserDetailsService = httpUserDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public AuthenticationResponse authenticate(@RequestBody @Valid final AuthenticationRequest authenticationRequest) {
        final String email = authenticationRequest.getEmail();
        final String password = authenticationRequest.getPassword();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        final HttpUserDetails userDetails = httpUserDetailsService.loadUserByUsername(email);

        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtTokenService.generateToken(userDetails));
        return authenticationResponse;
    }

}
