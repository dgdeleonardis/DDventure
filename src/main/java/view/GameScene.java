package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.util.Pair;
import logic.Cell;
import logic.CharacterInGame;
import logic.DDventureLogic;

import java.util.ArrayList;

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
    int indexCurrentPlayer = 0;

    public GameScene(){
        super();
        setPrefSize(
                DDventureView.PRIMARY_STAGE_WIDTH - (30*2),
                DDventureView.PRIMARY_STAGE_HEIGHT - (30*2));
        this.autosize();
        javafx.scene.text.Font textFont = Font.font("Alagard", 24);

        this.map = new MapView(MAP_WIDHT, MAP_HEIGHT);
        AnimationPane animationPane = new AnimationPane(map);

        //top
        this.pauseButton = new Button("Pausa");
        this.pauseButton.setFont(textFont);
        this.turnLabel = new Label("gpassato " + "-> " + "gpresente " + "-> " + "gfuturo");
        this.turnLabel.setFont(textFont);
        this.endTurnButton = new Button("Concludi il turno");
        this.endTurnButton.setFont(textFont);
        ArrayList<CharacterInGame> prova = DDventureLogic.getInstance().characterTurnOrder();
        this.endTurnButton.setOnAction(event -> {
            if(DDventureLogic.getInstance().isThereAWinner()) {
                DDventureView.getInstance().createAnOpenVictoryStage();
            } else {
                animationPane.clearView();
                DDventureLogic.getInstance().resetCharacter(indexCurrentPlayer);
                indexCurrentPlayer = (indexCurrentPlayer +1) % prova.size();
                //System.out.println(((indexCurrentPlayer-1)%prova.size()));
                this.turnLabel.setText(DDventureLogic.getInstance().getCharacterName((indexCurrentPlayer-1+prova.size())% prova.size()) +
                        " -> "+ DDventureLogic.getInstance().getCharacterName(indexCurrentPlayer % prova.size()) +
                        " -> "+ DDventureLogic.getInstance().getCharacterName((indexCurrentPlayer+1)% prova.size())
                );
                animationPane.clearView();
            }

        });

        HBox topSection = new HBox(this.pauseButton, this.turnLabel, this.endTurnButton);
        topSection.setSpacing(220);

        setTop(topSection);
        BorderPane.setMargin(topSection, new Insets(30, 0, 0, 50));
        BorderPane.setAlignment(topSection, Pos.CENTER);

        //left

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
                map.highlightCells(DDventureLogic.getInstance().getCellsToMove(),
                        DDventureView.TEAM_COLORS.get(DDventureLogic.getInstance().getTurnCharacterInGame().getTeam().getColor()));
            } else {
                DDventureLogic.getInstance().disableMoveMode();
                map.drawMap(DDventureLogic.getInstance().getGameMap());
                map.drawCharactersInGame();
            }
        });

        map.setOnMouseClicked(event -> {
            System.out.println("X: " + event.getX() + " Y: " + event.getY());
            System.out.println(DDventureLogic.getInstance().isInAttackMode());
            boolean flag;
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
            System.out.println(DDventureLogic.getInstance().isInAttackMode());
            if(DDventureLogic.getInstance().isInAttackMode()) {
                System.out.println(DDventureLogic.getInstance().isInAttackMode() + " sono entrato yuppie!");
                // determino le coordinate matriciali della cella
                int i = (int) (event.getX() / (map.getWidth()/DDventureLogic.getInstance().getGameMap().getColumns()));
                int j = (int) (event.getY() / (map.getHeight()/DDventureLogic.getInstance().getGameMap().getRows()));
                if(DDventureLogic.getInstance().isAttackable(new Pair<>(i, j))) {
                    animationPane.openRollDiceAnimation();
                }
            } else {
            }
        });

        HBox buttons = new HBox(this.attackButton, this.moveButton);
        buttons.setSpacing(15);
        attackButton.setOnAction( event -> {
            if(!DDventureLogic.getInstance().getTurnCharacterInGame().isHasAttacked()) {
                if(DDventureLogic.getInstance().isInAttackMode()) {
                    DDventureLogic.getInstance().setAttackMode(false);
                    map.drawMap(DDventureLogic.getInstance().getGameMap());
                    map.drawCharactersInGame();
                } else {
                    //map.highlightCells(DDventureLogic.getInstance().getCellsToAttack(), Color.RED);
                    DDventureLogic.getInstance().setAttackMode(true);
                    System.out.println(DDventureLogic.getInstance().isInAttackMode());
                    for (Pair<Integer, Integer> pair : DDventureLogic.getInstance().getCellsToAttack()) {
                        Cell cell = DDventureLogic.getInstance().getGameMap().getCell(pair.getKey(), pair.getValue());
                        map.highlightCells(pair, DDventureView.TEAM_COLORS.get(cell.getCharacterOnCell().getTeam().getColor()));
                    }
                }
            }
        });



        // new code BorderPane CENTER: stackPane BOTTOM: startAndStopBox -> due bottoni per l'animazione
        // end new code



        VBox rsection = new VBox(animationPane, buttons);
        rsection.setSpacing(60);
        rsection.setAlignment(Pos.CENTER);

        setRight(rsection);
        BorderPane.setMargin(rsection, new Insets(50, 70, 0, 0));



    }


}
