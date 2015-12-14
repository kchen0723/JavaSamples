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
	      //����ͼ�����
	      JFreeChart jfreechart = ChartFactory.createPieChart(name,piedataset,true,true,false);
	      //���ͼ����ʾ����
	      PiePlot pieplot = (PiePlot)jfreechart.getPlot();
	      //����ͼ���ǩ����
	      pieplot.setLabelFont(new Font("SansSerif",Font.BOLD,15));
	      pieplot.setNoDataMessage("No data available");
	      pieplot.setCircular(true);
	      pieplot.setLabelGap(0.01D);//���
	      pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}",new DecimalFormat("0.0"),new DecimalFormat("0.0%")));
	      return jfreechart;
	}
	
	public static JFreeChart createLineChart(CategoryDataset bardataset,String name,String province,String fanno){
	      //����ͼ�����
	      JFreeChart jfreechart = ChartFactory.createLineChart(name,province,fanno,bardataset,PlotOrientation.VERTICAL,false,true,false);
	      //���ͼ����ʾ����
	      CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
	      //����ͼ���ǩ����
	      categoryplot.setNoDataMessage("No data available");
	      return jfreechart;
	}
	
	public static JFreeChart createBarChart(CategoryDataset bardataset,String name,String province,String fanno){
	      //����ͼ�����
	      JFreeChart jfreechart = ChartFactory.createBarChart(name,province,fanno,bardataset,PlotOrientation.VERTICAL,false,true,false);
	      //���ͼ����ʾ����
	      CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
	      //����ͼ���ǩ����
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
	    //���� ������ ��ֱ��ʾ
	    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));
	    domainAxis.setLabelFont(new Font("����",Font.BOLD,15));
	    domainAxis.setTickLabelFont(new Font("����",Font.PLAIN,13));
	    BarRenderer renderer = new   BarRenderer(); 
	    
	    //��ʾÿ��������ֵ�����޸ĸ���ֵ����������
	    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	    renderer.setBaseItemLabelsVisible(true);
	    
	    //Ĭ�ϵ�������ʾ�������У�ͨ����������ɵ������ֵ���ʾ
	    //ע�⣺�˾�ܹؼ������޴˾䣬�����ֵ���ʾ�ᱻ���ǣ���������û����ʾ����������
	    renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
	    renderer.setItemLabelAnchorOffset(5D);
	    renderer.setSeriesItemLabelsVisible(0, true);
	    
	    //����ÿ��������������ƽ������֮�����
	  	renderer.setItemMargin(0.05);
	    plot.setRenderer(renderer); 
	}
	
	public void resetLineChart(JFreeChart chart,DefaultCategoryDataset db){
		CategoryPlot plot=(CategoryPlot) chart.getPlot();
		plot.setDataset(db);
		plot.datasetChanged(new DatasetChangeEvent(plot, db));
		CategoryAxis domainAxis = plot.getDomainAxis();
	    ////���� ������ ��ֱ��ʾ
	    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));
	    domainAxis.setLabelFont(new Font("����",Font.BOLD,15));
	    domainAxis.setTickLabelFont(new Font("����",Font.PLAIN,13));
	    LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();  
	    ////renderer.setLabelGenerator(new   StandardCategoryLabelGenerator()); 
	    
	    //����������ʾλ��
	    ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER_LEFT,TextAnchor.CENTER_LEFT, -Math.PI / 2.0 );
	    renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER)); 
	    //��ʾ�۵���Ӧ����
	    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
	    renderer.setSeriesItemLabelsVisible(0, true);
	    renderer.setSeriesItemLabelsVisible(1, true);
	    //����ʵ��
	    BasicStroke realLine = new BasicStroke(2.0f);
	    //��������
	    float dashes[] = { 1.0f };  
	    BasicStroke brokenLine = new BasicStroke(2.0f,      //������ϸ  
                BasicStroke.CAP_SQUARE,             //�˵���  
                BasicStroke.JOIN_MITER,                 //�۵���  
                8.f,                                //�۵㴦��취  
                dashes,                         //��������  
                0.0f);                          //����ƫ����  
        renderer.setSeriesStroke(0, realLine); 
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(1, brokenLine);
	    renderer.setBaseShapesVisible(true);
	    plot.setRenderer(renderer); 
	}
}
