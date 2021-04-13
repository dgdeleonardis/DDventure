package logic;

import java.util.HashMap;

public class Map {
    final int columns;
    final int rows;
    final Cell[][] cells;

    private int iSource;
    private int jSource;
    private boolean selectionMode;

    final static HashMap<Integer, Integer> TERRAIN_MAP = new HashMap<Integer, Integer>() {{
        put(1, 1);
        put(2, 2);
        put(3, Integer.MAX_VALUE);
    }};

    public Map(Cell[][] cells) {
        this.cells = cells;
        columns = cells.length;
        rows = cells[0].length;

        iSource = -1;
        jSource = -1;
        selectionMode = false;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean isSelectionMode() {
        return selectionMode;
    }

    public Cell getCell(int column, int row) {
        return cells[column][row];
    }

    public boolean setSource(int i, int j) {
        if(cells[i][j].isOccupied() && !selectionMode) {
            iSource = i;
            jSource = j;
            selectionMode = true;
        }
        return selectionMode;
    }

    public boolean moveCharacter(int iTarget, int jTarget) {
        // se la cella non è occupata && non è acqua allora puoi muovere il personaggio
        if(!cells[iTarget][jTarget].isOccupied() && cells[iTarget][jTarget].getCrossingCost() != Integer.MAX_VALUE) {
            // trova il personaggio e modifica le sue coordinate e modifica la variabile occupied delle celle
            // di cells e la selectionmode
            CharacterInGame characterInGame = null;
            for(int i = 0; i < DDventureLogic.getInstance().getGame().getCharactersInGame().size(); i++) {
                CharacterInGame character = DDventureLogic.getInstance().getGame().getCharactersInGame().get(i);
                if(character.getCoordinataX() == iSource && character.getCoordinataY() == jSource)
                    characterInGame = character;
            }
            characterInGame.setCoordinataX(iTarget);
            characterInGame.setCoordinataY(jTarget);
            cells[iSource][jSource].setOccupied(false);
            cells[iTarget][jTarget].setOccupied(true);
            selectionMode = false;
        }
        return !selectionMode;
    }
}
