package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BackTrackingSudokuSolver implements SudokuSolver, Serializable {

    @Override
    public void solve(SudokuBoard mySudokuBoard) {
        generateDiagonal(mySudokuBoard);
        sudokuBacktracker(mySudokuBoard);
    }

    private boolean sudokuBacktracker(SudokuBoard mySudokuBoard) {
        int currentRow = 0;
        int currentColumn = 0;
        boolean existsEmptyCell = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mySudokuBoard.getPoint(i,j) == 0) {
                    currentRow = i;
                    currentColumn = j;

                    existsEmptyCell = false;
                    break;
                }
            }
            if (!existsEmptyCell) {
                break;
            }
        }

        if (existsEmptyCell) {
            return true;
        } else {
            for (int num = 1; num <= 9; num++) {
                if (checkValid(currentRow, currentColumn, num, mySudokuBoard)) {
                    mySudokuBoard.setPoint(currentRow,currentColumn,num);

                    if (sudokuBacktracker(mySudokuBoard)) {
                        return true;
                    } else {
                        mySudokuBoard.setPoint(currentRow,currentColumn,0);
                    }
                }
            }
        }

        return false;

    }


    private void generateDiagonal(SudokuBoard mySudokuBoard) {
        List<Integer> nums = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            nums.addLast(i);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int rndmIndex = new Random().nextInt(nums.size());
                mySudokuBoard.setPoint(i,j,nums.get(rndmIndex));
                nums.remove(rndmIndex);
            }
        }

        List<Integer> nums2 = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            nums2.addLast(i);
        }

        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                int rndmIndex = new Random().nextInt(nums2.size());
                mySudokuBoard.setPoint(i,j,nums2.get(rndmIndex));
                nums2.remove(rndmIndex);

            }
        }

        List<Integer> nums3 = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            nums3.addLast(i);
        }

        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                int rndmIndex = new Random().nextInt(nums3.size());
                mySudokuBoard.setPoint(i,j,nums3.get(rndmIndex));
                nums3.remove(rndmIndex);
            }
        }

    }

    private Boolean checkValid(int row, int column, int newPoint, SudokuBoard mySudokuBoard) {

        for (int i = 0; i < 9; i++) {
            int currentPoint = mySudokuBoard.getPoint(row,i);
            if (currentPoint == newPoint) {
                return false;
            }
        }

        for (int j = 0; j < 9; j++) {
            int currentPoint = mySudokuBoard.getPoint(j,column);
            if (currentPoint == newPoint) {
                return false;
            }
        }

        // check for box!
        int modRow = row % 3;
        int modCol = column % 3;

        // if 0 then 2 right
        // if 1 then 1 left 1 right
        // if 2 then 2 left

        int currentRow = row - modRow;
        int currentCol = column - modCol;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mySudokuBoard.getPoint(currentRow,currentCol) == newPoint) {
                    return false;
                }

                currentCol++;
            }

            currentCol = column - modCol;
            currentRow++;
        }

        return true;
    }

    public Object solverError() throws SudokuBoardException {
        throw new SudokuBoardException(SudokuBoard.getLanguageVersion().getString("solverError"), new Error());
    }
}
