/**
 * 
 */
package mysqlbrowser;

import java.util.ArrayList;

import javax.swing.JTree;
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

import mysqlServer.DBHandler;

/**
 * @author Gifty Buah
 *
 */
public class databaseTree implements TreeWillExpandListener,
		TreeExpansionListener, TreeSelectionListener {

	JTree showdatabaseTree = new JTree();

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
			databases = new DefaultMutableTreeNode(dbName);
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
		return showdatabaseTree;
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

}
