package simulation;

public class SimulationGlobals {

    private int simDayLimit;
    private int maxDayToStay;
    private int minDayToStay; 

    public SimulationGlobals() {
        this.simDayLimit = 100;
        this.maxDayToStay = 5;
        this.minDayToStay = 1;
    }

    public int getMaxDayToStay() {
        return this.maxDayToStay;
    }

    public void setMaxDayToStay(int x) {
        this.maxDayToStay = x;
    }

    public void setMinDayToStay(int x) {
        this.minDayToStay = x;
    }

    public int getMinDayToStay() {
        return this.minDayToStay;
    }
}
