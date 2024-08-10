package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {
    SudokuBoard myBoard = new SudokuBoard(new BackTrackingSudokuSolver());

    @Test
    public void verifyTest() {
        myBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            SudokuColumn mySudokuColumn = myBoard.getColumn(i);
            int counter = 0;

            for (int j = 0; j < 9; j++) {
                counter += mySudokuColumn.getArrayPoint(j).getFieldValue();
            }

            assertEquals((counter == 45), mySudokuColumn.verifyColumn());
        }
    }

    @Test
    public void cloneTest(){

        SudokuColumn newSudokuColumn = new SudokuColumn();
        newSudokuColumn.setArrayPoint(2, 8);

        try {
            SudokuElement cloneElement = newSudokuColumn.clone();
            newSudokuColumn.setArrayPoint(2, 5);
            assertNotEquals(newSudokuColumn.getArrayPoint(2),cloneElement.getArrayPoint(2));
        }
        catch (Exception e) {
            throw new SudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneError"),e);
        }
    }

}