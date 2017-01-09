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
    private WorldController cntrl;
    private JPanel inputPanel;
    private JPanel buttonPanel;

    private JButton passButton;
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
        initButton = new JButton("init");

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
        labSuper = new JLabel("% Super");

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
                      numDays, percentInfected, percentSuper, percentDoctor,
                        numVaccine);
            }
        });
        this.passButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                cntrl.passDay();
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
    private JPanel countryBlocks;
    private JPanel textOutput;
    private JComponent[][] components;

    public WorldPanelView(WorldController cntrl) {
        super(cntrl);
    }

    public JComponent getOutputPanel() {
        countryBlocks = new JPanel();
        textOutput = new JPanel();
        JSplitPane splitOut = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                true, textOutput, countryBlocks);
        splitOut.setOneTouchExpandable(true);
        splitOut.setDividerLocation(0.4);
        return splitOut;
    }

    public JComponent getInputPanel() {
        InputFields inputPanel = new InputFields(this.cntrl);
        return inputPanel;
    }

    private JPanel getNewCell() {
        InfoPanel panel = new InfoPanel();
        return (JPanel) panel;
    }

    public void updateOutputPanel(String[][] stats) {
        String out = "";
        for (int i = 0; i < numVertical; i++) {
            for (int j = 0; j < numHorizontal; j++) {
                InfoPanel panel = (InfoPanel) this.components[i][j];
                out = updateToHtml(stats[i][j]);
                panel.setLabelMsg(out);
            }
        }
    }

    private String updateToHtml(String val) {
        String out = "";
        out = val.replace("\n", "<br>");
        out = "<html>" + out + "</html>";
        return out;
    }

    public void initOutputPanel(int numVertical, int numHorizontal,
            int numPeople, int numDays, double percentInfected,
                double percentSuper, double percentDoctor, int numVaccine) {
        this.countryBlocks.setLayout(new GridLayout(numVertical, numHorizontal));
        this.components = new JComponent[numVertical][numHorizontal];
        for (int i = 0; i < numVertical; i++) {
            for (int j = 0; j < numHorizontal; j++) {
                this.components[i][j] = getNewCell();
                this.countryBlocks.add(this.components[i][j]);
            }
        }
    }
}
