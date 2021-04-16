package logic;

public class Cell {
    //private boolean occupied;
    private CharacterInGame characterOnCell;
    private int crossingCost;

    public Cell(int crossingCost) {
        this.crossingCost = crossingCost;
        characterOnCell = null;
    }

    public boolean isOccupied() {
        if(characterOnCell == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setCharacterOnCell(CharacterInGame characterOnCell) {
        this.characterOnCell = characterOnCell;
    }

    public CharacterInGame getCharacterOnCell() {
        return characterOnCell;
    }

    public int getCrossingCost() {
        return crossingCost;
    }
}
