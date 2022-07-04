package com.example;

import java.awt.Dimension;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;

public class HemoDemo extends ApplicationFrame {

    String[] catArray = new String[] { "Ranges", "Measured" };
    JFreeChart chart;
    NumberAxis numberAxis;

    public HemoDemo(String title) {
        super(title);
        chart = createIntervalStackedChart();
        chart.removeLegend();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 200));
        setContentPane(chartPanel);
    }

    private JFreeChart createIntervalStackedChart() {
        XYIntervalSeriesCollection dataset = createXYIntervalxDataset();
        XYBarRenderer xyRend = new XYBarRenderer();
        xyRend.setShadowVisible(false);
        xyRend.setUseYInterval(true);
        xyRend.setBarPainter(new StandardXYBarPainter());
        xyRend.setSeriesPaint(0, ChartColor.DARK_GREEN);
        xyRend.setSeriesPaint(1, ChartColor.DARK_RED);
        xyRend.setSeriesPaint(2, ChartColor.LIGHT_GREEN);

        numberAxis = new NumberAxis();
        numberAxis.setVerticalTickLabels(true);
        XYPlot plot = new XYPlot(dataset, new SymbolAxis("", catArray), numberAxis, xyRend);
        plot.setOrientation(PlotOrientation.HORIZONTAL);
        plot.setBackgroundPaint(ChartColor.WHITE);

        return new JFreeChart(plot);
    }

    private XYIntervalSeriesCollection createXYIntervalxDataset() {
        XYIntervalSeriesCollection dataset = new XYIntervalSeriesCollection();

        // UnderLimit Range
        int catRange = 0;
        XYIntervalSeries serie1 = new XYIntervalSeries("UNDERLIMIT");
        serie1.add(catRange, catRange - 0.2, catRange + 0.2, 0L, 0, 125);
        dataset.addSeries(serie1);

        // OverLimit Range
        XYIntervalSeries serie2 = new XYIntervalSeries("OVERLIMIT");
        serie2.add(catRange, catRange - 0.2, catRange + 0.2, 125L, 125, 900);
        dataset.addSeries(serie2);

        // Measured
        int catValue = 1;
        XYIntervalSeries serie3 = new XYIntervalSeries("MEASURED");
        serie3.add(catValue, catValue - 0.2, catValue + 0.2, 80L, 80.1, 120.1);
        dataset.addSeries(serie3);

        return dataset;
    }

    public static void main(String[] args) {
        HemoDemo demo = new HemoDemo("Hemoglobin(mg/dL)");
        demo.pack();
        UIUtils.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}
