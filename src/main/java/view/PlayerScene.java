package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Transform;
import javafx.util.Callback;

import java.util.HashMap;

public class PlayerScene extends AnchorPane {

    // new code section
    private static final String BACKGROUND_IMAGE_FILE_NAME = "image/background-hell_gate.png";
    private static final String LEATHER_BACKGROUND_IMAGE_FILE_NAME = "image/background-lether.jpg";
    public static final String FONT_FILE_NAME = "fonts/alagard.ttf";
    public static final String AVATAR_DIRECTORY_RELATIVE_PATH = "image/avatar/";

    private static final HashMap<String, String> SPRITE_MAP = new HashMap<String, String>() {{
        put("Bandito", "Banditsheetver4.png");
        put("Cavaliere 1", "Cavalire brother1.png");
        put("Cavaliere 2", "cavalire brother2.png");
        put("Bandito delle caverne", "CaveBanditversheet.png");
        put("Primo ministro", "Corrupt prim ministerversheet.png");
        put("Maga", "Dark Magesheet.png");
        put("Guardia del re", "Enemy Guardver3.png");
        put("Lord 1", "Enemy Lordsheet3ver2.png");
        put("Lord 2", "Enemy Lordsheetnormver2.png");
        put("Mago", "Enemy Mageversheet.png");
        put("Bandita", "erika sonsheetver5.png");
        put("Guardia del capitano", "Guard Captain Enemysheet.png");
        put("Mago apprendista", "Light Mage.png");
        put("Stregone 1", "Lyon HoodOlderver3.png");
        put("Stregone 2", "Lyon HoodOlderver4no hood.png");
        put("Soldato semplice 1", "pegasus brother.png");
        put("Soldato semplice 1 (f.)", "pegasus sister1 sheetver2.png");
        put("Soldato semplice 2 (f.)", "pegasus sister2.png");
        put("Soldato semplice 2", "soldierver3Aran.png");
        put("Barbaro", "Tribe leader sheet.png");
    }};
    // end section

    private BorderPane mainPane;

    //top section
    protected Label title;

    //left section
    protected Label nameL;
    protected TextField nameInsert;
    protected Label pfL;
    protected TextField pfInsert;
    protected Label caL;
    protected TextField caInsert;
    protected Label speedL;
    protected TextField speedInsert;
    protected Label initiativeL;
    protected TextField initiativeInsert;
    protected Label teamL;
    protected ChoiceBox teamChoice;
    protected VBox lsection;

    //right section
    protected VBox rsection;

    private ImageView spriteView;
    private HBox avatarBox;
    protected Label avatarL;
    protected ComboBox<String> avatarComboBox;



    protected Label weaponL;
    protected ComboBox weaponBox;
    protected Label weaponDamageL;
    protected TextField weaponDamageInsert;
    protected Label tcL;
    protected TextField tcInsert;


    //bottom section
    protected AnchorPane bottomPane;
    protected Button backButton;
    protected Button nextPlayer;
    protected Button confirmButton;

    public PlayerScene(){
        super();
        BackgroundImage backgroundImage = new BackgroundImage(new Image(this.getClass().getResourceAsStream(BACKGROUND_IMAGE_FILE_NAME)),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        this.setBackground(new Background(backgroundImage));

        this.mainPane = new BorderPane();
        Image imgMainPane = new Image(this.getClass().getResourceAsStream(LEATHER_BACKGROUND_IMAGE_FILE_NAME));
        BackgroundImage letherBackground = new BackgroundImage(imgMainPane,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0,
                        1.0,
                        true,
                        true,
                        false,
                        false));
        this.mainPane.setBackground(new Background(letherBackground));
        this.getChildren().add(this.mainPane);
        AnchorPane.setTopAnchor(this.mainPane, 30.0);
        AnchorPane.setLeftAnchor(this.mainPane, 30.0);
        AnchorPane.setRightAnchor(this.mainPane, 30.0);
        AnchorPane.setBottomAnchor(this.mainPane, 30.0);
        this.mainPane.setPadding(new Insets(10, 10, 10, 10));

        Font textFont = Font.loadFont(this.getClass().getResourceAsStream(FONT_FILE_NAME), 24);

        //top section
        this.title = new Label("Costruzione del personaggio");
        this.title.setFont(Font.loadFont(this.getClass().getResourceAsStream(FONT_FILE_NAME), 34));
        this.mainPane.setTop(this.title);
        BorderPane.setMargin(this.title, new Insets(30, 0, 30, 0));
        BorderPane.setAlignment(this.title, Pos.CENTER);

        //left section
        this.nameL = new Label("Nome");
        this.nameL.setFont(textFont);
        this.nameInsert = new TextField();
        HBox name = new HBox(this.nameL,this.nameInsert);
        name.setSpacing(20);
        this.pfL = new Label("PF");
        this.pfL.setFont(textFont);
        this.pfInsert = new TextField();
        HBox pf = new HBox(this.pfL, this.pfInsert);
        pf.setSpacing(20);
        this.caL = new Label("CA");
        this.caL.setFont(textFont);
        this.caInsert = new TextField();
        HBox ca = new HBox(this.caL, this.caInsert);
        ca.setSpacing(20);
        this.speedL = new Label("Velocita'");
        this.speedL.setFont(textFont);
        this.speedInsert = new TextField();
        HBox speed = new HBox(this.speedL, this.speedInsert);
        speed.setSpacing(20);
        this.initiativeL = new Label("Modificatore Destrezza");
        this.initiativeL.setFont(textFont);
        this.initiativeInsert = new TextField();
        HBox initiative = new HBox(this.initiativeL, this.initiativeInsert);
        initiative.setSpacing(20);
        this.teamL = new Label("Schieramento");
        this.teamL.setFont(textFont);
        this.teamChoice = new ChoiceBox();
        this.teamChoice.getItems().addAll("2",
                "3",
                "4",
                "5",
                "6");
        HBox team = new HBox(this.teamL, this.teamChoice);
        initiative.setSpacing(20);

        this.lsection = new VBox(name, pf, ca, speed, initiative, team);
        this.mainPane.setLeft(this.lsection);
        BorderPane.setAlignment(this.lsection, Pos.CENTER);
        BorderPane.setMargin(this.lsection, new Insets(50, 0, 0, 80));
        this.lsection.setSpacing(30);

        //right section - new code

        this.spriteView = new ImageView();


        this.avatarL = new Label("Avatar");
        this.avatarL.setFont(textFont);

        this.avatarComboBox = new ComboBox();
        PlayerScene.SPRITE_MAP.forEach( (k, v) -> {
            this.avatarComboBox.getItems().add(k);
        });
        this.avatarComboBox.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
           this.spriteView.setImage(new Image(
                   getClass().getResourceAsStream(AVATAR_DIRECTORY_RELATIVE_PATH + SPRITE_MAP.get(newValue)))
           );
           this.spriteView.setViewport(new Rectangle2D(1, 0, 95, 80));
        });

        this.avatarComboBox.getSelectionModel().selectFirst();

        this.avatarComboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null) {
                            setText(item);
                            setFont(textFont);
                        }

                    }
                };
            }
        });
        this.avatarComboBox.setCache(true);
        this.avatarComboBox.setCacheHint(CacheHint.SPEED);

        this.avatarBox = new HBox(this.avatarL, this.avatarComboBox);
        this.avatarBox.setAlignment(Pos.CENTER);
        this.avatarBox.setSpacing(30);

        this.weaponL = new Label("Arma");
        this.weaponL.setFont(textFont);
        this.weaponBox = new ComboBox();
        HBox weapon = new HBox(this.weaponL, this.weaponBox);
        this.weaponDamageL = new Label("Dado Danni");
        this.weaponDamageL.setFont(textFont);
        this.weaponDamageInsert = new TextField();
        HBox weaponDamage = new HBox(this.weaponDamageL, this.weaponDamageInsert);
        weaponDamage.setSpacing(20);
        this.tcL = new Label("Modificatore TC");
        this.tcL.setFont(textFont);
        this.tcInsert = new TextField();
        HBox tc = new HBox(this.tcL, this.tcInsert);
        tc.setSpacing(20);

        this.rsection = new VBox(this.spriteView, this.avatarBox, weapon, weaponDamage, tc);
        this.spriteView.getTransforms().add(Transform.scale(2, 2.1, 100, 100));
        this.spriteView.setEffect(new DropShadow(30, Color.web("99CC99")));
        this.rsection.setSpacing(30);
        this.rsection.setAlignment(Pos.CENTER);
        this.mainPane.setRight(this.rsection);
        BorderPane.setAlignment(this.rsection, Pos.CENTER);
        BorderPane.setMargin(this.rsection, new Insets(50, 60, 0, 0));
        this.rsection.setSpacing(30);



        //bottom section
        this.bottomPane = new AnchorPane();
        this.backButton = new Button("Torna agli schieramenti");
        this.backButton.setOnAction(event -> {
            DDventureView.getInstance().createAnOpenTeamScene();
        });
        this.confirmButton = new Button("Avanti");
        this.nextPlayer = new Button("Prossimo Giocatore");
        HBox nextHBox = new HBox(this.nextPlayer, this.confirmButton);
        nextHBox.setSpacing(100);
        this.confirmButton.setFont(textFont);
        this.backButton.setFont(textFont);
        this.nextPlayer.setFont(textFont);

        this.bottomPane.getChildren().addAll(this.backButton, nextHBox);
        AnchorPane.setLeftAnchor(this.backButton, 10.0);
        AnchorPane.setBottomAnchor(this.backButton, 10.0);
        AnchorPane.setRightAnchor(nextHBox, 10.0);
        AnchorPane.setBottomAnchor(nextHBox, 10.0);
        this.mainPane.setBottom(this.bottomPane);
    }
}