/**
 * 
 */
package mysqlbrowser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
public class browser extends setFrame {
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
	JTabbedPane upWorkspaceLabel = new JTabbedPane();
	//SqlSplitPane sqlSplit = new SqlSplitPane();
	SqlTabbedPane sqlPanel = new SqlTabbedPane();
	ArrayList<JPanel> sqlDocuments = new ArrayList<JPanel>();
	JScrollPane propertiesPanel = new JScrollPane(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);;
	JPanel importPanel = new JPanel();
	JPanel exportPanel = new JPanel();
	JPanel goPanel = new JPanel();

	JToolBar ToolBar = new JToolBar();

	JButton goButton = new JButton("Go");

	static databaseTree displayTree = new databaseTree();
	
	public static databaseTree getDisplayTree(){
		return displayTree;
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

		upWorkspaceLabel.addTab("SQL", null, SqlTabbedPane.getTabbedPane(),
				"SQL Query Tab");
		upWorkspaceLabel.addTab("Properties", null, propertiesPanel,
				"Properties Tab");
		upWorkspaceLabel.addTab("Import", null, importPanel, "Import Tab");
		upWorkspaceLabel.addTab("Export", null, exportPanel, "Export Tab");

		rightmainPanel.add(upWorkspaceLabel, BorderLayout.CENTER);

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

}
