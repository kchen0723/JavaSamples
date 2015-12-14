package login;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
public class OauthPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 20111020;
	protected ImageIcon icon;
	public int width,height;
	public OauthPanel() {
		super();
		icon = new ImageIcon("res/login.jpg");
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		setSize(width, height);
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image img = icon.getImage();
		g.drawImage(img, 0, 0,getParent());
	}
}

