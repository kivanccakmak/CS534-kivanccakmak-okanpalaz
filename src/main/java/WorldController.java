import java.util.*;

public class WorldController {
    private int numHorizontal;
    private int numVertical;
    private WorldView view;
    private Simulator simulator;

    public WorldController(int numVertical, int numHorizontal,
            int count, double percentInfected, double percentSuper, double percentDoctor, int vaccineCnt) {
        this.numHorizontal = numHorizontal;
        this.numVertical = numVertical;
        this.view = new WorldPanelView(numVertical, numHorizontal, this);
        this.simulator = new Simulator(numVertical, numHorizontal);
        this.simulator.populate(count, percentInfected, percentSuper, percentDoctor, vaccineCnt);
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
        this.view.setSize(300, 300);
        this.view.setVisible(true);
    }

    //TODO: set button in view to trig this function
    public void passDay() {
        this.simulator.passDay();
    }
}
