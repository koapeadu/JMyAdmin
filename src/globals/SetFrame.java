/**
 * 
 */
package globals;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Gifty Buah
 * @author Quake
 */
public class SetFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public SetFrame() {
		super("JMyAdmin");
		setBounds(AppConstants.PROGRAM_WIDTH / 5,
				AppConstants.PROGRAM_HEIGHT / 5, AppConstants.PROGRAM_WIDTH,
				AppConstants.PROGRAM_HEIGHT);
		setResizable(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	

}
