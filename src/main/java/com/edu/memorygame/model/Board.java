package com.edu.memorygame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private Card[][] board;

    public void loadBoard(Integer rows, Integer cols) {
        board = new Card[rows][cols];
        List<String> images = new ArrayList<>();
        for (int i = 0; i < (rows * cols) / 2; i++) {
            images.add("kitten" + (i + 1));
        }
        int imageInd = 0;
        Random random = new Random();
        while(!isBoardFull()) {
            //int randomInd = random.nextInt(images.size());
            String randomImg = images.get(imageInd);

            int randomRow1 = random.nextInt(rows);
            int randomCol1 = random.nextInt(cols);
            while (board[randomRow1][randomCol1] != null) {
                randomRow1 = random.nextInt(rows);
                randomCol1 = random.nextInt(cols);
            }

            int randomRow2 = random.nextInt(rows);
            int randomCol2 = random.nextInt(cols);
            while ((randomRow1 == randomRow2 && randomCol1 == randomCol2) || board[randomRow2][randomCol2] != null) {
                randomRow2 = random.nextInt(rows);
                randomCol2 = random.nextInt(cols);
            }

            board[randomRow1][randomCol1] = new Card(randomImg, randomRow1, randomCol1);
            board[randomRow2][randomCol2] = new Card(randomImg, randomRow2, randomCol2);
            imageInd++;
        }
    }

    private boolean isBoardFull() {
        for (Card[] row : board) {
            for (Card card : row) {
                if (card == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isBoardChecked() {
        for (Card[] row : board) {
            for (Card card : row) {
                if (!card.isChecked()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Card[][] getBoard() {
        return board;
    }

    public void setBoard(Card[][] board) {
        this.board = board;
    }
}
