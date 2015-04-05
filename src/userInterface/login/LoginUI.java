/**
 * 
 */
package userInterface.login;

import globals.AppConstants;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mysqlServer.DBConnect;

/**
 * Creates the interface for logging in to the server and tries connecting with
 * the supplied credentials. If login is successful, the dialog is closed, else
 * the dialog remains open and a popup comes up to show error message.
 * 
 * @author Quake
 *
 */

public class LoginUI extends JDialog implements ActionListener {

	JPanel mainPanel = new JPanel();

	JLabel message = new JLabel(AppConstants.LOGIN_UI_MESSAGE);

	JPanel userNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
	JLabel userNameLabel = new JLabel("User name: ");
	JTextField userNameField = new JTextField();

	JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
	JLabel passwordLabel = new JLabel("Password: ");
	JPasswordField passwordField = new JPasswordField();

	JProgressBar connectProgress = new JProgressBar(SwingConstants.HORIZONTAL,
			0, 100);

	JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8));
	JButton connectButton = new JButton("Connect");
	JButton cancelButton = new JButton("Cancel");

	boolean isConnected = false;

	public LoginUI(JFrame parent) {
		super(parent, AppConstants.LOGIN_UI_TITLE, true);

		// position dialog
		if (parent != null) {
			Point origin = parent.getLocation();
			setBounds(origin.x + parent.getWidth() / 4,
					origin.y + parent.getHeight() / 4, parent.getWidth() / 4,
					parent.getHeight()*3 / 8);
		}
		// set layout
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		// Place title message
		message.setFont(new Font("Segoe UI", Font.BOLD, 24));
		message.setAlignmentX(0.5f);
		mainPanel.add(message);

		// Place user name form
		userNameLabel.setPreferredSize(new Dimension(getWidth() / 3 - 10,
				getHeight() / 8));
		userNameField.setPreferredSize(new Dimension(3 * getWidth() / 5 - 10,
				getHeight() / 8));
		userNamePanel.add(userNameLabel);
		userNamePanel.add(userNameField);
		mainPanel.add(userNamePanel);

		// Place password form
		passwordLabel.setPreferredSize(new Dimension(getWidth() / 3 - 10,
				getHeight() / 8));
		passwordField.setPreferredSize(new Dimension(3 * getWidth() / 5 - 10,
				getHeight() / 8));
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		mainPanel.add(passwordPanel);

		// place progress bar
		mainPanel.add(connectProgress);

		// Place actionButtons
		actionPanel.add(connectButton);
		actionPanel.add(cancelButton);
		mainPanel.add(actionPanel);

		// add action listeners
		connectButton.addActionListener(this);
		cancelButton.addActionListener(this);

		// place main panel on the dialog
		setContentPane(mainPanel);

		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * On click of the connect button connection is attempted in a thread. If
	 * connection is successful the dialog is closed else a popup shows the
	 * error and the dialog remains open. On click of the cancel button, the
	 * program is terminated.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == connectButton) {
			// try connecting to the server
			Thread connectThread = new Thread(new Runnable() {
				@Override
				public void run() {
					// disable connect button
					connectButton.setEnabled(false);
					// start progress bar
					connectProgress.setIndeterminate(true);
					// connect to server
					if (connect()) {
						isConnected = true;
						setVisible(false);
						dispose();
					} else {
						// stop progress bar
						connectProgress.setIndeterminate(false);
						connectProgress.setValue(0);
						// play error sound
						Toolkit.getDefaultToolkit().beep();
						// display error message
						JOptionPane.showMessageDialog(null,
								DBConnect.errorMessage, "Error",
								JOptionPane.ERROR_MESSAGE);
						// enable connect button
						connectButton.setEnabled(true);
					}
				}
			});
			connectThread.start();
		} else if (e.getSource() == cancelButton) {
			// exit
			setVisible(false);
			dispose();
		}
	}

	/**
	 * Initialize {@code userName} and {@code password} fields in
	 * {@code DBConnect} and make call to {@code DBConnect.connect()}
	 * 
	 * @return true if connection is successful or false if connection failed
	 */
	private boolean connect() {
		// get the field entries and connect
		String user = userNameField.getText();
		String password = new String(passwordField.getPassword());
		// initialize DBConnect with username and password entries
		new DBConnect(user, password);
		// try connecting
		if (DBConnect.connect()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return true if login was successful else false
	 */
	public boolean isConnected() {
		return isConnected;
	}
}