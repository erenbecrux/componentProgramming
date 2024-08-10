package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuBoard {

    private int[][] board;

    public SudokuBoard() {
        this.board = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = 0;
            }
        }

    }

    public void fillBoard() {
        generateDiagonal();

        if (sudokuBacktracker()) {
            //printBoard();
        }
    }

    private boolean sudokuBacktracker() {
        int currentRow = 0;
        int currentColumn = 0;
        boolean existsEmptyCell = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.board[i][j] == 0) {
                    currentRow = i;
                    currentColumn = j;

                    existsEmptyCell = false;
                    //printBoard();
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
                if (checkValid(currentRow, currentColumn, num)) {
                    this.board[currentRow][currentColumn] = num;

                    if (sudokuBacktracker()) {
                        return true;
                    } else {
                        this.board[currentRow][currentColumn] = 0;
                    }
                }
            }
        }

        return false;

    }

    private void printBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(this.board[i][j]);
            }

            System.out.println("\n");
        }

        System.out.print("----------------------------------\n");
    }

    private void generateDiagonal() {
        List<Integer> nums = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            nums.addLast(i);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int rndmIndex = new Random().nextInt(nums.size());
                this.board[i][j] = nums.get(rndmIndex);
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
                this.board[i][j] = nums2.get(rndmIndex);
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
                this.board[i][j] = nums3.get(rndmIndex);
                nums3.remove(rndmIndex);
            }
        }

    }


    public int getPoint(int row, int column) {
        return this.board[row][column];
    }

    private Boolean checkValid(int row, int column, int newPoint) {

        for (int i = 0; i < 9; i++) {
            int currentPoint = getPoint(row,i);
            if (currentPoint == newPoint) {
                return false;
            }
        }

        for (int j = 0; j < 9; j++) {
            int currentPoint = getPoint(j, column);
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
                if (this.board[currentRow][currentCol] == newPoint) {
                    return false;
                }

                currentCol++;
            }

            currentCol = column - modCol;
            currentRow++;
        }

        return true;
    }
}
