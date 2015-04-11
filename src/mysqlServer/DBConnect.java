/**
 * 
 */
package mysqlServer;

import globals.AppConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Establishes a connection to the MySQL server using the given username and
 * password. If the connection is successful, the connection is saved; else the
 * error message is saved
 * 
 * @author Quake
 *
 */
public class DBConnect {
	private static String userName;
	private static String password;
	public static Connection con;
	public static String errorMessage;

	/**
	 * Constructor
	 * 
	 * @param userName
	 *            User name for current user. e.g. root
	 * @param password
	 *            Password for current user
	 */
	public DBConnect(String userName, String password) {
		DBConnect.userName = userName;
		DBConnect.password = password;
	}

	/**
	 * Attempts to connect to MySQL Database using {@code userName} and
	 * {@code password} as credentials, {@code AppConstants.DB_HOST} as the
	 * database host, and the DriverManager to incorporate the MySQL
	 * Connector/J. If successful the connection is saved in {@code con} else
	 * the error message is saved in {@code errorMessage}
	 * 
	 * @return true if connection was established successfully or false if the
	 *         connection failed
	 */
	public static boolean connect() {
		try {
			con = DriverManager.getConnection(AppConstants.DB_HOST, userName,
					password);
			if (con != null && con.isValid(3000)) {
				return true;
			}
		} catch (SQLException e) {
			errorMessage = e.getMessage();
			e.printStackTrace();
		}
		return false;
	}
}
