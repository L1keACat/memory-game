package com.edu.memorygame.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label titleText;

    public void initialize() {
        titleText.setText("Memory Game");
    }

    @FXML
    protected void onNewGameButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edu/memorygame/new-game-setup-view.fxml"));

        openChildStage(loader, "New Game");
    }

    @FXML
    protected void onHighScoresButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edu/memorygame/high-scores-view.fxml"));

        openChildStage(loader, "High Scores");
    }

    private void openChildStage(FXMLLoader loader, String title) throws IOException {
        Stage primaryStage = (Stage) titleText.getScene().getWindow();
        Stage stage = new Stage(StageStyle.DECORATED);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setTitle(title);
        stage.show();
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }
}