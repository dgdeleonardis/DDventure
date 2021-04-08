package view;

import javafx.stage.Stage;

public interface IView {

    public void setPrimaryStage(Stage primaryStage);

    public Stage getPrimaryStage();

    public void setSecondaryStage();

    public Stage getSecondaryStage();

    public MusicManager getMusicManager();

    public void createAnOpenMainMenuScene();

    public void createAnOpenOptionScene(Stage currentStage);

    public void createAnOpenGuideScene(Stage currentStage);

    public void createAnOpenTeamScene();

    public void createAnOpenPlayerScene();

    public void createAnOpenMapScene();

    public void createAnOpenDragNDropScene();

    public void createAnOpenGameScene();

    public void createAnOpenInitiativeScene();

    public void createAnOpenVictoryStage();

    public void createAnOpenPauseMenu();

    public String openSaveStage();

    public String openLoadStage();
}
