import java.util.*;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JComponent;
import javax.swing.border.EtchedBorder;

// to query controller
interface CntrlRequester {
    public abstract void startSimulation(String numVertical,
            String numHorizontal, String numPeople, String numDays,
                String percentInfected, String percentSuper,
                    String percentDoctor, String numVaccine);

    public abstract void passDay();
}

// to act query from controller
interface CntrlHandler {
    public abstract void initOutput(int numVertical, int numHorizontal,
            int numPeople, int numDays, double percentInfected,
                double percentSuper, double percentDoctor, int numVaccine);

    public abstract void updateOutput(String[][] stats);
}

public abstract class WorldView extends JFrame  implements CntrlRequester, CntrlHandler {
    protected JComponent inputPanel;
    protected JComponent outputPanel;
    protected WorldController cntrl;

    public WorldView(WorldController cntrl) {
        this.cntrl = cntrl;
        setTitle("Epidemic Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputPanel = (JComponent) getInputPanel();
        outputPanel = (JComponent) getOutputPanel();

        JSplitPane container = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                inputPanel, outputPanel);

        container.setOneTouchExpandable(true);
        container.setDividerLocation(0.4);

        this.add(container);
        this.setVisible(true);
    }

    public abstract JComponent getInputPanel();

    public abstract JComponent getOutputPanel();

    public void startSimulation(String numVertical,
            String numHorizontal, String numPeople, String numDays,
                String percentInfected, String percentSuper,
                    String percentDoctor, String numVaccine) {
        this.cntrl.restart(numVertical, numHorizontal, numPeople,
                numDays, percentInfected, percentSuper, percentDoctor,
                    numVaccine);
    }

    public void passDay() {
        this.cntrl.passDay();
    }
}
