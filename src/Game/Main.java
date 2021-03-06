package Game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private static double w = 1500, h = 900;
    private Group root;
    private Scene scene;
    public static Menu menu;
    private static Status status = Status.MENU;
    private MouseEvent mouse;
    private KeyEvent key;
    private boolean keyPressed = false;
    public static TCPClient client;
    private boolean checkConexaoComServidor = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new Group();
        scene = new Scene(root, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pedra papel tesoura");
        primaryStage.setMaxWidth(w);
        primaryStage.setMaxHeight(h);
        Canvas canvas = new Canvas(w, h);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(0, canvas);
        menu = new Menu(gc, status, root);
        TCPClient client = new TCPClient();
        Game gameScreen = new Game(gc, status, root);
        WaitToPlay waitToPlay = new WaitToPlay(gc, status, root);
        WaitingOpponent waitingOpponent = new WaitingOpponent(gc, status, root);
        RoundResult roundResult = new RoundResult(gc, status, root);
        GameOver gameOver = new GameOver(gc, root);


        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouse = mouseEvent;
                System.out.println(mouseEvent.getX() + " " + mouseEvent.getY() + " " + mouseEvent.getEventType().getName());
            }
        });

        // [Game loop]
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                if (status == Status.MENU) {
                    menu.drawing(mouse, key, root);

                } else if(status == Status.WAITTOPLAY){
                    waitToPlay.drawing(key, root);
                    // [Tentando encontrar o servidor]
                    if(!checkConexaoComServidor){
                        client.conectarServidor();
                        checkConexaoComServidor = true;
                    }

                } else if(status == Status.GAME) {
                    // [Reseta para futuras partidas]
                    checkConexaoComServidor = false;
                    gameScreen.drawing(key, root, client, roundResult);

                } else if(status == Status.WAITINGOPPONENT) {
                    waitingOpponent.drawing(key, root, client);

                }else if(status == Status.ROUNDRESULT){
                    roundResult.drawing(key, root, gameScreen, client, gameOver);
                } else if(status == Status.GAMEOVER){
                    gameOver.drawing(key, root, menu, client);

                } else if (status == Status.CLOSE) {
                    primaryStage.close();
                }

            }

        };
        gameLoop.start();
        primaryStage.show();
    }


        public static void main(String[] args) {
        launch(args);
    }

    public static Status getStatus() {
        return status;
    }

    public static void setStatus(Status status) {
        Main.status = status;
    }
}

