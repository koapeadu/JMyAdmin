/**
 * 
 */
package UI.browser;

import globals.SetFrame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import UI.browser.tabs.sqlTab.SqlTabbedPane;

/**
 * This class sets up the menu bar and the tool bar for the program
 * 
 * @author Gifty Buah
 * @author Quake
 */

public class ToolbarActions implements ActionListener {
	/**
	 * Menu bar
	 */
	JMenuBar menuBar = new JMenuBar();
	/**
	 * File menu
	 */
	JMenu file = new JMenu("File");
	JMenuItem newFile = new JMenuItem("New");
	JMenuItem open = new JMenuItem("Open");
	JMenuItem save = new JMenuItem("Save");
	JMenuItem saveAs = new JMenuItem("Save As");
	JMenuItem exit = new JMenuItem("Exit");
	

	/**
	 * View menu
	 */
	JMenu view = new JMenu("View");

	/**
	 * Edit Menu
	 */
	JMenu edit = new JMenu("Edit");
	JMenuItem undo = new JMenuItem("Undo");
	JMenuItem redo = new JMenuItem("Redo");
	JMenuItem formatCode = new JMenuItem("Format Code");

	/**
	 * Help Menu
	 */
	JMenu help = new JMenu("Help");
	JMenuItem programHelp = new JMenuItem("Program Help");
	JMenuItem about = new JMenuItem("About");

	/**
	 * Main tool bar
	 */
	static JToolBar toolBar = new JToolBar();
	JButton[] toolBarButtons = new JButton[6];

	/**
	 * Default constructor
	 * <p>
	 * Initializes the <code>toolBar</code> and the <code>menuBar</code>
	 */
	public ToolbarActions() {

		setInterfaceOfToolbar();
		// add action listeners
		newFile.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		saveAs.addActionListener(this);
		
		// creating shortcuts for some menu items
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.CTRL_MASK));
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.CTRL_MASK));
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));

	}

	/**
	 * Returns the <code>menuBar</code>
	 * 
	 * @return <code>toolBar</code>
	 */
	/*public void showDatabaseInterface(){ 
		JPanel CreateDatabaseInterfacePanel=new JPanel();
		CreateDatabaseInterface newObject =new CreateDatabaseInterface();
		CreateDatabaseInterfacePanel=newObject.setUpInterface();
		browser.getPropertiesPanel().setViewportView(CreateDatabaseInterfacePanel);
		 }*/
	 
	public JMenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * Returns the <code>toolBar</code>
	 * 
	 * @return <code>toolBar</code>
	 */
	public JToolBar getToolBar() {
		return toolBar;
	}

	/**
	 * Add all the image buttons to the <code>toolBar</code> and add the menus
	 * and menu items to the <code>menuBar</code>
	 */
	public void setInterfaceOfToolbar() {

		ImageIcon openIcon = new ImageIcon(getClass().getResource(
				"/resources/open.jpg"));
		ImageIcon runIcon = new ImageIcon(getClass().getResource(
				"/resources/runicon2.jpg"));
		ImageIcon saveIcon = new ImageIcon(getClass().getResource(
				"/resources/saveicon.jpg"));
		ImageIcon stopIcon = new ImageIcon(getClass().getResource(
				"/resources/stopicon.jpg"));
		ImageIcon createDatabaseIcon = new ImageIcon(getClass().getResource(
				"/resources/createDatabaseIcon.jpeg"));
		ImageIcon createTableIcon = new ImageIcon(getClass().getResource(
				"/resources/createTableIcon.jpeg"));
		ImageIcon[] iconArray = { openIcon, runIcon, saveIcon, stopIcon,createDatabaseIcon,createTableIcon };
		String[] toolBarString = { "Open", "Run", "Save", "Stop","Create database","Create table" };
		toolBar.setPreferredSize(new Dimension(150, 30));

		for (int i = 0; i < 6; i++) {

			toolBarButtons[i] = new JButton(iconArray[i]);
			toolBarButtons[i].setToolTipText(toolBarString[i]);
			toolBarButtons[i].addActionListener(this);
			toolBar.add(toolBarButtons[i]);

		}

		file.add(newFile);
		file.add(open);
		file.add(save);
		file.add(exit);
		edit.add(undo);
		edit.add(redo);
		edit.add(formatCode);
		help.add(programHelp);
		help.add(about);
		menuBar.add(file);
		menuBar.add(view);
		menuBar.add(edit);
		menuBar.add(help);
	}

	/**
	 * Event handler for the <code>toolBar</code> and the <code>menuBar</code>
	 * <ul>
	 * <li>Create a new blank document if <code>newFile</code> or the new
	 * toolbar button is selected
	 * <li>Open a new document if <code>open</code> or the open toolbar button
	 * is selected
	 * <li>Save a document if <code>save</code> or the the save toolbar button
	 * is selected
	 * <li>Save a document as an SQL file if <code>saveAs</code> is selected
	 * </ul>
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == open || e.getSource() == toolBarButtons[0]) {
			SqlTabbedPane.createFileDocument();
		} else if (e.getSource() == save || e.getSource() == toolBarButtons[2]) {
			SqlTabbedPane.saveDocument();
		} else if (e.getSource() == newFile) {
			SqlTabbedPane.createBlankDocument();
		} else if (e.getSource() == saveAs) {
			SqlTabbedPane.saveDocumentAs();
		}  else if (e.getSource() == toolBarButtons[4]) {
			//Create dummy JFrame for the CreateDatabaseInterface constructor
			SetFrame dummy = new SetFrame();
			//open the create database interface
			CreateDatabaseInterface newDatabaseInterface =new CreateDatabaseInterface(dummy);
			
		}  else if (e.getSource() == toolBarButtons[5]) {
			//SqlTabbedPane.saveDocumentAs();
			SetFrame dummy = new SetFrame();
			CreateTableInterface newTableInterface =new CreateTableInterface(dummy);
		} else if (e.getSource() == toolBarButtons[1]){
			SqlTabbedPane.executeCurrent();
		}
		

	}
	
	
}
