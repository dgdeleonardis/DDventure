package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainMenu extends VBox {

    public MainMenu() {
        super();
        Label helloWorldLabel = new Label("Hello WORLD!");
        this.getChildren().add(helloWorldLabel);
    }
}
