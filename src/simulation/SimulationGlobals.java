package simulation;

public class SimulationGlobals {
    private static int simDayLimit = 50;
    private static int maxDayToStay = 5;
    private static int minDayToStay = 1;

    private SimulationGlobals() {
    }

    public static int getMaxDayToStay() {
        return maxDayToStay;
    }

    public static int getMinDayToStay() {
        return minDayToStay;
    }

    public static int getSimDayLimit() {
        return simDayLimit;
    }
}
