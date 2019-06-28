package client;

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
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import framework.WorldElements.CellarMap;
import framework.WorldElements.TypeOfSoil;
import Server.ServerGameImplementation;
import framework.Machines.Tank;
import java.util.Map;



public class ClientGame extends Application {

    public ServerGameImplementation serverGameImplementation;
    Tank tank1;
    private Canvas canvas;
    private GraphicsContext gc;
    private FlowPane root;
    public Map<Long, Long> borderLine;
    public CellarMap cellarMap;

    private void updateCanvas() {
        if (cellarMap != null)
            drawCellarMap();
        if (borderLine != null)
            commandDrawBorderLine();
        if (tank1 != null)
            drawTank();
    }

    public ClientGame() {
        this.serverGameImplementation = new ServerGameImplementation();
        this.cellarMap = serverGameImplementation.getCellarMap();
    }

    void updateObjects() {
        if (cellarMap != null)
            this.cellarMap = serverGameImplementation.getCellarMap();
        if (cellarMap != null)
            this.borderLine = serverGameImplementation.getBorderLine();
        if (tank1 != null)
            this.tank1 = serverGameImplementation.getTank();
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
        if (canvas == null) {
            canvas = new Canvas(800, 800);
            gc = canvas.getGraphicsContext2D();
            root.getChildren().add(canvas);
            updateObjects();
            updateCanvas();
            Thread t = new Thread(r);
            t.start();
        }
    }


    void runBackgClient() {
        for (int i = 1; i > 0; ) {
            updateObjects();
            updateCanvas();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Funny Tanks");
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        root = new FlowPane();

        Button start = new Button("Start");
        start.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                startAction();
            }
        });

        Button left = new Button("borderline");
        left.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                commandDrawBorderLine();
            }
        });
        Button drawBtn = new Button("draw tank ");
        drawBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                getTank();
                drawTank();
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
        root.getChildren().add(left);
        root.getChildren().add(drawBtn);
        root.getChildren().add(start);
        root.getChildren().add(moveRightBtn);
        root.getChildren().add(shootBtn);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    private void shootFire() {
        serverGameImplementation.tankShoot();
    }

    private void drawCellarMap() {
        WritableImage image = new WritableImage(800,800);

        PixelWriter pixelWriter = image.getPixelWriter();
        for (int i = 0; i < cellarMap.cellars.length; i++) {
            for (int j = 0; j < cellarMap.cellars[0].length; j++) {
                Cellar c = cellarMap.cellars[i][j];
                if (c.type == TypeOfSoil.Air)
                    pixelWriter.setColor(i, j, Color.BLUE);
                if (c.type == TypeOfSoil.Ground)
                    pixelWriter.setColor(i, j, Color.GREEN);
            }
        }
        gc.strokeText("FUNNY TANKS", 400, 50);

        gc.drawImage(image,0,0);
        if (cellarMap.blow!=null){
            if (cellarMap.blow.stage==0){
                gc.setFill(Color.BLACK);
                gc.fillOval(cellarMap.blow.bullet.endPoint.x.intValue(),cellarMap.blow.bullet.endPoint.y.intValue(),55,55);
            }
            if (cellarMap.blow.stage==1){
                gc.setFill(Color.BLACK);
                gc.fillOval(cellarMap.blow.bullet.endPoint.x.intValue(),cellarMap.blow.bullet.endPoint.y.intValue(),45,45);
            }
            if (cellarMap.blow.stage==2){
                gc.setFill(Color.BLACK);
                gc.fillOval(cellarMap.blow.bullet.endPoint.x.intValue(),cellarMap.blow.bullet.endPoint.y.intValue(),25,25);
            }
            if (cellarMap.blow.stage==3){
                gc.setFill(Color.BLACK);
                gc.fillOval(cellarMap.blow.bullet.endPoint.x.intValue(),cellarMap.blow.bullet.endPoint.y.intValue(),20,20);
            }
            if (cellarMap.blow.stage==4){
                gc.setFill(Color.BLACK);
                gc.fillOval(cellarMap.blow.bullet.endPoint.x.intValue(),cellarMap.blow.bullet.endPoint.y.intValue(),15,15);
            }
        }
        gc.strokeText("FUNNY TANKS", 200, 50);

    }

    public void getBorderline() {
        if (this.borderLine == null)
            this.borderLine = serverGameImplementation.getBorderLine();
    }

    public void getTank() {
        tank1 = serverGameImplementation.getTank();
    }

    public void moveTankRight() {
        serverGameImplementation.moveTank(10);
        updateObjects();
        updateCanvas();
    }

    public void drawTank() {
        this.tank1.left.y = this.borderLine.get(this.tank1.left.x);
        gc.setFill(Color.YELLOW);
        gc.fillRoundRect(this.tank1.left.x,
                this.tank1.left.y - 25,
                50,
                25, 2, 3);

        if (tank1.bullet!=null){
            gc.setFill(Color.RED);
            gc.fillOval(tank1.bullet.endPoint.x,tank1.bullet.endPoint.y,10,8);

        }
    }

    private void commandDrawBorderLine() {
        if (borderLine!=null) {
            WritableImage image = new WritableImage(800,800);

            PixelWriter pixelWriter = image.getPixelWriter();
            borderLine = serverGameImplementation.getBorderLine();
            for (Long x = 1L; x < 800; x++) {
                Long y = borderLine.get(x);
                pixelWriter.setColor(x.intValue(), y.intValue(), Color.RED);
                pixelWriter.setColor(x.intValue(), y.intValue() + 1, Color.RED);
                pixelWriter.setColor(x.intValue(), y.intValue() + 2, Color.RED);
            }
            gc.drawImage(image,0,0);
        }
    }
}
