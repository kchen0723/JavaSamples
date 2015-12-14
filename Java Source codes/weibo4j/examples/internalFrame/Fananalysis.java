package internalFrame;

import internalFrame.fananalysis.FanAnalysis;
import internalFrame.fananalysis.GetFanList;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import login.OauthLogin;

public class Fananalysis extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5483086044895133821L;
	
	public Fananalysis() {
		super();
		setIconifiable(true);
		setClosable(true);
		setSize( 491, 287);
		setMaximizable(true);
		
		//Client ol=new Client();
		//String name=ol.getLoginclient();
		//System.out.println(name);
		//setTitle(name);
		setTitle("粉丝分析");
		
		JTabbedPane tabPane = new JTabbedPane();
		final GetFanList gslPanel = new GetFanList();
		final FanAnalysis rfPanel = new FanAnalysis();
		
		tabPane.addTab("获取粉丝列表", null, gslPanel, "获取粉丝列表");
		tabPane.addTab("粉丝信息分析", null, rfPanel, "粉丝信息分析");
		
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


