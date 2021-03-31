package ddventuremain;

import javafx.application.Application;
import javafx.stage.Stage;
import view.DDventureView;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        DDventureView.getInstance().setPrimaryStage(primaryStage);
        DDventureView.getInstance().getPrimaryStage().setTitle("DDventure");
        //DDventureView.getInstance().getMusicManager().playMusic();
        DDventureView.getInstance().createAnOpenMainMenuScene();

        // test section
        //DDventureView.getInstance().createAnOpenPauseMenu();
        //DDventureView.getInstance().createAnOpenMapScene();
        //DDventureView.getInstance().createAnOpenPauseStage();
        // end test section

        DDventureView.getInstance().getPrimaryStage().show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
