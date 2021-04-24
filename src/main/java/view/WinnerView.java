package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.DDventureLogic;

public class WinnerView  extends BorderPane {

    public WinnerView() {
        // top
        String topLabelText = "Congratulazioni, " + DDventureLogic.getInstance().getWinnerTeamName();
        Label topLabel = new Label(topLabelText);
        topLabel.setFont(Font.font("Alagard", 64));
        this.setTop(topLabel);
        BorderPane.setAlignment(topLabel, Pos.CENTER);
        BorderPane.setMargin(topLabel, new Insets(30));

        // center
        ScrollPane centerSection = new ScrollPane();
        centerSection.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
        centerSection.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        centerSection.setFitToWidth(true);
        centerSection.setFitToHeight(true);

        VBox centerBox = new VBox();
        centerBox.setSpacing(30);
        centerBox.setAlignment(Pos.CENTER);

        String[] winnerTeamMembers = DDventureLogic.getInstance().getWinnerTeamMembers();
        for(int i = 0; i < winnerTeamMembers.length; i++) {
            Label winnerMemberLabel = new Label(winnerTeamMembers[i]);
            winnerMemberLabel.setFont(Font.font("Alagard", 32));
            winnerMemberLabel.setAlignment(Pos.CENTER);
            winnerMemberLabel.setTextFill(Color.BLACK);
            centerBox.getChildren().add(winnerMemberLabel);
        }
        centerSection.setContent(centerBox);
        this.setCenter(centerSection);
        BorderPane.setAlignment(centerSection, Pos.CENTER);

        // bottom
        Button backToMenuButton = new Button("Torna al menu principale");
        backToMenuButton.setFont(Font.font("Alagard", 24));
        backToMenuButton.setOnAction(event -> {
            DDventureLogic.getInstance().resetGame();
            DDventureView.getInstance().createAnOpenMainMenuScene();
        });
        this.setBottom(backToMenuButton);
        BorderPane.setAlignment(backToMenuButton, Pos.CENTER);
        BorderPane.setMargin(backToMenuButton, new Insets(30));
    }
}
