package view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DDventureView implements IView{

    private static final String MAIN_BACKGROUND_FILE_NAME = "image/background-hell_gate.png";
    private static final String CREATION_GAME_BACKGROUND_FILE_NAME = "image/background-leather.jpg";
    public static final String FONT_FILE_NAME = "font/alagard.ttf";
    public static final int PRIMARY_STAGE_WIDTH = 1280;
    public static final int PRIMARY_STAGE_HEIGHT = 720;

    public static final HashMap<String, Color> TEAM_COLORS = new HashMap<String, Color>() {{
        put("Rosso", Color.RED);
        put("Blue", Color.BLUE);
        put("Verde", Color.GREEN);
        put("Rosa", Color.PINK);
        put("Arancione", Color.ORANGE);
        put("Oro", Color.GOLD);
    }};

    private static DDventureView instance = null;
    private static MusicManager musicManager= null;

    private Stage primaryStage = null;
    private Stage secondaryStage = null;

    private Scene mainScene;
    private AnchorPane creationGamePane;

    private Scene pauseScene;

    private final BackgroundImage mainBackgroundImage;
    private final BackgroundImage creationGameBackgroundImage;

    private DDventureView() {
        Font.loadFont(getClass().getResourceAsStream(FONT_FILE_NAME), 24);
        mainBackgroundImage = new BackgroundImage(new Image(getClass().getResourceAsStream(MAIN_BACKGROUND_FILE_NAME)),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        creationGameBackgroundImage = new BackgroundImage(new Image(getClass().getResourceAsStream(CREATION_GAME_BACKGROUND_FILE_NAME)),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
    }

    public static IView getInstance() {
        if(instance == null)
            instance = new DDventureView();
        return instance;
    }

    @Override
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    @Override
    public void setSecondaryStage() {

    }

    @Override
    public Stage getSecondaryStage() {
        return this.secondaryStage;
    }

    @Override
    public MusicManager getMusicManager() {
        return MusicManager.getInstance();
    }

    @Override
    public void createAnOpenMainMenuScene () {
        AnchorPane backgroundPane;
        if(mainScene == null) {
            backgroundPane = new AnchorPane();
            backgroundPane.setBackground(new Background(mainBackgroundImage));
            mainScene = new Scene(backgroundPane);
            primaryStage.setScene(mainScene);
        } else {
            backgroundPane = (AnchorPane) mainScene.getRoot();
        }
        backgroundPane.getChildren().clear();
        MainMenuView mainMenu = new MainMenuView();
        backgroundPane.getChildren().add(mainMenu);
        centerPanel(mainMenu, 0);
    }



    private Parent getRootPane() {
        Parent rootPane = null;
        if(mainScene == null) {
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("strings");
                rootPane = FXMLLoader.load(
                        this.getClass().getResource(DDventureViewConstants.ROOT_PANE_FXML_FILE_NAME),
                        resourceBundle);
                mainScene = new Scene(rootPane);
                primaryStage.setScene(mainScene);
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            rootPane = mainScene.getRoot();
        }
        return rootPane;
    }

    @Override
    public void createAnOpenOptionScene(Stage currentStage) {
        AnchorPane backgroundPane;
        if(currentStage.equals(primaryStage)){
            if(mainScene != null) {
                backgroundPane = (AnchorPane) mainScene.getRoot();
            } else {
                backgroundPane = new AnchorPane();
                backgroundPane.setBackground(new Background(mainBackgroundImage));
            }
        } else {
            backgroundPane = new AnchorPane();
            backgroundPane.setBackground(new Background(mainBackgroundImage));
            secondaryStage.setScene(new Scene(backgroundPane, 800, 600));
            secondaryStage.setX(primaryStage.getX() + primaryStage.getWidth()/2 - secondaryStage.getWidth()/2);
            secondaryStage.setY(primaryStage.getY() + primaryStage.getHeight()/2 - secondaryStage.getHeight()/2);
        }
        backgroundPane.getChildren().clear();
        OptionMenuView optionMenuView = new OptionMenuView(currentStage);
        backgroundPane.getChildren().add(optionMenuView);
        centerPanel(optionMenuView,0);
    }

    @Override
    public void createAnOpenGuideScene(Stage currentStage) {
        AnchorPane backgroundPane;
        if(currentStage.equals(primaryStage)) {
            if(mainScene != null) {
                backgroundPane = (AnchorPane) mainScene.getRoot();
                backgroundPane.getChildren().clear();
            } else {
                backgroundPane = new AnchorPane();
                backgroundPane.setBackground(new Background(mainBackgroundImage));
            }
        } else {
            backgroundPane = new AnchorPane();
            backgroundPane.setBackground(new Background(mainBackgroundImage));
            secondaryStage.setScene(new Scene(backgroundPane, PRIMARY_STAGE_WIDTH, PRIMARY_STAGE_HEIGHT));
            secondaryStage.setX(primaryStage.getX() + primaryStage.getWidth()/2 - secondaryStage.getWidth()/2);
            secondaryStage.setY(primaryStage.getY() + primaryStage.getHeight()/2 - secondaryStage.getHeight()/2);
        }
        GuideScene guideScene = new GuideScene(currentStage);
        backgroundPane.getChildren().add(guideScene);
        centerPanel(guideScene, 0);

    }

    @Override
    public void createAnOpenTeamScene() {
        AnchorPane backgroundPane = (AnchorPane) mainScene.getRoot();
        if(mainScene != null) {
            if(creationGamePane == null) {
                creationGamePane = new AnchorPane();
                creationGamePane.setBackground(new Background(creationGameBackgroundImage));
            }
            backgroundPane.getChildren().clear();
            backgroundPane.getChildren().add(creationGamePane);
            centerPanel(creationGamePane, 30);
            creationGamePane.getChildren().clear();
            TeamCreationMenuView teamCreationMenuView = new TeamCreationMenuView();
            creationGamePane.getChildren().add(teamCreationMenuView);
            centerPanel(teamCreationMenuView, 0);
        }
    }

    @Override
    public void createAnOpenPlayerScene() {
        if(creationGamePane != null) {
            creationGamePane.getChildren().clear();
            CharacterCreationMenuView characterCreationMenuView = new CharacterCreationMenuView();
            creationGamePane.getChildren().add(characterCreationMenuView);
            centerPanel(characterCreationMenuView, 0);
        }
    }

    @Override
    public void createAnOpenMapScene() {
        if(creationGamePane != null) {
            creationGamePane.getChildren().clear();
            MapMenuView mapMenuView = new MapMenuView();
            creationGamePane.getChildren().add(mapMenuView);
            centerPanel(mapMenuView, 0);
        }
    }

    @Override
    public void createAnOpenDragNDropScene() {
        if(creationGamePane !=  null) {
            creationGamePane.getChildren().clear();
            DragNDropView dragNDropView = new DragNDropView();
            creationGamePane.getChildren().add(dragNDropView);
            centerPanel(dragNDropView, 0);
        }
    }

    @Override
    public void createAnOpenGameScene() {
        if(creationGamePane != null) {
            creationGamePane.getChildren().clear();
            GameScene gameView = new GameScene();
            creationGamePane.getChildren().add(gameView);
            centerPanel(gameView, 0);
        }
    }

    @Override
    public void createAnOpenInitiativeScene() {
        if(creationGamePane != null) {
            creationGamePane.getChildren().clear();
            InitiativeView initiativeView = new InitiativeView();
            creationGamePane.getChildren().add(initiativeView);
            centerPanel(initiativeView, 0);
        }
    }

    @Override
    public void createAnOpenVictoryStage() {
        if(creationGamePane != null) {
            creationGamePane.getChildren().clear();
            WinnerView winnerView = new WinnerView();
            creationGamePane.getChildren().add(winnerView);
            centerPanel(winnerView, 0);
        }
    }

    @Override
    public void createAnOpenPauseMenu() {
        if(secondaryStage == null) {
            secondaryStage = new Stage();
            pauseScene = new Scene(new PauseBox());
            secondaryStage.setScene(this.pauseScene);
            secondaryStage.setAlwaysOnTop(true);
            secondaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.getScene().getRoot().setDisable(true);
            secondaryStage.show();
        } else {
            secondaryStage.setScene(new Scene(new PauseBox()));
        }
        this.secondaryStage.setX(this.primaryStage.getX() + this.primaryStage.getWidth() / 2 - this.secondaryStage.getWidth() / 2);
        this.secondaryStage.setY(this.primaryStage.getY() + this.primaryStage.getHeight() / 2 - this.secondaryStage.getHeight() / 2);
    }

    private void centerPanel(Pane childPane, double value) {
        AnchorPane.setTopAnchor(childPane, value);
        AnchorPane.setBottomAnchor(childPane, value);
        AnchorPane.setLeftAnchor(childPane, value);
        AnchorPane.setRightAnchor(childPane, value);
    }

    public String openSaveStage() {
        Stage saveStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showSaveDialog(saveStage).getAbsolutePath();
    }

    @Override
    public String openLoadStage() {
        Stage loadStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(loadStage).getAbsolutePath();
    }
}
