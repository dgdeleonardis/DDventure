package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import logic.DDventureLogic;

public class GameScene extends BorderPane {

    private static final double MAP_WIDHT = 720;
    private static final double MAP_HEIGHT = 450;

    //top section
    private Button pauseButton;
    private Label turnLabel;
    private Button endTurnButton;

    //center left section
    private MapView map;

    //center right section
    private ImageView animation;
    private Circle circle;
    private Button attackButton;
    private Button moveButton;

    public GameScene(){
        super();
        setPrefSize(
                DDventureView.PRIMARY_STAGE_WIDTH - (30*2),
                DDventureView.PRIMARY_STAGE_HEIGHT - (30*2));
        this.autosize();
        javafx.scene.text.Font textFont = Font.font("Alagard", 24);

        //top
        this.pauseButton = new Button("Pausa");
        this.pauseButton.setFont(textFont);
        this.turnLabel = new Label("gpassato " + "-> " + "gpresente " + "-> " + "gfuturo");
        this.turnLabel.setFont(textFont);
        this.endTurnButton = new Button("Concludi il turno");
        this.endTurnButton.setFont(textFont);

        HBox topSection = new HBox(this.pauseButton, this.turnLabel, this.endTurnButton);
        topSection.setSpacing(220);

        setTop(topSection);
        BorderPane.setMargin(topSection, new Insets(30, 0, 0, 50));
        BorderPane.setAlignment(topSection, Pos.CENTER);

        //left
        this.map = new MapView(MAP_WIDHT, MAP_HEIGHT);
        this.map.drawMap(DDventureLogic.getInstance().getGameMap());
        map.drawCharactersInGame();
        setLeft(this.map);
        BorderPane.setMargin(this.map, new Insets(50, 0, 0, 70));

        //right
        this.circle = new Circle();
        this.circle.setRadius(140);
        this.circle.setStroke(Color.BLACK);
        this.circle.setFill(Color.TRANSPARENT);
        this.animation = new ImageView();
        StackPane stack = new StackPane(this.animation, this.circle);


        this.attackButton = new Button("Attacca");
        this.attackButton.setFont(textFont);
        this.moveButton = new Button("Muovi");
        this.moveButton.setFont(textFont);
        // new code:
        moveButton.setOnAction(event -> {
            /*
            TODO:
                1. active the moveMode on characterInGame [LOGIC]
                2. define the cells where he/she can move [LOGIC cellsToMove]
                3. on canvas highlight the cells [VIEW]
                if moveMode on, turn off it (DONE)
             */
            if(!DDventureLogic.getInstance().isInMoveMode()) {
                DDventureLogic.getInstance().enableMoveMode();
                DDventureLogic.getInstance().searchCellsToMove(DDventureLogic.getInstance().getTurnCharacterInGame());
                map.highlightCellsToMoveTo(DDventureLogic.getInstance().getCellsToMove(),
                        DDventureView.TEAM_COLORS.get(DDventureLogic.getInstance().getTurnCharacterInGame().getTeam().getColor()));
            } else {
                DDventureLogic.getInstance().disableMoveMode();
                map.drawMap(DDventureLogic.getInstance().getGameMap());
                map.drawCharactersInGame();
            }
        });

        map.setOnMouseClicked(event -> {
            /*
            TODO:
                0. understand which characterInGame have to move
                1. verify if character is in moveMode [LOGIC] (done)
                    1.a move the characterInGame
                    1.b re-draw the map
                2. else do nothing
             */
            boolean flag = true;
            if(DDventureLogic.getInstance().isInMoveMode()) {
                int iTarget = (int) (event.getX()/(map.getWidth()/DDventureLogic.getInstance().getGameMap().getColumns()));
                int jTarget = (int) (event.getY()/(map.getHeight()/DDventureLogic.getInstance().getGameMap().getRows()));
                flag = DDventureLogic.getInstance().moveCharacterInGame(
                        DDventureLogic.getInstance().getTurnCharacterInGame(),
                        iTarget,
                        jTarget);
                if(flag) {
                    DDventureLogic.getInstance().disableMoveMode();
                    map.drawMap(DDventureLogic.getInstance().getGameMap());
                    map.drawCharactersInGame();
                }


            }


        });

        HBox buttons = new HBox(this.attackButton, this.moveButton);
        buttons.setSpacing(15);
        attackButton.setOnAction( event -> {

            if(!DDventureLogic.getInstance().getTurnCharacterInGame().isHasAttacked()) {

            }

            /*

                 TODO-V2:
                     !onAction di attackButton!
                        [LOGIC] getTurnCharacter().hasAttacked() ?
                            true:
                            false:
                                [LOGIC] determinaLeCoordinateDiQualeCellaAdiacenteAlPersonaggioPresentaNemici() return -> Elenco di celle in cui ci sono i nemici
                                [VIEW] illuminaCelleAdiacentiConNemici(elenco di celle trovato in precedenza)
                   !onClicked di map!
                        [LOGIC] stoAttaccando() ?
                                true:
                                    [VIEW] determino le coordinate matriciali della cella
                                    [LOGIC] verificare se la cella selezionata rientra nelle celle in cui è possibile attaccare
                                        true:
                                            [LOGIC] determinare qual è il nemico da attaccare
                                            [LOGIC] provaARompereLaDifesa() return int [lancia il dado]
                                            [VIEW] avvia l'animazione e mostra a schermo il risultato
                                                WARNING: DUE POSSIBILI IMPLEMENTAZIONI:
                                                       A. risultato deciso a priori con model
                                                       B. pulsante start&stop
                                            [LOGIC] èStataRotta()?
                                                true:
                                                    [VIEW] avvia animazione attacco
                                                    [LOGIC] ottengo risultato dado danno da funzione del logic

                                                false: [VIEW] zio, sei uno sfigato e ciccia.

                                                [LOGIC] imposta a true l'attributo hasAttacked
             */
        });

        VBox rsection = new VBox(stack, buttons);
        rsection.setSpacing(60);
        rsection.setAlignment(Pos.CENTER);

        setRight(rsection);
        BorderPane.setMargin(rsection, new Insets(50, 70, 0, 0));



    }


}
