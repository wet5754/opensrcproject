package opensrcproject;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Chart extends JFrame{
	private int numChart =1;
	private XYChart chart;
	private List<XYChart> charts = new ArrayList();
	private XChartPanel<XYChart> chartdata;
	private String keyword;
	
	Chart(){
		NaverAPI temp = new NaverAPI(keyword);
		for(int i =0;i<numChart;i++) {
			chart = new XYChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(600).height(400).build();
			chart.getStyler().setYAxisMin(-10.0);
			chart.getStyler().setYAxisMax(10.0);
			XYSeries series = chart.addSeries(" "+i,null, getRandomWalk(200));
			series.setMarker(SeriesMarkers.NONE);
			charts.add(chart);
		}
		chartdata = new XChartPanel<>(chart);

	}
	
	public XChartPanel<XYChart> data() {
		return chartdata;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	private static double[] getRandomWalk(int numPoint) {
		double[] y = new double[numPoint];
		y[0] =0;
		for(int i =1;i<y.length;i++) {
			y[i] =y[i-1] + Math.random()-.5;
		}
		return y;
	}
}
