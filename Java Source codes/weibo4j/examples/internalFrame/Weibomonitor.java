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
		setTitle("΢�����");
		JTabbedPane tabPane = new JTabbedPane();
		final StatusSearch ssPanel = new StatusSearch();
		final StatusQuery sqPanel = new StatusQuery();
		final QueryAnalysis qaPanel = new QueryAnalysis();
		final AutoFilter afPanel = new AutoFilter();
		tabPane.addTab("��ʱ΢�����", null, afPanel, "��ʱ΢�����");
		tabPane.addTab("ʵʱ΢������", null, ssPanel, "ʵʱ΢������");
		tabPane.addTab("��ʷ΢����ѯ", null, sqPanel, "��ʷ΢����ѯ");
		tabPane.addTab("΢��ͼ�����", null, qaPanel, "΢��ͼ�����");
		getContentPane().add(tabPane);
		pack();
		setVisible(true);
	}
}
