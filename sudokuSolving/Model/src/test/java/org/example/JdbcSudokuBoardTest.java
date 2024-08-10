package org.example;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuBoardTest {

    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();

    @Test
    public void jdbcTest() {
        SudokuBoard sb = new SudokuBoard(new BackTrackingSudokuSolver());
        sb.solveGame();

        JdbcSudokuBoard jdbc = (JdbcSudokuBoard) factory.getDatabaseDao();
        jdbc.write("baharBoard",sb);

        SudokuBoard testBoard = jdbc.read("baharBoard");

        jdbc.dropBoard("baharBoard");

        assertTrue(sb.equals(testBoard));
        assertEquals(sb,testBoard);
        assertNotSame(sb,testBoard);

    }

    @Test
    public void namesTest() {
        JdbcSudokuBoard jdbc = (JdbcSudokuBoard) factory.getDatabaseDao();
        List<String> names = jdbc.names();

        assertEquals("testBoard",names.get(0));
        assertEquals(1,names.size());
    }

    @Test
    public void connectionTest() {
        DatabaseConnector connector = new DatabaseConnector();
        assertThrows(JdbcException.class, () -> {connector.getConnection("eren");});
    }

    @Test
    public void exceptionTest() {
        SudokuBoard sb = new SudokuBoard(new BackTrackingSudokuSolver());

        JdbcSudokuBoard jdbc = (JdbcSudokuBoard) factory.getDatabaseDao();

        jdbc.conName = "eren";

        assertThrows(JdbcException.class, () -> {jdbc.read("baharBoard");});
        assertThrows(JdbcException.class, () -> {jdbc.dropBoard("randomBoard");});
        assertThrows(JdbcException.class, () -> {jdbc.write("erenBoard",sb);});
        assertThrows(JdbcException.class, () -> {jdbc.names();});

    }

}