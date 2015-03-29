/**
 * 
 */
package mysqlbrowser;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Gifty Buah
 *
 */
public class browser extends setFrame {

	/**
	 * @param args
	 */
	BorderLayout mainLayout = new BorderLayout();
	JPanel mainPanel = new JPanel();
	JPanel belowMainPanel = new JPanel();
	JPanel leftmainPanel = new JPanel();
	JPanel rightmainPanel = new JPanel();
	JPanel leftUpperPanel = new JPanel();
	JPanel leftLowerPanel = new JPanel();
	JPanel rightLowerPanel = new JPanel();
	JPanel rightUpperPanel = new JPanel();
	JTabbedPane upWorkspaceLabel = new JTabbedPane();
	JTabbedPane sqlPanel = new JTabbedPane(JTabbedPane.BOTTOM,
			JTabbedPane.SCROLL_TAB_LAYOUT);
	ArrayList<JPanel> sqlDocuments = new ArrayList<JPanel>();
	JPanel propertiesPanel = new JPanel();
	JPanel importPanel = new JPanel();
	JPanel exportPanel = new JPanel();
	JPanel goPanel = new JPanel();
	// JPanel workspace =new JPanel();
	JMenuBar MenuBar = new JMenuBar();
	JToolBar ToolBar = new JToolBar();
	JTextArea workspaceArea = new JTextArea(10, 60);
	JScrollPane SworkspaceArea = new JScrollPane(workspaceArea,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
	JMenuItem formatCode = new JMenuItem("Format Code");
	JMenuItem programHelp = new JMenuItem("programHelp");
	JMenuItem about = new JMenuItem("About");
	JButton goButton = new JButton("Go");
	JButton[] toolBarIcons = new JButton[4];
	JButton[] upWorkspaceButtons = new JButton[4];

	// Constructor
	public browser() {
		super();
		setJMenuBar(MenuBar);
		mainPanel.setLayout(mainLayout);  //No Layout for a tabbed pane
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
		goButton.setBackground(Color.GREEN);
		goButton.setPreferredSize(new Dimension(50, 30));

		// ToolBar.setRollover(true);
		ToolBar.setPreferredSize(new Dimension(150, 20));

		for (int i = 0; i < 4; i++) {

			toolBarIcons[i] = new JButton(iconArray[i]);
			toolBarIcons[i].setToolTipText(toolBarString[i]);
			ToolBar.add(toolBarIcons[i]);

		}
		mainPanel.add(ToolBar, BorderLayout.PAGE_START);

		horizonSplitPane(leftUpperPanel, leftLowerPanel, leftmainPanel, 400);
		horizonSplitPane(rightUpperPanel, rightLowerPanel, rightmainPanel, 300);
		vertSplitPane(leftmainPanel, rightmainPanel, mainPanel, 240);

		goPanel.add(goButton);
		// setup default document
		sqlDocuments.add(new JPanel(new BorderLayout()));
		sqlDocuments.get(0).add(SworkspaceArea, BorderLayout.CENTER);
		sqlDocuments.get(0).add(goPanel, BorderLayout.SOUTH);

		sqlPanel.addTab("Untitled1", null, sqlDocuments.get(0), "doc");

		upWorkspaceLabel.addTab("SQL", null, sqlPanel, "SQL Query Tab");
		upWorkspaceLabel.addTab("Properties", null, propertiesPanel,
				"Properties Tab");
		upWorkspaceLabel.addTab("Import", null, importPanel, "Import Tab");
		upWorkspaceLabel.addTab("Export", null, exportPanel, "Export Tab");

		rightUpperPanel.add(upWorkspaceLabel, BorderLayout.NORTH);
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

	public void horizonSplitPane(JPanel panel1, JPanel panel2, JPanel addPane,
			int minsize) {
		panel1.setMinimumSize(new Dimension(170, minsize));
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
				panel1, panel2);

		splitPane.setOneTouchExpandable(true);
		addPane.add(splitPane);
		splitPane.setContinuousLayout(true);
	}

}
