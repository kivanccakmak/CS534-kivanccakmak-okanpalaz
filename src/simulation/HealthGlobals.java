package simulation;

public class HealthGlobals {
    private int dayToSick = 2;
    private int dayToDie = 14;
    private int dayToImmune = 16;
    private int dayToBackHealthy = 18;

    private double probToTransmitVirus = 0.4;
    private double probToDie = 0.25;

    public int getDayToSick() {
        return this.dayToSick;
    }

    public void setDayToSick(int x) {
        this.dayToSick = x;
    }

    public int getDayToDie() {
        return this.dayToDie;
    }

    public void setDayToDie(int x) {
        this.dayToDie = x;
    }

    public int getDayToImmune() {
        return this.dayToImmune;
    }

    public void setDayToImmune(int x) {
        this.dayToImmune = x;
    }

    public double getProbToTransmitVirus() {
        return this.probToTransmitVirus;
    }

    public void setProbToTransmitVirus(double x) {
        this.probToTransmitVirus = x;
    }

    public double getProbToDie() {
        return this.probToDie;
    }

    public void setProbToDie() {
        this.probToDie = probToDie;
    }

    public int getDayToBackHealthy() {
        return this.dayToBackHealthy;
    }

    public void setDayToBackHealthy(int x) {
        this.dayToBackHealthy = x;
    }
}
