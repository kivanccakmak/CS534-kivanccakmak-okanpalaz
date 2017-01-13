import java.util.*;

public class WorldController {
    private WorldView view;
    private Simulator simulator;

    public WorldController() {
        this.view = new WorldView(this);
        this.view.setSize(300, 300);
        this.view.setVisible(true);
    }

    public void restart(String sNumVertical, String sNumHorizontal,
            String sNumPeople, String sPercentInfected,
            String sPercentSuper, String sPercentDoctor, String sNumVaccine,
            String sAirChance) throws NumberFormatException, IllegalArgumentException {

        SimulationRules rules = SimulationRules.getInstance();
        int numVertical = Integer.parseInt(sNumVertical);
        int numHorizontal = Integer.parseInt(sNumHorizontal);
        int numPeople = Integer.parseInt(sNumPeople);
        double percentInfected = Double.parseDouble(sPercentInfected);
        double percentSuper = Double.parseDouble(sPercentSuper);
        double percentDoctor = Double.parseDouble(sPercentDoctor);
        int numVaccine = Integer.parseInt(sNumVaccine);
        double airChance = Double.parseDouble(sAirChance);

        rules.setDailyVaccines(numVaccine);
        rules.setAirTravelChance(airChance);

        simulator = new Simulator(numVertical, numHorizontal);
        simulator.populate(numPeople, percentInfected, percentSuper, percentDoctor);
        view.initOutput(numVertical, numHorizontal);

        view.updateOutput(simulator.getCountryStats());
    }

    public void passDay() {
        simulator.passDay();
        view.updateOutput(simulator.getCountryStats());
    }
}
