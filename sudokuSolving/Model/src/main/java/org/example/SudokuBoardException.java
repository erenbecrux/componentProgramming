package org.example;

public class SudokuBoardException extends SudokuException {
    public SudokuBoardException(String errorMessage, Throwable err) {
        super(errorMessage,err);
    }

}
