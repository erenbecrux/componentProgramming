package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    private BackTrackingSudokuSolver backTrackSolver = new BackTrackingSudokuSolver();


    @Test
    public void stringTest() {
        SudokuBoard mySudokuBoard = new SudokuBoard(backTrackSolver);
        assertEquals("0 0 0 0 0 0 0 0 0 \n0 0 0 0 0 0 0 0 0 \n0 0 0 0 0 0 0 0 0 \n0 0 0 0 0 0 0 0 0 \n0 0 0 0 0 0 0 0 0 \n0 0 0 0 0 0 0 0 0 \n0 0 0 0 0 0 0 0 0 \n0 0 0 0 0 0 0 0 0 \n0 0 0 0 0 0 0 0 0 \n",mySudokuBoard.toString());
    }

    @Test
    public void equalsTest() {
        SudokuBoard mySudokuBoard = new SudokuBoard(backTrackSolver);
        SudokuBoard otherBoard = new SudokuBoard(backTrackSolver);

        mySudokuBoard.setPoint(2,2,5);
        otherBoard.setPoint(2,2,5);

        assertTrue(mySudokuBoard.equals(otherBoard));

        SudokuBoard sameBoard = mySudokuBoard;
        assertTrue(mySudokuBoard.equals(sameBoard));

        SudokuElement anotherType = new SudokuElement();
        assertFalse(mySudokuBoard.equals(anotherType));
    }

    @Test
    public void hashCodeTest() {
        SudokuBoard mySudokuBoard = new SudokuBoard(backTrackSolver);
        SudokuBoard otherBoard = new SudokuBoard(backTrackSolver);

        mySudokuBoard.setPoint(3,3,9);
        otherBoard.setPoint(3,3,9);
        int myHashCode = mySudokuBoard.hashCode();
        int otherHashCode = otherBoard.hashCode();

        assertEquals(myHashCode,otherHashCode);

        // test different objects
        otherBoard.setPoint(3,3,5);
        otherHashCode = otherBoard.hashCode();
        if(myHashCode == otherHashCode) {
            assertFalse(mySudokuBoard.equals(otherBoard));
        }
        else {
            assertNotEquals(myHashCode,otherHashCode);
        }
    }

    @Test
    public void duplicateTest()
    {
        SudokuBoard mySudokuBoard = new SudokuBoard(backTrackSolver);

        List<Integer> testList = new ArrayList<>();
        for(int i = 1; i < 10; i++)
        {
            testList.add(i);
        }

        assertTrue(mySudokuBoard.solveGame());

        // check rows
        for(int i = 0; i < 9; i++)
        {
            List<Integer> currentList = new ArrayList<>();

            for(int j = 0; j < 9; j++)
            {
                currentList.add(mySudokuBoard.getPoint(i,j));
            }

            assertTrue(testList.size() == currentList.size() && testList.containsAll(currentList) && currentList.containsAll(testList));

        }

        // check columns
        for(int i = 0; i < 9; i++)
        {
            List<Integer> currentList = new ArrayList<>();

            for(int j = 0; j < 9; j++)
            {
                currentList.add(mySudokuBoard.getPoint(j,i));
            }

            assertTrue(testList.size() == currentList.size() && testList.containsAll(currentList) && currentList.containsAll(testList));

        }

        // check boxes
        for (int i = 0; i < 9; i = i + 3)
        {
            for(int j = 0; j < 9; j = j + 3)
            {
                List<Integer> currentList = new ArrayList<>();

                for(int row = i; row < i + 3; row++)
                {
                    for(int column = j; column < j + 3; column++)
                    {
                        currentList.add(mySudokuBoard.getPoint(row,column));
                    }
                }

                assertTrue(testList.size() == currentList.size() && testList.containsAll(currentList) && currentList.containsAll(testList));

            }
        }

    }

    @Test
    public void subsequentBoardsTest()
    {
        SudokuBoard mySudokuBoardOne = new SudokuBoard(backTrackSolver);
        assertTrue(mySudokuBoardOne.solveGame());
        List<Integer> firstCall = new ArrayList<>();
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                firstCall.add(mySudokuBoardOne.getPoint(i,j));
            }
        }

        SudokuBoard mySudokuBoardTwo = new SudokuBoard(backTrackSolver);
        assertTrue(mySudokuBoardTwo.solveGame());
        List<Integer> secondCall = new ArrayList<>();
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                secondCall.add(mySudokuBoardTwo.getPoint(i,j));
            }
        }

        int sameNumCounter = 0;
        for(int i = 0; i < 81; i++)
        {
            if(firstCall.get(i) == secondCall.get(i))
            {
                sameNumCounter++;
            }
        }

        assertNotEquals(81,sameNumCounter);
    }

    @Test
    public void solverErrorTest() {
        BackTrackingSudokuSolver solver = new BackTrackingSudokuSolver();
        SudokuBoard sb = new SudokuBoard(solver);

        assertThrows(SudokuBoardException.class, () -> {solver.solverError();});
    }

    @Test
    public void getterTests()
    {
        SudokuBoard mySudokuBoard = new SudokuBoard(backTrackSolver);

        assertTrue(mySudokuBoard.solveGame());

        for (int i = 0; i < 9; i++) {

            // check row
            SudokuRow currentRow = mySudokuBoard.getRow(i);

            for (int j = 0; j < 9; j++) {
                assertEquals(mySudokuBoard.getPoint(i,j),currentRow.getArrayPoint(j).getFieldValue());
            }

            // check column
            SudokuColumn currentColumn = mySudokuBoard.getColumn(i);

            for (int j = 0; j < 9; j++) {
                assertEquals(mySudokuBoard.getPoint(j,i),currentColumn.getArrayPoint(j).getFieldValue());
            }

        }

        // check box
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                SudokuBox currentBox = mySudokuBoard.getBox(i,j);

                int counter = 0;
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        assertEquals(mySudokuBoard.getPoint(i * 3 + k, j * 3 + l), currentBox.getArrayPoint(counter).getFieldValue());
                        counter++;
                    }
                }
            }
        }
    }

    @Test
    public void checkTest()
    {
        SudokuBoard mySudokuBoard = new SudokuBoard(backTrackSolver);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuField newField = new SudokuField();
                newField.setFieldValue(1);
                mySudokuBoard.setPoint(i,j,newField.getFieldValue());
            }
        }

        assertFalse(mySudokuBoard.solveGame());
    }

    @Test
    public void cloneTest() {
        SudokuBoard newBoard = new SudokuBoard(backTrackSolver);
        newBoard.solveGame();

        try {
            SudokuBoard copyBoard = newBoard.clone();
            assertNotSame(newBoard,copyBoard);
            assertEquals(newBoard,copyBoard);
            copyBoard.setPoint(1,1,0);
            assertNotEquals(newBoard,copyBoard);
        }
        catch (Exception e) {
            throw new SudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneError"),e);
        }
    }

}