package com.jobf.finder.member.port;

public interface SecurityPort {

    String encryptText(String text);

    boolean validate(String actual, String encrypted);
}
