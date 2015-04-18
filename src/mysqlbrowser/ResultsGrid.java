package mysqlbrowser;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import mysqlServer.DBHandler;

/**
 * Provides a table for displaying the results of a query
 * 
 * @author Quake
 *
 */
public class ResultsGrid {

	String databaseName;
	String query;

	JTable resultsTable = new JTable();

	/**
	 * Sets the database from which query was obtained
	 * 
	 * @param databaseName
	 */
	public void setDatabase(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * Sets the query which generated the data
	 * 
	 * @param query
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Populates the <code>resultsTable</code> with the results of the query
	 * 
	 * @param tableRes
	 *            A two-dimensional <code>Object</code> array where row 3
	 *            contains the column names and the remaining rows are the data
	 *            fetched
	 */
	public void showQueryResults(Object[][] tableRes) {
		resultsTable.setModel(new ResTableModel(tableRes));
	}

	private static class ResTableModel extends AbstractTableModel {
		private static final long serialVersionUID = -5305738650939251365L;

		private Object[][] data;

		public ResTableModel(Object[][] dataTable) {
			data = dataTable;
		}

		@Override
		public int getColumnCount() {
			return data[2].length;
		}

		@Override
		public int getRowCount() {
			return data.length - 3;
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row + 3][col];
		}

		public String getColumnName(int col) {
			return data[2][col].toString();
		}

		/*
		 * public Class getColumnClass(int c) { return getValueAt(0,
		 * c).getClass(); }
		 */

		public boolean isCellEditable(int row, int col) {
			return true;
		}

		public void setValueAt(Object value, int row, int col) {
			data[row + 3][col] = value;
			fireTableCellUpdated(row + 3, col);
		}

	}

	/**
	 * Returns the table that displays the data
	 * 
	 * @return <code>resultsTable</code>
	 */
	public JTable getResults() {
		return resultsTable;
	}

	public ResultsGrid() {
		// TODO Auto-generated constructor stub
	}

}
