package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.*;


public class SudokuBoard implements Cloneable, Serializable {

    private ArrayList<List<SudokuField>> board;
    private final SudokuSolver mySolver;
    public static ResourceBundle languageVersion;

    public SudokuBoard(SudokuSolver solver) {

        languageVersion = ResourceBundle.getBundle("resource_" + Locale.getDefault().getLanguage());
        this.board = new ArrayList<List<SudokuField>>();

        for (int i = 0; i < 9; i++) {
            List<SudokuField> newFieldArray = Arrays.asList(new SudokuField[9]);
            for (int j = 0; j < 9; j++) {
                SudokuField newField = new SudokuField();
                newField.setFieldValue(0);
                newFieldArray.set(j,newField);
            }

            this.board.add(newFieldArray);
        }

        this.mySolver = solver;
    }

    public boolean solveGame() {
        mySolver.solve(this);

        return checkBoard();
    }

    public static ResourceBundle getLanguageVersion() {
        return languageVersion;
    }

    public int getPoint(int row, int column) {
        return this.board.get(row).get(column).getFieldValue();
    }

    public void setPoint(int row, int column, int value) {
        this.board.get(row).get(column).setFieldValue(value);
    }

    public SudokuRow getRow(int x) {
        SudokuRow currentRow = new SudokuRow();

        for (int i = 0; i < 9; i++) {
            currentRow.setArrayPoint(i,board.get(x).get(i).getFieldValue());
        }

        return currentRow;
    }

    public SudokuColumn getColumn(int x) {
        SudokuColumn currentColumn = new SudokuColumn();

        for (int i = 0; i < 9; i++) {
            currentColumn.setArrayPoint(i,board.get(i).get(x).getFieldValue());
        }

        return currentColumn;
    }

    public SudokuBox getBox(int x, int y) {
        SudokuBox currentBox = new SudokuBox();

        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                currentBox.setArrayPoint(counter,board.get(x * 3 + i).get(y * 3 + j).getFieldValue());
                counter++;
            }
        }

        return currentBox;
    }

    public boolean checkBoard() {

        boolean currentState = true;

        for (int i = 0; i < 9; i++) {

            // check row
            SudokuRow currentRow = getRow(i);
            if (currentRow.verifyRow() == false) {
                currentState = false;
            }

            // check column
            SudokuColumn currentColumn = getColumn(i);
            if (currentColumn.verifyColumn() == false) {
                currentState = false;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                //check box
                SudokuBox currentBox = getBox(i,j);
                if (currentBox.verifyBox() == false) {
                    currentState = false;
                }
            }
        }

        return currentState;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(this.board.get(i).get(j).toString());
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof SudokuBoard)) {
            return false;
        }

        SudokuBoard otherBoard = (SudokuBoard) obj;

        return new EqualsBuilder().append(this.board,otherBoard.board).isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        for (List<SudokuField> innerList : this.board) {
            int innerListHashCode = new HashCodeBuilder().append(innerList).toHashCode();
            builder.append(innerListHashCode);
        }

        return builder.toHashCode();

        //return new HashCodeBuilder().append(this.board).toHashCode();
    }

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        SudokuBoard clone = (SudokuBoard) super.clone();
        ArrayList<List<SudokuField>> newBoard = new ArrayList<List<SudokuField>>();

        for (int i = 0; i < 9; i++) {
            List newFieldArray = new ArrayList<SudokuField>();

            for (int j = 0; j < 9; j++) {
                SudokuField current = this.board.get(i).get(j).clone();
                newFieldArray.add(j,current);
            }
            newBoard.add(newFieldArray);
        }
        clone.board = newBoard;
        return clone;
    }

}
