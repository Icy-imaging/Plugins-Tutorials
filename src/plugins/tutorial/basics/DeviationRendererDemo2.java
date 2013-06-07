/*
 * Copyright 2010, 2011 Institut Pasteur.
 * 
 * This file is part of ICY.
 * 
 * ICY is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ICY is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ICY. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
 * 
 * Project Info: http://www.jfree.org/jfreechart/index.html
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
 * USA.
 * 
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 * 
 * ------------------
 * BarChartDemo1.java
 * ------------------
 * (C) Copyright 2003-2009, by Object Refinery Limited and Contributors.
 * 
 * Original Author: David Gilbert (for Object Refinery Limited);
 * Contributor(s): ;
 * 
 * Changes
 * -------
 * 09-Mar-2005 : Version 1 (DG);
 */
package plugins.tutorial.basics;

import icy.gui.frame.IcyFrame;
import icy.gui.util.GuiUtil;
import icy.plugin.abstract_.PluginActionable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Week;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 * A simple demonstration of a more complex graph from JFreeChart, adapted to ICY
 */
public class DeviationRendererDemo2 extends PluginActionable
{

    JPanel mainPanel = GuiUtil.generatePanel("Graph");
    IcyFrame mainFrame = GuiUtil.generateTitleFrame("Chart demo", mainPanel, new Dimension(300, 100), true, true, true,
            true);

    /**
     * Returns a sample dataset.
     * 
     * @return The dataset.
     */
    private static XYDataset createDataset()
    {

        YIntervalSeries yintervalseries = new YIntervalSeries("Series 1");
        YIntervalSeries yintervalseries1 = new YIntervalSeries("Series 2");
        Object obj = new Week();
        double d = 100D;
        double d1 = 100D;
        for (int i = 0; i <= 52; i++)
        {
            double d2 = 0.050000000000000003D * i;
            yintervalseries.add(((RegularTimePeriod) (obj)).getFirstMillisecond(), d, d - d2, d + d2);
            d = (d + Math.random()) - 0.45000000000000001D;
            double d3 = 0.070000000000000007D * i;
            yintervalseries1.add(((RegularTimePeriod) (obj)).getFirstMillisecond(), d1, d1 - d3, d1 + d3);
            d1 = (d1 + Math.random()) - 0.55000000000000004D;
            obj = ((RegularTimePeriod) (obj)).next();
        }

        YIntervalSeriesCollection yintervalseriescollection = new YIntervalSeriesCollection();
        yintervalseriescollection.addSeries(yintervalseries);
        yintervalseriescollection.addSeries(yintervalseries1);
        return yintervalseriescollection;

    }

    /**
     * Creates a sample chart.
     * 
     * @param dataset
     *        the dataset.
     * @return The chart.
     */
    private static JFreeChart createChart(XYDataset xydataset)
    {

        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("Projected Values - Test", "Date",
                "Index Projection", xydataset, true, true, false);
        jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        xyplot.setInsets(new RectangleInsets(5D, 5D, 5D, 20D));
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        DeviationRenderer deviationrenderer = new DeviationRenderer(true, false);
        deviationrenderer.setSeriesStroke(0, new BasicStroke(3F, 1, 1));
        deviationrenderer.setSeriesStroke(0, new BasicStroke(3F, 1, 1));
        deviationrenderer.setSeriesStroke(1, new BasicStroke(3F, 1, 1));
        deviationrenderer.setSeriesFillPaint(0, new Color(255, 200, 200));
        deviationrenderer.setSeriesFillPaint(1, new Color(200, 200, 255));
        xyplot.setRenderer(deviationrenderer);
        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        numberaxis.setAutoRangeIncludesZero(false);
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return jfreechart;

    }

    @Override
    public void run()
    {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        mainPanel.add(chartPanel);

        mainFrame.pack();
        addIcyFrame(mainFrame);

        mainFrame.setVisible(true);
        mainFrame.center();
        mainFrame.requestFocus();

    }

}
