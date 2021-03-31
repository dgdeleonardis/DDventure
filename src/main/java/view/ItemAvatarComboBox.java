package view;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ItemAvatarComboBox extends HBox {

    private static final double WIDTH_THUMBNAIL = 31;
    private static final double HEIGHT_THUMBNAIL = 32;
    private static final double MIN_Y_THUMBNAIL = 16;

    public static final String AVATAR_DIRECTORY_RELATIVE_PATH = "image/avatar/";

    private final Label spriteNameLabel;

    public ItemAvatarComboBox(String spriteName, String spriteImageFileName) {
        spriteNameLabel = new Label(spriteName);
        spriteNameLabel.setFont(Font.loadFont(
                getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), 16)
        );

        ImageView spriteThumbnail = new ImageView(
                new Image(getClass().getResourceAsStream(AVATAR_DIRECTORY_RELATIVE_PATH + spriteImageFileName))
        );
        spriteThumbnail.setFitHeight(WIDTH_THUMBNAIL);
        spriteThumbnail.setViewport(new Rectangle2D(spriteThumbnail.getImage().getWidth() - WIDTH_THUMBNAIL,
                MIN_Y_THUMBNAIL,
                WIDTH_THUMBNAIL,
                HEIGHT_THUMBNAIL
        ));
        spriteThumbnail.setCache(true);
        spriteThumbnail.setCacheHint(CacheHint.SPEED);
        this.getChildren().addAll(spriteNameLabel, spriteThumbnail);
        this.setSpacing(10);
        this.setBackground(new Background(
                new BackgroundFill(Color.web("99CC99"), CornerRadii.EMPTY, Insets.EMPTY)
        ));
    }

    public String getSpriteName() {
        return spriteNameLabel.getText();
    }

}
