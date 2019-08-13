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
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.FillRule;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import framework.WorldElements.TypeOfSoil;
import sun.awt.geom.Curve;


import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;


public class ClientGame extends Application {

    public  volatile Level level;
    private Canvas canvas;
    private GraphicsContext gc;
    private FlowPane root;
    private volatile LevelImplementation li;

    private synchronized void updateCanvas() {
        drawCellarMap();
        drawTank();
        drawBullet();
    }

    public ClientGame() {
        li = new LevelImplementation();
    }

    synchronized  void updateObjects() {
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
        li.createLevel(800, 800, 10);
        canvas = new Canvas(800, 800);
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
        root = gamePanel.getPanel();
        primaryStage.setTitle("Funny Tanks");
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    private void shootFire() {
        li.tankShoot(level.tank1);
    }

    private synchronized void drawCellarMap() {
        WritableImage image = new WritableImage(800, 800);
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
        for (Blow blow :level.blows){
            Point  p = blow.position.center;
            gc.fillOval(p.x, p.y, 55, 55);
        }

        gc.setFill(Color.GRAY);
        for (int i = 0; i < level.borderLine.size(); i++) {
            try {
                gc.fillOval(i, level.borderLine.get(i), 15, 15);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void moveTankRight() {
        li.moveTank();
    }


    public void drawTank() {
        level.tank1.left.y = level.borderLine.get(level.tank1.left.x);
        gc.setFill(Color.YELLOW);
        gc.fillRoundRect(level.tank1.left.x,
                level.tank1.left.y - 25,
                50,
                25, 2, 3);
        level.tank2.left.y = level.borderLine.get(level.tank2.left.x);
        gc.setFill(Color.YELLOW);
        gc.fillRoundRect(level.tank2.left.x,
                level.tank2.left.y - 25,
                50,
                25, 2, 3);
    }

    private void drawBullet() {
        for (Map.Entry<Integer, Bullet> entry : level.bullets.entrySet()) {
            gc.setFill(Color.RED);
            gc.fillOval(entry.getValue().endPoint.x, entry.getValue().endPoint.y, 10, 8);
        }
    }

    List<Point> getBorderPoints() {
        Random r = new Random();
        List<Point> points = new LinkedList<Point>();
        for (int i = 0; i < 800; i += 50) {
            int randomY = r.nextInt(200) - 100;
            points.add(new Point(i, randomY));
        }
        return points;
    }

    private void drawLine(Point point1, Point point2) {
        int distanceX = point1.x - point2.x;
        int distanceY = point1.y - point2.y;
        int iterationsNumber = (distanceX > distanceY) ? distanceX : distanceY;
        int velocityX = 0;
        int velocityY = 0;
        Point midPoint = new Point(point1.x + (distanceX / 2), point1.y + distanceY / 2);
        for (int iX = point1.x; iX < point2.x; iX++) {

        }
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


    class GamePanel {

        private FlowPane getPanel() {
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
                    shootFire();
                }
            });

            panel.getChildren().add(start);
            panel.getChildren().add(moveRightBtn);
            panel.getChildren().add(shootBtn);
            return panel;
        }
    }


}
