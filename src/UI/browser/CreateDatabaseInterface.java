/**
 * 
 */
package UI.browser;

import globals.AppConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import mysqlServer.DBHandler;

/**
 * @author Gifty Buah
 *
 */
public class CreateDatabaseInterface extends JDialog implements ActionListener {

	JPanel databaseInterface = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,
			45));
	JTextField databaseNameField = new JTextField();
	JLabel databaseNameLabel = new JLabel("Database Name:  ");
	JButton doneButton = new JButton("Done");

	public CreateDatabaseInterface(JFrame parent) {
		super(parent, "Create Database Wizard", true);

		// position dialog
		if (parent != null) {
			Point origin = parent.getLocation();
			setBounds(origin.x + parent.getWidth() / 4,
					origin.y + parent.getHeight() / 4, parent.getWidth() / 4,
					parent.getHeight() * 2 / 6);
		}
		databaseNameField.setPreferredSize(new Dimension(getWidth() / 2,
				getHeight() / 8));
		databaseInterface.add(databaseNameLabel);
		databaseInterface.add(databaseNameField);
		// databaseInterface.add(Box.createHorizontalGlue());
		//databaseInterface.add(Box.createHorizontalStrut(getWidth()-50));
		databaseInterface.add(doneButton);

		doneButton.addActionListener(this);

		setContentPane(databaseInterface);

		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (databaseNameField.getText() != null
				&& !databaseNameField.getText().trim().isEmpty()) {
			Object[][] res = DBHandler.sqlQuery("CREATE DATABASE "+databaseNameField.getText(),
					null);
			if ((boolean) res[0][0] == true) {
				JOptionPane.showMessageDialog(databaseInterface, res[0][1],
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane
						.showMessageDialog(databaseInterface,
								databaseNameField.getText()
										+ " created successfully.",
								"Success", JOptionPane.INFORMATION_MESSAGE);
				Browser.getDisplayTree().showdatabases();
				CreateDatabaseInterface.this.dispose();
			}
		}
	}

}
