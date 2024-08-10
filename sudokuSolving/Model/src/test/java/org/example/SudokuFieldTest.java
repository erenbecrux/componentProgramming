package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    SudokuField myField = new SudokuField();

    @Test
    public void getAndSetTest()
    {
        myField.setFieldValue(5);
        assertEquals(5,myField.getFieldValue());
    }

    @Test
    public void stringTest() {
        myField.setFieldValue(3);
        assertEquals("3",myField.toString());
    }

    @Test
    public void equalsTest() {
        SudokuField otherField = new SudokuField();

        myField.setFieldValue(5);
        otherField.setFieldValue(5);

        assertTrue(myField.equals(otherField));

        SudokuField sameField = myField;
        assertTrue(myField.equals(sameField));

        SudokuElement anotherType = new SudokuElement();
        assertFalse(myField.equals(anotherType));
    }

    @Test
    public void hashCodeTest() {
        myField.setFieldValue(2);
        int myHashCode = myField.hashCode();

        SudokuField otherField = new SudokuField();
        otherField.setFieldValue(2);
        int otherHashCode = otherField.hashCode();

        assertEquals(myHashCode,otherHashCode);

        // test different objects
        otherField.setFieldValue(5);
        otherHashCode = otherField.hashCode();
        if(myHashCode == otherHashCode) {
            assertFalse(myField.equals(otherField));
        }
        else {
            assertNotEquals(myHashCode,otherHashCode);
        }
    }

    @Test
    public void cloneTest() {
        SudokuField newSudokuField = new SudokuField();
        newSudokuField.setFieldValue(7);

        try {
            SudokuField cloneField = newSudokuField.clone();
            assertEquals(newSudokuField.getFieldValue(),cloneField.getFieldValue());
            cloneField.setFieldValue(5);
            assertNotEquals(newSudokuField,cloneField);
        }
        catch (Exception e) {
            throw new SudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneError"),e);
        }

    }

    @Test
    public void CompareTest() {
        SudokuBoard sb = new SudokuBoard(new BackTrackingSudokuSolver());
        SudokuField firstField = new SudokuField();
        firstField.setFieldValue(4);
        SudokuField secondField = new SudokuField();
        secondField.setFieldValue(4);
        assertEquals(0,firstField.compareTo(secondField));

        assertThrows(NullPointerFieldException.class, () -> {firstField.compareTo(null);});
    }
}