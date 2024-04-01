package ru.melnikov.computershop.service.user;

import ru.melnikov.computershop.dto.user.JwtAuthenticationResponse;
import ru.melnikov.computershop.dto.user.SignInRequest;
import ru.melnikov.computershop.dto.user.SignUpRequest;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);


}
