package Game;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameOver {
    private GraphicsContext gc;
    private static double w = 1500, h = 900;
    private Image backgroundImg, loser, winner, menuButtonImg;
    private MenuItem menuButton;
    private boolean ganhou, buttonsOn;

    public GameOver(GraphicsContext gc, Group root) {
        this.gc = gc;
        images();
        this.menuButton = new MenuItem(menuButtonImg, 500,530,gc,root);
        menuButton.removeFromView(root);
        this.ganhou = false;
        this.buttonsOn = false;
    }

    // [Carrega as imagens]
    private void images(){
        backgroundImg = new Image("/Img/Background.png");
        loser = new Image("/Img/Perdeu.png");
        winner = new Image("/Img/Venceu.png");
        menuButtonImg = new Image("/Img/sair.png");
    }

    // [Desenha tela GameOver]
    public void drawing(KeyEvent key, Group root, Menu menu, TCPClient client) {

        // [Exibe background]
        gc.drawImage(backgroundImg, 0, 0, w, h);

        // [Checa ganhador]
        if(ganhou){
            // [Exibe titulo]
            gc.drawImage(winner, 330, 20, 800, 200);
        } else {
            // [Exibe titulo]
            gc.drawImage(loser, 330, 20, 800, 200);
        }

        // [Adiciona botao para ir ao menu]
        if(!buttonsOn) {
            menuButton.addToView(root);
            setButtonsOn(true);
        }

        menuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // [Remove botao]
                root.getChildren().remove(1, root.getChildren().size());
                // [Reinicia estado do botao para futuras partidas]
                setButtonsOn(false);
                // [Reinicia estado dos botoes do menu para serem adicionados antes da troca de tela]
                menu.setButtonsOn(false);

               // client.setJogada(5);
                // [Vai para a tela Menu]
                Main.setStatus(Status.CLOSE);
                // [Reinicia estado de ganhou]
                setGanhou(false);
//                // [Reinicio o valor da jogada do cliente e servidor]
//                server.setJogadaCliente(0);
//                server.setJogada(0);
            }
        });

    }

    public void setGanhou(boolean ganhou) {
        this.ganhou = ganhou;
    }

    public void setButtonsOn(boolean buttonsOn) {
        this.buttonsOn = buttonsOn;
    }
}
