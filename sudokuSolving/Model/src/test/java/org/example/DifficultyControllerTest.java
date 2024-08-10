package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyControllerTest {



    @Test
    public void createLevelTest() {
        SudokuBoard firstBoard = new SudokuBoard(new BackTrackingSudokuSolver());
        SudokuBoard secondBoard = new SudokuBoard(new BackTrackingSudokuSolver());
        SudokuBoard thirdBoard = new SudokuBoard(new BackTrackingSudokuSolver());
        DifficultyController firstController = new DifficultyController(DiffLevel.easy);
        DifficultyController secondController = new DifficultyController(DiffLevel.medium);
        DifficultyController thirdController = new DifficultyController(DiffLevel.hard);

        firstBoard.solveGame();
        secondBoard.solveGame();
        thirdBoard.solveGame();

        firstController.createLevel(firstBoard);
        secondController.createLevel(secondBoard);
        thirdController.createLevel(thirdBoard);

        int emptyFirst = 0;
        int emptySecond = 0;
        int emptyThird = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (firstBoard.getPoint(i,j) == 0) {
                    emptyFirst++;
                }

                if (secondBoard.getPoint(i,j) == 0) {
                    emptySecond++;
                }

                if (thirdBoard.getPoint(i,j) == 0) {
                    emptyThird++;
                }
            }
        }

        boolean flag1 = emptyFirst < emptySecond;
        boolean flag2 = emptySecond < emptyThird;
        assertTrue(flag1);
        assertTrue(flag2);

    }
}