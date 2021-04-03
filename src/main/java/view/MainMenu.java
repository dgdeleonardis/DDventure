package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenu extends BorderPane  {
    //top
    protected Label title;
    //center
    protected VBox centerBox;
    protected Button startGame;
    protected Button loadGame;
    //bottom
    protected Button optionbutton;

    public MainMenu() {
        super();
        setPrefSize(DDventureView.PRIMARY_STAGE_WIDTH, DDventureView.PRIMARY_STAGE_HEIGHT);

        //top section
        this.title = new Label("DDventures");
        Font titleFont = Font.loadFont(getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), 74);
        this.title.setFont(titleFont);
        this.title.setTextFill(Color.WHITE);
        this.setTop(this.title);
        BorderPane.setAlignment(this.title, Pos.CENTER);
        BorderPane.setMargin(this.title, new Insets(30,0,30,0));


        //center section
        this.centerBox = new VBox();
        this.centerBox.setSpacing(30);

        Font buttonFont = Font.loadFont(getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), 24);
        this.loadGame = new Button("Carica partita");
        this.loadGame.setFont(buttonFont);
        this.startGame = new Button("Nuova partita");
        this.startGame.setFont(buttonFont);
        this.centerBox.getChildren().addAll(this.startGame, this.loadGame);
        this.centerBox.setAlignment(Pos.CENTER);
        this.setCenter(this.centerBox);
        BorderPane.setAlignment(this.centerBox, Pos.CENTER);
        this.startGame.setMinWidth(320);
        this.loadGame.setMinWidth(320);
        this.loadGame.setOnAction(event -> {
            /* TODO: VIEW -apri scena esplora risorse
                    scelto il file dobbiamo caricarlo con il metodo del MODEL caricaPartita(String FILE_NAME_PARTITA)
                    deve generare un oggetto Game (compreso di personaggi, mappa, disposizione dei giocatori, turno, ordine
                    dei giocatori in gara)
                    VIEW - crei una GameScene
            */
        });
        this.startGame.setOnAction(event -> {
            view.DDventureView.getInstance().createAnOpenTeamScene();
        });

        //bottom section
        this.optionbutton = new Button("Opzioni");
        this.optionbutton.setFont(buttonFont);
        BorderPane.setAlignment(this.optionbutton, Pos.CENTER);
        BorderPane.setMargin(this.optionbutton, new Insets(30,0,30,0));

        this.setBottom(this.optionbutton);

        this.optionbutton.setOnAction( event ->  {
                DDventureView.getInstance().createAnOpenOptionScene((Stage) getScene().getWindow());
        });
    }
}
