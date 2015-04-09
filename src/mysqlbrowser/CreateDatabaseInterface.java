/**
 * 
 */
package mysqlbrowser;

import globals.AppConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.*;


/**
 * @author Gifty Buah
 *
 */
public class CreateDatabaseInterface extends JDialog{

	/**
	 * @param args
	 */
	JPanel databaseInterface =new JPanel(new FlowLayout(FlowLayout.LEFT,10,85));
	JTextField databaseNameField = new JTextField("Enter database Name");
	JLabel databaseNameLabel = new JLabel("Database Name:");
	

		public  CreateDatabaseInterface (JFrame parent){
			super(parent, "Create Database", true);

			// position dialog
			if (parent != null) {
				Point origin = parent.getLocation();
				setBounds(origin.x + parent.getWidth() / 4,
						origin.y + parent.getHeight() / 4, parent.getWidth() /4,
						parent.getHeight() * 3 / 8);
			}
			databaseNameField.setPreferredSize(new Dimension(getWidth()/2,
					getHeight() /10 ));
		databaseInterface.add(databaseNameLabel);
		databaseInterface.add(databaseNameField);
		
		setContentPane(databaseInterface);

		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
