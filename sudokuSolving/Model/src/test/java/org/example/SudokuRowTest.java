package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {

    SudokuBoard myBoard = new SudokuBoard(new BackTrackingSudokuSolver());

    @Test
    public void verifyTest() {
        myBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            SudokuRow mySudokuRow = myBoard.getRow(i);
            int counter = 0;

            for (int j = 0; j < 9; j++) {
                counter += mySudokuRow.getArrayPoint(j).getFieldValue();
            }

            assertEquals((counter == 45), mySudokuRow.verifyRow());
        }
    }
    @Test
    public void cloneTest(){

        SudokuRow newSudokuRow = new SudokuRow();
        newSudokuRow.setArrayPoint(2, 8);

        try {
            SudokuRow cloneElement = newSudokuRow.clone();
            newSudokuRow.setArrayPoint(2, 5);
            assertNotEquals(newSudokuRow.getArrayPoint(2),cloneElement.getArrayPoint(2));
        }
        catch (Exception e) {
            throw new SudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneError"),e);
        }
    }

}