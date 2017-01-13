import java.util.*;

public class WorldController {
    private WorldView view;
    private Simulator simulator;

    public WorldController() {
        view = new WorldView(this);
        view.setSize(300, 300);
        view.setVisible(true);
        simulator = null;
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

        view.updateOutput(simulator.getCountryStats(), simulator.getDaysPassed());
    }

    public void passDay() throws IllegalStateException {
        if (simulator == null) {
            throw new IllegalStateException("Simulator not initialized");
        }
        simulator.passDay();
        view.updateOutput(simulator.getCountryStats(), simulator.getDaysPassed());
    }
}
