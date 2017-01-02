import java.util.*;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;

public abstract class WorldView extends JFrame {
    protected JComponent[][] components;
    //TODO: we need dayPass() button.

    public WorldView(int numVertical, int numHorizontal, final WorldController cntrl) {
        components = new JComponent[numVertical][numHorizontal];
        setLayout(new GridLayout(numVertical, numHorizontal));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 0; i < numVertical; i++) {
            for (int j = 0; j < numHorizontal; j++) {
                components[i][j] = getNewCell();
                add(components[i][j]);
                final int row = i;
                final int col = j;
                //TODO: this might be only for showing detailed data
                //we should show summary sick/healthy info at each dayPass()
                //not because mouse click.
                components[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent arg0) {
                        cntrl.cellClicked(row, col);
                    }
                });
            }
        }
    }

    public abstract void showCell(int row, int col, String stats);

    protected abstract JComponent getNewCell();
}
