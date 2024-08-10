package org.example;

public class SudokuException extends RuntimeException {
    public SudokuException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}