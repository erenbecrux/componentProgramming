package org.example;

public class SudokuColumn extends SudokuElement implements Cloneable {

    public boolean verifyColumn() {
        return this.verifyArray();
    }

    @Override
    public SudokuColumn clone() throws CloneNotSupportedException {
        return (SudokuColumn) super.clone();
    }
}
