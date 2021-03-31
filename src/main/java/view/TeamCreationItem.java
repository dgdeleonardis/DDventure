package view;

import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class TeamCreationItem extends HBox {

    private TextField teamNameText;
    private ColorPicker teamColorPicker;

    public TeamCreationItem() {
        super();
        this.teamNameText = new TextField("Schieramento N");
        this.teamColorPicker = new ColorPicker();
        this.getChildren().addAll(this.teamNameText, this.teamColorPicker);
        Font textFont = Font.loadFont(getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), 24);
        this.teamNameText.setFont(textFont);
        this.setSpacing(30);
        this.setAlignment(Pos.CENTER);
    }
}
