package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PauseBox extends VBox {

    private final static String FONT_FILE_NAME = "fonts/alagard.ttf";
    private final static int BUTTON_FONT_SIZE = 20;
    public final static double PREFERRED_WIDTH = 280.0;

    private final Button resumeGameButton;
    private final Button saveGameButton;
    private final Button optionButton;
    private final Button backToMenuButton;

    public PauseBox() {
        super();
        this.setPrefWidth(PREFERRED_WIDTH);
        Font buttonFont = Font.loadFont(this.getClass().getResourceAsStream(FONT_FILE_NAME), BUTTON_FONT_SIZE);

        this.resumeGameButton = new Button("Riprendi partita");
        this.resumeGameButton.setFont(buttonFont);
        this.resumeGameButton.setMinWidth(this.getPrefWidth());
        this.resumeGameButton.setOnAction( event -> {
            DDventureView.getInstance().getSecondaryStage().close();
            DDventureView.getInstance().getPrimaryStage().getScene().getRoot().setDisable(false);
        });

        this.saveGameButton = new Button("Salva partita");
        this.saveGameButton.setFont(buttonFont);
        this.saveGameButton.setMinWidth(this.getPrefWidth());
        this.saveGameButton.setOnAction( event -> {
            //TODO: implementare risposta
        });

        this.optionButton = new Button("Opzioni");
        this.optionButton.setFont(buttonFont);
        this.optionButton.setMinWidth(this.getPrefWidth());
        this.optionButton.setOnAction( event -> {
            DDventureView.getInstance().createAnOpenOptionScene(DDventureView.getInstance().getSecondaryStage());
        });

        this.backToMenuButton = new Button("Torna al menu");
        this.backToMenuButton.setFont(buttonFont);
        this.backToMenuButton.setMinWidth(this.getPrefWidth());
        this.backToMenuButton.setOnAction( event -> {
            //TODO: implementare risposta
        });

        this.getChildren().addAll(this.resumeGameButton, this.saveGameButton, this.optionButton, this.backToMenuButton);
        this.setAlignment(Pos.CENTER);


    }
}
