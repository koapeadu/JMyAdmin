/**
 * 
 */
package mysqlbrowser;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Gifty Buah
 *
 */
public class CreateDatabaseInterface extends JFrame{

	/**
	 * @param args
	 */
	JPanel databaseInterface =new JPanel(new BorderLayout());
	JTextField databaseNameField = new JTextField();
	JLabel databaseNameLabel = new JLabel("Database Name:");
	

		public JPanel setUpInterface(){
		databaseInterface.add(databaseNameLabel, BorderLayout.WEST);
		databaseInterface.add(databaseNameField, BorderLayout.EAST);
		return databaseInterface;
	}

}
