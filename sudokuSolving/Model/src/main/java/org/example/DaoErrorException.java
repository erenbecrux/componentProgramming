package org.example;

public class DaoErrorException extends RuntimeException {
    public DaoErrorException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
