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
    }

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
            String numPeople, String numDays, String percentInfected,
            String percentSuper, String percentDoctor, String numVaccine) {

        int ret = isValidInputs(numVertical, numHorizontal, numPeople,
                percentInfected, percentSuper, percentDoctor, numVaccine);
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
        System.out.println(errMsg); //TODO: send event to quee, trigs pop-up
    }

    //TODO: no cell click would be used
    //just, automatically update  cells at each passDay.
    public void cellClicked(int row, int col) {
        for (int i = 0; i < this.numHorizontal; i++) {
            for (int j = 0; j < this.numVertical; j++) {
                int idx = numHorizontal * i + j;
                String info = this.simulator.getCountryInfo(idx);
                this.view.showCell(i, j, info);
            }
        }
        passDay();
    }

    public void startSimulation() {
        this.view = new WorldPanelView(numVertical, numHorizontal, this);
        this.simulator = new Simulator(numVertical, numHorizontal);
        this.simulator.populate(numPeople, percentInfected,
                percentSuper, percentDoctor);
        this.view.setSize(300, 300);
        this.view.setVisible(true);
    }

    //TODO: set button in view to trig this function
    public void passDay() {
        this.simulator.passDay();
    }
}
