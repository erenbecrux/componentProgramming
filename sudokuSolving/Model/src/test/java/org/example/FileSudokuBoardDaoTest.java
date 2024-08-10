package org.example;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    @Test
    void writeAndRead() {
        SudokuBoardDaoFactory myFactory = new SudokuBoardDaoFactory();
        FileSudokuBoardDao myFileDao = (FileSudokuBoardDao) myFactory.getFileDao(".");
        SudokuBoard testBoard = new SudokuBoard(new BackTrackingSudokuSolver());
        myFileDao.write("writing",testBoard);
        SudokuBoard readBoard = myFileDao.read("writing");

        assertTrue(readBoard.equals(testBoard));
        assertThrows(DaoErrorException.class, () -> myFileDao.read("nonexist"));
    }

    @Test
    void write2() {
        SudokuBoardDaoFactory myFactory = new SudokuBoardDaoFactory();
        FileSudokuBoardDao myFileDao = (FileSudokuBoardDao) myFactory.getFileDao(".");

        SudokuBoard testBoard = new SudokuBoard(new BackTrackingSudokuSolver());
        myFileDao.directoryPath = "error";
        assertThrows(DaoErrorException.class, () -> myFileDao.write("deneme",testBoard));
    }

    @Test
    void names() {
        SudokuBoardDaoFactory myFactory = new SudokuBoardDaoFactory();
        FileSudokuBoardDao myFileDao = (FileSudokuBoardDao) myFactory.getFileDao(".");
        List<String> names = myFileDao.names();

        List<String> testNames = new ArrayList<>();
        testNames.add(0,"deneme.txt");
        testNames.add(1,"writing.txt");



        assertEquals(testNames,names);
    }

    @Test
    void names2() {
        SudokuBoardDaoFactory myFactory = new SudokuBoardDaoFactory();
        FileSudokuBoardDao myFileDao = (FileSudokuBoardDao) myFactory.getFileDao(".\\emptyDirectory");
        List<String> names = myFileDao.names();

        List<String> testNames = new ArrayList<>();
        assertEquals(testNames,names);
    }
    @Test
    void close() {

        try{
            SudokuBoardDaoFactory myFactory = new SudokuBoardDaoFactory();
            FileSudokuBoardDao myFileDao = (FileSudokuBoardDao) myFactory.getFileDao(".");

            myFileDao.close();

            myFileDao.fileReader = new FileReader(new File(".\\deneme.txt"));
            myFileDao.bufferedReader = new BufferedReader((myFileDao.fileReader));
            myFileDao.fileWriter = new FileWriter(".\\deneme.txt", true);
            myFileDao.bufferedWriter = new BufferedWriter(myFileDao.fileWriter);

            assertDoesNotThrow(() -> myFileDao.close());

            myFileDao.close();
        }
        catch (Exception e) {
            throw new DaoErrorException(SudokuBoard.getLanguageVersion().getString("closeError"),e);
        }

    }
}