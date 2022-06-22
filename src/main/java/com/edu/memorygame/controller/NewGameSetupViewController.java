package com.edu.memorygame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Arrays;

public class NewGameSetupViewController {
    @FXML
    private ComboBox<Integer> comboBoxRows;
    @FXML
    private ComboBox<Integer> comboBoxCols;
    @FXML
    private Button playButton;

    final Integer[] SELECTION_VALUES = {2, 3, 4, 5, 6};
    final Integer[] MODIFIED_SELECTION_VALUES = {2, 4, 6};

    public void initialize() {
        comboBoxRows.getItems().addAll(Arrays.asList(SELECTION_VALUES));
        comboBoxCols.getItems().addAll(Arrays.asList(SELECTION_VALUES));
        comboBoxRows.getSelectionModel().selectFirst();
        comboBoxCols.getSelectionModel().selectFirst();
        handleSelection();
    }

    private void handleSelection() {
        comboBoxRows.setOnAction(event -> {
            Integer selectedHeight = comboBoxRows.getSelectionModel().getSelectedItem();
            Integer selectedWidth = comboBoxCols.getSelectionModel().getSelectedItem();
            comboBoxCols.getItems().clear();
            if (selectedHeight % 2 != 0) {
                comboBoxCols.getItems().addAll(Arrays.asList(MODIFIED_SELECTION_VALUES));
                if (selectedWidth % 2 == 0) {
                    comboBoxCols.getSelectionModel().select(selectedWidth);
                } else {
                    comboBoxCols.getSelectionModel().selectFirst();
                }
            } else {
                comboBoxCols.getItems().addAll(Arrays.asList(SELECTION_VALUES));
                comboBoxCols.getSelectionModel().select(selectedWidth);
            }
        });
    }

    @FXML
    protected void onPlayButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edu/memorygame/game-view.fxml"));

        GameController gameController = new GameController(comboBoxRows.getSelectionModel().getSelectedItem(), comboBoxCols.getSelectionModel().getSelectedItem());
        loader.setController(gameController);

        Scene scene = new Scene(loader.load());

        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Stage stage = new Stage(StageStyle.DECORATED);

        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage.getOwner());
        stage.setTitle("Memory Game");

        stage.getScene().getAccelerators().put(
                KeyCombination.keyCombination("CTRL+SHIFT+Q"),
                () -> {
                    System.out.println("Key combination detected");
                    stage.close();
                }
        );

        primaryStage.close();
        stage.show();
    }
}
