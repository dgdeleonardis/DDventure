package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenuView extends BorderPane  {

    private final static int TITLE_FONT_SIZE = 72;
    private final static int BUTTOM_FONT_SIZE = 24;
    private final static int GENERIC_SPACING = 30;
    private final static double BUTTON_MIN_WIDTH = 320;

    public MainMenuView() {
        super();
        this.setPrefSize(DDventureView.PRIMARY_STAGE_WIDTH, DDventureView.PRIMARY_STAGE_HEIGHT);

        //top section
        Label titleLabel = new Label("DDventure");
        titleLabel.setFont(Font.font("Alagard", TITLE_FONT_SIZE));
        titleLabel.setTextFill(Color.WHITE);
        this.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(GENERIC_SPACING));

        //center section
        Font buttonFont = Font.loadFont(getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), BUTTOM_FONT_SIZE);

        Button startGameButton = new Button("Nuova partita");
        startGameButton.setFont(buttonFont);
        startGameButton.setMinWidth(GENERIC_SPACING);
        startGameButton.setOnAction(event -> {
            DDventureView.getInstance().createAnOpenTeamScene();
        });

        Button loadGameButton = new Button("Carica partita");
        loadGameButton.setFont(buttonFont);
        loadGameButton.setMinWidth(BUTTON_MIN_WIDTH);
        loadGameButton.setOnAction(event -> {
            //TODO: [VIEW] apri scena esplora risorse
            //      [LOGIC] caricaPartita(String FILE_NAME_PARTITA) -> memorizza nel logic
            //      [VIEW] apri la schermata di gioco
        });

        VBox centerBox = new VBox(startGameButton, loadGameButton);
        centerBox.setSpacing(GENERIC_SPACING);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);
        BorderPane.setAlignment(centerBox, Pos.CENTER);

        //bottom section
        Button optionbutton = new Button("Opzioni");
        optionbutton.setFont(buttonFont);
        optionbutton.setOnAction( event ->  {
            DDventureView.getInstance().createAnOpenOptionScene((Stage) getScene().getWindow());
        });
        this.setBottom(optionbutton);
        BorderPane.setAlignment(optionbutton, Pos.CENTER);
        BorderPane.setMargin(optionbutton, new Insets(GENERIC_SPACING));
    }
}
