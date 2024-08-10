package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.SupportedOptions;

import static org.junit.jupiter.api.Assertions.*;

class SudokuElementTest {

    SudokuElement myElement = new SudokuElement();

    @Test
    public void getAndSetTest() {
        for (int i = 0; i < 9; i++) {
            myElement.setArrayPoint(i,i * 2);
            assertEquals(myElement.fieldList.get(i).getFieldValue(), i * 2);

            int getValue = myElement.getArrayPoint(i).getFieldValue();
            assertEquals(i * 2, getValue);
        }
    }


    @Test
    public void verifyTest() {

        // false verification
        for (int i = 0; i < 9; i++) {
            if (i == 4) {
                myElement.setArrayPoint(i,i);
            }
            else {
                myElement.setArrayPoint(i,i + 1);
            }
        }
        assertFalse(myElement.verifyArray());

        //correct verification
        for (int i = 0; i < 9; i++) {
            myElement.setArrayPoint(i,i + 1);
        }
        assertTrue(myElement.verifyArray());

    }

    @Test
    public void stringTest() {
        for (int i = 0; i < 9; i++) {
            myElement.setArrayPoint(i,i + 1);
        }
        assertEquals("1 2 3 4 5 6 7 8 9 ",myElement.toString());
    }

    @Test
    public void equalsTest() {
        for (int i = 0; i < 9; i++) {
            myElement.setArrayPoint(i,i + 1);
        }

        SudokuElement otherElement = new SudokuElement();
        for (int i = 0; i < 9; i++) {
            otherElement.setArrayPoint(i,i + 1);
        }

        assertTrue(myElement.equals(otherElement));

        SudokuElement sameElement = myElement;
        assertTrue(myElement.equals(sameElement));

        SudokuField anotherType = new SudokuField();
        assertFalse(myElement.equals(anotherType));
    }

    @Test
    public void hashCodeTest() {
        for (int i = 0; i < 9; i++) {
            myElement.setArrayPoint(i,i + 1);
        }

        SudokuElement otherElement = new SudokuElement();
        for (int i = 0; i < 9; i++) {
            otherElement.setArrayPoint(i,i + 1);
        }

        int myHashCode = myElement.hashCode();
        int otherHashCode = otherElement.hashCode();

        assertEquals(myHashCode,otherHashCode);

        // test different objects
        otherElement.setArrayPoint(5,2);
        otherHashCode = otherElement.hashCode();
        if(myHashCode == otherHashCode) {
            assertFalse(myElement.equals(otherElement));
        }
        else {
            assertNotEquals(myHashCode,otherHashCode);
        }
    }

    @Test
    public void cloneTest(){

        SudokuElement newSudokuElement = new SudokuElement();
        newSudokuElement.setArrayPoint(2, 8);

        try {
            SudokuElement cloneElement = newSudokuElement.clone();
            newSudokuElement.setArrayPoint(2, 5);
            assertNotEquals(newSudokuElement.getArrayPoint(2),cloneElement.getArrayPoint(2));
        }
        catch (Exception e){
            throw new SudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneError"),e);
        }
    }


}