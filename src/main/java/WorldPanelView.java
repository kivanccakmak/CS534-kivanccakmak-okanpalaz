import java.util.*;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import java.awt.Component;
import java.awt.Container;

class InfoPanel extends JPanel{
    private JLabel label;

    InfoPanel() {
        label = new JLabel();
        this.add(label);
        this.setBorder(new EtchedBorder());
    }

    public void setLabelMsg(String msg) {
        this.label.setText(msg);
    }
}

public class WorldPanelView extends WorldView {
    public WorldPanelView(int numVertical, int numHorizontal,
            final WorldController cntrl) {
        super(numVertical, numHorizontal, cntrl);
    }

    protected JComponent getNewCell(int row, int col) {
        InfoPanel panel = new InfoPanel();
        return panel;
    }

    public void showCell(int row, int col, String stats) {
        //TODO: have String[] or dict
        String out = "";
        out = stats.replace("\n", "<br>");
        out = "<html>" + out + "</html>";
        System.out.println(out);
        InfoPanel panel = (InfoPanel) this.components[row][col];
        panel.setLabelMsg(out);
    }
}
