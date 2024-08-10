package org.example;

    public class NullPointerFieldException extends SudokuException {
    public NullPointerFieldException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
