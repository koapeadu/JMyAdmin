/**
 * 
 */
package mysqlbrowser;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import mysqlServer.DBHandler;

/**
 * @author Gifty Buah
 *
 */
public class propertiesTabHandler {
	static String databaseName;
	static String tableName;

	static JTable descTable = new JTable();

	public static void setDatabase(String databaseName) {
		propertiesTabHandler.databaseName = databaseName;
	}

	public static void setTable(String tableName) {
		propertiesTabHandler.tableName = tableName;
	}

	public static void showTableProperties() {
		Thread propertiesThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// fetch table description from server
				Object[][] tableDesc = DBHandler.decsribeTable(databaseName,
						tableName);
				if (tableDesc.length != 0) {
					descTable.setModel(new DescTableModel(tableDesc));
				}
			}

		});
		propertiesThread.start();
	}

	private static class DescTableModel extends AbstractTableModel {
		private static final long serialVersionUID = -5305738650939251365L;

		private Object[][] data;

		public DescTableModel(Object[][] dataTable) {
			data = dataTable;
		}

		@Override
		public int getColumnCount() {
			return data[0].length;
		}

		@Override
		public int getRowCount() {
			return data.length - 1;
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row + 1][col];
		}

		public String getColumnName(int col) {
			return data[0][col].toString();
		}

		/*
		 * public Class getColumnClass(int c) { return getValueAt(0,
		 * c).getClass(); }
		 */

		public boolean isCellEditable(int row, int col) {
			return true;
		}

		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

	}

	public JTable getProperties() {
		return descTable;
	}

	/*
	 * public void createpropertiesTable(ArrayList<ArrayList<String>>
	 * properties){ String[] columnNames={" "}; DefaultTableModel tableModel =
	 * new DefaultTableModel(columnNames, 0); JTable table= new
	 * JTable(tableModel); for(int j=0;j<properties.size();j++){ //String[]
	 * array =properties.get(j); //tableModel.addRow(); }
	 * 
	 * }
	 */
	/*
	 * @Override public void stateChanged(ChangeEvent e) { // TODO
	 * Auto-generated method stub
	 * 
	 * JTabbedPane tabSource = (JTabbedPane) e.getSource(); //get the tab which
	 * has been selected //An array to hold the properties of the table selected
	 * // ArrayList<ArrayList<String>> properties = new
	 * ArrayList<ArrayList<String>>();
	 * 
	 * String tab = tabSource.getTitleAt(tabSource.getSelectedIndex());
	 * 
	 * if (tab.equals("propertiesPanel")) { //get the focused database or table
	 * 
	 * TreePath selectedPath = showdatabaseTree.getSelectionPath();
	 * DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
	 * selectedPath .getLastPathComponent(); DefaultMutableTreeNode tableParent=
	 * (DefaultMutableTreeNode) selectedNode.getParent(); String
	 * stringSelectedNode =selectedNode.toString(); String stringtableParent
	 * =tableParent.toString();
	 * 
	 * DBHandler.decsribeTable(stringSelectedNode,stringtableParent);
	 * 
	 * } }
	 */

}
