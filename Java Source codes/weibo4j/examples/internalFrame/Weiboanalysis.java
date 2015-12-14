package internalFrame;

import internalFrame.weiboanalysis.GetStatusList;
import internalFrame.weiboanalysis.StatusAnalysis;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

public class Weiboanalysis extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5483086044895133821L;
	
	public Weiboanalysis() {
		super();
		setIconifiable(true);
		setClosable(true);
		setSize( 491, 287);
		setMaximizable(true);
		setTitle("΢������");
		JTabbedPane tabPane = new JTabbedPane();
		final GetStatusList gslPanel = new GetStatusList();
		final StatusAnalysis rfPanel = new StatusAnalysis();
		
		tabPane.addTab("��ȡ΢���б�", null, gslPanel, "��ȡ΢���б�");
		tabPane.addTab("΢��ת������", null, rfPanel, "΢��ת������");
		
		getContentPane().add(tabPane);
		//tabPane.addChangeListener(new ChangeListener() {
		//	public void stateChanged(ChangeEvent e) {
		//		delPanel.initTable();
		//	}
		//});
		pack();
		setVisible(true);
	}
}
