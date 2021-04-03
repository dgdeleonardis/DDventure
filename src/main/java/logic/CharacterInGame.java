package logic;

public class CharacterInGame extends Character {
    private int coordinataX;
    private int coordinataY;
    private int turnOrder;
    private boolean hasAttacked;

    public CharacterInGame(String name, int pf, int ca, int speed, int initiative, String avatar, int coordinataX, int coordinataY) {
        super(name, pf, ca, speed, initiative, avatar);
        this.coordinataX = coordinataX;
        this.coordinataY = coordinataY;
        this.turnOrder = 0;
        this.hasAttacked = false;
    }

    public int getCoordinataX() {
        return coordinataX;
    }

    public void setCoordinataX(int coordinataX) {
        this.coordinataX = coordinataX;
    }

    public int getCoordinataY() {
        return coordinataY;
    }

    public void setCoordinataY(int coordinataY) {
        this.coordinataY = coordinataY;
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
    }

    public int getTurnOrder() {
        return this.turnOrder;
    }

    public boolean isHasAttacked() {
        return this.hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}