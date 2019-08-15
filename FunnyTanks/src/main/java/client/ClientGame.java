package client;

import Server.*;
import com.sun.javafx.geom.FlatteningPathIterator;
import com.sun.javafx.geom.PathIterator;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.scenario.animation.SplineInterpolator;
import framework.Calculations.CurvesCreator;
import framework.EventBus.Action;
import framework.EventBus.TankMoveAction;
import framework.Machines.Blow;
import framework.Machines.Bullet;
import framework.Machines.GameObject;
import framework.Machines.Tank;
import framework.Settings.LevelSettings;
import framework.WorldElements.Cellar;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import framework.WorldElements.TypeOfSoil;
import sun.awt.geom.Curve;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;


public class ClientGame extends Application {

    public volatile Level level;
    private Canvas canvas;
    private GraphicsContext gc;
    private FlowPane root;
    private volatile LevelImplementation li;
    WritableImage image;

    private synchronized void updateCanvas() {
        drawCellarMap();
        drawTank();
        drawBullet();
    }


    public ClientGame() {
        li = new LevelImplementation();
        this.level = li.createLevel(800, 800, 10);
    }

    synchronized void updateObjects() {
        this.level = li.level;
    }

    public void startPr() {
        launch("");
    }

    private void startAction() {
        Runnable r = new Runnable() {
            public void run() {
                runBackgClient();
            }
        };
        canvas = new Canvas(level.xDimension, level.yDimension);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Thread t = new Thread(r);
        t.start();
    }


    synchronized void runBackgClient() {
        while (1 > 0) {
            updateObjects();
            updateCanvas();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(Stage primaryStage) throws Exception {
        GamePanel gamePanel = new GamePanel();
        image = new WritableImage(level.xDimension, level.yDimension);
        root = gamePanel.getPanel();
        primaryStage.setTitle("Funny Tanks");
        primaryStage.setHeight(level.yDimension);
        primaryStage.setWidth(level.xDimension);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    private void shootFire(Integer weapon) {
        li.tankShoot(level.tank1, weapon);
    }

    private synchronized void drawCellarMap() {
        PixelWriter pixelWriter = image.getPixelWriter();
        for (int i = 0; i < level.map.cellars.length; i++) {
            for (int j = 0; j < level.map.cellars[0].length; j++) {
                Cellar c = level.map.cellars[i][j];
                if (c.type == TypeOfSoil.Air)
                    pixelWriter.setColor(i, j, Color.BLUE);
                if (c.type == TypeOfSoil.Ground)
                    pixelWriter.setColor(i, j, Color.GREEN);
            }
        }
        gc.drawImage(image, 0, 0);
        for (Blow blow : level.blows) {
            Point p = blow.position;
            gc.fillOval(p.x, p.y, 55, 55);
        }


    }


    public void moveTankRight() {
        li.moveTank();
    }


    public void drawTank() {
        gc.setFill(Color.YELLOW);
        gc.fillRoundRect(level.tank1.left.x,
                level.tank1.left.y - 25,
                50,
                25, 2, 3);
        gc.setFill(Color.YELLOW);
        gc.fillRoundRect(level.tank2.left.x,
                level.tank2.left.y - 25,
                50,
                25, 5, 6);


        Line line = new Line();

        //Setting the properties to a line
        line.setStartX(100.0);
        line.setStartY(150.0);
        line.setEndX(500.0);
        line.setEndY(150.0);


        //Creating a Group
        Group root = new Group(line);
        gc.setLineWidth(10);
        gc.strokeLine(level.tank1.cannon.mountingPoint.x, level.tank1.cannon.mountingPoint.y, level.tank1.cannon.edgePoint.x, level.tank1.cannon.edgePoint.y);
        gc.lineTo(200, 300);
    }

    private Point2D.Double getPoint(Point point, double angle, double distance) {
        // Angles in java are measured clockwise from 3 o'clock.
        double theta = Math.toRadians(angle);
        Point2D.Double p = new Point2D.Double();
        p.x = point.x + distance * Math.cos(theta);
        p.y = point.y + distance * Math.sin(theta);
        return p;
    }


    @FXML
    private void rotateButtonHandle(ActionEvent event) {
        //handle for rotate
        Spinner spinner = new Spinner(1, 10, 1);

        spinner.setOnMouseClicked((MouseEvent t) -> {
            System.out.println("X " + (t.getX()));
            System.out.println("\nY " + (t.getY()));
            Node shape = (Node) t.getSource();
            shape.getTransforms().add(new Rotate(20.0, t.getX(), t.getY()));
        });

    }

    private void drawBullet() {
        for (Bullet bullet : level.bullets
        ) {
            gc.setFill(bullet.getColour());
            gc.fillOval(bullet.endPoint.x, bullet.endPoint.y, bullet.getCalibre(), 8);
        }
    }

    List<Point> getBorderPoints() {
        Random r = new Random();
        List<Point> points = new LinkedList<Point>();
        for (int i = 0; i < level.xDimension; i += 50) {
            int randomY = r.nextInt(200) - 100;
            points.add(new Point(i, randomY));
        }
        return points;
    }


    private Point[] setEdges() {
        Point[] points = new Point[800 / 50];
        Random rand = new Random();
        for (int i = 50; i < 800; i += 50) {
            Point point = new Point(i, rand.nextInt(300) + 200);
            points[i] = point;
        }
        return points;
    }

    void moveCannon(int angle) {
        li.moveCannon(angle);
    }

    class GamePanel {

        private FlowPane getPanel() {

            Text text = new Text("This is a test");
            text.setX(10);
            text.setY(50);

            text.getTransforms().add(new Rotate(30, 50, 30));


            Spinner spinner = new Spinner(1, 10, 1);
            Spinner spinnerAngle = new Spinner(0, 360, 90);
            spinnerAngle.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                if (!"".equals(newValue)) {
                    moveCannon(Integer.parseInt(spinnerAngle.getValue().toString()));
                }
            });
            FlowPane panel = new FlowPane();
            Button start = new Button("Start");
            start.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    startAction();
                }
            });

            Button moveRightBtn = new Button("move right ");
            moveRightBtn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    moveTankRight();
                }
            });

            Button shootBtn = new Button("shoot ");
            shootBtn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    shootFire(Integer.parseInt(spinner.getValue().toString()));
                }
            });
            panel.getChildren().add(spinner);
            panel.getChildren().add(spinnerAngle);
            panel.getChildren().add(text);
            panel.getChildren().add(start);
            panel.getChildren().add(moveRightBtn);
            panel.getChildren().add(shootBtn);
            return panel;
        }
    }


}
