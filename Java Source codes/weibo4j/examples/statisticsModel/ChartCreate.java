package statisticsModel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.TextAnchor;

public class ChartCreate {
	public static JFreeChart createChart(PieDataset piedataset,String name){
	      //定义图表对象
	      JFreeChart jfreechart = ChartFactory.createPieChart(name,piedataset,true,true,false);
	      //获得图表显示对象
	      PiePlot pieplot = (PiePlot)jfreechart.getPlot();
	      //设置图表标签字体
	      pieplot.setLabelFont(new Font("SansSerif",Font.BOLD,15));
	      pieplot.setNoDataMessage("No data available");
	      pieplot.setCircular(true);
	      pieplot.setLabelGap(0.01D);//间距
	      pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}",new DecimalFormat("0.0"),new DecimalFormat("0.0%")));
	      return jfreechart;
	}
	
	public static JFreeChart createLineChart(CategoryDataset bardataset,String name,String province,String fanno){
	      //定义图表对象
	      JFreeChart jfreechart = ChartFactory.createLineChart(name,province,fanno,bardataset,PlotOrientation.VERTICAL,false,true,false);
	      //获得图表显示对象
	      CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
	      //设置图表标签字体
	      categoryplot.setNoDataMessage("No data available");
	      return jfreechart;
	}
	
	public static JFreeChart createBarChart(CategoryDataset bardataset,String name,String province,String fanno){
	      //定义图表对象
	      JFreeChart jfreechart = ChartFactory.createBarChart(name,province,fanno,bardataset,PlotOrientation.VERTICAL,false,true,false);
	      //获得图表显示对象
	      CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
	      //设置图表标签字体
	      categoryplot.setNoDataMessage("No data available");
	      return jfreechart;
	}
	
	public void resetChart(JFreeChart chart,DefaultPieDataset db){
		PiePlot plot=(PiePlot) chart.getPlot();
		plot.setDataset(db);
		plot.datasetChanged(new DatasetChangeEvent(plot, db));
	}
	
	public void resetBarChart(JFreeChart chart,DefaultCategoryDataset db){
		CategoryPlot plot=(CategoryPlot) chart.getPlot();
		plot.setDataset(db);
		plot.datasetChanged(new DatasetChangeEvent(plot, db));
		CategoryAxis domainAxis = plot.getDomainAxis();
	    //设置 横坐标 垂直显示
	    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));
	    domainAxis.setLabelFont(new Font("宋体",Font.BOLD,15));
	    domainAxis.setTickLabelFont(new Font("宋体",Font.PLAIN,13));
	    BarRenderer renderer = new   BarRenderer(); 
	    
	    //显示每个柱的数值，并修改该数值的字体属性
	    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	    renderer.setBaseItemLabelsVisible(true);
	    
	    //默认的数字显示在柱子中，通过如下两句可调整数字的显示
	    //注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
	    renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
	    renderer.setItemLabelAnchorOffset(5D);
	    renderer.setSeriesItemLabelsVisible(0, true);
	    
	    //设置每个地区所包含的平行柱的之间距离
	  	renderer.setItemMargin(0.05);
	    plot.setRenderer(renderer); 
	}
	
	public void resetLineChart(JFreeChart chart,DefaultCategoryDataset db){
		CategoryPlot plot=(CategoryPlot) chart.getPlot();
		plot.setDataset(db);
		plot.datasetChanged(new DatasetChangeEvent(plot, db));
		CategoryAxis domainAxis = plot.getDomainAxis();
	    ////设置 横坐标 垂直显示
	    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));
	    domainAxis.setLabelFont(new Font("宋体",Font.BOLD,15));
	    domainAxis.setTickLabelFont(new Font("宋体",Font.PLAIN,13));
	    LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();  
	    ////renderer.setLabelGenerator(new   StandardCategoryLabelGenerator()); 
	    
	    //设置数据显示位置
	    ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER_LEFT,TextAnchor.CENTER_LEFT, -Math.PI / 2.0 );
	    renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER)); 
	    //显示折点相应数据
	    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
	    renderer.setSeriesItemLabelsVisible(0, true);
	    renderer.setSeriesItemLabelsVisible(1, true);
	    //设置实线
	    BasicStroke realLine = new BasicStroke(2.0f);
	    //设置虚线
	    float dashes[] = { 1.0f };  
	    BasicStroke brokenLine = new BasicStroke(2.0f,      //线条粗细  
                BasicStroke.CAP_SQUARE,             //端点风格  
                BasicStroke.JOIN_MITER,                 //折点风格  
                8.f,                                //折点处理办法  
                dashes,                         //虚线数组  
                0.0f);                          //虚线偏移量  
        renderer.setSeriesStroke(0, realLine); 
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(1, brokenLine);
	    renderer.setBaseShapesVisible(true);
	    plot.setRenderer(renderer); 
	}
}
