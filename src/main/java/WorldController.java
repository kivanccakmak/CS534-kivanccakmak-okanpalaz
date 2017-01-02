import java.util.*;

public class WorldController {
    private WorldView view;
    private Simulator simulator;

    public WorldController(int numVertical, int numHorizontal,
            int count, double percentInfected ) {
        this.view = new WorldPanelView(numVertical, numHorizontal, this);
        this.simulator = new Simulator(numVertical, numHorizontal);
        this.simulator.populate(count, percentInfected);
    }

    //TODO: no cell click would be used
    //just, automatically update  cells at each passDay.
    public void cellClicked(int row, int col) {
        //TODO: query simulator to get country -or world- info
        //ps: this is just for dummy integration
        String str = "";
        str += "info " + this.simulator.getDayPassed();
        this.view.showCell(row, col, str);
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
