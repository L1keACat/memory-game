package com.edu.memorygame.controller;

import com.edu.memorygame.model.HighScoreEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScoresViewController {
    @FXML
    private ListView<String> highScoresList;

    private List<HighScoreEntry> highScores;

    public void initialize() {
        highScores = new ArrayList<>();
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

        ObservableList<String> stringList = FXCollections.observableArrayList();
        if (!highScores.isEmpty())
            for (HighScoreEntry entry : highScores) {
                System.out.println(entry.getName() + ", " + entry.getScore() + ", " + entry.getTimeSpent() + ", " + entry.getGridSize());
                stringList.add(entry.getName() + ", " + entry.getScore() + ", time: " + entry.getTimeSpent() + " seconds, grid: " + entry.getGridSize());
            }
        else
            stringList.add("No records yet.");
        highScoresList.setItems(stringList);
    }
}
