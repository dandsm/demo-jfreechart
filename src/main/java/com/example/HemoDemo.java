package com.example;

import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;

public class HemoDemo extends ApplicationFrame {

    private static final int X_LENGHT = 600;
    private static final int Y_LENGHT = 200;
    private static final String UNDER_THRESHOLD_RANGE = "UNDER_THRESHOLD_RANGE";
    private static final String OVER_THRESHOLD_RANGE = "OVER_THRESHOLD_RANGE";
    private static final String MEASURED_RANGE = "MEASURED_RANGE";

    String[] catArray;
    String testName;
    String testUnits;
    int minRange;
    int maxRange;
    double threshold;
    double minResult;
    double maxResult;
    JFreeChart chart;
    NumberAxis numberAxis;

    public HemoDemo(String testName, String testUnits, int minRange, int maxRange, double threshold, double minResult,
            double maxResult) {
        super(testName + " " + testUnits);
        this.testName = testName;
        this.testUnits = testUnits;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.threshold = threshold;
        this.minResult = minResult;
        this.maxResult = maxResult;
        catArray = new String[] { "", "" };
        chart = createIntervalStackedChart();
        chart.removeLegend();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(X_LENGHT, Y_LENGHT));
        setContentPane(chartPanel);
    }

    private JFreeChart createIntervalStackedChart() {
        XYIntervalSeriesCollection dataset = createXYIntervalxDataset();

        XYBarRenderer xyRend = new XYBarRenderer();
        xyRend.setShadowVisible(false);
        xyRend.setUseYInterval(true);
        xyRend.setBarPainter(new StandardXYBarPainter());
        xyRend.setSeriesPaint(0, Color.GREEN);
        xyRend.setSeriesPaint(1, Color.RED);
        if (minResult >= threshold || maxResult > threshold) {
            xyRend.setSeriesPaint(2, Color.RED);
        } else {
            xyRend.setSeriesPaint(2, Color.GREEN);
        }

        numberAxis = new NumberAxis();
        numberAxis.setTickUnit(new NumberTickUnit(100));
        numberAxis.setVerticalTickLabels(true);
        numberAxis.setUpperMargin(0);

        ValueMarker marker = new ValueMarker(125);
        marker.setPaint(Color.DARK_GRAY);
        marker.setStroke(new BasicStroke(2f));

        XYPlot plot = new XYPlot(dataset, new SymbolAxis("", catArray), numberAxis, xyRend);
        plot.setOrientation(PlotOrientation.HORIZONTAL);
        plot.setBackgroundPaint(Color.WHITE);
        plot.addRangeMarker(marker);

        return new JFreeChart(plot);
    }

    private XYIntervalSeriesCollection createXYIntervalxDataset() {
        XYIntervalSeriesCollection dataset = new XYIntervalSeriesCollection();
        int catPosition;

        // Under Threshold Range
        catPosition = 0;
        XYIntervalSeries serie0 = new XYIntervalSeries(UNDER_THRESHOLD_RANGE);
        serie0.add(catPosition, catPosition - 0.2, catPosition + 0.2, minRange, minRange, threshold);
        dataset.addSeries(serie0);

        // Over Threshold Range
        XYIntervalSeries serie1 = new XYIntervalSeries(OVER_THRESHOLD_RANGE);
        serie1.add(catPosition, catPosition - 0.2, catPosition + 0.2, threshold, threshold, maxRange);
        dataset.addSeries(serie1);

        // Measured Range
        catPosition = 1;
        XYIntervalSeries serie2 = new XYIntervalSeries(MEASURED_RANGE);
        serie2.add(catPosition, catPosition - 0.2, catPosition + 0.2, minResult, minResult, maxResult);
        dataset.addSeries(serie2);

        return dataset;
    }

    public static void main(String[] args) {
        HemoDemo demo = new HemoDemo("Hemoglobin", "mg/dL", 0, 900, 125, 80.1, 120.1);
        demo.pack();
        UIUtils.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}
