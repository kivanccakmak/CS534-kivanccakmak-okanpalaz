public class SimulationGlobals {
    private static int simDayLimit = 50;
    private static int maxDayToStay = 5;
    private static int minDayToStay = 1;
    private static int numHorizontalCountries = 3;
    private static int numVerticalCountries = 3;

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

    public static int getNumHorizontalCountries() {
        return numHorizontalCountries;
    }

    public static int getNumVerticalCountries() {
        return numVerticalCountries;
    }
}
