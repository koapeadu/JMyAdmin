/**
 * 
 */
package mysqlbrowser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

/**
 * @author Gifty Buah
 *
 */
public class browser extends setFrame implements WindowListener {
	private static final long serialVersionUID = 1L;

	BorderLayout mainLayout = new BorderLayout();
	toolBarActions newtoolBarAction = new toolBarActions();
	JPanel mainPanel = new JPanel();
	JPanel belowMainPanel = new JPanel();
	JPanel leftmainPanel = new JPanel();
	JPanel rightmainPanel = new JPanel();
	JScrollPane leftUpperPanel = new JScrollPane(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JPanel leftLowerPanel = new JPanel();
	JPanel rightLowerPanel = new JPanel();
	JPanel rightUpperPanel = new JPanel();
	static JTabbedPane rightTabbedPane = new JTabbedPane();
	//SqlSplitPane sqlSplit = new SqlSplitPane();
	SqlTabbedPane sqlPanel = new SqlTabbedPane();
	ArrayList<JPanel> sqlDocuments = new ArrayList<JPanel>();
	static JScrollPane propertiesPanel = new JScrollPane(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);;
	JPanel importPanel = new JPanel();
	//JPanel exportPanel = new JPanel();
	ExportTab exportTab = new ExportTab();
	JPanel goPanel = new JPanel();

	JToolBar ToolBar = new JToolBar();

	JButton goButton = new JButton("Go");

	static databaseTree displayTree = new databaseTree();
	
	public static databaseTree getDisplayTree(){
		return displayTree;
	}public static JScrollPane getPropertiesPanel(){
		return propertiesPanel;
		
	}
	
	public static JTabbedPane getRightTabbedPane(){
		return rightTabbedPane;
	}

	// Constructor
	public browser() {
		super();
		setJMenuBar(newtoolBarAction.getMenuBar());
		mainPanel.setLayout(mainLayout); // No Layout for a tabbed pane
		rightmainPanel.setLayout(new BorderLayout());
		rightUpperPanel.setLayout(new BorderLayout());
		leftmainPanel.setLayout(new BorderLayout());
		goPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		addWindowListener(this);

		setContentPane(mainPanel);
		setInterface();
		setVisible(true);

	}

	// setInterface creates the window
	public void setInterface() {

		goButton.setBackground(Color.GREEN);
		goButton.setPreferredSize(new Dimension(50, 30));

		ToolBar = newtoolBarAction.getToolBar();
		mainPanel.add(ToolBar, BorderLayout.PAGE_START);

		horizonSplitPane(leftUpperPanel, leftLowerPanel, leftmainPanel, 400);
		//horizonSplitPane(rightUpperPanel, rightLowerPanel, rightmainPanel, 300);
		vertSplitPane(leftmainPanel, rightmainPanel, mainPanel, 240);

		goPanel.add(goButton);

		propertiesTabHandler displayProperties = new propertiesTabHandler();
		propertiesPanel.setViewportView(displayProperties.getProperties());

		rightTabbedPane.addTab("SQL", null, SqlTabbedPane.getTabbedPane(),
				"SQL Query Tab");
		rightTabbedPane.addTab("Properties", null, propertiesPanel,
				"Properties Tab");
		rightTabbedPane.addTab("Import", null, importPanel, "Import Tab");
		rightTabbedPane.addTab("Export", null, exportTab.getPanel(), "Export Tab");

		rightmainPanel.add(rightTabbedPane, BorderLayout.CENTER);

		leftUpperPanel.setViewportView(displayTree.getDatabaseTree());// add(displayTree.getDatabaseTree());
	}

	public void vertSplitPane(JPanel panel1, JPanel panel2, JPanel addPane,
			int minsize) {
		panel1.setMinimumSize(new Dimension(minsize, 250));
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true, panel1, panel2);
		splitPane.setResizeWeight(0.2);
		splitPane.setMinimumSize(new Dimension(270, 400));
		splitPane.setContinuousLayout(true);

		splitPane.setOneTouchExpandable(true);
		addPane.add(splitPane);

	}


	public void horizonSplitPane(JScrollPane panel1, JPanel panel2,
			JPanel addPane, int minsize) {
		panel1.setMinimumSize(new Dimension(170, minsize));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
				panel1, panel2);
		
		splitPane.setResizeWeight(0.2);
		splitPane.setOneTouchExpandable(true);
		addPane.add(splitPane);
		splitPane.setContinuousLayout(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		if (SqlTabbedPane.saveAllTabsOnWindowClose()) {
			System.exit(0);
		}
		else {
		
		}

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	

}
