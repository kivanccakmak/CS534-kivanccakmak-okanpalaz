import java.util.*;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;

public abstract class WorldView extends Jframe {

    protected WorldMap [][] components;

    public WorldView(int numHorizontal, int numVertical, Controller controller) {
        this.components = new JComponent[numHorizontal][numVertical];
        setLayout(new GridLayout(numHorizontal, numVertical));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < numVertical; i++) {
            for (int j = 0; j < numHorizontal; j++) {
                components[i][j] = getNewCell();
                add(components[i][j]);
                final int row = i;
                final int col = j;
                components[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent arg0) {
                        controller.cellClicked(row, col);
                    }
                });
            }
        }
        setTitle("World Map");
    }

    public abstract void updateCell(int i, int j, int playerID);

    protected abstract JComponent getNewCell();
}
