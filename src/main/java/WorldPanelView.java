import java.util.*;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import java.awt.Component;
import java.awt.Container;

public class WorldPanelView extends WorldView {
    public WorldPanelView(int numVertical, int numHorizontal,
            final WorldController cntrl) {
        super(numVertical, numHorizontal, cntrl);
    }

    protected JComponent getNewCell() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        panel.add(label);
        panel.setBorder(new EtchedBorder());
        return panel;
    }

    public void showCell(int row, int col, String stats) {
        System.out.println(row + " | " + col);
        System.out.println(stats);
        Container c = (Container) this.components[row][col];
        Component[] components = c.getComponents();
        JLabel label = (JLabel) components[0];
        label.setText(stats);
    }
}
