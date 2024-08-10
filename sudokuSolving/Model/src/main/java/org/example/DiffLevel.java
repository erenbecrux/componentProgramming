package org.example;

import java.util.Random;

public enum DiffLevel {
    easy(15),
    medium(40),
    hard(70);

    private int loopCount;

    DiffLevel(int count) {
        this.loopCount = count;
    }

    public void createLevel(SudokuBoard sudokuBoard) {

        for (int i = 0; i < loopCount; i++) {
            int row = new Random().nextInt(0,9);
            int column = new Random().nextInt(0,9);

            sudokuBoard.setPoint(row,column,0);
        }
    }
}
