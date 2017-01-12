import java.util.*;
import javax.swing.JOptionPane;

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
            String sPercentSuper, String sPercentDoctor, String sNumVaccine) {
        try {
            int numVertical = Integer.parseInt(sNumVertical);
            int numHorizontal = Integer.parseInt(sNumHorizontal);
            int numPeople = Integer.parseInt(sNumPeople);
            double percentInfected = Double.parseDouble(sPercentInfected);
            double percentSuper = Double.parseDouble(sPercentSuper);
            double percentDoctor = Double.parseDouble(sPercentDoctor);
            int numVaccine = Integer.parseInt(sNumVaccine);

            simulator = new Simulator(numVertical, numHorizontal);
            simulator.populate(numPeople, percentInfected, percentSuper, percentDoctor);
            view.initOutput(numVertical, numHorizontal);

            view.updateOutput(simulator.getCountryStats());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input");
        }
    }

    public void passDay() {
        simulator.passDay();
        view.updateOutput(simulator.getCountryStats());
    }
}
