
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import java.io.IOException;

public class ShapesDrawer extends Application {
    private static Shape currentShape = null;
    private static double startX, startY, endX, endY;
    private static Color currentColor = Color.BLACK;
    private static boolean fill = false;
    private static final double SCREEN_WIDTH = 800;
    private static final double SCREEN_HEIGHT = 600;

    private static void setButtons(Pane root) {
        Button undo = new Button("Undo");
        undo.setLayoutX(SCREEN_WIDTH - 100);
        undo.setPrefSize(100, 40);
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (root.getChildren().size() > 3) {
                    root.getChildren().remove(root.getChildren().size() - 1);
                }
            }
        });

        Button clear = new Button("Clear");
        clear.setLayoutX(SCREEN_WIDTH - 200);
        clear.setPrefSize(100, 40);
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (root.getChildren().size() > 3) {
                    root.getChildren().remove(3, root.getChildren().size());
                }
            }
        });
        root.getChildren().addAll(undo, clear);
    }

    private static void drawCircle(Pane root) {
        currentShape = new Circle();
        ((Circle) currentShape).setCenterX((startX + endX) / 2);
        ((Circle) currentShape).setCenterY((startY + endY) / 2);
        ((Circle) currentShape).setRadius((Math.sqrt(Math.pow((startX - endX), 2) + Math.pow((startY - endY), 2))) / 2);
        if (fill) {
            currentShape.setFill(currentColor);
        }
        if (!fill) {
            currentShape.setFill(Color.TRANSPARENT);
            currentShape.setStroke(currentColor);
        }
        currentShape.setVisible(true);
        root.getChildren().add(currentShape);
    }

    private static void drawRectangle(Pane root) {
        currentShape = new Rectangle();
        ((Rectangle) currentShape).setX(Math.min(endX, startX));
        ((Rectangle) currentShape).setY(Math.min(endY, startY));
        ((Rectangle) currentShape).setWidth(Math.abs(startX - endX));
        ((Rectangle) currentShape).setHeight(Math.abs(startY - endY));
        if (fill) {
            currentShape.setFill(currentColor);
        }
        if (!fill) {
            currentShape.setFill(Color.TRANSPARENT);
            currentShape.setStroke(currentColor);
        }
        currentShape.setVisible(true);
        root.getChildren().add(currentShape);
    }

    private static void drawLine(Pane root) {
        currentShape = new Line();
        ((Line) currentShape).setStartX(startX);
        ((Line) currentShape).setStartY(startY);
        ((Line) currentShape).setEndX(endX);
        ((Line) currentShape).setEndY(endY);
        if (fill) {
            currentShape.setStroke(currentColor);
        }
        if (!fill) {
            currentShape.setFill(Color.TRANSPARENT);
            currentShape.setStroke(currentColor);
        }
        currentShape.setVisible(true);
        root.getChildren().add(currentShape);
    }

    private static void setMenuBarFilled(MenuBar mb) {
        MenuItem i1 = new MenuItem("Filled");
        MenuItem i2 = new MenuItem("Hollow");
        Label l1 = new Label("Choose Filled/Hollow");
        Menu menu = new Menu("", l1,i1, i2);
        i1.setOnAction(e -> {
            fill = true;
            l1.setText("Filled");
        });
        i2.setOnAction(e -> {
            fill = false;
            l1.setText("Hollow");
        });
        mb.getMenus().add(menu);
    }

    private static void setMenuBarColors(MenuBar mb) {
        MenuItem i1 = new MenuItem("Red");
        MenuItem i2 = new MenuItem("Green");
        MenuItem i3 = new MenuItem("Yellow");
        MenuItem i4 = new MenuItem("Blue");
        MenuItem i5 = new MenuItem("Black");
        Label l1 = new Label("Choose Color");
        Menu menu = new Menu("", l1,i1, i2 ,i3 ,i4, i5);
        i1.setOnAction(e -> {
            currentColor = Color.RED;
            l1.setText("Red");
        });
        i2.setOnAction(e -> {
            currentColor = Color.GREEN;
            l1.setText("Green");
        });
        i3.setOnAction(e -> {
            currentColor = Color.YELLOW;
            l1.setText("Yellow");
        });
        i4.setOnAction(e -> {
            currentColor = Color.BLUE;
            l1.setText("Blue");
        });
        i5.setOnAction(e -> {
            currentColor = Color.BLACK;
            l1.setText("Black");
        });
        mb.getMenus().add(menu);
    }

    private static void setMenuBarShapes(MenuBar mb) {
        MenuItem i1 = new MenuItem("Circle");
        MenuItem i2 = new MenuItem("Line");
        MenuItem i3 = new MenuItem("Rectangle");
        Label l1 = new Label("Choose Shape");
        Menu menu = new Menu("", l1,i1, i2 ,i3);
        i1.setOnAction(e -> {
            l1.setText("Circle");
            currentShape = new Circle();
        });
        i2.setOnAction(e -> {
            l1.setText("Line");
            currentShape = new Line();
        });
        i3.setOnAction(e -> {
            l1.setText("Rectangle");
            currentShape = new Rectangle();
        });
        mb.getMenus().add(menu);
    }

    public void setMenuBar(Pane root) {
        MenuBar mb = new MenuBar();
        setMenuBarShapes(mb);
        setMenuBarColors(mb);
        setMenuBarFilled(mb);
        root.getChildren().add(mb);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Shapes Drawer");
        Pane root = new Pane();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.WHITE);
        setMenuBar(root);
        setButtons(root);

        root.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                startX = event.getSceneX();
                startY = event.getSceneY();
            }
        });

        root.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                endX = event.getSceneX();
                endY = event.getSceneY();
                if (currentShape instanceof Line) {
                    drawLine(root);
                }
                if (currentShape instanceof Rectangle) {
                    drawRectangle(root);
                }
                if (currentShape instanceof Circle) {
                    drawCircle(root);
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
