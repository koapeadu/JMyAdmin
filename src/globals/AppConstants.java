/**
 * 
 */
package globals;

import java.awt.Toolkit;

/**
 * Holds the values for various constants in the application
 * 
 * @author Quake
 *
 */
public interface AppConstants {
	Double PROGRAM_VERSION = 1.0;
	String LOGIN_UI_TITLE = "Connect to MySQL Server";
	String PROGRAM_NAME = "MySQL Studio/J";
	String LOGIN_UI_MESSAGE = PROGRAM_NAME + " " + PROGRAM_VERSION;

	String DB_HOST = "jdbc:mysql://localhost:3306/";

	int PROGRAM_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize()
			.getWidth() * 3 / 4;
	int PROGRAM_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize()
			.getHeight() * 3 / 4;
}
