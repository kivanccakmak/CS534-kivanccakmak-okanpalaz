package simulation;

public class HealthGlobals {
    private int dayToSick = 2;
    private int dayToDie = 14;
    private int dayToImmune = 16;
    private int dayToBackHealthy = 18;

    private double probToTransmitVirus = 0.4;
    private double probToDie = 0.9;

    public int getDayToSick() {
        return dayToSick;
    }

    public int getDayToDie() {
        return dayToDie;
    }

    public int getDayToImmune() {
        return dayToImmune;
    }

    public double getProbToTransmitVirus() {
        return probToTransmitVirus;
    }

    public double getProbToDie() {
        return probToDie;
    }

    public int getDayToBackHealthy() {
        return dayToBackHealthy;
    }
}
