package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoxTest {

    SudokuBoard myBoard = new SudokuBoard(new BackTrackingSudokuSolver());

    @Test
    public void verifyTest() {
        myBoard.solveGame();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                SudokuBox mySudokuBox = myBoard.getBox(i,j);
                int counter = 0;

                for (int k = 0; k < 9; k++) {
                    counter += mySudokuBox.getArrayPoint(k).getFieldValue();
                }

                assertEquals((counter == 45), mySudokuBox.verifyBox());
            }
        }
    }

    @Test
    public void cloneTest(){

        SudokuBox newSudokuBox = new SudokuBox();
        newSudokuBox.setArrayPoint(2, 8);

        try {
            SudokuBox cloneElement = newSudokuBox.clone();
            newSudokuBox.setArrayPoint(2, 5);
            assertNotEquals(newSudokuBox.getArrayPoint(2),cloneElement.getArrayPoint(2));
        }
        catch (Exception e) {
            throw new SudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneError"),e);
        }
    }
}