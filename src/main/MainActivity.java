/**
 * 
 */
package main;

import mysqlbrowser.browser;
import mysqlbrowser.setFrame;
import userInterface.login.LoginUI;

/**
 * Brings up the login interface and opens up main JMyAdmin window if login is
 * successful
 * 
 * @author Gifty Buah
 *
 */
public class MainActivity {

	public static void main(String[] args) {
		// 1. Create dummy JFrame to set location and size of Login dialog
		setFrame dummy = new setFrame();
		// 2. Open Login interface
		LoginUI dbLogin = new LoginUI(dummy);
		// 3. If login is successful open JMyAdmin
		if (dbLogin.isConnected()) {
			browser newBrowser = new browser();
		} else {
			// Terminate application
			System.exit(0);
		}
	}

}
