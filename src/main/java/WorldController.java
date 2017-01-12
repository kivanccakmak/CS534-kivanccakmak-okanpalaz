import java.util.*;
import javax.swing.JOptionPane;

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
        try {
            this.numVertical = Integer.parseInt(numVertical);
            this.numHorizontal = Integer.parseInt(numHorizontal);
            this.numPeople = Integer.parseInt(numPeople);
            this.percentInfected = Double.parseDouble(percentInfected);
            this.percentSuper = Double.parseDouble(percentSuper);
            this.percentDoctor = Double.parseDouble(percentDoctor);
            this.numVaccine = Integer.parseInt(numVaccine);

            startSimulation();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input");
        }
    }

    public void startSimulation() {
        this.simulator = new Simulator(this.numVertical, this.numHorizontal);
        this.simulator.populate(numPeople, percentInfected, percentSuper, percentDoctor);
        this.view.initOutput(numVertical, numHorizontal, numPeople,
                percentInfected, percentSuper, percentDoctor,
                numVaccine);
        this.view.updateOutput(getCountryStats());
    }

    public void passDay() {
        this.simulator.passDay();
        this.view.updateOutput(getCountryStats());
    }

    private List<Country.HealthStats> getCountryStats() {
        return simulator.getCountryStats();
    }
}
