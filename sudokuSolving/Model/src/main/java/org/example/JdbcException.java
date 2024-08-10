package org.example;


public class JdbcException extends RuntimeException {
    public JdbcException(String errorMessage, Throwable err) {
        super(errorMessage,err);
    }

}
