/**
 * 
 */
package mysqlServer;

import globals.AppConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Quake
 *
 */
public class DBConnect {
	private static String userName;
	private static String password;
	public static Connection con;
		
	public DBConnect(String userName, String password) {
		DBConnect.userName = userName;
		DBConnect.password = password;
	}
	
	public static boolean connect(){
		try {
			con = DriverManager.getConnection(AppConstants.DB_HOST, userName, password);
			if(con != null){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
