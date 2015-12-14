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
		setTitle("���۹���");
		JTabbedPane tabPane = new JTabbedPane();
		final FriendSelect fsPanel = new FriendSelect();
		final FriendCreate fcPanel = new FriendCreate();
		final FriendDestroy fdPanel = new FriendDestroy();
				
		tabPane.addTab("����Ŀ���˿", null, fsPanel, "����Ŀ���˿");
		tabPane.addTab("ȷ����ע����", null, fcPanel, "ȷ����ע����");
		tabPane.addTab("ȡ����ע����", null, fdPanel, "ȡ����ע����");
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
