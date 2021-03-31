package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;


public class DragNDropView extends BorderPane {

    private static final int TITLE_FONT_SIZE = 64;
    private static final int BUTTON_FONT_SIZE = 24;
    private static final double MAP_WIDHT = 720;
    private static final double MAP_HEIGHT = 450;

    private double sourceX;
    private double sourceY;

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
        setCenter(map);
        BorderPane.setAlignment(map, Pos.CENTER);

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
        /* TODO:
            #1 passo: movePlayerModel(sourceX/cellWidth, sourceY/cellHeight, ... con target)
            #2 passo: ridisegnare la mappa
        */
    }
}
