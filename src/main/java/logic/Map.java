package logic;

public class Map {
    final int columns;
    final int rows;
    final Cell[][] cells;

    private int iSource;
    private int jSource;
    private boolean selectionMode;

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

    public void setSource(int i, int j) {
        if(cells[i][j].isOccupied()) {
            iSource = i;
            jSource = j;
            selectionMode = true;
        }
    }
}
