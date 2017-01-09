import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JComponent;
import javax.swing.border.EtchedBorder;

// to act query from controller
interface CntrlHandler {
    public abstract void initOutput(int numVertical, int numHorizontal,
            int numPeople, int numDays, double percentInfected,
                double percentSuper, double percentDoctor, int numVaccine);

    public abstract void updateOutput(String[][] stats);
}

public abstract class WorldView extends JFrame  implements CntrlHandler {
    protected JComponent inputPanel;
    protected JComponent outputPanel;
    protected WorldController cntrl;
    protected int numVertical = 0;
    protected int numHorizontal = 0;
    JSplitPane container;

    public WorldView(WorldController cntrl) {
        this.cntrl = cntrl;
        setTitle("Epidemic Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputPanel = (JComponent) getInputPanel();
        container = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputPanel, null);
    }

    public abstract JComponent getInputPanel();

    public abstract JComponent getOutputPanel();

    public abstract void initOutputPanel(int numVertical, int numHorizontal,
            int numPeople, int numDays, double percentInfected,
                double percentSuper, double percentDoctor, int numVaccine);

    public abstract void updateOutputPanel(String[][] stats);

    public void initOutput(int numVertical, int numHorizontal,
            int numPeople, int numDays, double percentInfected,
                double percentSuper, double percentDoctor, int numVaccine) {
        this.remove(container);
        this.numVertical = numVertical;
        this.numHorizontal = numHorizontal;
        outputPanel = (JComponent) getOutputPanel();
        container = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputPanel, outputPanel);
        container.setOneTouchExpandable(true);
        container.setDividerLocation(0.4);
        this.add(container);
        initOutputPanel(numVertical, numHorizontal, numPeople, numDays,
                percentInfected, percentSuper, percentDoctor, numVaccine);
        this.setVisible(true);
    }

    public void updateOutput(String[][] stats) {
        updateOutputPanel(stats);
    }
}
