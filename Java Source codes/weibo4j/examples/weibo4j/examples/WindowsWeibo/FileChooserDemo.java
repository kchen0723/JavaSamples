package weibo4j.examples.WindowsWeibo;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class FileChooserDemo extends JFrame implements ActionListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public FileChooserDemo() {
    setTitle("ZipTest");
    setSize(300, 400);

    JMenuBar mbar = new JMenuBar();
    JMenu m = new JMenu("File");
    openItem = new JMenuItem("Open");
    openItem.addActionListener(this);
    m.add(openItem);
    exitItem = new JMenuItem("Exit");
    exitItem.addActionListener(this);
    m.add(exitItem);
    mbar.add(m);

    Container contentPane = getContentPane();
    contentPane.add(mbar, "North");
  }

  public void actionPerformed(ActionEvent evt) {
    Object source = evt.getSource();
    if (source == openItem) {
      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));
      chooser.setFileFilter(new FileFilter() {
        public boolean accept(File f) {
          return f.getName().toLowerCase().endsWith(".xls")
              || f.isDirectory();
        }

        public String getDescription() {
          return "XLS Files";
        }
      });
      int r = chooser.showOpenDialog(this);
      if (r == JFileChooser.APPROVE_OPTION) {
        String zipname = chooser.getSelectedFile().getPath();
        if(zipname.indexOf(".xls")<0){
        	zipname=zipname+".xls";
        	System.out.println(zipname);
        }else if (JOptionPane.showConfirmDialog(new JFrame(),
                "确认覆盖文件？", "保存",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
        	System.out.println("1");
        	 System.out.println(zipname);
            }
       
      }
    } else if (source == exitItem)
      System.exit(0);
  }

  public static void main(String[] args) {
    Frame f = new FileChooserDemo();
    f.show();
  }

  private JMenuItem openItem;

  private JMenuItem exitItem;

}
