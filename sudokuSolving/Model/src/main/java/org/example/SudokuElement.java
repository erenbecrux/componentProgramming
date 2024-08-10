package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;
import java.util.List;

public class SudokuElement implements Cloneable {

    protected List<SudokuField> fieldList;

    public SudokuElement() {

        fieldList = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            SudokuField newField = new SudokuField();
            newField.setFieldValue(0);
            fieldList.set(i,newField);
        }
    }

    protected boolean verifyArray() {
        int[] verifyingArray = new int[9];
        int counter = 0;

        for (int i = 0; i < 9; i++) {
            verifyingArray[counter] = this.fieldList.get(i).getFieldValue();

            for (int j = 0; j < i; j++) {
                if (verifyingArray[j] == verifyingArray[counter]) {
                    return false;
                }
            }

            counter++;
        }

        return true;

    }

    public void setArrayPoint(int x, int value) {
        SudokuField newField = new SudokuField();
        newField.setFieldValue(value);
        fieldList.set(x,newField);
    }

    public SudokuField getArrayPoint(int x) {
        return fieldList.get(x);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(this.fieldList.get(i).toString());
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof SudokuElement)) {
            return false;
        }

        SudokuElement otherElement = (SudokuElement) obj;

        return new EqualsBuilder().append(this.fieldList,otherElement.fieldList).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.fieldList).toHashCode();
    }

    @Override
    public SudokuElement clone() throws CloneNotSupportedException {
        SudokuElement clone = (SudokuElement) super.clone();
        SudokuField[] copyArray = new SudokuField[9];
        clone.fieldList = Arrays.asList(copyArray);

        for (int i = 0; i < 9; i++) {
            SudokuField current = new SudokuField();
            int currentNum = this.fieldList.get(i).getFieldValue();
            current.setFieldValue(currentNum);
            clone.fieldList.set(i,current);
        }
        return clone;
    }
}

