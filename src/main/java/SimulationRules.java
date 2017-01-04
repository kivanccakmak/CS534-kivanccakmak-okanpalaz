import java.util.Random;

public class SimulationRules {
    private int maxDayToStay = 5;
    private int minDayToStay = 1;

    // Health releated params
    private int daysUntilSick = 6;
    private int daysUntilDeathChance = 8;
    private int daysUntilImmune = 10;
    private int daysUntilHealthy = 2;

    private double probToTransmitVirus = 0.4;
    private double probToDie = 0.25;
    private double probToAirtravel = 0.4;

    private int dailyVaccines = 5;

    private Random rng = new Random();

    private static SimulationRules instance  = new SimulationRules();

    private SimulationRules() {
    }

    public static SimulationRules getInstance() {
        return instance;
    }

    public void setAirTravelChance(double percentVal) {
        // TODO: Error if invalid
        probToAirtravel = percentVal / 100.0;
    }

    public void setDailyVaccines(int v) {
        if (v >= 0) {
            dailyVaccines = v;
        } else {
            // TODO: Error
        }
    }

    public int getMaxDayToStay() {
        return maxDayToStay;
    }

    public int getMinDayToStay() {
        return minDayToStay;
    }

    public int getDaysUntilSick() {
        return daysUntilSick;
    }

    public int getDaysUntilDeathChance() {
        return daysUntilDeathChance;
    }

    public int getDaysUntilImmune() {
        return daysUntilImmune;
    }

    public int getDaysUntilHealthy() {
        return daysUntilHealthy;
    }

    public boolean infectionDiceThrow() {
        double dice = rng.nextDouble();
        return dice <= probToTransmitVirus;
    }

    public boolean airtravelDiceThrow() {
        double dice = rng.nextDouble();
        return dice <= probToAirtravel;
    }

    public boolean dieDiceThrow() {
        double dice = rng.nextDouble();
        return dice <= probToDie;
    }

    public int getDailyVaccines() {
        return dailyVaccines;
    }

    public Random getRng() {
        return rng;
    }
}
