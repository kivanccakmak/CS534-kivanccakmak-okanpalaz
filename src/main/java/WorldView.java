import java.util.*;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;

public abstract class WorldView{

    public WorldView() {
    }

    public abstract void updateCell(int i, int j, int playerID);

    protected abstract JComponent getNewCell();
}
