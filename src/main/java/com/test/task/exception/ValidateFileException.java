package com.test.task.exception;

import java.io.IOException;

public class ValidateFileException extends RuntimeException {

    public ValidateFileException(Throwable e) {
        super(e);
    }
}
