import java.util.*;
import java.util.stream.*;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.chart.plot.PlotOrientation;


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

    private JLabel labAir;
    private JTextField txtAir;

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
        passButton = new JButton("Pass day");
        initButton = new JButton("Re-initialize");

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
        labAir = new JLabel("% Air Travel Chance");

        txtVert = new JTextField("3", 5);
        txtHoriz = new JTextField("3", 5);
        txtPeople = new JTextField("1000", 5);
        txtInfect = new JTextField("20.0", 3);
        txtDoctor = new JTextField("5.0", 3);
        txtVaccine = new JTextField("2", 3);
        txtSuper = new JTextField("2.0", 3);
        txtAir = new JTextField("10.0", 3);

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
        inputPanel.add(labAir);
        inputPanel.add(txtAir);
    }

    private void initButtonActions() {
        this.initButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    cntrl.restart(getVertCountry(), getHorizCountry(), getNumPeople(),
                            getPercentInfected(), getPercentSuper(), getPercentDoctor(),
                            getNumVaccine(), getAirChance());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input: " + e.getMessage());
                }
            }
        });
        this.passButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    cntrl.passDay();
                } catch (IllegalStateException e) {
                    JOptionPane.showMessageDialog(null, "Invalid state: " + e.getMessage());
                }
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
    private String getAirChance() { return txtAir.getText(); }
}

class InfoPanel extends JPanel{
    private JLabel label;
    private JTabbedPane tabs;
    private ChartPanel health;
    private ChartPanel breakdown;
    private ChartPanel history;
    private ArrayList<Country.HealthStats> statHistory;

    InfoPanel() {
        this.setBorder(new EtchedBorder());
        tabs = new JTabbedPane();

        health = new ChartPanel(null);
        breakdown = new ChartPanel(null);
        history = new ChartPanel(null);

        tabs.addTab("Health", health);
        tabs.addTab("Population Breakdown", breakdown);
        tabs.addTab("Population History", history);
        statHistory = new ArrayList<Country.HealthStats>();

        this.setLayout(new BorderLayout());
        this.add(tabs);
    }

    private JFreeChart genHealthChart(Country.HealthStats stats) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        if (stats.healthyCount() > 0) {
            dataset.setValue("Healthy", new Double(stats.healthyCount()));
        }
        if (stats.infectedCount() > 0) {
            dataset.setValue("Infected", new Double(stats.infectedCount()));
        }
        if (stats.sickCount() > 0) {
            dataset.setValue("Sick", new Double(stats.sickCount()));
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
        ((PiePlot)chart.getPlot()).setSectionPaint("Infected", Color.magenta);
        ((PiePlot)chart.getPlot()).setSectionPaint("Sick", Color.red);
        ((PiePlot)chart.getPlot()).setSectionPaint("Immune", Color.orange);
        ((PiePlot)chart.getPlot()).setSectionPaint("Super", Color.white);
        ((PiePlot)chart.getPlot()).setSectionPaint("Dead", Color.black);

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

    private JFreeChart genPopulationHistory(Country.HealthStats stats) {
        String[] categories = {"Healthy", "Infected", "Sick", "Immune", "Super", "Dead"};
        statHistory.add(stats);

        double[][] data = new double[][] {
            statHistory.stream().mapToDouble(s -> s.healthyCount()).toArray(),
            statHistory.stream().mapToDouble(s -> s.infectedCount()).toArray(),
            statHistory.stream().mapToDouble(s -> s.sickCount()).toArray(),
            statHistory.stream().mapToDouble(s -> s.immuneCount()).toArray(),
            statHistory.stream().mapToDouble(s -> s.superHealthyCount()).toArray(),
            statHistory.stream().mapToDouble(s -> s.deadCount()).toArray()
        };

        String[] days = IntStream.range(1, statHistory.size() + 1)
            .mapToObj(String::valueOf)
            .toArray(String[]::new);

        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(categories, days, data);

        JFreeChart chart = ChartFactory.createStackedAreaChart(
                stats.name(),
                "",
                "",
                dataset,
                PlotOrientation.VERTICAL, true, true, true
                );

        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.green);
        chart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.magenta);
        chart.getCategoryPlot().getRenderer().setSeriesPaint(2, Color.red);
        chart.getCategoryPlot().getRenderer().setSeriesPaint(3, Color.orange);
        chart.getCategoryPlot().getRenderer().setSeriesPaint(4, Color.white);
        chart.getCategoryPlot().getRenderer().setSeriesPaint(5, Color.black);

        return chart;
    }

    public void updateStats(Country.HealthStats stats) {
        health.setChart(genHealthChart(stats));
        breakdown.setChart(genBreakdown(stats));
        history.setChart(genPopulationHistory(stats));
        this.updateUI();
    }
}

public class WorldView extends JFrame {
    private InfoPanel leftPanel;
    private JPanel rightPanel;
    private InfoPanel[] components;

    private JComponent inputPanel;
    private JPanel outputPanel;
    private WorldController cntrl;

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

    public JSplitPane getOutputPanel() {
        rightPanel = new JPanel();
        leftPanel = new InfoPanel();

        JSplitPane splitOut = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                true, leftPanel, rightPanel);
        splitOut.setOneTouchExpandable(true);
        return splitOut;
    }

    public JComponent getInputPanel() {
        InputFields inputPanel = new InputFields(this.cntrl);
        return inputPanel;
    }

    public void updateOutput(List<Country.HealthStats> stats) {
        Country.HealthStats summed =
            stats.stream()
            .reduce(new Country.HealthStats(), (s, a) -> {s.add(a); return s;});

        leftPanel.updateStats(summed);

        for (int i = 0; i < components.length; i++) {
            InfoPanel panel = components[i];
            panel.updateStats(stats.get(i));
        }
    }

    public void initOutput(int numVertical, int numHorizontal) throws IllegalArgumentException {
        if (numVertical < 1 || numHorizontal < 1) {
            throw new IllegalArgumentException("Invalid dimentions");
        }

        outputPanel.removeAll();
        JSplitPane split = getOutputPanel();
        outputPanel.add(split);
        rightPanel.setLayout(new GridLayout(numVertical, numHorizontal));
        components = new InfoPanel[numVertical * numHorizontal];

        for (int i = 0; i < components.length; i++) {
            components[i] = new InfoPanel();
            this.rightPanel.add(components[i]);
        }

        this.setVisible(true);
        split.setDividerLocation(0.4);
    }
}
