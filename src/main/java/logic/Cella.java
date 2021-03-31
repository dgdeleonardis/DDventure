package logic;

public class Cella {
    private boolean occupata;
    private int costoAttraversamento;

    public Cella(int costoAttraversamento) {
        this.costoAttraversamento = costoAttraversamento;
        occupata = false;
    }

    public boolean isOccupata() {
        return occupata;
    }

    public void setOccupata(boolean occupata) {
        this.occupata = occupata;
    }

    public int getCostoAttraversamento() {
        return costoAttraversamento;
    }
}
