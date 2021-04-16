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
import logic.DDventureLogic;

import java.util.HashMap;

public class CharacterCreationMenuView extends BorderPane {

    public static final String AVATAR_DIRECTORY_RELATIVE_PATH = "image/mugshoot/";

    private static final HashMap<String, String> SPRITE_MAP = new HashMap<String, String>() {{
        put("Arcer (M)", "Arcer.png");
        put("Arcer (F)", "Rebecca.png");
        put("Assassin (M)", "Assassin.png");
        put("Assassin (F)", "Assassina.png");
        put("Ax (M)", "Hector.png");
        put("Ax (F)", "Asciafem.png");
        put("Mage (M)", "Athos.png");
        put("Mage (F)", "Maga.png");
        put("Dark Mage(M)", "MagoOscuro.png");
        put("Dark Mage (F)", "MagaOscura.png");
        put("Knight (M)", "Cavalierone.png");
        put("Knight (F)", "Cavaliera.png");
        put("Swordman (M)", "Eliwood.png");
        put("Swodman (F)", "Lyn.png");
    }};
    // end section

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
    protected ChoiceBox<String> teamChoice;
    protected VBox lsection;

    //right section
    protected VBox rsection;

    private ImageView spriteView;
    private HBox avatarBox;
    protected Label avatarL;
    protected ComboBox<String> avatarComboBox;



    protected Label weaponL;
    protected ComboBox<String> weaponBox;
    protected Label weaponDamageL;
    protected TextField weaponDamageInsert;
    protected Label tcL;
    protected TextField tcInsert;


    //bottom section
    protected AnchorPane bottomPane;
    protected Button backButton;
    protected Button nextPlayer;
    protected Button confirmButton;

    public CharacterCreationMenuView(){
        super();
        setPrefSize(
                DDventureView.PRIMARY_STAGE_WIDTH - (30*2),
                DDventureView.PRIMARY_STAGE_HEIGHT - (30*2));
        setPadding(new Insets(10, 10, 10, 10));

        Font textFont = Font.font("Alagard", 24);

        //top section
        this.title = new Label("Costruzione del personaggio");
        this.title.setFont(Font.font("Alagard", 34));
        setTop(this.title);
        BorderPane.setMargin(this.title, new Insets(30, 0, 30, 0));
        BorderPane.setAlignment(this.title, Pos.CENTER);

        //left section
        Button saveCharacterButton = new Button("Salva personaggio");
        saveCharacterButton.setFont(textFont);
        saveCharacterButton.setOnAction(event -> {
            //TODO: implementare salvataggio scegliendo il percorso del file e il nome.
            DDventureLogic.getInstance().createCharacter(
                    nameInsert.getText(),
                    Integer.parseInt(pfInsert.getText()),
                    Integer.parseInt(caInsert.getText()),
                    Integer.parseInt(speedInsert.getText()),
                    Integer.parseInt(initiativeInsert.getText()),
                    avatarComboBox.getSelectionModel().getSelectedItem(),
                    weaponBox.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(weaponDamageInsert.getText()),
                    Integer.parseInt(tcInsert.getText())
            );
            String fileName = DDventureView.getInstance().openSaveStage();
            if(DDventureLogic.getInstance().saveCharacter(fileName)) {
                System.out.println("Operazione andata a buon fine");
            } else {
                System.out.println("Qualcosa è andato storto");
            }
        });

        Button loadCharacterButton = new Button("Carica personaggio");
        loadCharacterButton.setFont(textFont);
        loadCharacterButton.setOnAction(event -> {
            String fileName = DDventureView.getInstance().openLoadStage();
            DDventureLogic.getInstance().loadCharacter(fileName);
            populateView();
        });

        HBox saveAndLoadBox = new HBox(saveCharacterButton, loadCharacterButton);
        saveAndLoadBox.setSpacing(30.0);

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
        this.teamChoice = new ChoiceBox<>();

        this.teamChoice.getItems().addAll(DDventureLogic.getInstance().getTeamNames());
        HBox team = new HBox(this.teamL, this.teamChoice);
        initiative.setSpacing(20);

        this.lsection = new VBox(saveAndLoadBox, name, pf, ca, speed, initiative, team);
        setLeft(this.lsection);
        BorderPane.setAlignment(this.lsection, Pos.CENTER);
        BorderPane.setMargin(this.lsection, new Insets(50, 0, 0, 80));
        this.lsection.setSpacing(30);

        //right section - new code

        this.spriteView = new ImageView();


        this.avatarL = new Label("Avatar");
        this.avatarL.setFont(textFont);

        this.avatarComboBox = new ComboBox();
        CharacterCreationMenuView.SPRITE_MAP.forEach( (k, v) -> {
            this.avatarComboBox.getItems().add(k);
        });
        this.avatarComboBox.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
           this.spriteView.setImage(new Image(
                   getClass().getResourceAsStream(AVATAR_DIRECTORY_RELATIVE_PATH + SPRITE_MAP.get(newValue)))
           );
           this.spriteView.setViewport(new Rectangle2D(1, 0, 95, 80));
        });

        this.avatarComboBox.getSelectionModel().selectFirst();
        //FIXME: possibilità di convertire la ComboBox in una ChoiceBox;
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
        this.weaponBox = new ComboBox<>();
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
        setRight(this.rsection);
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
        this.nextPlayer.setOnAction(event -> {
            createCharacterInGame();
            clearView();
        });

        HBox nextHBox = new HBox(this.nextPlayer, this.confirmButton);
        nextHBox.setSpacing(100);
        this.confirmButton.setFont(textFont);
        confirmButton.setOnAction( event -> {
            createCharacterInGame();
            DDventureView.getInstance().createAnOpenMapScene();
        });
        this.backButton.setFont(textFont);
        this.backButton.setOnAction(event -> {
            DDventureLogic.getInstance().deleteCharactersInGame();
            DDventureLogic.getInstance().deleteTeams();
            DDventureView.getInstance().createAnOpenTeamScene();
        });
        this.nextPlayer.setFont(textFont);

        this.bottomPane.getChildren().addAll(this.backButton, nextHBox);
        AnchorPane.setLeftAnchor(this.backButton, 10.0);
        AnchorPane.setBottomAnchor(this.backButton, 10.0);
        AnchorPane.setRightAnchor(nextHBox, 10.0);
        AnchorPane.setBottomAnchor(nextHBox, 10.0);
        setBottom(this.bottomPane);
    }

    private void populateView() {
        nameInsert.setText(DDventureLogic.getInstance().getTempCharacter().getName());
        pfInsert.setText(Integer.toString(DDventureLogic.getInstance().getTempCharacter().getPF()));
        caInsert.setText(Integer.toString(DDventureLogic.getInstance().getTempCharacter().getCA()));
        speedInsert.setText(Integer.toString(DDventureLogic.getInstance().getTempCharacter().getSpeed()));
        initiativeInsert.setText(Integer.toString(DDventureLogic.getInstance().getTempCharacter().getInitiativeModifier()));
        //avatarComboBox.getSelectionModel().select();
        avatarComboBox.getSelectionModel().selectFirst();
        //weaponBox.getSelectionModel().select();
        weaponBox.getSelectionModel().selectFirst();
        weaponDamageInsert.setText(Integer.toString(DDventureLogic.getInstance().getTempCharacter().getWeapon().getDamageDice()));
        tcInsert.setText(Integer.toString(DDventureLogic.getInstance().getTempCharacter().getWeapon().getTpcModifier()));
    }

    private void createCharacterInGame() {
        DDventureLogic.getInstance().createCharacterInGame(
                nameInsert.getText(),
                Integer.parseInt(pfInsert.getText()),
                Integer.parseInt(caInsert.getText()),
                Integer.parseInt(speedInsert.getText()),
                Integer.parseInt(initiativeInsert.getText()),
                avatarComboBox.getSelectionModel().getSelectedItem(),
                teamChoice.getSelectionModel().getSelectedItem(),
                weaponBox.getSelectionModel().getSelectedItem(),
                Integer.parseInt(weaponDamageInsert.getText()),
                Integer.parseInt(tcInsert.getText())
        );
    }

    private void clearView() {
        nameInsert.clear();
        pfInsert.clear();
        caInsert.clear();
        speedInsert.clear();
        initiativeInsert.clear();
        avatarComboBox.getSelectionModel().selectFirst();
        teamChoice.getSelectionModel().selectFirst();
        weaponBox.getSelectionModel().selectFirst();
        weaponDamageInsert.clear();
        tcInsert.clear();
    }
}