package view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class DDventureView implements IView{

    private static DDventureView instance = null;

    private Stage primaryStage = null;
    private Stage secondaryStage;

    private Scene mainMenuScene;
    private Scene optionScene;
    private Scene teamScene;
    private Scene playerScene;
    private Scene mapScene;
    private Scene gameScene;
    private Scene iniativeScene;
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
    public void getOpenMainScene() {
    }

    @Override
    public void createAnOpenMainMenuScene() {
        this.mainMenuScene = new Scene(new MainMenu(), 300, 300);
        this.primaryStage.setScene(this.mainMenuScene);
    }

    @Override
    public void createAnOpenOptionScene() {

    }

    @Override
    public void createAnOpenGuideScene() {

    }

    @Override
    public void createAnOpenTeamScene() {

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
