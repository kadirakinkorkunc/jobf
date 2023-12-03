package com.jobf.finder.adapters.member.security;

import com.jobf.finder.member.port.SecurityPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityDataAdapter implements SecurityPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encryptText(String text) {
        return passwordEncoder.encode(text);
    }

    @Override
    public boolean validate(String actual, String encrypted) {
        return passwordEncoder.matches(actual, encrypted);
    }
}
