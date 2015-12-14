package statisticsModel;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;

import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

public   class   TextComponentPopupMenu   extends   JPopupMenu   implements   
                MouseListener,   ActionListener   {   
        private   static   TextComponentPopupMenu   sharedInstance   =   null;   

        public   static   void   installToComponent(JTextComponent   c)   {   
                if   (c   instanceof   JTextField   &&   !(c   instanceof   JPasswordField))   {   
                        c.addMouseListener(TextComponentPopupMenu.getSharedInstance());   
                }   
        }   

        public   static   void   uninstallFromComponent(JTextComponent   c)   {   
                if   (c   instanceof   JTextField   &&   !(c   instanceof   JPasswordField))   {   
                        c.removeMouseListener(getSharedInstance());   
                }   
        }   

        JMenuItem   cutItem,   copyItem,   pasteItem,   deleteItem,   selectAllItem,printItem;   

        public   TextComponentPopupMenu()   {   
                add(cutItem   =   new   JMenuItem("����   (T)"));   
                add(copyItem   =   new   JMenuItem("����   (C)"));   
                add(pasteItem   =   new   JMenuItem("ճ��   (P)"));   
                add(deleteItem   =   new   JMenuItem("ɾ��   (D)"));   
                addSeparator();   
                add(selectAllItem   =   new   JMenuItem("ȫѡ   (A)"));   
                addSeparator(); 
     add(printItem   =   new   JMenuItem("��ӡ   (R)"));
    
                cutItem.setMnemonic('T');   
                copyItem.setMnemonic('C');   
                pasteItem.setMnemonic('P');   
                deleteItem.setMnemonic('D');   
                selectAllItem.setMnemonic('A');   
                printItem.setMnemonic('R');
                
                cutItem.addActionListener(this);   
                copyItem.addActionListener(this);   
                pasteItem.addActionListener(this);   
                deleteItem.addActionListener(this);   
                selectAllItem.addActionListener(this);   
                printItem.addActionListener(this);
        }   

        public static   TextComponentPopupMenu   getSharedInstance()   {                  //����ģʽ
                if   (sharedInstance   ==   null)   {   
                        sharedInstance   =   new   TextComponentPopupMenu();   
                }   
                return   sharedInstance;   
        }   

        public   void   mouseReleased(MouseEvent   e)   {   
                if   (e.isPopupTrigger()   &&   e.getSource()   instanceof   JTextField)   {       //e.isPopupTrigger()����Ҽ� 
                        JTextField   textfield   =   (JTextField)   e.getSource();   
                        if   (Boolean.TRUE.equals(textfield   
                                        .getClientProperty("DisablePopupMenu")))   {   
                                return;   
                        }   
                        textfield.requestFocusInWindow();   
                       this.show(textfield,   e.getX(),   e.getY());      //��������λ��
                }   
        }   

        public   void   mouseClicked(MouseEvent   e)   {   
        }   

        public   void   mousePressed(MouseEvent   e)   {   
        }   

        public   void   mouseEntered(MouseEvent   e)   {   
        }   

        public   void   mouseExited(MouseEvent   e)   {   
        }   

        public   void   show(Component   invoker,   int   x,   int   y)   {   
                JTextComponent   tc   =   (JTextComponent)   invoker;   
                String   sel   =   tc.getSelectedText();   
          

                boolean   selected   =   sel   !=   null   &&   !sel.equals("");   
                boolean   enableAndEditable   =   tc.isEnabled()   &&   tc.isEditable();   
                //����������
                cutItem.setEnabled(selected   &&   enableAndEditable);   
                copyItem.setEnabled(selected   &&   tc.isEnabled());   
                deleteItem.setEnabled(selected   &&   enableAndEditable);   
                pasteItem.setEnabled(enableAndEditable);   
                selectAllItem.setEnabled(tc.isEnabled());   

                super.show(invoker,   x,   y);   
        }   

        public   void   actionPerformed(ActionEvent   e)   {   
                JTextComponent   tc   =   (JTextComponent)   getInvoker();   
                String   sel   =   tc.getSelectedText();   
               

                if   (e.getSource()   ==   cutItem)   {   
                        tc.cut();   
                }   else   if   (e.getSource()   ==   copyItem)   {   
                        tc.copy();   
                }   else   if   (e.getSource()   ==   pasteItem)   {   
                        tc.paste();   
                }   else   if   (e.getSource()   ==   selectAllItem)   {   
                        tc.selectAll();   
                }   else   if   (e.getSource()   ==   deleteItem)   {   
                        Document   doc   =   tc.getDocument();   
                        int   start   =   tc.getSelectionStart();   
                        int   end   =   tc.getSelectionEnd();   

                        try   {   
                                Position   p0   =   doc.createPosition(start);   
                                Position   p1   =   doc.createPosition(end);   

                                if   ((p0   !=   null)   &&   (p1   !=   null)   
                                                &&   (p0.getOffset()   !=   p1.getOffset()))   {   
                                        doc.remove(p0.getOffset(),   p1.getOffset()   -   p0.getOffset());   
                                }   
                        }   catch   (BadLocationException   be)   {   
                        }   
                } 
                if(e.getSource() == printItem ){
                   try {
        tc.print();
       } catch (PrinterException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
       }
                  
                }
        }   
          
        
}
