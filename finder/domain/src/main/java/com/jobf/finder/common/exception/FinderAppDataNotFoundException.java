package com.jobf.finder.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FinderAppDataNotFoundException extends RuntimeException {

    String key;
    String[] args;

    public FinderAppDataNotFoundException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public FinderAppDataNotFoundException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
