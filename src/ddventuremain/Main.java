package ddventuremain;

import javafx.application.Application;
import javafx.stage.Stage;
import view.DDventureView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DDventureView.getInstance().setPrimaryStage(primaryStage);
        DDventureView.getInstance().createAnOpenMainMenuScene();
        DDventureView.getInstance().getPrimaryStage().setTitle("DDventure");
        DDventureView.getInstance().getPrimaryStage().show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
