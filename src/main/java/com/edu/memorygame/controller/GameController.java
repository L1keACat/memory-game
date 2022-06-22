package com.edu.memorygame.controller;

import com.edu.memorygame.model.Board;
import com.edu.memorygame.model.Card;
import com.edu.memorygame.model.HighScoreEntry;
import com.edu.memorygame.model.Time;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private final Integer GRID_ROWS;
    private final Integer GRID_COLS;

    @FXML
    private Text timerText;

    @FXML
    private Text addPointsText;

    @FXML
    private GridPane gameGrid;

    Time time = new Time("00:00:00");

    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1),
                    e -> {
                        time.oneSecondPassed();
                        timerText.setText("Time: " + time.getCurrentTime());
                    }));

    private final Board BOARD = new Board();
    private Card firstCard = null;
    private Card secondCard = null;
    int score;
    int rightGuessesCounter = 0;
    private Timer timer;

    public GameController(Integer rows, Integer cols) {
        GRID_ROWS = rows;
        GRID_COLS = cols;
        score = GRID_COLS * GRID_ROWS * 10000;
    }

    public void initialize() {
        BOARD.loadBoard(GRID_ROWS, GRID_COLS);

        timerText.setText("Timer: " + time.getCurrentTime());

        Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/edu/memorygame/img/placeholder.jpg")).toExternalForm());
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(120);
                imageView.setFitHeight(120);
                imageView.setUserData(row + "," + col);
                imageView.setOnMouseClicked(this::cardListener);
                gameGrid.add(imageView, col, row);
            }
        }

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void cardListener(MouseEvent event) {
        Node sourceComponent = (Node) event.getSource();
        String rowCol = (String) sourceComponent.getUserData();
        int row = Integer.parseInt(rowCol.split(",")[0]);
        int col = Integer.parseInt(rowCol.split(",")[1]);

        String imageValue = BOARD.getBoard()[row][col].getValue();
        if (!BOARD.getBoard()[row][col].isExposed())
            flipCard((ImageView) sourceComponent, imageValue, "open");
        checkMatch(row, col);
    }

    public void checkMatch(int row, int col) {
        if (!BOARD.getBoard()[row][col].isChecked() && !BOARD.getBoard()[row][col].isExposed()) {
            addPointsText.setText(" ");
            BOARD.getBoard()[row][col].setExposed(true);
            BOARD.getBoard()[row][col].incrementCounter();
            if (firstCard == null) {
                firstCard = BOARD.getBoard()[row][col];
            } else if (secondCard == null) {
                secondCard = BOARD.getBoard()[row][col];
                if (firstCard.getValue().equals(secondCard.getValue())) {
                    BOARD.getBoard()[firstCard.getRow()][firstCard.getCol()].setChecked(true);
                    BOARD.getBoard()[secondCard.getRow()][secondCard.getCol()].setChecked(true);
                    System.out.println(firstCard.getCounter());
                    System.out.println(secondCard.getCounter());
                    if (firstCard.getCounter() == 1 && secondCard.getCounter() == 1) {
                        score += 1000;
                        addPointsText.setText("Additional points for right guess!");
                        rightGuessesCounter++;
                    }
                }
                /*else if (!firstCard.getValue().equals(secondCard.getValue())) {
                    int indexFirstCard = (firstCard.getRow() * GRID_COLS) + firstCard.getCol();
                    int indexSecondCard = (secondCard.getRow() * GRID_COLS) + secondCard.getCol();

                    ImageView imageview1 = ((ImageView) gameGrid.getChildren().get(indexFirstCard));
                    ImageView imageview2 = ((ImageView) gameGrid.getChildren().get(indexSecondCard));

                    this.timer = new Timer();
                    TimerTask closeCards = new TimerTask() {
                        public void run() {
                            System.out.println("close cards");
                            flipCard(imageview1, "test", "close");
                            flipCard(imageview2, "test", "close");
                        }
                    };

                    this.timer.schedule(closeCards, 2000);
                }*/
            } else {
                if (!firstCard.getValue().equals(secondCard.getValue())) {
                    int indexFirstCard = (firstCard.getRow() * GRID_COLS) + firstCard.getCol();
                    int indexSecondCard = (secondCard.getRow() * GRID_COLS) + secondCard.getCol();

                    ImageView imageview1 = ((ImageView) gameGrid.getChildren().get(indexFirstCard));
                    ImageView imageview2 = ((ImageView) gameGrid.getChildren().get(indexSecondCard));

                    flipCard(imageview1, "placeholder", "close");
                    flipCard(imageview2, "placeholder", "close");
                } //comment if
                BOARD.getBoard()[firstCard.getRow()][firstCard.getCol()].setExposed(false);
                BOARD.getBoard()[secondCard.getRow()][secondCard.getCol()].setExposed(false);
                firstCard = BOARD.getBoard()[row][col];
                secondCard = null;
            }
        }
        if (BOARD.isBoardChecked()) {
            timeline.stop();
            System.out.println("Time spent: " + time.getCurrentTime());
            System.out.println("Time spent in seconds: " + time.getTotalSeconds());
            score = score - time.getTotalSeconds() * 1000;
            System.out.println("Score: " + score);

            try {
                goToPostgameStage();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
                System.out.println("Postgame view file was not found!");
            }
        }
    }

    private void goToPostgameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edu/memorygame/postgame-view.fxml"));

        PostgameViewController postgameViewController = new PostgameViewController(
                time.getTotalSeconds(), GRID_ROWS, GRID_COLS, rightGuessesCounter, score);
        loader.setController(postgameViewController);

        Stage primaryStage = (Stage) gameGrid.getScene().getWindow();
        Stage stage = new Stage(StageStyle.DECORATED);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage.getOwner());
        stage.setTitle("Submit your high score");

        stage.show();
    }

    private void flipCard(ImageView imageView, String imageName, String action) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/edu/memorygame/img/" + imageName + ".jpg")).toExternalForm());
        RotateTransition rt = new RotateTransition(Duration.millis(400), imageView);
        rt.setAxis(Rotate.Y_AXIS);
        if (action.equals("open")) {
            rt.setFromAngle(0);
            rt.setToAngle(180);
        } else if (action.equals("close")) {
            rt.setFromAngle(180);
            rt.setToAngle(360);
        }
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
        PauseTransition pause = new PauseTransition(Duration.millis(200));
        pause.setOnFinished(e -> imageView.setImage(image));
        pause.play();
    }
}
