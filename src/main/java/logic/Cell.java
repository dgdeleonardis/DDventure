package logic;

public class Cell {
    private boolean occupied;
    private int crossingCost;

    public Cell(int crossingCost) {
        this.crossingCost = crossingCost;
        occupied = false;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getCrossingCost() {
        return crossingCost;
    }
}
