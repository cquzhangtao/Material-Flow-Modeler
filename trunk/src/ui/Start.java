package ui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Start {
	public static void main(String[] args) {
//		try {
//		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//		        if ("Nimbus".equals(info.getName())) {
//		            UIManager.setLookAndFeel(info.getClassName());
//		            break;
//		        }
//		    }
//		} catch (Exception e) {
//		    // If Nimbus is not available, you can set the GUI to another look and feel.
//		}

		MainFrame mainframe=new MainFrame();
		mainframe.setExtendedState( mainframe.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		mainframe.setVisible(true);
		
	}
}
