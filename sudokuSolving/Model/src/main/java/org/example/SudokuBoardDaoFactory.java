package org.example;

public class SudokuBoardDaoFactory {
    public Dao<SudokuBoard> getFileDao(String fileName) {
        FileSudokuBoardDao myDao = new FileSudokuBoardDao(fileName);

        return myDao;
    }

    public Dao<SudokuBoard> getDatabaseDao() {
        JdbcSudokuBoard jdbc = new JdbcSudokuBoard();
        return jdbc;
    }
}
