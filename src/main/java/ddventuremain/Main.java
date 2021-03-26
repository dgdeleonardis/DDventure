package ddventuremain;

import javafx.application.Application;
import javafx.stage.Stage;
import view.DDventureView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        DDventureView.getInstance().setPrimaryStage(primaryStage);
        DDventureView.getInstance().getPrimaryStage().setTitle("DDventure");
        DDventureView.getInstance().getMusicManager().playMusic();
        // test section
        DDventureView.getInstance().createAnOpenMainMenuScene();
        // end test section
        DDventureView.getInstance().getPrimaryStage().setResizable(true);
        DDventureView.getInstance().getPrimaryStage().show();

        // test section part 2
            DDventureView.getInstance().createAnOpenPauseStage();
        // end test section
    }


    public static void main(String[] args) {
        launch(args);
    }


}
