package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import logic.CharacterInGame;
import logic.DDventureLogic;
import logic.Weapon;

import java.util.ArrayList;


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
                DDventureView.PRIMARY_STAGE_WIDTH - (30 * 2),
                DDventureView.PRIMARY_STAGE_HEIGHT - (30 * 2));
        this.autosize();
        setPadding(new Insets(10, 10, 10, 10));
        // top section
        Label titleLabel = new Label("Disposizione dei personaggi");
        titleLabel.setFont(Font.loadFont(getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), TITLE_FONT_SIZE));
        setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(30));

        // FIXME: ELIMINA!
        ArrayList<CharacterInGame> characterInGame = DDventureLogic.getInstance().getGame().getCharactersInGame();
        characterInGame.get(0).setCoordinataX(0);
        characterInGame.get(0).setCoordinataY(0);
        characterInGame.add(new CharacterInGame("diego", 4, 4, 5, 6, "", 8, 6, new Weapon("", 4, 5), DDventureLogic.getInstance().getGame().getTeams().get(0)));
        characterInGame.add(new CharacterInGame("diego", 4, 4, 5, 6, "", 6, 8, new Weapon("", 4, 5), DDventureLogic.getInstance().getGame().getTeams().get(0)));
        characterInGame.add(new CharacterInGame("diego", 4, 4, 5, 6, "", 1, 3, new Weapon("", 4, 5), DDventureLogic.getInstance().getGame().getTeams().get(0)));
        characterInGame.add(new CharacterInGame("diego", 4, 4, 5, 6, "", 2, 1, new Weapon("", 4, 5), DDventureLogic.getInstance().getGame().getTeams().get(0)));
        characterInGame.add(new CharacterInGame("diego", 4, 4, 5, 6, "", 5, 0, new Weapon("", 4, 5), DDventureLogic.getInstance().getGame().getTeams().get(0)));
        DDventureLogic.getInstance().generateRandomPositions();
        characterInGame.forEach(character -> {
            DDventureLogic.getInstance().getGameMap().getCells()[character.getCoordinataX()][character.getCoordinataY()].setCharacterOnCell(character);
        });

        // center section
        MapView map = new MapView(MAP_WIDHT, MAP_HEIGHT);
        map.drawMap(DDventureLogic.getInstance().getGameMap());
        map.drawCharactersInGame();
        setCenter(map);
        BorderPane.setAlignment(map, Pos.CENTER);


        map.setOnMouseClicked(event -> {
            if (DDventureLogic.getInstance().getGameMap().isSelectionMode()) {
                // sposta
                int i = (int) (event.getX() / (map.getWidth() / DDventureLogic.getInstance().getGameMap().getColumns()));
                int j = (int) (event.getY() / (map.getHeight() / DDventureLogic.getInstance().getGameMap().getRows()));
                if (DDventureLogic.getInstance().getGameMap().moveCharacter(i, j)) {
                    map.drawMap(DDventureLogic.getInstance().getGameMap());
                    map.drawCharactersInGame();
                }
            } else {
                // seleziona
                int i = (int) (event.getX() / (map.getWidth() / DDventureLogic.getInstance().getGameMap().getColumns()));
                int j = (int) (event.getY() / (map.getHeight() / DDventureLogic.getInstance().getGameMap().getRows()));
                if (DDventureLogic.getInstance().getGameMap().setSource(i, j)) {
                    map.drawBorderCell(i, j, DDventureLogic.getInstance().getGameMap());
                }
            }
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
        goToGameButton.setOnAction( event -> {
            //DDventureView.getInstance().createAnOpenGameScene();
            DDventureView.getInstance().createAnOpenInitiativeScene();
        });

        setBottom(new AnchorPane(goToGameButton, goBackButton));
        AnchorPane.setLeftAnchor(goBackButton, 10.0);
        AnchorPane.setBottomAnchor(goBackButton, 10.0);
        AnchorPane.setRightAnchor(goToGameButton, 10.0);
        AnchorPane.setBottomAnchor(goToGameButton, 10.0);

    }
}
