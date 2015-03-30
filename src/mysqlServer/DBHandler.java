/**
 * 
 */
package mysqlServer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The DBhandler class uses the connection resource {@code DBConnect.con} to
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
	 * @param db The name of the whose tables are of interest
	 * @return ArrayList<String> of names of tables in the database called {@code db}
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
}
