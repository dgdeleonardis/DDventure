package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Transform;
import logic.DDventureLogic;

import java.util.HashMap;


public class CharacterCreationMenuView extends BorderPane {

    public static final String AVATAR_DIRECTORY_RELATIVE_PATH = "image/avatar/";

    private final static int TITLE_FONT_SIZE = 32;
    private final static int BODY_FONT_SIZE = 20;
    private final static int COMBO_BOX_FONT_SIZE = 16;
    private final static double GENERIC_SPACING = 20;
    private final static double LINE_FORM_SPACING = 10;


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

    FormLine nameFormLine;
    FormLine hPFormLine;
    FormLine armorClassFormLine;
    FormLine speedFormLine;
    FormLine initiativeModifierFormLine;
    ChoiceBoxLine teamBoxLine;
    ChoiceBoxLine avatarBoxLine;
    ChoiceBoxLine weaponBoxLine;
    FormLine avatarFormLine;
    FormLine weaponDamageFormLine;
    FormLine tcModifierFormLine;

    ImageView spriteView;

    private class FormLine extends VBox {

        private final TextField lineTextField;

        public FormLine(String lineInformation) {
            super();
            Font font = Font.font("Alagard", COMBO_BOX_FONT_SIZE);

            Label lineLabel = new Label(lineInformation);
            lineLabel.setFont(font);

            lineTextField = new TextField();
            lineTextField.setFont(font);

            lineLabel.setLabelFor(lineTextField);
            this.getChildren().addAll(lineLabel, lineTextField);
            this.setSpacing(LINE_FORM_SPACING);

        }
    }

    private class ChoiceBoxLine extends HBox {

        private final ChoiceBox<String> lineChoiceBox;

        public ChoiceBoxLine(String lineInformation, String[] choiceBoxSet) {
            super();
            Label lineLabel = new Label(lineInformation);
            lineLabel.setFont(Font.font("Alagard", COMBO_BOX_FONT_SIZE));

            lineChoiceBox = new ChoiceBox<>();
            lineChoiceBox.getItems().addAll(choiceBoxSet);
            lineChoiceBox.setStyle("-fx-font: " + COMBO_BOX_FONT_SIZE +" \"Alagard\"");
            lineChoiceBox.getSelectionModel().selectFirst();

            lineLabel.setLabelFor(lineChoiceBox);
            this.getChildren().addAll(lineLabel, lineChoiceBox);
            this.setSpacing(LINE_FORM_SPACING);
        }
    }

    public CharacterCreationMenuView(){
        super();
        setPrefSize(
                DDventureView.PRIMARY_STAGE_WIDTH - (DDventureView.GAME_PANE_SPACING*2),
                DDventureView.PRIMARY_STAGE_HEIGHT - (DDventureView.GAME_PANE_SPACING*2));
        setPadding(new Insets(10, 10, 10, 10));

        Font textFont = Font.font("Alagard", BODY_FONT_SIZE);

        //top section
        Label titleLabel = new Label("Costruzione del personaggio");
        titleLabel.setFont(Font.font("Alagard", TITLE_FONT_SIZE));
        this.setTop(titleLabel);
        BorderPane.setMargin(titleLabel, new Insets(GENERIC_SPACING));
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        //left section
        Button saveCharacterButton = new Button("Salva personaggio");
        saveCharacterButton.setFont(textFont);
        saveCharacterButton.setOnAction(event -> {
            DDventureLogic.getInstance().createCharacter(
                    nameFormLine.lineTextField.getText(),
                    Integer.parseInt(hPFormLine.lineTextField.getText()),
                    Integer.parseInt(armorClassFormLine.lineTextField.getText()),
                    Integer.parseInt(speedFormLine.lineTextField.getText()),
                    Integer.parseInt(initiativeModifierFormLine.lineTextField.getText()),

                    avatarBoxLine.lineChoiceBox.getSelectionModel().getSelectedItem(),
                    weaponBoxLine.lineChoiceBox.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(weaponDamageFormLine.lineTextField.getText()),
                    Integer.parseInt(tcModifierFormLine.lineTextField.getText())
            );
            String fileName = DDventureView.getInstance().openSaveStage();
            // FIXME: modificare
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
            populateFormWithLoadedCharacter();
        });

        HBox saveAndLoadBox = new HBox(saveCharacterButton, loadCharacterButton);
        saveAndLoadBox.setSpacing(GENERIC_SPACING);

        nameFormLine = new FormLine("Nome");
        hPFormLine = new FormLine("PF");
        armorClassFormLine = new FormLine("CA");
        speedFormLine = new FormLine("Velocità");
        initiativeModifierFormLine = new FormLine("Modificatore destrezza");

        String[] teamNames = DDventureLogic.getInstance().getTeamNames();
        ChoiceBoxLine teamBoxLine = new ChoiceBoxLine("Schieramento", teamNames);

        VBox leftSection = new VBox(saveAndLoadBox, nameFormLine, hPFormLine, armorClassFormLine, speedFormLine, initiativeModifierFormLine, teamBoxLine);
        leftSection.setSpacing(GENERIC_SPACING);

        this.setLeft(leftSection);
        BorderPane.setAlignment(leftSection, Pos.CENTER);
        BorderPane.setMargin(leftSection, new Insets(GENERIC_SPACING, 0, 0, 80));

        //right section - new code
        this.spriteView = new ImageView();
        

        String[] avatarNames = new String[SPRITE_MAP.size()];
        SPRITE_MAP.keySet().toArray(avatarNames);
        avatarBoxLine = new ChoiceBoxLine("Avatar", avatarNames);
        avatarBoxLine.lineChoiceBox.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
           this.spriteView.setImage(new Image(
                   getClass().getResourceAsStream(AVATAR_DIRECTORY_RELATIVE_PATH + SPRITE_MAP.get(newValue)))
           );
           this.spriteView.setViewport(new Rectangle2D(1, 0, 95, 80));
        });

        avatarFormLine = new FormLine("Avatar");

        // TODO: definire elenco delle armi
        String[] weaponNames = new String[0];
        weaponBoxLine = new ChoiceBoxLine("Arma", weaponNames);
        weaponDamageFormLine = new FormLine("Dado danni");
        tcModifierFormLine = new FormLine("Modificatore TC");

        this.spriteView.getTransforms().add(Transform.scale(2, 2.1, 100, 100));
        this.spriteView.setEffect(new DropShadow(30, Color.web("99CC99")));

        VBox rightSection = new VBox(this.spriteView, avatarBoxLine, weaponBoxLine, weaponDamageFormLine, tcModifierFormLine);
        rightSection.setSpacing(GENERIC_SPACING);
        rightSection.setAlignment(Pos.CENTER);
        setRight(rightSection);

        BorderPane.setAlignment(rightSection, Pos.CENTER);
        BorderPane.setMargin(rightSection, new Insets(50, 60, 0, 0));

        //bottom section
        AnchorPane bottomPane = new AnchorPane();

        Button backButton = new Button("Torna agli schieramenti");
        backButton.setFont(textFont);
        backButton.setOnAction(event -> {
            DDventureLogic.getInstance().deleteCharactersInGame();
            DDventureLogic.getInstance().deleteTeams();
            DDventureView.getInstance().createAnOpenTeamScene();
        });

        Button confirmButton = new Button("Avanti");
        confirmButton.setFont(textFont);
        confirmButton.setOnAction( event -> {
            createCharacterInGame();
            DDventureView.getInstance().createAnOpenMapScene();
        });

        Button nextPlayerButton = new Button("Prossimo Giocatore");
        nextPlayerButton.setFont(textFont);
        nextPlayerButton.setOnAction(event -> {
            createCharacterInGame();
            clearView();
        });

        HBox nextHBox = new HBox(nextPlayerButton, confirmButton);
        nextHBox.setSpacing(100);

        bottomPane.getChildren().addAll(backButton, nextHBox);

        AnchorPane.setLeftAnchor(backButton, 10.0);
        AnchorPane.setBottomAnchor(backButton, 10.0);
        AnchorPane.setRightAnchor(nextHBox, 10.0);
        AnchorPane.setBottomAnchor(nextHBox, 10.0);
        this.setBottom(bottomPane);
    }

    private void populateFormWithLoadedCharacter() {
        String characterName = DDventureLogic.getInstance().getTempCharacter().getName();
        nameFormLine.lineTextField.setText(characterName);

        String characterHP = Integer.toString(DDventureLogic.getInstance().getTempCharacter().getPF());
        hPFormLine.lineTextField.setText(characterHP);

        String characterArmorClass = Integer.toString(DDventureLogic.getInstance().getTempCharacter().getCA());
        armorClassFormLine.lineTextField.setText(characterArmorClass);

        String characterSpeed = Integer.toString(DDventureLogic.getInstance().getTempCharacter().getSpeed());
        speedFormLine.lineTextField.setText(characterSpeed);

        String characterInitiativeModifier = Integer.toString(DDventureLogic.getInstance().getTempCharacter().getInitiativeModifier());
        initiativeModifierFormLine.lineTextField.setText(characterInitiativeModifier);

        String characterAvatarName = DDventureLogic.getInstance().getTempCharacter().getAvatar();
        avatarBoxLine.lineChoiceBox.getSelectionModel().select(characterAvatarName);

        String characterWeaponName = DDventureLogic.getInstance().getTempCharacter().getWeapon().getName();
        weaponBoxLine.lineChoiceBox.getSelectionModel().select(characterWeaponName);

        String characterWeaponDamage = Integer.toString(DDventureLogic.getInstance().getTempCharacter().getWeapon().getDamageDice());
        weaponDamageFormLine.lineTextField.setText(characterWeaponDamage);

        String characterWeaponTCModifier = Integer.toString(DDventureLogic.getInstance().getTempCharacter().getWeapon().getTpcModifier());
        tcModifierFormLine.lineTextField.setText(characterWeaponTCModifier);
    }

    private void createCharacterInGame() {
        DDventureLogic.getInstance().createCharacterInGame(
                nameFormLine.lineTextField.getText(),
                Integer.parseInt(hPFormLine.lineTextField.getText()),
                Integer.parseInt(armorClassFormLine.lineTextField.getText()),
                Integer.parseInt(speedFormLine.lineTextField.getText()),
                Integer.parseInt(initiativeModifierFormLine.lineTextField.getText()),
                avatarBoxLine.lineChoiceBox.getSelectionModel().getSelectedItem(),
                teamBoxLine.lineChoiceBox.getSelectionModel().getSelectedItem(),
                weaponBoxLine.lineChoiceBox.getSelectionModel().getSelectedItem(),
                Integer.parseInt(weaponDamageFormLine.lineTextField.getText()),
                Integer.parseInt(tcModifierFormLine.lineTextField.getText())
        );
    }

    private void clearView() {
        nameFormLine.lineTextField.clear();
        hPFormLine.lineTextField.clear();
        armorClassFormLine.lineTextField.clear();
        speedFormLine.lineTextField.clear();
        initiativeModifierFormLine.lineTextField.clear();
        avatarBoxLine.lineChoiceBox.getSelectionModel().selectFirst();
        teamBoxLine.lineChoiceBox.getSelectionModel().selectFirst();
        weaponBoxLine.lineChoiceBox.getSelectionModel().selectFirst();
        weaponDamageFormLine.lineTextField.clear();
        tcModifierFormLine.lineTextField.clear();
    }
}