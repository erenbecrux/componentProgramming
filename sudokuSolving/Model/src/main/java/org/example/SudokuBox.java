package org.example;

public class SudokuBox extends SudokuElement implements Cloneable {

    public boolean verifyBox() {
        return this.verifyArray();
    }

    @Override
    public SudokuBox clone() throws CloneNotSupportedException {
        return (SudokuBox) super.clone();
    }
}
