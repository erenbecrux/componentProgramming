package org.example;

public class DifficultyController {
    public DiffLevel currentLevel;

    public DifficultyController(DiffLevel lvl) {
        this.currentLevel = lvl;
    }

    public void createLevel(SudokuBoard sudokuBoard) {
        currentLevel.createLevel(sudokuBoard);
    }
}
