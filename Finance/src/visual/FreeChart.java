package visual;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class FreeChart extends ApplicationFrame {

	private static final long serialVersionUID = -2958492061935343891L;

	private XYSeries series1 = null;
	private XYSeries series2 = null;
	private XYSeries series3 = null;
	private XYSeriesCollection dataset = new XYSeriesCollection();

	public FreeChart(final String title) {

		super(title);
		createSampleDataset();
		XYDataset dataset = this.dataset;
		JFreeChart chart = ChartFactory.createXYLineChart(title, "X", "Y",
				dataset, PlotOrientation.VERTICAL, true, false, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesLinesVisible(1, false);
		renderer.setSeriesShapesVisible(1, true);
		plot.setRenderer(renderer);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
		setContentPane(chartPanel);

	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return A dataset.
	 */
	private void createSampleDataset() {
		series1 = new XYSeries("Series 1");
		series2 = new XYSeries("Series 2");
		series3 = new XYSeries("Series 3");
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);

	}

//	public static void main(final String[] args) {
//
//		final FreeChart demo = new FreeChart("XYLineAndShapeRenderer Demo");
//		demo.pack();
//		RefineryUtilities.centerFrameOnScreen(demo);
//		demo.setVisible(true);
//
//	}

	public XYSeries getSeries1() {
		return series1;
	}

	public void setSeries1(XYSeries series1) {
		this.series1 = series1;
	}

	public XYSeries getSeries2() {
		return series2;
	}

	public void setSeries2(XYSeries series2) {
		this.series2 = series2;
	}

	public XYSeries getSeries3() {
		return series3;
	}

	public void setSeries3(XYSeries series3) {
		this.series3 = series3;
	}

	public XYSeriesCollection getDataset() {
		return dataset;
	}

	public void setDataset(XYSeriesCollection dataset) {
		this.dataset = dataset;
	}
}