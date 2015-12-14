package internalFrame;
import internalFrame.weibomonitor.AutoFilter;
import internalFrame.weibomonitor.QueryAnalysis;
import internalFrame.weibomonitor.StatusQuery;
import internalFrame.weibomonitor.StatusSearch;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
public class Weibomonitor extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5483086044895133821L;
	
	public Weibomonitor() {
		super();
		setIconifiable(true);
		setClosable(true);
		setSize( 491, 287);
		setMaximizable(true);
		setTitle("微博监控");
		JTabbedPane tabPane = new JTabbedPane();
		final StatusSearch ssPanel = new StatusSearch();
		final StatusQuery sqPanel = new StatusQuery();
		final QueryAnalysis qaPanel = new QueryAnalysis();
		final AutoFilter afPanel = new AutoFilter();
		tabPane.addTab("定时微博监控", null, afPanel, "定时微博监控");
		tabPane.addTab("实时微博检索", null, ssPanel, "实时微博检索");
		tabPane.addTab("历史微博查询", null, sqPanel, "历史微博查询");
		tabPane.addTab("微博图表分析", null, qaPanel, "微博图表分析");
		getContentPane().add(tabPane);
		pack();
		setVisible(true);
	}
}
