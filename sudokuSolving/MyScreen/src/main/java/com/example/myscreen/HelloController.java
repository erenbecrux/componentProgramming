package com.example.myscreen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.example.*;

import java.util.Locale;
import java.util.ResourceBundle;

public class HelloController {

    public static Locale locale = Locale.getDefault();

    @FXML
    private Label welcomeText;
    public Label authorLabel;

    @FXML
    public GridPane boardGrid;
    public TextField textFields[][] = new TextField[9][9];
    public static SudokuBoard currentBoard;
    public static SudokuBoard copyBoard;
    private StringConverter<Integer> converter = new IntegerStringConverter();
    public static ResourceBundle langProperty = ResourceBundle.getBundle("lang_" + Locale.getDefault().getLanguage());
    public static ResourceBundle langBundle = ResourceBundle.getBundle("com.example.myscreen.Lang_" + Locale.getDefault().getLanguage());
    public Button easyButton;
    public Button mediumButton;
    public Button hardButton;
    public Button checkButton;
    public Button saveButton;
    public Button loadButton;
    public Button authorsButton;
    public Button languageButton;
    public String currentLanguage = locale.getLanguage();

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Current board is not valid!");
    }

    @FXML
    public void getAuthors() {
        StringBuilder authorsText = new StringBuilder();
        authorsText.append(langBundle.getString("Authors"));
        authorsText.append(" ");
        authorsText.append(langBundle.getString("Author 1"));
        authorsText.append(", ");
        authorsText.append(langBundle.getString("Author 2"));

        authorLabel.setText(authorsText.toString());
    }

    @FXML
    public void changeLanguage() {
        if (currentLanguage == "en") {
            locale = new Locale("tr","TR");
            Locale.setDefault(locale);
            langProperty = ResourceBundle.getBundle("lang_" + locale.getLanguage());
            langBundle = ResourceBundle.getBundle("com.example.myscreen.Lang_" + locale.getLanguage());
            currentLanguage = "tr";
        }
        else {
            locale = new Locale("en","EN");
            Locale.setDefault(locale);
            langProperty = ResourceBundle.getBundle("lang_" + locale.getLanguage());
            langBundle = ResourceBundle.getBundle("com.example.myscreen.Lang_" + locale.getLanguage());
            currentLanguage = "en";
        }

        easyButton.setText(langProperty.getString("easyField"));
        mediumButton.setText(langProperty.getString("mediumField"));
        hardButton.setText(langProperty.getString("hardField"));
        checkButton.setText(langProperty.getString("checkButton"));
        saveButton.setText(langProperty.getString("saveButton"));
        loadButton.setText(langProperty.getString("loadButton"));
        languageButton.setText(langProperty.getString("languageButton"));
        authorsButton.setText(langProperty.getString("authorsButton"));

    }

    @FXML
    public void selectLevel(DiffLevel currentLevel) throws CloneNotSupportedException {
        BackTrackingSudokuSolver currentSolver = new BackTrackingSudokuSolver();
        currentBoard = new SudokuBoard(currentSolver);
        currentBoard.solveGame();
        copyBoard = currentBoard.clone();

        DifficultyController diffController = new DifficultyController(currentLevel);
        diffController.createLevel(copyBoard);
    }

    @FXML
    public void easyLevel() throws CloneNotSupportedException {
        selectLevel(DiffLevel.easy);
        showBoard();
    }

    @FXML
    public void mediumLevel() throws CloneNotSupportedException {
        selectLevel(DiffLevel.medium);
        showBoard();
    }

    @FXML
    public void hardLevel() throws CloneNotSupportedException {
        selectLevel(DiffLevel.hard);
        showBoard();
    }

    @FXML
    public void showBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField fieldText = new TextField();
                TextFormatter<Integer> textFormatter = new TextFormatter<>(converter);
                fieldText.setTextFormatter(textFormatter);
                if (copyBoard.getPoint(i,j) == 0) {
                    fieldText.setText("0");
                }
                else {
                    fieldText.setText(Integer.toString(copyBoard.getPoint(i,j)));
                    fieldText.setEditable(false);
                }

                int row = i;
                int col = j;
                textFormatter.valueProperty().addListener((observable, oldValue, newValue) -> {
                    copyBoard.setPoint(row, col, currentBoard.getPoint(row,col));
                });
                boardGrid.add(fieldText,i,j);
                textFields[i][j] = fieldText;
            }
        }
    }

    @FXML
    public void checkSudoku() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copyBoard.setPoint(i,j, Integer.parseInt(textFields[i][j].getText()));
            }
        }

        boolean currentState = true;

        for (int i = 0; i < 9; i++) {

            // check row
            SudokuRow currentRow = copyBoard.getRow(i);
            if (currentRow.verifyRow() == false) {
                currentState = false;
            }

            // check column
            SudokuColumn currentColumn = copyBoard.getColumn(i);
            if (currentColumn.verifyColumn() == false) {
                currentState = false;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                //check box
                SudokuBox currentBox = copyBoard.getBox(i,j);
                if (currentBox.verifyBox() == false) {
                    currentState = false;
                }
            }
        }

        if (currentState == false) {
            welcomeText.setText(langProperty.getString("failLabel"));

        }
        else {
            welcomeText.setText(langProperty.getString("correctLabel"));
        }
    }

    @FXML
    public void saveGame() {
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> fileDao = daoFactory.getFileDao(".");
        fileDao.write("prevGame",copyBoard);
        fileDao.write("checkGame",currentBoard);
    }

    @FXML
    public void loadGame() {
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> fileDao = daoFactory.getFileDao(".");
        SudokuBoard savedGame = fileDao.read("prevGame");
        SudokuBoard checkGame = fileDao.read("checkGame");

        currentBoard = checkGame;
        copyBoard = savedGame;

        showBoard();

    }

}