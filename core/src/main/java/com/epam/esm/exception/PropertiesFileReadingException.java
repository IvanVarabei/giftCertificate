package com.epam.esm.exception;

public class PropertiesFileReadingException extends RuntimeException{
    public PropertiesFileReadingException() {
        super();
    }

    public PropertiesFileReadingException(String message) {
        super(message);
    }

    public PropertiesFileReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesFileReadingException(Throwable cause) {
        super(cause);
    }
}
