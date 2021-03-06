package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class GuideScene extends BorderPane {
    private Label title;
    private Text guide;
    private Button gobackbotton;
    private final Stage currentStage;

    public GuideScene(Stage currentStage) {
        super();
        this.currentStage = currentStage;
        setPrefSize(DDventureView.PRIMARY_STAGE_WIDTH, DDventureView.PRIMARY_STAGE_HEIGHT);

        //top section
        Font titleFont = Font.font("Alagard", 34);
        Font textFont = Font.font("Alagard", 24);
        this.title = new Label("Breve guida su come usare DDventure");
        this.title.setFont(titleFont);
        BorderPane.setMargin(this.title, new Insets(30,0,30,0));
        this.title.setTextFill(Color.WHITE);
        this.setTop(this.title);
        BorderPane.setAlignment(this.title, Pos.CENTER);

        //center section
        this.guide = new Text();
        this.guide.setText("Per iniziare una partita inserire il numero di team che andranno ad affornatrsi in battaglia, costruite \ni personaggi e inserite la grandezza della mappa di gioco. \n" +
                "Dopo che l'applicazione ha creato la mappa, i giocatori possono posizionare al suo interno gli avatar dei \npersonaggi, " +
                "quindi l'applicazione lanciera' per tutti il dado iniziativa, a cui va a sommare il modificatore \ndestrezza, mostrando a schermo tutti i risultati\n" +
                "Una partita e' strutturata a turni ciclici, chi ha ottenuto iniziativa piu' alta iniziera' la partita, \nseguito da tutti i giocatori. " +
                "Finito il turno di colui che aveva ottonuto il risultato piu' basso, si rincomncia \ndal giocatore ancora in vita con iniziativa piu' alta \n" +
                "Durante il proprio turno, un giocatore puo' compiere due azioni distinte: \n" +
                "       muoversi per un numero di caselle pari alla sua velocita' \n" +
                "       attaccare un nemico entro un range dato dalla sua velicita' + la gittata dell'arma\n" +
                "Un personaggio muore quando i suoi Punti Ferita scendono a 0\n" +
                "La partita finisce quando sono rimasti in vita i giocatori di un solo team");
        this.guide.setFont(textFont);
        this.guide.setLineSpacing(3);
        this.guide.setFill(Color.WHITE);
        BorderPane.setAlignment(this.guide, Pos.CENTER);
        this.setCenter(this.guide);

        //bottom section
        this.gobackbotton = new Button("Indietro");
        this.gobackbotton.setFont(textFont);
        BorderPane.setAlignment(this.gobackbotton, Pos.CENTER);
        BorderPane.setMargin(this.gobackbotton, new Insets(30,0,30,0));

        this.setBottom(this.gobackbotton);

        this.gobackbotton.setOnAction( event ->  {
                DDventureView.getInstance().createAnOpenOptionScene(this.currentStage);
        });

    }

}

