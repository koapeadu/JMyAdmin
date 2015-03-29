/**
 * 
 */
package userInterface.login;

import globals.AppConstants;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mysqlServer.DBConnect;

/**
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
	
	JProgressBar connectProgress = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
	
	JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8));
	JButton connectButton = new JButton("Connect");
	JButton cancelButton = new JButton("Cancel");

	public LoginUI(JFrame parent) {
		super(parent, AppConstants.LOGIN_UI_TITLE, true);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// position dialog
		if (parent != null) {
			Point origin = parent.getLocation();
			setBounds(origin.x + parent.getWidth() / 4,
					origin.y + parent.getHeight() / 4, parent.getWidth() / 4,
					parent.getHeight() / 4);

		}

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		// Place title message
		message.setFont(new Font("Segoe UI", Font.BOLD, 24));
		message.setAlignmentX(0.6f);
		mainPanel.add(message);

		// Place user name form
		userNameLabel.setPreferredSize(new Dimension(getWidth() / 3 - 10,
				getHeight() / 8));
		userNameField.setPreferredSize(new Dimension(3*getWidth() / 5 - 10,
				getHeight() / 8));
		userNamePanel.add(userNameLabel);
		userNamePanel.add(userNameField);
		mainPanel.add(userNamePanel);

		// Place password form
		passwordLabel.setPreferredSize(new Dimension(getWidth() / 3 - 10,
				getHeight() / 8));
		passwordField.setPreferredSize(new Dimension(3*getWidth() / 5 - 10,
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
		
		// position on dialog
		setContentPane(mainPanel);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == connectButton){
			Thread connectThread = new Thread(new Runnable() {
				@Override
				public void run() {
					connectButton.setEnabled(false);
					connectProgress.setIndeterminate(true);
					if(connect()){
						setVisible(false);
						dispose();
					} else {
						connectButton.setEnabled(true);
						connectProgress.setIndeterminate(false);
						connectProgress.setValue(0);
					}
				}
			});
			connectThread.start();
		} else if(e.getSource() == cancelButton){
			setVisible(false);
			dispose();
		}
	}

	private boolean connect() {
		// get the field entries and connect
		String user = userNameField.getText();
		String password = new String(passwordField.getPassword());
		new DBConnect(user, password);
		if(DBConnect.connect()){
			return true;
		} else {
			return false;
		}
	}
}