/**
 * 
 */
package mysqlServer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The DBHandler class uses the connection resource {@code DBConnect.con} to
 * perform queries to the database
 * 
 * @author Quake
 *
 */
public class DBHandler {
	/**
	 * Queries the server for a list of all databases
	 * 
	 * @return ArrayList<String> of names of the databases in the server
	 */
	public static ArrayList<String> showDatabases() {
		ArrayList<String> dbList = new ArrayList<String>();
		try {
			DBConnect.con.createStatement();
			ResultSet dbs = DBConnect.con.prepareStatement("SHOW DATABASES")
					.executeQuery();
			while (dbs.next()) {
				dbList.add(dbs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dbList;
	}

	/**
	 * Queries a particular database whose name is {@code db} for the list of
	 * all tables it contains
	 * 
	 * @param db
	 *            The name of the whose tables are of interest
	 * @return ArrayList<String> of names of tables in the database called
	 *         {@code db}
	 */
	public static ArrayList<String> showTables(String db) {
		ArrayList<String> tableList = new ArrayList<String>();
		try {
			DBConnect.con.createStatement();
			// use the database db
			DBConnect.con.setCatalog(db);
			ResultSet tbls = DBConnect.con.prepareStatement("SHOW TABLES")
					.executeQuery();
			while (tbls.next()) {
				tableList.add(tbls.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableList;
	}

	public static Object[][] decsribeTable(String db, String table) {
		Object[][] tableDesc = null;
		try {
			DBConnect.con.createStatement();
			// use the database db
			DBConnect.con.setCatalog(db);
			// prepare statement
			PreparedStatement descTableQuery = DBConnect.con
					.prepareStatement("DESCRIBE "+table);
			// execute query
			ResultSet tbls = descTableQuery.executeQuery();
			
			// get metadata
			ResultSetMetaData metaData = tbls.getMetaData();
			// get number of columns
			int numberOfColumns = metaData.getColumnCount();
			// get column names
			Object[] colNames = new Object[numberOfColumns];
			for(int i=1; i<=numberOfColumns; i++){
				colNames[i-1] = metaData.getColumnName(i);
			}
			// get number of rows in ResultSet
			tbls.last(); // move to last row
			int numberOfRows = tbls.getRow(); // get row number
			
			// populate table with results
			tableDesc = new Object[numberOfRows+1][];
			// Store column names as first row
			tableDesc[0] = colNames;
			// get and store data rows
			for (int i=1; i<=numberOfRows; i++) {
				tbls.absolute(i);
				Object[] row = new Object[numberOfColumns];
				for (int j = 1; j <= numberOfColumns; j++) {
					row[j-1] = tbls.getObject(j);
				}
				tableDesc[i] = row;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableDesc;
	}
}
