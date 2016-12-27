import java.util.Random;

public class HealthGlobals {
    private static int daysUntilSick = 6;
    private static int daysUntilDeathChance = 8;
    private static int daysUntilImmune = 10;
    private static int daysUntilHealthy = 2;
    private static Random rng = new Random();

    private static double probToTransmitVirus = 0.4;
    private static double probToDie = 0.25;

    private HealthGlobals() {
    }

    public static int getDaysUntilSick() {
        return daysUntilSick;
    }

    public static int getDaysUntilDeathChance() {
        return daysUntilDeathChance;
    }

    public static int getDaysUntilImmune() {
        return daysUntilImmune;
    }

    public static int getDaysUntilHealthy() {
        return daysUntilHealthy;
    }

    public static boolean infectionDiceThrow() {
        double dice = rng.nextDouble();
        return dice <= probToTransmitVirus;
    }

    public static boolean dieDiceThrow() {
        double dice = rng.nextDouble();
        return dice <= probToDie;
    }

    public static Random getRng() {
        return rng;
    }
}
