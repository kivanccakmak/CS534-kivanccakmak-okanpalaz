import java.util.*;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;

class InputFields extends JPanel {
    private JPanel inputPanel;
    private JPanel buttonPanel;

    private JButton passButton;
    private JButton simulateButton;
    private JButton initButton;

    private JLabel labHoriz;
    private JTextField txtHoriz;

    private JLabel labVert;
    private JTextField txtVert;

    private JLabel labPeople;
    private JTextField txtPeople;

    private JLabel labDays;
    private JTextField txtDays;

    private JLabel labInfect;
    private JTextField txtInfect;

    private JLabel labDoctor;
    private JTextField txtDoctor;

    private JLabel labVaccine;
    private JTextField txtVaccine;

    private JLabel labSuper;
    private JTextField txtSuper;

    private WorldController cntrl;

    InputFields(WorldController cntrl) {
        this.cntrl = cntrl;

        JSplitPane allInputs;

        inputPanel = new JPanel();
        fillInputPanel();

        buttonPanel = new JPanel();
        fillButtonPanel();
        initButtonActions();

        allInputs = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                true, inputPanel, buttonPanel);

        this.add(allInputs);
    }

    private void fillButtonPanel() {
        passButton = new JButton("pass day");
        simulateButton = new JButton("simulate");
        initButton = new JButton("init");

        buttonPanel.add(simulateButton);
        buttonPanel.add(passButton);
        buttonPanel.add(initButton);
    }

    private void fillInputPanel() {
        labVert = new JLabel("# Vertical");
        labHoriz = new JLabel("# Horizontal");
        labPeople = new JLabel("# People");
        labDays = new JLabel("# Days");
        labInfect = new JLabel("% Infected");
        labDoctor = new JLabel("% Doctor");
        labVaccine = new JLabel("# Vaccine");
        labSuper = new JLabel("# Super");

        txtVert = new JTextField(5);
        txtHoriz = new JTextField(5);
        txtPeople = new JTextField(5);
        txtDays = new JTextField(3);
        txtInfect = new JTextField(3);
        txtDoctor = new JTextField(3);
        txtVaccine = new JTextField(3);
        txtSuper = new JTextField(3);

        inputPanel.add(labVert);
        inputPanel.add(txtVert);
        inputPanel.add(labHoriz);
        inputPanel.add(txtHoriz);
        inputPanel.add(labPeople);
        inputPanel.add(txtPeople);
        inputPanel.add(labDays);
        inputPanel.add(txtDays);
        inputPanel.add(labInfect);
        inputPanel.add(txtInfect);
        inputPanel.add(labDoctor);
        inputPanel.add(txtDoctor);
        inputPanel.add(labVaccine);
        inputPanel.add(txtVaccine);
        inputPanel.add(labSuper);
        inputPanel.add(txtSuper);
    }

    private void initButtonActions() {
        this.initButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              String numVertical = getVertCountry();
              String numHorizontal = getHorizCountry();
              String numPeople = getNumPeople();
              String numDays = getNumDays();
              String percentInfected = getPercentInfected();
              String percentSuper = getPercentSuper();
              String percentDoctor = getPercentDoctor();
              String numVaccine = getNumVaccine();

              cntrl.restart(numVertical, numHorizontal, numPeople,
                      numDays, percentInfected, percentSuper,
                        percentDoctor, numVaccine);
            }
        });
    }

    private String getVertCountry() { return txtVert.getText(); }
    private String getHorizCountry() { return txtHoriz.getText(); }
    private String getNumPeople() { return txtPeople.getText(); }
    private String getNumDays() { return txtDays.getText(); }
    private String getPercentInfected() { return txtInfect.getText(); }
    private String getPercentSuper() { return txtSuper.getText(); }
    private String getPercentDoctor() { return txtDoctor.getText(); }
    private String getNumVaccine() { return txtVaccine.getText(); }
}

public abstract class WorldView extends JFrame {
    protected JPanel countryBlocks;
    protected JPanel inputField;
    protected JPanel outputPane;

    public WorldView(int numVertical, int numHorizontal, WorldController cntrl) {
        setTitle("World Map");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        countryBlocks = new JPanel();
        inputField = new InputFields(cntrl);
        outputPane = new JPanel();

        JSplitPane splitOut = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                true, countryBlocks, outputPane);
        splitOut.setOneTouchExpandable(true);
        splitOut.setDividerLocation(0.4);

        JSplitPane container = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                inputField, splitOut);
        container.setOneTouchExpandable(true);
        container.setDividerLocation(0.4);

        this.add(container);
        this.setVisible(true);

    }

    public abstract void addCountries(int numVertical, int numHorizontal);

    public abstract void showCell(int row, int col, String stats);

    protected abstract JComponent getNewCell(int row, int col);
}
