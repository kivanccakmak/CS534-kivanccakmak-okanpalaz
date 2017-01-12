import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JComponent;
import javax.swing.border.EtchedBorder;

import javax.swing.JLabel;
import java.awt.BorderLayout;

public abstract class WorldView extends JFrame {
    protected JComponent inputPanel;
    protected JPanel outputPanel;
    protected WorldController cntrl;
    JSplitPane container;

    public WorldView(WorldController cntrl) {
        this.cntrl = cntrl;
        setTitle("Epidemic Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputPanel = getInputPanel();
        outputPanel = new JPanel();

        container = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputPanel, outputPanel);
        container.setOneTouchExpandable(true);
        container.setDividerLocation(0.4);

        outputPanel.setLayout(new BorderLayout());

        this.add(container);
        this.setVisible(true);
    }

    public abstract JComponent getInputPanel();

    public abstract JComponent getOutputPanel();

    public abstract void initOutputPanel(int numVertical, int numHorizontal);
    public abstract void updateOutput(List<Country.HealthStats> stats);

    public void initOutput(int numVertical, int numHorizontal) {
        outputPanel.removeAll();
        outputPanel.add(getOutputPanel());
        initOutputPanel(numVertical, numHorizontal);
        this.setVisible(true);
    }
}
