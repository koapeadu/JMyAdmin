/**
 * 
 */
package mysqlbrowser;

import java.awt.Dimension;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Gifty Buah
 *
 */

public class toolBarActions  implements ActionListener{

	/**
	 * @param args
	 */
	String fileLocation;
	String contentsOfFile="";
	BufferedWriter input;
	JMenu File = new JMenu("File");
	JMenu View = new JMenu("View");
	JMenu Edit = new JMenu("Edit");
	JMenu Help = new JMenu("Help");
	JMenuItem newfile = new JMenuItem("new");
	JMenuItem open = new JMenuItem("open");
	JMenuItem save = new JMenuItem("save");
	JMenuItem exit = new JMenuItem("exit");
	JMenuItem undo = new JMenuItem("undo");
	JMenuItem redo = new JMenuItem("redo");
	static JToolBar ToolBar = new JToolBar();
	JMenuBar MenuBar = new JMenuBar();
	JMenuItem formatCode = new JMenuItem("Format Code");
	JMenuItem programHelp = new JMenuItem("programHelp");
	JMenuItem about = new JMenuItem("About");
	static JTextArea workspaceArea = new JTextArea(10, 60);
	JButton[] toolBarIcons = new JButton[4];
	
	
	public toolBarActions(){
		
	
		
		setInterfaceOfToolbar();
		open.addActionListener(this);
		save.addActionListener(this);
		toolBarIcons[0].addActionListener(this);
		toolBarIcons[2].addActionListener(this);
	
		//creating shortcuts for some menu items
		open.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK ) );
		save.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK ) );
		exit.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, ActionEvent.CTRL_MASK ) );
		undo.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Z, ActionEvent.CTRL_MASK ) );
		redo.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_R, ActionEvent.CTRL_MASK ) );
		newfile.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK ) );
		
		
	
	}
	
	public JMenuBar getMenuBar(){
		return MenuBar;
	}
	
	public  JToolBar getToolBar(){
		return ToolBar;
	}
	
	public  JTextArea getTextArea(){
		return workspaceArea;
	}
	
	 public void setInterfaceOfToolbar(){
				 
			ImageIcon openIcon = new ImageIcon(getClass().getResource(
					"/resources/open.jpg"));
			ImageIcon runIcon = new ImageIcon(getClass().getResource(
					"/resources/runicon2.jpg"));
			ImageIcon saveIcon = new ImageIcon(getClass().getResource(
					"/resources/saveicon.jpg"));
			ImageIcon stopIcon = new ImageIcon(getClass().getResource(
					"/resources/stopicon.jpg"));
			ImageIcon[] iconArray = { openIcon, runIcon, saveIcon, stopIcon };
			String[] toolBarString = { "open", "run", "save", "stop" };
			ToolBar.setPreferredSize(new Dimension(150, 20));

			for (int i = 0; i < 4; i++) {

				toolBarIcons[i] = new JButton(iconArray[i]);
				toolBarIcons[i].setToolTipText(toolBarString[i]);
				ToolBar.add(toolBarIcons[i]);

			}
		 
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
		toolBarActions newtoolBarAction =new toolBarActions();

	}

	 private Path getOpenFilePath(JComponent open){
		 
		 JFileChooser fileChooser = new JFileChooser();
		 FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "sql");
		 fileChooser.addChoosableFileFilter(filter);
		 fileChooser.setFileSelectionMode(
				 JFileChooser.FILES_ONLY);
				 int result = fileChooser.showOpenDialog(open);
				 
				 if(result==JFileChooser.CANCEL_OPTION){
					 System.exit(1);
					// fileChooser.showOpenDialog(open).
				 }
				 return fileChooser.getSelectedFile().toPath();
	 }
	 

	 
	
	  
	 private Path getSaveFilePath(JComponent save){
		 JFileChooser fileChooser = new JFileChooser();
		 fileChooser.setFileSelectionMode(
				 JFileChooser.FILES_ONLY);
				 int result = fileChooser.showSaveDialog(save);
				 
				 if(result==JFileChooser.CANCEL_OPTION){
					 System.exit(1);
				 }
				 return fileChooser.getSelectedFile().toPath();
	 } 


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Path filePath;
		if(e.getSource()==open ||e.getSource()==toolBarIcons[0]){
			filePath=getOpenFilePath((JComponent)e.getSource());
		
			fileLocation=filePath.toString();
			JOptionPane.showMessageDialog(null, fileLocation);
			try {
				BufferedReader output =new BufferedReader(new FileReader(fileLocation));
				try {
					//read the contents of the file
					String temp=output.readLine();
					while(temp!=null){
						contentsOfFile = contentsOfFile+temp+System.lineSeparator();
						temp=output.readLine();
						
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//create new sql tab with contents of file
				workspaceArea.setText(contentsOfFile);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}
	
		
		else if(e.getSource()==save||e.getSource()==toolBarIcons[2]){
			filePath=getSaveFilePath((JComponent)e.getSource());
		
			fileLocation=filePath.toString();
			//testing if this thing works at all
			JOptionPane.showMessageDialog(null, fileLocation);
			try {
				input = new BufferedWriter(new FileWriter(fileLocation));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				//read the contents of the Text Area
				contentsOfFile=workspaceArea.getText();
						input.write(contentsOfFile);
						input.close();			
				}
			 catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//create new sql tab with contents of file
			//workspaceArea.setText(contentsOfFile);
			
			
			
		}
		
		
	}

}
