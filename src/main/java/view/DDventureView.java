package view;

import javafx.scene.Scene;
import javafx.stage.Stage;

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
        return null;
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
    public void createAnOpenOptionScene() {
        this.optionScene = new Scene(new OptionMenu(), 1280, 720);
        this.primaryStage.setScene(this.optionScene);
    }

    @Override
    public void createAnOpenGuideScene() {

    }

    @Override
    public void createAnOpenTeamScene() {
        this.teamScene = new Scene(new TeamCreationWindow(), 1280, 720);
        this.primaryStage.setScene(this.teamScene);
    }

    @Override
    public void createAnOpenPlayerScene() {

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

    }
}
