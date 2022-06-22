package com.edu.memorygame.model;

import java.io.Serializable;

public class HighScoreEntry implements Serializable {
    private String name;
    private int score;
    private String grid_size;
    private int time_spent;

    public HighScoreEntry (String name, int score, String grid_size, int time_spent) {
        this.name = name;
        this.score = score;
        this.grid_size = grid_size;
        this.time_spent = time_spent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGridSize() {
        return grid_size;
    }

    public void setGridSize(String grid_size) {
        this.grid_size = grid_size;
    }

    public int getTimeSpent() {
        return time_spent;
    }

    public void setTimeSpent(int time_spent) {
        this.time_spent = time_spent;
    }
}
