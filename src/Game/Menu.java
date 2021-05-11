package Game;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Menu implements Drawable{
    private GraphicsContext gc;
    private Status status;
    private Image backgroundImg,titleMenu, playButton, perg;
    private static double w = 1500, h = 900;
    private List<MenuItem> items;
    private Group root;
    private boolean buttonsOn;

    // [Construtor]
    public Menu(GraphicsContext gc, Status status, Group root) {
        this.gc = gc;
        this.status = status;
        this.root = root;
        items = new ArrayList<>();
        buttons();
        menuItems();
        images();
        this.buttonsOn = false;
    }

    // [Carrega imagens]
    private void images() {
        backgroundImg = new Image("/Img/Background.png");
        titleMenu = new Image ("/Img/title.png");
    }

    // [Carrega imagens dos botoes]
    private void buttons() {
        playButton = new Image("/Img/jogar.png");
        perg = new Image("/Img/sair.png");
    }

    // [Desenha tela Menu]
    @Override
    public void drawing(MouseEvent mouse, KeyEvent key, Group root ){
        gc.drawImage(backgroundImg, 0,0,w,h);
        gc.drawImage(titleMenu, 0, 0, w, h);

        if(!buttonsOn){
            items.get(0).addToView(root);
            items.get(1).addToView(root);
            setButtonsOn(true);
        }

        items.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                root.getChildren().remove(1, root.getChildren().size());
                Main.setStatus(Status.WAITTOPLAY);
            }
        });

        items.get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                root.getChildren().remove(1, root.getChildren().size());
                Main.setStatus(Status.CLOSE);
            }
        });
    }

    // [Cria botoes]
    private void menuItems() {
        items.add(new MenuItem(playButton,500,393, gc, root));
        items.get(0).removeFromView(root);
        items.add(new MenuItem(perg,500,535, gc, root));
        items.get(1).removeFromView(root);
    }

    // [Getters e setters]
    public List<MenuItem> getItems() {
        return items;
    }

    public void setButtonsOn(boolean buttonsOn) {
        this.buttonsOn = buttonsOn;
    }
}
