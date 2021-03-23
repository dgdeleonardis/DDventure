package view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public interface IView {

    public void setPrimaryStage(Stage primaryStage);

    public Stage getPrimaryStage();

    public void setSecondaryStage();

    public Stage getSecondaryStage();

    public MusicManager getMusicManager();

    public void getOpenMainScene();

    public void createAnOpenMainMenuScene();

    public void createAnOpenOptionScene();

    public void createAnOpenGuideScene();

    public void createAnOpenTeamScene();

    public void createAnOpenPlayerScene();

    public void createAnOpenMapScene();

    public void createAnOpenDragNDropScene();

    public void createAnOpenGameScene();

    public void createAnOpenInitiativeScene();

    public void createAnOpenVictoryStage();

    public void createAnOpenPauseStage();
}
