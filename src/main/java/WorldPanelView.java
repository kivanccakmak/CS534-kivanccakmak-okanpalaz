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
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;


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
        labInfect = new JLabel("% Infected");
        labDoctor = new JLabel("% Doctor");
        labVaccine = new JLabel("# Vaccine");
        labSuper = new JLabel("% Super");

        txtVert = new JTextField(5);
        txtHoriz = new JTextField(5);
        txtPeople = new JTextField(5);
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
        this.initButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String numVertical = getVertCountry();
                String numHorizontal = getHorizCountry();
                String numPeople = getNumPeople();
                String percentInfected = getPercentInfected();
                String percentSuper = getPercentSuper();
                String percentDoctor = getPercentDoctor();
                String numVaccine = getNumVaccine();
                cntrl.restart(numVertical, numHorizontal, numPeople,
                        percentInfected, percentSuper, percentDoctor, numVaccine);
            }
        });
        this.passButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cntrl.passDay();
            }
        });
    }

    private String getVertCountry() { return txtVert.getText(); }
    private String getHorizCountry() { return txtHoriz.getText(); }
    private String getNumPeople() { return txtPeople.getText(); }
    private String getPercentInfected() { return txtInfect.getText(); }
    private String getPercentSuper() { return txtSuper.getText(); }
    private String getPercentDoctor() { return txtDoctor.getText(); }
    private String getNumVaccine() { return txtVaccine.getText(); }
}

class InfoPanel extends JPanel{
    private JLabel label;
    private JTabbedPane tabs;
    private ChartPanel health;
    private ChartPanel breakdown;

    InfoPanel() {
        this.setBorder(new EtchedBorder());
        tabs = new JTabbedPane();

        health = new ChartPanel(null);
        breakdown = new ChartPanel(null);

        tabs.addTab("Health", health);
        tabs.addTab("Population Breakdown", breakdown);

        this.setLayout(new BorderLayout());
        this.add(tabs);
    }

    private JFreeChart genHealthChart(Country.HealthStats stats) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        if (stats.sickCount() > 0) {
            dataset.setValue("Sick", new Double(stats.sickCount()));
        }
        if (stats.healthyCount() > 0) {
            dataset.setValue("Healthy", new Double(stats.healthyCount()));
        }
        if (stats.infectedCount() > 0) {
            dataset.setValue("Infected", new Double(stats.infectedCount()));
        }
        if (stats.immuneCount() > 0) {
            dataset.setValue("Immune", new Double(stats.immuneCount()));
        }
        if (stats.superHealthyCount() > 0) {
            dataset.setValue("Super", new Double(stats.superHealthyCount()));
        }
        if (stats.deadCount() > 0) {
            dataset.setValue("Dead", new Double(stats.deadCount()));
        }

        JFreeChart chart = ChartFactory.createPieChart(
                stats.name(),
                dataset,
                true,
                true,
                false
                );

        ((PiePlot)chart.getPlot()).setSectionPaint("Healthy", Color.green);
        ((PiePlot)chart.getPlot()).setSectionPaint("Sick", Color.red);
        ((PiePlot)chart.getPlot()).setSectionPaint("Infected", Color.magenta);
        ((PiePlot)chart.getPlot()).setSectionPaint("Super", Color.white);
        ((PiePlot)chart.getPlot()).setSectionPaint("Dead", Color.black);
        ((PiePlot)chart.getPlot()).setSectionPaint("Immune", Color.orange);

        return chart;
    }

    private JFreeChart genBreakdown(Country.HealthStats stats) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        long docs = stats.doctors();
        long regular = stats.population() - docs;
        if (docs > 0) {
            dataset.setValue("Doctor", new Double(docs));
        }

        if (regular > 0) {
            dataset.setValue("Regular", new Double(regular));
        }

        JFreeChart chart = ChartFactory.createPieChart(
                stats.name(),
                dataset,
                true,
                true,
                false
                );

        ((PiePlot)chart.getPlot()).setSectionPaint("Doctor", Color.white);
        ((PiePlot)chart.getPlot()).setSectionPaint("Regular", Color.gray);

        return chart;
    }

    public void updateDat(Country.HealthStats stats) {
        health.setChart(genHealthChart(stats));
        breakdown.setChart(genBreakdown(stats));
        this.updateUI();
    }
}

public class WorldPanelView extends WorldView {
    private InfoPanel leftPanel;
    private JPanel rightPanel;
    private JComponent[] components;



    public WorldPanelView(WorldController cntrl) {
        super(cntrl);
    }

    public JComponent getOutputPanel() {
        rightPanel = new JPanel();
        leftPanel = new InfoPanel();

        JSplitPane splitOut = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                true, leftPanel, rightPanel);
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

    public void updateOutputPanel(List<Country.HealthStats> stats) {
        Country.HealthStats summed =
            stats.stream()
            .reduce(new Country.HealthStats(), (s, a) -> {s.add(a); return s;});

        leftPanel.updateDat(summed);

        String out = "";
        for (int i = 0; i < components.length; i++) {
            InfoPanel panel = (InfoPanel) components[i];
            out = updateToHtml(stats.get(i).toString());
            panel.updateDat(stats.get(i));
        }
    }

    private String updateToHtml(String val) {
        String out = "";
        out = val.replace("\n", "<br>");
        out = "<html>" + out + "</html>";
        return out;
    }

    public void initOutputPanel(int numVertical, int numHorizontal,
            int numPeople, double percentInfected,
            double percentSuper, double percentDoctor, int numVaccine) {
        rightPanel.setLayout(new GridLayout(numVertical, numHorizontal));
        components = new JComponent[numVertical * numHorizontal];

        for (int i = 0; i < components.length; i++) {
            components[i] = getNewCell();
            this.rightPanel.add(components[i]);
        }
    }
}
