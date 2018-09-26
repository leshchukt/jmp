package com.epam.training.toto.util.exception;

import java.io.IOException;

public class DataCanNotBeReadException extends IOException {
    public DataCanNotBeReadException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "There is a trouble with the input file: " + super.getMessage();
    }
}
