package com.edu.memorygame.controller;

import com.edu.memorygame.model.HighScoreEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PostgameViewController {
    @FXML
    private TextField nameInputField;
    @FXML
    private Button submitButton;

    @FXML
    private Text gridSizeFieldText;
    @FXML
    private Text timeSpentFieldText;
    @FXML
    private Text rightGuessesFieldText;
    @FXML
    private Text scoreFieldText;

    private final int TIME_SPENT;
    private final int GRID_HEIGHT;
    private final int GRID_WIDTH;
    private final int RIGHT_GUESSES;
    private final int SCORE;

    public PostgameViewController(int time_spent, int grid_height, int grid_width, int right_guesses, int score) {
        TIME_SPENT = time_spent;
        GRID_HEIGHT = grid_height;
        GRID_WIDTH = grid_width;
        RIGHT_GUESSES = right_guesses;
        SCORE = score;
    }

    public void initialize() {
        gridSizeFieldText.setText(gridSizeFieldText.getText() + GRID_HEIGHT + "x" + GRID_WIDTH + " (+" + (GRID_HEIGHT * GRID_WIDTH * 10000) + ")");
        timeSpentFieldText.setText(timeSpentFieldText.getText() + TIME_SPENT + " seconds (-" + (TIME_SPENT * 1000) + ")");
        rightGuessesFieldText.setText(rightGuessesFieldText.getText() + RIGHT_GUESSES + " (+" + (RIGHT_GUESSES * 1000) + ")");
        scoreFieldText.setText(scoreFieldText.getText() + SCORE);
        submitButton.setOnAction(this::onSubmitButtonClick);
    }

    @FXML
    protected void onSubmitButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edu/memorygame/high-scores-view.fxml"));

        saveScore();

        Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Stage stage = new Stage(StageStyle.DECORATED);
        Scene scene;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            System.out.println("High Scores scene is not found.");
            return;
        }
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage.getOwner());
        stage.setTitle("High Scores");

        primaryStage.close();
        stage.show();
    }

    private void saveScore() {
        HighScoreEntry highScoreEntry = new HighScoreEntry(nameInputField.getText(), SCORE, GRID_HEIGHT + "x" + GRID_WIDTH, TIME_SPENT);

        List<HighScoreEntry> highScores = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("src/main/resources/com/edu/memorygame/save/highscores.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            highScores = (List<HighScoreEntry>) in.readObject();
            in.close();
            fileIn.close();
        } catch (EOFException | FileNotFoundException e) {
            System.out.println("File is empty");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        if (!highScores.isEmpty())
            for (int i = 0; i < highScores.size(); i++) {
                if (highScoreEntry.getScore() > highScores.get(i).getScore()) {
                    highScores.add(i, highScoreEntry);
                    break;
                }
                if (i + 1 == highScores.size()) {
                    highScores.add(highScoreEntry);
                    break;
                }
            }
        else
            highScores.add(highScoreEntry);

        try {
            FileOutputStream fileOut =
                    new FileOutputStream("src/main/resources/com/edu/memorygame/save/highscores.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(highScores);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in /save/highscores.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
