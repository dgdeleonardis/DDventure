package view;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import logic.Cella;
import logic.PersonaggioInPartita;

import java.util.ArrayList;
import java.util.HashMap;

public class MapView extends Canvas {

    private static final String TEXTURE_MAP_FILE_NAME = "image/texture-map.png";

    private Cella[][] map;
    public int columns;
    public int rows;
    public ArrayList<PersonaggioInPartita> personaggioInPartita;

    private static final HashMap<Integer, Rectangle2D> LAND_TEXTURE_MASK_MAP = new HashMap<Integer, Rectangle2D>() {{
        put(1, new Rectangle2D(16, 0, 16, 16));
        put(2, new Rectangle2D(208, 0, 16, 16));
        put(3, new Rectangle2D(352, 0, 16, 16));
    }};

    public MapView(double width, double height) {
        super(width, height);
        double moltiplicatoreGrandezza = MapScene.ITEM_DIMENSION_OF_THE_MAP_BOX.get("piccola");
        // roba da eliminare in seguito
        // genero una mappa casuale
        int columns = (int) Math.round(16*moltiplicatoreGrandezza);
        int rows = (int) Math.round(9*moltiplicatoreGrandezza);
        Cella[][] modelMap = new Cella[columns][rows];
        for(int i = 0; i < 16*moltiplicatoreGrandezza; i++) {
            for (int j = 0; j < 9*moltiplicatoreGrandezza; j++) {
                modelMap[i][j] = new Cella((int) Math.ceil(Math.random() * 3.0));
            }
        }
        map = modelMap;
        draw();
    }

    public MapView(double width, double height, Cella[][] map) {
        super(width, height);
        this.map = map;
        draw();



    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0,getWidth(), getHeight());
        columns = map.length;
        rows = map[0].length;
        gc.setLineWidth(3);
        Image textureMapImage = new Image(getClass().getResourceAsStream(TEXTURE_MAP_FILE_NAME));
        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {
                gc.strokeRect(getWidth()/columns*i, getHeight()/rows*j, getWidth()/columns, getHeight()/rows);
                gc.drawImage(textureMapImage,
                        LAND_TEXTURE_MASK_MAP.get(map[i][j].getCostoAttraversamento()).getMinX(),
                        LAND_TEXTURE_MASK_MAP.get(map[i][j].getCostoAttraversamento()).getMinY(),
                        LAND_TEXTURE_MASK_MAP.get(map[i][j].getCostoAttraversamento()).getWidth(),
                        LAND_TEXTURE_MASK_MAP.get(map[i][j].getCostoAttraversamento()).getHeight(),
                        getWidth()/columns*i, getHeight()/rows*j, getWidth()/columns, getHeight()/rows);
            }
        }
        drawPersonaggi(gc);
    }

    public void setMap(Cella[][] map) {
        this.map = map;
    }

    public void drawPersonaggi(GraphicsContext gc) {
        Image annaSprite = makeTransparent(new Image(getClass().getResourceAsStream("image/anna_sheet.png")));
        // roba in più da eliminare più in là
        personaggioInPartita = new ArrayList<PersonaggioInPartita>(){{
           add(new PersonaggioInPartita(4, 6));
            add(new PersonaggioInPartita(4, 7));
            add(new PersonaggioInPartita(4, 4));
            add(new PersonaggioInPartita(7, 10));
            add(new PersonaggioInPartita(9, 11));
        }};
        personaggioInPartita.forEach( p -> {
            gc.drawImage(annaSprite, getWidth()/columns*p.getCoordinataX(), getHeight()/rows* p.getCoordinataY(),
                    getWidth()/columns, getHeight()/rows);
        });

    }

    public Image makeTransparent(Image inputImage) {

        int W = (int) 32;
        int H = (int) 32;
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                int argb = reader.getArgb(x, y);

                String color = Integer.toHexString(argb);
                if(color.equals("ffff00ff"))
                    writer.setArgb(x, y, 0);
                else writer.setArgb(x, y, argb);
            }
        }

        return outputImage;
    }

}
