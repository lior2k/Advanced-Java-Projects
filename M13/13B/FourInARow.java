

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;


public class FourInARow extends Application {
    static final int rows = 6;
    static final int cols = 7;
    public static HashMap<String, Integer> playField = new HashMap<>();
    public static Color[][] winCondition = new Color[rows][cols];
    public static Color color = Color.RED;
    final double COL_WIDTH = 71.43;
    final double ROW_HEIGHT = 66.66;
    final double LEFT_X_BOUNDARY = 150;
    final double RIGHT_X_BOUNDARY = 650;
    final double LOWER_Y_BOUNDARY = 100;
    final double HIGHER_Y_BOUNDARY = 500;
    final Image red_disk = new Image("https://i.imgur.com/hgU4wY2.png");
    final Image yellow_disk = new Image("https://i.imgur.com/lujvbwA.png");


    public void setButtons(Pane root) {
        // set clear button
        Button clear = new Button("Clear");
        clear.setLayoutX(375);
        clear.setPrefSize(60, 30);
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().removeIf(child -> child instanceof Circle);
                for (Node child : root.getChildren()) {
                    if (child instanceof Button) {
                        child.setDisable(false);
                    }
                }
                winCondition = new Color[rows][cols];
                color = Color.RED;
                playField.replaceAll((k, v) -> 0);
            }
        });
        root.getChildren().add(clear);

        // set the 7 controller disks
        for (int i = 0; i < cols; i++) {
            Button b = new Button(Integer.toString(i+1));
            playField.put(Integer.toString(i+1), 0);
            b.setLayoutX(LEFT_X_BOUNDARY + i * COL_WIDTH);
            b.setLayoutY(HIGHER_Y_BOUNDARY + 25);
            b.setPrefSize(COL_WIDTH, ROW_HEIGHT / 2);
            b.setVisible(true);
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //disable button if the column is full
                    if (playField.get(b.getText()) >= 5) {
                        b.setDisable(true);
                    }
                    //draw circle accordingly
                    Circle circle = new Circle();
                    circle.setRadius(COL_WIDTH / 2);
                    circle.setCenterX(LEFT_X_BOUNDARY + COL_WIDTH /2 + COL_WIDTH * (Integer.parseInt(b.getText()) - 1));
                    circle.setCenterY(HIGHER_Y_BOUNDARY - ROW_HEIGHT / 2 - playField.get(b.getText()) * ROW_HEIGHT);
                    Image img;
                    img = (color == Color.RED) ? red_disk : yellow_disk;
                    circle.setFill(new ImagePattern(img));
                    circle.setVisible(true);
                    root.getChildren().add(circle);
                    playField.put(b.getText(), playField.get(b.getText()) + 1); //basicly playfield[num of button]++
                    winCondition[playField.get(b.getText()) - 1][Integer.parseInt(b.getText()) - 1] = color; // insert colors to 2d array to check for win condition
                    checkWin(color);
                    color = (color == Color.RED) ? Color.YELLOW : Color.RED; //swap colors
                }
            });
            root.getChildren().add(b);
        }
    }

    //draw lines (rows and columns)
    public void setRowsAndCols(Pane root) {
        Line L;
        //set rows
        for (int i = 0; i <= rows; i++) {
            L = new Line();
            L.setStartX(LEFT_X_BOUNDARY);
            L.setEndX(RIGHT_X_BOUNDARY);
            L.setStartY(LOWER_Y_BOUNDARY + (ROW_HEIGHT * i));
            L.setEndY(LOWER_Y_BOUNDARY + (ROW_HEIGHT * i));
            L.setStroke(Color.BLACK);
            L.setStrokeWidth(5);
            L.setVisible(true);
            root.getChildren().add(L);
        }
        //set cols
        for (int i = 0; i <= cols; i++) {
            L = new Line();
            L.setStartX(LEFT_X_BOUNDARY + (COL_WIDTH * i));
            L.setEndX(LEFT_X_BOUNDARY + (COL_WIDTH * i));
            L.setStartY(LOWER_Y_BOUNDARY);
            L.setEndY(HIGHER_Y_BOUNDARY);
            L.setStroke(Color.BLACK);
            L.setStrokeWidth(5);
            L.setVisible(true);
            root.getChildren().add(L);
        }
    }

    public void checkWin(Color col) {
        // check 4 in a row  0000
        for (Color[] colors : winCondition) {
            int rowStreak = 0;
            for (int j = 0; j < winCondition[0].length; j++) {
                if (colors[j] == col) {
                    rowStreak++;
                } else {
                    rowStreak = 0;
                }
                if (rowStreak == 4) {
                    Alert game_over_msg = new Alert(Alert.AlertType.INFORMATION);
                    game_over_msg.setTitle("Game Over");
                    if (col == Color.RED) {
                        game_over_msg.setContentText("Red won!");
                    } else {
                        game_over_msg.setContentText("Yellow won!");
                    }
                    game_over_msg.show();
                    break;
                }
            }
        }
        // check 4 in a col
        for (int i = 0; i < winCondition[0].length; i++) {
            int colStreak = 0;
            for (Color[] colors : winCondition) {
                if (colStreak == 4) {
                    Alert game_over_msg = new Alert(Alert.AlertType.INFORMATION);
                    game_over_msg.setTitle("Game Over");
                    if (col == Color.RED) {
                        game_over_msg.setContentText("Red won!");
                    } else {
                        game_over_msg.setContentText("Yellow won!");
                    }
                    game_over_msg.show();
                    break;
                }
                if (colors[i] == col) {
                    colStreak++;
                } else {
                    colStreak = 0;
                }
            }
        }
        // check diagonals
        for (int i = 0; i < winCondition.length; i++) {
            for (int j = 0; j < winCondition[0].length; j++) {
                // i,j i+1,j+1, i+2,j+2, i+3,j+3
                if (i+3 < winCondition.length && j+3 < winCondition[0].length) {
                    if (winCondition[i][j] == col && winCondition[i+1][j+1] == col && winCondition[i+2][j+2] == col && winCondition[i+3][j+3] == col) {
                        Alert game_over_msg = new Alert(Alert.AlertType.INFORMATION);
                        game_over_msg.setTitle("Game Over");
                        if (col == Color.RED) {
                            game_over_msg.setContentText("Red won!");
                        } else {
                            game_over_msg.setContentText("Yellow won!");
                        }
                        game_over_msg.show();
                        break;
                    }
                }
                if (i-3 >= 0 && j+3 <= winCondition[0].length) {
                    if (winCondition[i][j] == col && winCondition[i-1][j+1] == col && winCondition[i-2][j+2] == col && winCondition[i-3][j+3] == col) {
                        Alert game_over_msg = new Alert(Alert.AlertType.INFORMATION);
                        game_over_msg.setTitle("Game Over");
                        if (col == Color.RED) {
                            game_over_msg.setContentText("Red won!");
                        } else {
                            game_over_msg.setContentText("Yellow won!");
                        }
                        game_over_msg.show();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Four In A Row");
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        stage.setResizable(true);
        scene.setFill(Color.BLACK);
        Image img = new Image("https://i.imgur.com/BtL0RXf.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(800, 600, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        root.setBackground(background);

        setRowsAndCols(root);
        setButtons(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
