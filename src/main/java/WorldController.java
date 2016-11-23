
public class WorldController {
    private WorldView view;

    public WorldController() {
        int numHorizontal = SimulationGlobals.getNumVerticalCountries();
        int numVertical = SimulationGlobals.getNumVerticalCountries();
        this.view = new WorldPanelView(numHorizontal, numVertical);
    }

    public void startSimulation() {
        view.setSize(500, 500);
        view.setVisible(true);
    }
}
