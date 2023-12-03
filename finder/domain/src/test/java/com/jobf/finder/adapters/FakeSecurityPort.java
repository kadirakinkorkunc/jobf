package com.jobf.finder.adapters;

import com.jobf.finder.member.port.SecurityPort;

public class FakeSecurityPort implements SecurityPort {

    public static String INVALID_ACTUAL_PASSWORD = "invalidPass";

    @Override
    public String encryptText(String text) {
        return text + ".encoded";
    }

    @Override
    public boolean validate(String actual, String encrypted) {
        return !INVALID_ACTUAL_PASSWORD.contentEquals(actual);
    }
}
