package client;

import Server.*;
import com.sun.javafx.geom.FlatteningPathIterator;
import com.sun.javafx.geom.PathIterator;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.scenario.animation.SplineInterpolator;
import framework.Calculations.CurvesCreator;
import framework.EventBus.Action;
import framework.EventBus.TankMoveAction;
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

    public Level level;
    private Canvas canvas;
    private GraphicsContext gc;
    private FlowPane root;
    private User user;
    private LevelImplementation li;

    private void updateCanvas() {
        drawCellarMap();
        commandDrawBorderLine();
        drawTank();
        drawBullet();
    }

    public ClientGame() {
        li = new LevelImplementation();
        this.user = li.createUser();

    }

    void updateObjects() {
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
        li.createLevel(800L, 800L, 10, user);
        canvas = new Canvas(800, 800);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Thread t = new Thread(r);
        t.start();
    }


    void runBackgClient() {
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

    private void getHorizon() {
        double x1 = 11;
        double y1 = 22;
        double x2 = 33;
        double y2 = 44;
        SplineInterpolator si = new SplineInterpolator(0, 1, 0, 1);
        try {
            double res = si.interpolate(0, 1, 1);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        double d;
    }

    private void drawCellarMap() {
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
        if (level.map.blow != null) {
            if (level.map.blow.stage == 0) {
                gc.setFill(Color.BLACK);
                gc.fillOval(level.map.blow.bullet.endPoint.x.intValue(), level.map.blow.bullet.endPoint.y.intValue(), 55, 55);
            }
            if (level.map.blow.stage == 1) {
                gc.setFill(Color.BLACK);
                gc.fillOval(level.map.blow.bullet.endPoint.x.intValue(), level.map.blow.bullet.endPoint.y.intValue(), 45, 45);
            }
            if (level.map.blow.stage == 2) {
                gc.setFill(Color.BLACK);
                gc.fillOval(level.map.blow.bullet.endPoint.x.intValue(), level.map.blow.bullet.endPoint.y.intValue(), 25, 25);
            }
            if (level.map.blow.stage == 3) {
                gc.setFill(Color.BLACK);
                gc.fillOval(level.map.blow.bullet.endPoint.x.intValue(), level.map.blow.bullet.endPoint.y.intValue(), 20, 20);
            }
            if (level.map.blow.stage == 4) {
                gc.setFill(Color.BLACK);
                gc.fillOval(level.map.blow.bullet.endPoint.x.intValue(), level.map.blow.bullet.endPoint.y.intValue(), 15, 15);
            }
        }

        List<Point[]> borderline = getCurvs(800);
        gc.setFill(Color.GRAY);
        for (Point[] curve : borderline
        ) {
            for (int i = 0; i < curve.length; i++) {
                try {
                    gc.fillOval(curve[i].x, curve[i].y, 15, 15);
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    private List<Point[]> getCurvs(int maxX) {
        CurvesCreator cc = new CurvesCreator();
        List<Point[]> result = new LinkedList<>();
        Point p1 = new Point(0, 200);
        Point p2 = new Point(0, 200);
        int x1 = 0, x2 = 0;

        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < maxX; i += 100) {
            if (i > 0) {
                p1 = new Point(p2.x, p2.y);
                x2 = p2.x + 100;
                int y2 = rand.nextInt(100) + 200;
                int distanceY = getDistance(p2.y, y2);
                if (distanceY < 10) {
                    if (p2.y > y2) {
                        y2 -= 10;
                    } else {
                        y2 += 10;
                    }
                }
                p2 = new Point(x2, y2);
            } else {
                x1 = i;
                int y1 = rand.nextInt(100) + 200;
                p1 = new Point(x1, y1);
                x2 = i + 100;
                int y2 = rand.nextInt(100) + 200;
                int distanceY = getDistance(y2, y1);
                if (distanceY < 10) {
                    if (y2 > y1) {
                        y2 += 10;
                    } else {
                        y2 -= 10;
                    }
                }
                p2 = new Point(x2, y2);
            }
            Point[] points = cc.drawCurve(p1, p2);
            if (points[0] == null) {
                System.out.println("shit");
            }
            result.add(points);
        }
        return result;
    }

    int getDistance(int x11, int x22) {
        if (x11 > x22) {
            return x11 - x22;
        }
        return x22 - x11;
    }

    public void moveTankRight() {
        li.moveTank(user);
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


    private void commandDrawBorderLine() {
        List<Point> points = getBorderPoints();
        for (int i = 1; i < points.size(); i++) {
            drawLine(points.get(i - 1), points.get(i));
        }

        WritableImage image = new WritableImage(800, 800);
        PixelWriter pixelWriter = image.getPixelWriter();
        for (Long x = 1L; x < 800; x++) {
            Long y = level.borderLine.get(x);
            pixelWriter.setColor(x.intValue(), y.intValue(), Color.RED);
            pixelWriter.setColor(x.intValue(), y.intValue() + 1, Color.RED);
            pixelWriter.setColor(x.intValue(), y.intValue() + 2, Color.RED);
        }
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

    public static <T> Object[] concatenate(T[] a, T[] b) {
        // Function to merge two arrays of
        // same type
        return Stream.of(a, b)
                .flatMap(Stream::of)
                .toArray();

        // Arrays::stream can also be used in place
        // of Stream::of in the flatMap() above.
    }

}
