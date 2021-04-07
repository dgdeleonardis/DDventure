package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import logic.DDventureLogic;


public class DragNDropView extends BorderPane {

    private static final int TITLE_FONT_SIZE = 64;
    private static final int BUTTON_FONT_SIZE = 24;
    private static final double MAP_WIDHT = 720;
    private static final double MAP_HEIGHT = 450;
    /*
    drag & drop version
    private double sourceX;
    private double sourceY;
    */
    public DragNDropView() {
        // global settings
        setPrefSize(
                DDventureView.PRIMARY_STAGE_WIDTH - (30*2),
                DDventureView.PRIMARY_STAGE_HEIGHT - (30*2));
        this.autosize();
        setPadding(new Insets(10, 10, 10, 10));
         // top section
        Label titleLabel = new Label("Disposizione dei personaggi");
        titleLabel.setFont(Font.loadFont(getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), TITLE_FONT_SIZE));
        setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(30));

        // center section
        MapView map = new MapView(MAP_WIDHT, MAP_HEIGHT);
        map.drawMap(DDventureLogic.getInstance().getGameMap());
        setCenter(map);
        BorderPane.setAlignment(map, Pos.CENTER);
        /*
        drag & drop version
        map.setOnMousePressed(event -> {
            //System.out.println("Pressed");
            sourceX = event.getX();
            sourceY = event.getY();
            map.setCursor(Cursor.CLOSED_HAND);
        });
        map.setOnMouseReleased(event -> {
            double targetX = event.getX();
            double targetY = event.getY();
            movePlayer(sourceX, sourceY, targetX, targetY);
            map.setCursor(Cursor.DEFAULT);
        });
        */
        // selected version
        map.setOnMouseClicked(event -> {
            // TODO [MODEL]: if(c'è un personaggio?(cella x e y)) { istruzioni per la selezione }
            // onClick: o il personaggio è nella cella o no;
            // --- se sei in modalità spostamento o no
            int i = (int) (event.getX() / (map.getWidth()/DDventureLogic.getInstance().getGameMap().getColumns()));
            int j = (int) (event.getY() / (map.getHeight()/DDventureLogic.getInstance().getGameMap().getRows()));
            //FIXME: vedi se spostare il isOccupied sul DDventureLogic
            //if(DDventureLogic.getInstance().getGameMap().getCells()[i][j].isOccupied()) {
            //}
            // se in selectionMode: 1. selezione la casella e poi mi salvo le coordinate I e J.
            map.drawBorderCell(i, j, DDventureLogic.getInstance().getGameMap());
            //System.out.println("x:" + event.getX() + "; y:" + event.getY());
        });

        // bottom section
        Font buttonFont = Font.font("Alagard", BUTTON_FONT_SIZE);

        Button goBackButton = new Button("Torna indietro");
        goBackButton.setFont(buttonFont);
        goBackButton.setOnAction(event -> {
            DDventureView.getInstance().createAnOpenMapScene();
        });

        Button goToGameButton = new Button("Avvia la partita");
        goToGameButton.setFont(buttonFont);

        setBottom(new AnchorPane(goToGameButton, goBackButton));
        AnchorPane.setLeftAnchor(goBackButton, 10.0);
        AnchorPane.setBottomAnchor(goBackButton, 10.0);
        AnchorPane.setRightAnchor(goToGameButton, 10.0);
        AnchorPane.setBottomAnchor(goToGameButton, 10.0);

    }

    private void movePlayer(double sourceX, double sourceY, double targetX, double targetY) {
        /* TODO MODEL:
            #1 passo: setPlayerCoordinatesModel(sourceX/cellWidth, sourceY/cellHeight, ... con target) MODEL
            #2 passo: ridisegnare la mappa VIEW
        */
    }
}
