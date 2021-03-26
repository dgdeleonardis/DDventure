package view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DDventureView implements IView{

    private static DDventureView instance = null;
    private static MusicManager musicManager= null;;

    private Stage primaryStage = null;
    private Stage secondaryStage = null;

    private Scene mainMenuScene;
    private Scene optionScene;
    private Scene teamScene;
    private Scene playerScene;
    private Scene mapScene;
    private Scene gameScene;
    private Scene initiativeScene;
    private Scene pauseScene;
    private Scene victoryScene;
    private Scene guideScene;

    private DDventureView() {
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
        this.mainMenuScene = new Scene(new MainMenu(), 1280, 720);
        this.primaryStage.setScene(this.mainMenuScene);
    }

    @Override
    public void createAnOpenOptionScene(Stage currentStage) {
        if(currentStage.equals(this.primaryStage)) {
            this.optionScene = new Scene(new OptionMenu(this.primaryStage), 1280, 720);
            this.primaryStage.setScene(this.optionScene);
        } else if (currentStage.equals(this.secondaryStage)) {
            this.optionScene = new Scene(new OptionMenu(this.secondaryStage), 800, 600);
            this.secondaryStage.setScene(this.optionScene);
            this.secondaryStage.setX(this.primaryStage.getX() + this.primaryStage.getWidth() / 2 - this.secondaryStage.getWidth() / 2);
            this.secondaryStage.setY(this.primaryStage.getY() + this.primaryStage.getHeight() / 2 - this.secondaryStage.getHeight() / 2);
        }

    }

    @Override
    public void createAnOpenGuideScene(Stage currentStage) {
        if(currentStage.equals(this.primaryStage)) {
            this.guideScene = new Scene(new GuideScene(this.primaryStage), 1280, 720);
            this.primaryStage.setScene(this.guideScene);
        } else if(currentStage.equals(this.secondaryStage)) {
            this.guideScene = new Scene(new GuideScene(this.secondaryStage), 1280, 720);
            this.secondaryStage.setScene(this.guideScene);
            this.secondaryStage.setX(this.primaryStage.getX() + this.primaryStage.getWidth() / 2 - this.secondaryStage.getWidth() / 2);
            this.secondaryStage.setY(this.primaryStage.getY() + this.primaryStage.getHeight() / 2 - this.secondaryStage.getHeight() / 2);
        }
    }

    @Override
    public void createAnOpenTeamScene() {
        this.teamScene = new Scene(new TeamCreationWindow(), 1280, 720);
        this.primaryStage.setScene(this.teamScene);
    }

    @Override
    public void createAnOpenPlayerScene() {
        this.playerScene = new Scene(new PlayerScene(), 1280, 720);
        this.primaryStage.setScene(this.playerScene);
    }

    @Override
    public void createAnOpenMapScene() {

    }

    @Override
    public void createAnOpenDragNDropScene() {

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
    public void createAnOpenPauseStage() {
        if(this.secondaryStage == null) {
            this.secondaryStage = new Stage();
            this.pauseScene = new Scene(new PauseBox());
            this.secondaryStage.setScene(this.pauseScene);
            this.secondaryStage.setAlwaysOnTop(true);
            this.secondaryStage.initStyle(StageStyle.UNDECORATED);
            this.primaryStage.getScene().getRoot().setDisable(true);
            this.secondaryStage.show();
        } else {
            this.secondaryStage.setScene(new Scene(new PauseBox()));
        }
        this.secondaryStage.setX(this.primaryStage.getX() + this.primaryStage.getWidth() / 2 - this.secondaryStage.getWidth() / 2);
        this.secondaryStage.setY(this.primaryStage.getY() + this.primaryStage.getHeight() / 2 - this.secondaryStage.getHeight() / 2);
    }
}
