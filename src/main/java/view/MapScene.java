package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import logic.Cella;

import java.util.HashMap;

public class MapScene extends BorderPane {

    private static final int TITLE_FONT_SIZE = 64;
    private static final int BUTTON_FONT_SIZE = 24;

    public static final HashMap<String, Double> ITEM_DIMENSION_OF_THE_MAP_BOX = new HashMap<String, Double>(){{
        put("piccola", 1.0);
        put("media", 1.5);
        put("grande", 2.0);
    }};

    //top section
    private Label title;

    //center high section
    private Label dimensionOfTheMapLabel;
    private ChoiceBox dimensionOfTheMapBox;
    private Button createMap;
    private Button saveMap;

    private Button loadMap;

    private Button startGame;

    //center low section
    // TODO: è un MapView
    private MapView map; //800x450

    public MapScene() {
        super();
        setPrefSize(
                DDventureView.PRIMARY_STAGE_WIDTH - (30*2),
                DDventureView.PRIMARY_STAGE_HEIGHT - (30*2));
        setPadding(new Insets(10, 10, 10, 10));

        //top section
        /*this.title = new Label("Crea la mappa");
        this.title.setFont(Font.loadFont(this.getClass().getResourceAsStream(FONT_FILE_NAME), TITLE_FONT_SIZE));
        this.mainPane.setTop(this.title);
        BorderPane.setAlignment(this.title, Pos.CENTER);
        BorderPane.setMargin(this.title, new Insets(30));*/

        //left
        this.dimensionOfTheMapLabel = new Label("Dimensione della mappa");
        this.dimensionOfTheMapLabel.setFont(Font.loadFont(this.getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), BUTTON_FONT_SIZE));
        this.dimensionOfTheMapBox = new ChoiceBox();
        this.dimensionOfTheMapBox.getItems().addAll(ITEM_DIMENSION_OF_THE_MAP_BOX.keySet());
        this.dimensionOfTheMapBox.getSelectionModel().selectFirst();

        HBox dimensionOfTheMap = new HBox(this.dimensionOfTheMapLabel, this.dimensionOfTheMapBox);

        this.createMap = new Button("Crea mappa");
        this.createMap.setFont(Font.loadFont(this.getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), BUTTON_FONT_SIZE));
        this.createMap.setMinWidth(250);


        this.saveMap = new Button("Salva mappa");
        this.saveMap.setFont(Font.loadFont(this.getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), BUTTON_FONT_SIZE));
        this.saveMap.setMinWidth(250);

        VBox leftside = new VBox(dimensionOfTheMap, this.createMap, this.saveMap);
        leftside.setSpacing(10);

        //center
        this.loadMap = new Button("Carica mappa");
        this.loadMap.setFont(Font.loadFont(this.getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), BUTTON_FONT_SIZE));
        this.loadMap.setMinWidth(250);

        //right
        this.startGame = new Button("Avanti");
        this.startGame.setFont(Font.loadFont(this.getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), BUTTON_FONT_SIZE));
        this.startGame.setMinWidth(250);
        startGame.setOnAction( event -> {
            DDventureView.getInstance().createAnOpenDragNDropScene();
        });

        HBox topSection = new HBox(leftside, this.loadMap, this.startGame);
        topSection.setSpacing(100);

        setTop(topSection);
        BorderPane.setAlignment(topSection, Pos.CENTER);
        BorderPane.setMargin(topSection, new Insets(50, 0, 0, 70));

        //center section

        this.map = new MapView(720, 405);

        createMap.setOnAction(event -> {
            double moltiplicatoreGrandezza = ITEM_DIMENSION_OF_THE_MAP_BOX.get(dimensionOfTheMapBox.getSelectionModel().getSelectedItem().toString());
            // roba da eliminare in seguito
            // genero una mappa casuale
            int columns = (int) Math.round(16*moltiplicatoreGrandezza);
            int rows = (int) Math.round(9*moltiplicatoreGrandezza);
            Cella[][] modelMap = new Cella[columns][rows];
            for(int i = 0; i < 16*moltiplicatoreGrandezza; i++) {
                for (int j = 0; j < 9*moltiplicatoreGrandezza; j++) {
                    modelMap[i][j] = new Cella((int) Math.ceil(Math.random() * 3.0));
                }
            }
            map.setMap(modelMap);
            map.draw();

        });
        setCenter(this.map);
        BorderPane.setAlignment(this.map, Pos.CENTER);





    }

}
