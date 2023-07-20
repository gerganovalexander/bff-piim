package com.tinqin.academy.bff.business.operations.authentication;

import com.tinqin.academy.bff.domain.ClientInterpreter;
import com.tinqin.academy.piim.api.errors.user.GetUserByEmailError;
import com.tinqin.academy.piim.api.user.getbyusername.GetUserByEmailInput;
import com.tinqin.academy.piim.api.user.getbyusername.GetUserByEmailResult;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientInterpreter clientInterpreter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Either<GetUserByEmailError, GetUserByEmailResult> userFromClient = clientInterpreter.getUserByEmail(
                GetUserByEmailInput.builder().email(email).build());
        GetUserByEmailResult result =
                userFromClient.getOrElseThrow(() -> new UsernameNotFoundException("oprei sa brat"));
        return User.builder()
                .username(result.getEmail())
                .password(result.getPassword())
                .build();
    }
}
