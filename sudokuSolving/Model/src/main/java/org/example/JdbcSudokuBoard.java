package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcSudokuBoard implements Dao<SudokuBoard>, AutoCloseable {

    Connection myConnection = null;
    String conName = "bahar";

    @Override
    public SudokuBoard read(String name) {
        SudokuBoard sb = new SudokuBoard(new BackTrackingSudokuSolver());

        String query1 = "SELECT b.id from boards b where b.name = ? ;";
        String query2 = "SELECT f.value from fields f where f.id = ? AND f.boardrow = ? AND f.boardcolumn = ? ;";

        DatabaseConnector connector = new DatabaseConnector();

        try {
            myConnection = connector.getConnection(conName);
            PreparedStatement psID = myConnection.prepareStatement(query1);
            psID.setString(1,name);
            ResultSet countSet = psID.executeQuery();
            int boardID = 0;

            while (countSet.next()) {
                boardID = countSet.getInt(1);
            }

            psID.close();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    PreparedStatement ps = myConnection.prepareStatement(query2);
                    ps.setInt(1,boardID);
                    ps.setInt(2,i);
                    ps.setInt(3,j);

                    ResultSet valueSet = ps.executeQuery();
                    int value = 0;

                    while (valueSet.next()) {
                        value = valueSet.getInt(1);
                    }

                    ps.close();

                    sb.setPoint(i,j,value);
                }
            }

            myConnection.close();
            this.close();

        } catch (Exception e) {
            throw new JdbcException(e.getMessage(),e);
        }

        return sb;
    }

    @Override
    public void write(String name, SudokuBoard obj) {
        String countQuery = "SELECT COUNT(*) FROM boards;";
        String query1 = "INSERT INTO boards(id, name) VALUES (?, ?);";
        String query2 = "INSERT INTO fields(id,boardrow,boardcolumn,value) VALUES (?, ?, ?, ?);";

        DatabaseConnector connector = new DatabaseConnector();

        try {
            myConnection = connector.getConnection(conName);
            PreparedStatement psCount = myConnection.prepareStatement(countQuery);
            ResultSet countSet = psCount.executeQuery();
            int countID = 0;

            while (countSet.next()) {
                countID = countSet.getInt(1) + 1;
            }

            psCount.close();

            PreparedStatement ps1 = myConnection.prepareStatement(query1);
            ps1.setInt(1, countID);
            ps1.setString(2, name);

            ps1.executeUpdate();
            ps1.close();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    PreparedStatement ps2 = myConnection.prepareStatement(query2);

                    ps2.setInt(1, countID);
                    ps2.setInt(2, i);
                    ps2.setInt(3, j);
                    ps2.setInt(4, obj.getPoint(i,j));

                    ps2.executeUpdate();
                    ps2.close();
                }
            }

            myConnection.close();
            this.close();

        } catch (Exception e) {
            throw new JdbcException(e.getMessage(),e);
        }
    }

    @Override
    public List<String> names() {
        List<String> names = new ArrayList<>();

        String query = "SELECT b.name from boards b;";
        DatabaseConnector connector = new DatabaseConnector();

        try {
            myConnection = connector.getConnection(conName);
            PreparedStatement ps = myConnection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                names.add(rs.getString(1));
            }

            ps.close();
            myConnection.close();
            this.close();

        } catch (Exception e) {
            throw new JdbcException(e.getMessage(),e);
        }

        return names;
    }

    public void dropBoard(String name) {
        String query1 = "SELECT b.id from boards b where b.name = ? ;";
        String query2 = "DELETE from fields where id = ? ;";
        String query3 = "DELETE from boards where id = ? ;";

        DatabaseConnector connector = new DatabaseConnector();

        try {
            myConnection = connector.getConnection(conName);
            PreparedStatement psID = myConnection.prepareStatement(query1);
            psID.setString(1,name);
            ResultSet countSet = psID.executeQuery();
            int boardID = 0;

            while (countSet.next()) {
                boardID = countSet.getInt(1);
            }

            psID.close();

            PreparedStatement ps2 = myConnection.prepareStatement(query2);
            ps2.setInt(1,boardID);

            PreparedStatement ps3 = myConnection.prepareStatement(query3);
            ps3.setInt(1,boardID);

            ps2.executeUpdate();
            ps3.executeUpdate();

            ps2.close();
            ps3.close();

            myConnection.close();
            this.close();

        } catch (Exception e) {
            throw new JdbcException(e.getMessage(),e);
        }
    }

    @Override
    public void close() throws Exception {
        myConnection.close();
    }

}
