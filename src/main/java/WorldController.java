import java.util.*;
import java.io.StringWriter;
import java.io.PrintWriter;

public class WorldController {
    private int numHorizontal;
    private int numVertical;
    private int numPeople;
    private int numDays;
    private int numVaccine;
    private double percentSuper;
    private double percentDoctor;
    private double percentInfected;

    private WorldView view;
    private Simulator simulator;

    public WorldController() {
        this.view = new WorldPanelView(this);
        this.view.setSize(300, 300);
        this.view.setVisible(true);
    }

    // for debugging purposes, won't be trigged from UI
    public void initialize(int numVertical, int numHorizontal, int numPeople,
            double percentInfected, double percentSuper, double percentDoctor,
                int numVaccine) {
        this.numHorizontal = numHorizontal;
        this.numVertical = numVertical;
        this.numPeople = numPeople;
        this.percentInfected = percentInfected;
        this.percentSuper = percentSuper;
        this.percentDoctor = percentDoctor;
        this.numVaccine = numVaccine;
		SimulationRules rules = SimulationRules.getInstance();
        rules.setDailyVaccines(numVaccine);
        rules.setAirTravelChance(30.0);
    }

    public void restart(String numVertical, String numHorizontal,
            String numPeople, String percentInfected,
                String percentSuper, String percentDoctor, String numVaccine) {
        int ret = isValidInputs(numVertical, numHorizontal, numPeople,
                percentInfected, percentSuper, percentDoctor, numVaccine);
        System.out.println("ret: " + ret);
        if (ret != 1) { return; }

        this.numVertical = Integer.parseInt(numVertical);
        this.numHorizontal = Integer.parseInt(numHorizontal);
        this.numPeople = Integer.parseInt(numPeople);
        this.percentInfected = Double.parseDouble(percentInfected);
        this.percentSuper = Double.parseDouble(percentSuper);
        this.percentDoctor = Double.parseDouble(percentDoctor);
        this.numVaccine = Integer.parseInt(numVaccine);

        startSimulation();
    }

    private int isValidInputs(String numVertical, String numHorizontal,
            String numPeople, String percentInfected, String percentSuper,
                String percentDoctor, String numVaccine) {
        int iTemp;
        double dTemp;

        try {
            iTemp = Integer.parseInt(numVertical);
            if (iTemp <= 0) { return 0; }
        } catch (NumberFormatException e) {
            raiseException(e);
            return 0;
        }

        try {
            iTemp = Integer.parseInt(numHorizontal);
            if (iTemp <= 0) { return 0; }
        } catch (NumberFormatException e) {
            raiseException(e);
            return 0;
        }

        try {
            iTemp = Integer.parseInt(numPeople);
            if (iTemp <= 0) { return 0; }
        } catch (NumberFormatException e) {
            raiseException(e);
            return 0;
        }

        try {
            iTemp = Integer.parseInt(numVaccine);
            if (iTemp < 0) { return 0; }
        } catch (NumberFormatException e) {
            raiseException(e);
            return 0;
        }

        try {
            dTemp = Double.parseDouble(percentInfected);
            if (dTemp < 0 || dTemp > 100) { return 0; }
        } catch (NumberFormatException e) {
            raiseException(e);
            return 0;
        }

        try {
            dTemp = Double.parseDouble(percentDoctor);
            if (dTemp < 0 || dTemp > 100) { return 0; }
        } catch (NumberFormatException e) {
            raiseException(e);
            return 0;
        }

        try {
            dTemp = Double.parseDouble(percentSuper);
            if (dTemp < 0 || dTemp > 100) { return 0; }
        } catch (NumberFormatException e) {
            raiseException(e);
            return 0;
        }

        return 1;
    }

    private void raiseException(Exception e) {
        String errMsg = null;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        errMsg = sw.toString();
        //TODO: send event to quee, trigs pop-up
        System.out.println(errMsg);
    }

    public void startSimulation() {
        this.simulator = new Simulator(this.numVertical, this.numHorizontal);
        this.simulator.populate(numPeople, percentInfected, percentSuper, percentDoctor);
        this.view.initOutput(numVertical, numHorizontal, numPeople,
                percentInfected, percentSuper, percentDoctor,
                    numVaccine);
        String[][] stats = getCountryStats();
        this.view.updateOutput(stats);
    }

    public void passDay() {
        this.simulator.passDay();
        String[][] stats = getCountryStats();
        this.view.updateOutput(stats);
    }

    private String[][] getCountryStats() {
        String[][] stats = new String[numVertical][numHorizontal];
        int idx = 0;
        for (int i = 0; i < numVertical; i++) {
            for (int j = 0; j < numHorizontal; j++) {
                idx = i * numVertical + j;
                stats[i][j] = this.simulator.getCountryInfo(idx);
            }
        }
        return stats;
    }
}
