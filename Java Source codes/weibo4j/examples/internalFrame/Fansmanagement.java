package internalFrame;

import internalFrame.fansmanagement.FriendCreate;
import internalFrame.fansmanagement.FriendDestroy;
import internalFrame.fansmanagement.FriendSelect;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

public class Fansmanagement extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5483086044895133821L;
	
	public Fansmanagement() {
		super();
		setIconifiable(true);
		setClosable(true);
		setSize( 491, 287);
		setMaximizable(true);
		setTitle("互粉管理");
		JTabbedPane tabPane = new JTabbedPane();
		final FriendSelect fsPanel = new FriendSelect();
		final FriendCreate fcPanel = new FriendCreate();
		final FriendDestroy fdPanel = new FriendDestroy();
				
		tabPane.addTab("搜索目标粉丝", null, fsPanel, "搜索目标粉丝");
		tabPane.addTab("确定关注对象", null, fcPanel, "确定关注对象");
		tabPane.addTab("取消关注对象", null, fdPanel, "取消关注对象");
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
