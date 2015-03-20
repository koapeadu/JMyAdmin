 /**
 * 
 */
package mysqlbrowser;
import javax.swing.*;

import java.awt.*;
/**
 * @author Gifty Buah
 *
 */
public class browser extends setFrame {

	/**
	 * @param args
	 */
	BorderLayout mainLayout = new BorderLayout();
	 JPanel mainPanel =new JPanel();
	 JPanel leftUpperPanel =new JPanel();
	 JPanel dbAndTables =new JPanel();
	 JPanel workspace =new JPanel();
	 JMenuBar MenuBar=new JMenuBar();
	 JToolBar ToolBar=new JToolBar(SwingConstants.HORIZONTAL);
	 JMenu File = new JMenu("File");
	 JMenu View = new JMenu("View");
	 JMenu Edit = new JMenu("Edit");
	 JMenu Help = new JMenu("Help");
	 JMenuItem newfile =new JMenuItem("new");
	 JMenuItem open =new JMenuItem("open");
	 JMenuItem save =new JMenuItem("save");
	 JMenuItem exit =new JMenuItem("exit");
	 JMenuItem undo =new JMenuItem("undo");
	 JMenuItem redo =new JMenuItem("redo");
	 JMenuItem formatCode =new JMenuItem("Format Code");
	 JMenuItem programHelp =new JMenuItem("programHelp");
	 JMenuItem about =new JMenuItem("About");
	// JButton[] toolBarIcons = new JButton[4];
	 
	 
	 //Constructor
	public browser(){
		super();
		setJMenuBar(MenuBar);
		setInterface();
		
		mainPanel.setLayout(mainLayout);
		setContentPane(mainPanel);
		setVisible(true);
		
	}
	
	//setInterface creates the window
	public void setInterface(){
		File.add(newfile);
		File.add(open);
		File.add(save);
		File.add(exit);
		Edit.add(undo);
		Edit.add(redo);
		Edit.add(formatCode);
		Help.add(programHelp);
		Help.add(about);
		MenuBar.add(File);
		MenuBar.add(View);
		MenuBar.add(Edit);
		MenuBar.add(Help);
	
	
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		browser newBrowser =new browser();

	}

}
