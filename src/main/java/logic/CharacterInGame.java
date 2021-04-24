package logic;

public class CharacterInGame extends Character {
    private Team team;
    private int coordinataX;
    private int coordinataY;
    private int turnOrder;
    private int initiative;
    private int remainingHP;
    private int initiativeModifier;
    private int remainingSpeed;
    private CharacterSprite sprite;
    private boolean hasAttacked;

    public CharacterInGame(String name, int pf, int ca, int speed, int initiative, String avatar, int coordinataX, int coordinataY, Weapon weapon, Team team) {
        super(name, pf, ca, speed, initiative, avatar, weapon);
        this.team = team;
        this.coordinataX = coordinataX;
        this.coordinataY = coordinataY;
        this.turnOrder = 0;
        remainingHP = pf;
        remainingSpeed = speed;
        this.hasAttacked = false;
    }

    public CharacterInGame(String name, int pf, int ca, int speed, int initiative, String avatar, Team team, Weapon weapon) {
        super(name, pf, ca, speed, initiative, avatar, weapon);
        this.team = team;
        coordinataX = 0;
        coordinataY = 0;
        remainingHP = pf;
        turnOrder = -1;
        remainingSpeed = speed;
        hasAttacked = false;
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

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public int getInitiative() {
        return initiative;
    }

    @Override
    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void setRemainingSpeed(int remainingSpeed) {
        this.remainingSpeed = remainingSpeed;
    }

    public int getRemainingHP() {
        return remainingHP;
    }

    public boolean isDead() {
        return !(remainingHP > 0);
    }

    public void decreaseRemainingHP(int damage) {
        this.remainingHP -= damage;
    }
}