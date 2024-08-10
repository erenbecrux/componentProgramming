package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    private SudokuBoard mySudokuBoard = new SudokuBoard();

    @Test
    public void duplicateTest()
    {

        List<Integer> testList = new ArrayList<>();
        for(int i = 1; i < 10; i++)
        {
            testList.add(i);
        }

        mySudokuBoard.fillBoard();

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

        mySudokuBoard.fillBoard();
        List<Integer> firstCall = new ArrayList<>();
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                firstCall.add(mySudokuBoard.getPoint(i,j));
            }
        }

        mySudokuBoard.fillBoard();
        List<Integer> secondCall = new ArrayList<>();
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                secondCall.add(mySudokuBoard.getPoint(i,j));
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

}