package weibo4j.examples.WindowsWeibo;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OptionDialog {

  public static void main(String argv[]) {
    if (JOptionPane.showConfirmDialog(new JFrame(),
        "Do you want to quit this application ?", "Title",
        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
    	System.out.println("1");
    }
      System.exit(0);

  }
}