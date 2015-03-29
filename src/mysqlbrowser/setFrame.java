/**
 * 
 */
package mysqlbrowser;

import globals.AppConstants;

import javax.swing.*;

import java.awt.*;

/**
 * @author Gifty Buah
 *
 */
public class setFrame extends JFrame {

	public setFrame() {
		super("JMyAdmin");
		setBounds(AppConstants.PROGRAM_WIDTH/5, AppConstants.PROGRAM_HEIGHT/5, AppConstants.PROGRAM_WIDTH, AppConstants.PROGRAM_HEIGHT);
		setResizable(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

}
