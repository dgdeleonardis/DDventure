package view;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import logic.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MapView extends Canvas {

    private static final String TEXTURE_MAP_FILE_NAME = "image/texture-map.png";

    public ArrayList<CharacterInGame> characterInGame;

    private static final HashMap<Integer, Rectangle2D> LAND_TEXTURE_MASK_MAP = new HashMap<Integer, Rectangle2D>() {{
        put(1, new Rectangle2D(16, 0, 16, 16));
        put(2, new Rectangle2D(208, 0, 16, 16));
        put(Integer.MAX_VALUE, new Rectangle2D(352, 0, 16, 16));
    }};

    public MapView(double width, double height) {
        super(width, height);
    }

    public void drawMap(Map map) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.clearRect(0, 0,getWidth(), getHeight());
        int columns = map.getColumns();
        int rows = map.getRows();
        gc.setLineWidth(3);
        Image textureMapImage = new Image(getClass().getResourceAsStream(TEXTURE_MAP_FILE_NAME));
        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {
                gc.strokeRect(getWidth()/columns*i, getHeight()/rows*j, getWidth()/columns, getHeight()/rows);
                gc.drawImage(textureMapImage,
                        LAND_TEXTURE_MASK_MAP.get(map.getCells()[i][j].getCrossingCost()).getMinX(),
                        LAND_TEXTURE_MASK_MAP.get(map.getCells()[i][j].getCrossingCost()).getMinY(),
                        LAND_TEXTURE_MASK_MAP.get(map.getCells()[i][j].getCrossingCost()).getWidth(),
                        LAND_TEXTURE_MASK_MAP.get(map.getCells()[i][j].getCrossingCost()).getHeight(),
                        getWidth()/columns*i, getHeight()/rows*j, getWidth()/columns, getHeight()/rows);
            }
        }
    }



    public void drawCharactersInGame() {
        GraphicsContext gc = getGraphicsContext2D();
        int columns = DDventureLogic.getInstance().getGameMap().getColumns();
        int rows = DDventureLogic.getInstance().getGameMap().getRows();
        Image annaSprite = makeTransparent(new Image(getClass().getResourceAsStream("image/anna_sheet.png")));
        // roba in più da eliminare più in là
        ArrayList<CharacterInGame> characterInGame = DDventureLogic.getInstance().getGame().getCharactersInGame();
        characterInGame.forEach(p -> {
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

    public void drawBorderCell(int i, int j, Map modelMap) {
        GraphicsContext gc = getGraphicsContext2D();
        int mapColumns = modelMap.getColumns();
        int mapRows = modelMap.getRows();
        gc.setStroke(Color.ORANGE);
        gc.strokeRect(i*(getWidth()/mapColumns),
                j*(getHeight()/mapRows),
                getWidth()/mapColumns,
                getHeight()/mapRows);
    }

    public void highlightCells(Pair<Integer, Integer>[] cellsToHighlight, Color fillColor) {
        Map map = DDventureLogic.getInstance().getGameMap();
        GraphicsContext gc = getGraphicsContext2D();
        int columns = map.getColumns();
        int rows = map.getRows();

        gc.setFill(Color.rgb((int) (fillColor.getRed()* 255), (int) (fillColor.getGreen()* 255), (int) (fillColor.getBlue() * 255), 0.4));
        for (Pair<Integer, Integer> cell: cellsToHighlight) {
            gc.fillRect(getWidth()/columns*cell.getKey().intValue(), getHeight()/rows*cell.getValue().intValue(), getWidth()/columns, getHeight()/rows);
        }
        drawCharactersInGame();
    }

    public void highlightCells(Pair<Integer, Integer> cell, Color fillColor) {
        Map map = DDventureLogic.getInstance().getGameMap();
        GraphicsContext gc = getGraphicsContext2D();
        int columns = map.getColumns();
        int rows = map.getRows();

        gc.setFill(Color.rgb((int) (fillColor.getRed()* 255), (int) (fillColor.getGreen()* 255), (int) (fillColor.getBlue() * 255), 0.4));
        gc.fillRect(getWidth()/columns*cell.getKey().intValue(), getHeight()/rows*cell.getValue().intValue(), getWidth()/columns, getHeight()/rows);

        drawCharactersInGame();
    }
}
