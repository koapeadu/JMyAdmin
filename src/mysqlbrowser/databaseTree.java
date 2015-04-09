/**
 * 
 */
package mysqlbrowser;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.crypto.Data;

import mysqlServer.DBHandler;

/**
 * @author Gifty Buah
 *
 */
public class databaseTree implements TreeWillExpandListener,
		TreeExpansionListener, TreeSelectionListener {

	JTree showdatabaseTree = new JTree();

	private JPopupMenu popupMenu = new JPopupMenu();

	private ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();

	private String rightClickedDB;
	private String rightClickedTable;

	public void showdatabases() {
		Thread showdatabaseThread = new Thread(new Runnable() {

			@Override
			public void run() {
				ArrayList<String> databaseNames = new ArrayList<String>();

				// populate databaseNames with list of databases from the server
				databaseNames = DBHandler.showDatabases();

				DefaultMutableTreeNode top = new DefaultMutableTreeNode(
						"Databases");
				createNodes(top, databaseNames);

				showdatabaseTree.setModel(new DefaultTreeModel(top));
			}

		});
		showdatabaseThread.start();

	}

	public void createNodes(DefaultMutableTreeNode top,
			ArrayList<String> databaseNames) {
		DefaultMutableTreeNode databases = null;
		for (String dbName : databaseNames) {
			databases = new DefaultMutableTreeNode(dbName) {
				@Override
				public boolean isLeaf() {
					// TODO Auto-generated method stub
					return false;
				}
			};
			databases.add(new DefaultMutableTreeNode("No Tables"));
			top.add(databases);
		}

	}

	public JTree getDatabaseTree() {
		showdatabases();
		showdatabaseTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		showdatabaseTree.addTreeExpansionListener(this);
		showdatabaseTree.addTreeWillExpandListener(this);
		showdatabaseTree.addTreeSelectionListener(this);
		// showdatabaseTree.addMouseListener(this);
		addPopupMenu();
		return showdatabaseTree;
	}

	String[] tableItems = new String[] { "New Table", "Show first 200 rows",
			"Edit table", "Delete table", "Properties" };
	String[] databaseItems = new String[] { "New database", "Delete database",
			"Properties" };

	private void addPopupMenu() {
		// table items
		for (String string : tableItems) {
			menuItems.add(new JMenuItem(string));
		}
		// database items
		for (String string : databaseItems) {
			menuItems.add(new JMenuItem(string));
		}

		// add all to button group
		ButtonGroup optionsGroup = new ButtonGroup();
		for (JMenuItem menuItem : menuItems) {
			optionsGroup.add(menuItem);
		}

		// add action listeners for menu items
		TableMenuActionListener listen1 = new TableMenuActionListener();
		for (int i = 0; i < tableItems.length; i++) {
			menuItems.get(i).addActionListener(listen1);
		}

		DatabaseMenuActionListener listen2 = new DatabaseMenuActionListener();
		for (int i = tableItems.length; i < menuItems.size(); i++) {
			menuItems.get(i).addActionListener(listen2);
		}

		// add mouse listener
		showdatabaseTree.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
					int level = ((DefaultMutableTreeNode) ((JTree) e
							.getSource())
							.getPathForLocation(e.getX(), e.getY())
							.getLastPathComponent()).getLevel();
					if (level == 2) {
						rightClickedTable = ((DefaultMutableTreeNode) ((JTree) e
								.getSource()).getPathForLocation(e.getX(),
								e.getY()).getLastPathComponent())
								.getUserObject().toString();
						rightClickedDB = ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) ((JTree) e
								.getSource()).getPathForLocation(e.getX(),
								e.getY()).getLastPathComponent()).getParent())
								.getUserObject().toString();
						popupMenu.removeAll();
						for (int i = 0; i < tableItems.length; i++) {
							popupMenu.add(menuItems.get(i));
						}
					} else if (level == 1) {
						rightClickedDB = ((DefaultMutableTreeNode) ((JTree) e
								.getSource()).getPathForLocation(e.getX(),
								e.getY()).getLastPathComponent())
								.getUserObject().toString();
						popupMenu.removeAll();
						for (int i = tableItems.length; i < menuItems.size(); i++) {
							popupMenu.add(menuItems.get(i));
						}
					}
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	protected void addTableMenuItems() {

	}

	protected void addDatabaseMenuItems() {

	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent arg0)
			throws ExpandVetoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
		TreePath selectedPath = e.getPath();
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath
				.getLastPathComponent();

		showTables(selectedNode);
	}

	private void showTables(DefaultMutableTreeNode selectedNode) {
		if (selectedNode.getLevel() == 0) {

		} else if (selectedNode.getLevel() == 1) {

			Thread getTablesThread = new Thread(new Runnable() {

				@Override
				public void run() {
					String selectedDB = (String) selectedNode.getUserObject();

					ArrayList<String> tableNames = new ArrayList<String>();

					// populate tablesNames with names of tables from selected
					// database
					tableNames = DBHandler.showTables(selectedDB);
					if (tableNames.size() != 0) {
						selectedNode.removeAllChildren();
						for (String tableName : tableNames) {
							selectedNode.add(new DefaultMutableTreeNode(
									tableName));
						}
					}
					showdatabaseTree.updateUI();
				}
			});
			getTablesThread.start();
		}

	}

	@Override
	public void treeCollapsed(TreeExpansionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void treeExpanded(TreeExpansionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) showdatabaseTree
				.getLastSelectedPathComponent();

		/* if nothing is selected */
		if (node == null) {
			return;
		}

		switch (node.getLevel()) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			/* retrieve the name of table that was selected and its database */
			String tableName = node.getUserObject().toString();
			String databaseName = ((DefaultMutableTreeNode) node.getParent())
					.getUserObject().toString();
			/* display the description of the table in the properties tab */
			displayTableProperties(databaseName, tableName);
			break;
		}
	}

	private void displayTableProperties(String databaseName, String tableName) {
		propertiesTabHandler.setDatabase(databaseName);
		propertiesTabHandler.setTable(tableName);
		propertiesTabHandler.showTableProperties();
	}

	public String getSelectedDatabase() {
		String db = null;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) showdatabaseTree
				.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		switch (node.getLevel()) {
		case 0:
			break;
		case 1:
			db = node.getUserObject().toString();
			break;
		case 2:
			db = ((DefaultMutableTreeNode) node.getParent()).getUserObject()
					.toString();
			break;
		}
		return db;

	}

	private class TableMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// create new table
			if (((JMenuItem) e.getSource()).getText().equals(tableItems[0])) {

			}
			// show first 200 rows
			else if (((JMenuItem) e.getSource()).getText()
					.equals(tableItems[1])) {

			}
			// edit table
			else if (((JMenuItem) e.getSource()).getText()
					.equals(tableItems[2])) {

			}
			// delete table
			else if (((JMenuItem) e.getSource()).getText()
					.equals(tableItems[3])) {
				int response = JOptionPane
						.showConfirmDialog(
								null,
								"Do you want to drop "
										+ rightClickedDB
										+ "?\nWarning: This operation will delete all the data therein.",
								"Confirm", JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					Object[][] res = DBHandler.sqlQuery("DROP TABLE IF EXISTS "
							+ rightClickedDB + "." + rightClickedTable, null);
					if ((boolean) res[0][0] == true) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(showdatabaseTree,
								res[0][1], "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			// properties
			else if (((JMenuItem) e.getSource()).getText()
					.equals(tableItems[4])) {
				propertiesTabHandler.setDatabase(rightClickedDB);
				propertiesTabHandler.setTable(rightClickedTable);
				propertiesTabHandler.showTableProperties();
			}
		}
	}

	private class DatabaseMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// create new database
			if (((JMenuItem) e.getSource()).getText().equals(databaseItems[0])) {

			}
			// delete database
			else if (((JMenuItem) e.getSource()).getText().equals(
					databaseItems[1])) {
				int response = JOptionPane
						.showConfirmDialog(
								null,
								"Do you want to drop "
										+ rightClickedDB
										+ "?\nWarning: This operation will delete all tables in the database.",
								"Confirm", JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					Object[][] res = DBHandler.sqlQuery(
							"DROP DATABASE IF EXISTS " + rightClickedDB, null);
					if ((boolean) res[0][0] == true) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(showdatabaseTree,
								res[0][1], "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			// properties
			else if (((JMenuItem) e.getSource()).getText().equals(
					databaseItems[2])) {

			}
		}
	}

	public String getSelectedTable() {
		String table = null;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) showdatabaseTree
				.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		switch (node.getLevel()) {

		case 2:
			table = node.getUserObject().toString();
			break;

		}
		return table;
	}

}
