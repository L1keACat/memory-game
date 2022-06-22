package com.edu.memorygame.model;

public class Card {
    private String value;
    private Integer row;
    private Integer col;
    private boolean checked;
    private boolean exposed;
    private int counter;

    Card(String value, Integer row, Integer col) {
        this.value = value;
        this.row = row;
        this.col = col;
        this.checked = false;
        this.exposed = false;
        this.counter = 0;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter() {
        this.counter++;
    }
}
