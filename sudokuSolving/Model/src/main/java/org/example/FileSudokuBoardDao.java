package org.example;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable, Serializable {

    public String directoryPath;
    public FileWriter fileWriter;
    public  BufferedWriter bufferedWriter;
    public FileReader fileReader;
    public BufferedReader bufferedReader;
    private transient Logger logger = LogManager.getLogger(FileSudokuBoardDao.class);

    public FileSudokuBoardDao(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public SudokuBoard read(String name) throws DaoErrorException {
        String filePath = directoryPath + "\\" + name + ".txt";
        SudokuBoard readBoard;

        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);

            readBoard = (SudokuBoard) ois.readObject();
            ois.close();
            logger.info(SudokuBoard.getLanguageVersion().getString("readSuccess"));

        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DaoErrorException(SudokuBoard.getLanguageVersion().getString("readError"),e);
        }

        return readBoard;

    }

    @Override
    public void write(String name, SudokuBoard obj) throws DaoErrorException {
        String filePath = directoryPath + "\\" + name + ".txt";

        try {

            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
            logger.info(SudokuBoard.getLanguageVersion().getString("writeSuccess"));

        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DaoErrorException(SudokuBoard.getLanguageVersion().getString("writeError"),e);
        }

    }

        @Override
        public List<String> names() {
            List<String> fileNames = new ArrayList<>();
            File directory = new File(directoryPath);
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    fileNames.add(file.getName());
                }
            }

            return fileNames;
        }

    @Override
    public void close() throws Exception {
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }

        if (bufferedReader != null) {
            bufferedReader.close();
        }

        if (fileWriter != null) {
            fileWriter.close();
        }

        if (fileReader != null) {
            fileReader.close();
        }

        logger.info(SudokuBoard.getLanguageVersion().getString("closeSuccess"));

    }
}