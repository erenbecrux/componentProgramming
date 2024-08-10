package org.example;

public class SudokuRow extends SudokuElement implements Cloneable {

    public boolean verifyRow() {
        return this.verifyArray();
    }

    @Override
    public SudokuRow clone() throws CloneNotSupportedException {
        return (SudokuRow) super.clone();
    }
}
