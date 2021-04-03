package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DDventureView implements IView{

    private static final String MAIN_BACKGROUND_FILE_NAME = "image/background-hell_gate.png";
    private static final String CREATION_GAME_BACKGROUND_FILE_NAME = "image/background-leather.jpg";
    public static final String FONT_FILE_NAME = "font/alagard.ttf";
    public static final int PRIMARY_STAGE_WIDTH = 1280;
    public static final int PRIMARY_STAGE_HEIGHT = 720;

    private static DDventureView instance = null;
    private static MusicManager musicManager= null;

    private Stage primaryStage = null;
    private Stage secondaryStage = null;

    private Scene mainScene;
    private AnchorPane creationGamePane;

    private Scene gameScene;
    private Scene initiativeScene;
    private Scene pauseScene;
    private Scene victoryScene;
    private Scene guideScene;

    private final BackgroundImage mainBackgroundImage;
    private final BackgroundImage creationGameBackgroundImage;

    private DDventureView() {
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
    public void getOpenMainScene() {
    }

    @Override
    public void createAnOpenMainMenuScene() {
        /*
        per marta: per risolvere il problema relativo al refresh dell'immagine di background ho pensato di creare un
        panello di sfondo fisso e aggiungere di volta in volta i vari layout.
        */
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
        MainMenu mainMenu = new MainMenu();
        backgroundPane.getChildren().add(mainMenu);
        centerPanel(mainMenu, 0);
    }

    private void centerPanel(Pane childPane, double value) {
        AnchorPane.setTopAnchor(childPane, value);
        AnchorPane.setBottomAnchor(childPane, value);
        AnchorPane.setLeftAnchor(childPane, value);
        AnchorPane.setRightAnchor(childPane, value);
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
        OptionMenu optionMenu = new OptionMenu(currentStage);
        backgroundPane.getChildren().add(optionMenu);
        centerPanel(optionMenu,0);
    }

    @Override
    public void createAnOpenGuideScene(Stage currentStage) {
        if(currentStage.equals(this.primaryStage)) {
            this.guideScene = new Scene(new GuideScene(this.primaryStage));
            this.primaryStage.setScene(this.guideScene);
        } else if(currentStage.equals(this.secondaryStage)) {
            this.guideScene = new Scene(new GuideScene(this.secondaryStage), 1280, 720);
            this.secondaryStage.setScene(this.guideScene);
            secondaryStage.centerOnScreen();
            this.secondaryStage.setX(this.primaryStage.getX() + this.primaryStage.getWidth() / 2 - this.secondaryStage.getWidth()/2);
            this.secondaryStage.setY(this.primaryStage.getY() + this.primaryStage.getHeight() / 2 - this.secondaryStage.getHeight()/2);
        }
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
            TeamCreationWindow teamCreationWindow = new TeamCreationWindow();
            creationGamePane.getChildren().add(teamCreationWindow);
            centerPanel(teamCreationWindow, 0);
        }
    }

    @Override
    public void createAnOpenPlayerScene() {
        if(creationGamePane != null) {
            creationGamePane.getChildren().clear();
            PlayerScene playerScene = new PlayerScene();
            creationGamePane.getChildren().add(playerScene);
            centerPanel(playerScene, 0);
        }
    }

    @Override
    public void createAnOpenMapScene() {
        if(creationGamePane != null) {
            creationGamePane.getChildren().clear();
            MapScene mapScene = new MapScene();
            creationGamePane.getChildren().add(mapScene);
            centerPanel(mapScene, 0);
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
    }

    @Override
    public void createAnOpenInitiativeScene() {

    }

    @Override
    public void createAnOpenVictoryStage() {

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
}
