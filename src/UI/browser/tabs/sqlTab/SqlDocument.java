/**
 * 
 */
package UI.browser.tabs.sqlTab;

import globals.AppConstants;
import globals.FileHandler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import UI.browser.Browser;

/**
 * An <code>SqlDocument</code> is a customized JPanel that comprises a text
 * editor for editing SQL files and writing SQL queries, a button which causes
 * the statements in the editor to be executed and functions for opening SQL
 * files and saving the statements to an SQL file.
 * 
 * @author Quake
 *
 */
public class SqlDocument extends JPanel {
	private static final long serialVersionUID = -8824535533917116577L;

	private JTextArea sqlArea = new JTextArea();
	private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8,
			8));
	private JButton goButton = new JButton("Go");

	private String filePath;

	private boolean isEdited = false;

	ResultsTabbedPane res = new ResultsTabbedPane();

	public SqlDocument(String path) {
		filePath = path;

		setLayout(new BorderLayout());

		sqlArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				isEdited = true;
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});

		JScrollPane sqlScrollPane = new JScrollPane(sqlArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sqlScrollPane.setPreferredSize(new Dimension(
				AppConstants.PROGRAM_WIDTH * 8 / 10,
				AppConstants.PROGRAM_HEIGHT * 5 / 10));
		add(sqlScrollPane, BorderLayout.CENTER);

		buttonPanel.add(goButton);
		add(buttonPanel, BorderLayout.SOUTH);

		goButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				execute();
			}
		});

		open();
	}
	
	public void execute(){
		String sqlQuery = sqlArea.getText();
		res.setTabbedPane((JTabbedPane) ((JSplitPane) SqlDocument.this
				.getParent()).getBottomComponent());
		res.exec(sqlQuery, Browser.getDisplayTree()
				.getSelectedDatabase());
		//browser.getDisplayTree().showdatabases();
	}

	/*public void executeSelected() {
		String sqlQuery = sqlArea.getSelectedText();
		ResultsTabbedPane res = new ResultsTabbedPane();
		res.setTabbedPane((JTabbedPane) ((JSplitPane) SqlDocument.this
				.getParent()).getBottomComponent());
		res.exec(sqlQuery, browser.getDisplayTree().getSelectedDatabase());
	}*/

	/**
	 * Saves changes to a document to the path in <code>filePath</code>
	 * 
	 * @return <code>true</code> if file was saved successfully;
	 *         <code>false</code> if the file could not be saved
	 */
	public boolean save() {
		if (FileHandler.write(filePath, sqlArea.getText())) {
			return true;
		}
		JOptionPane.showMessageDialog(this, "Error saving changes to document",
				"Error", JOptionPane.ERROR_MESSAGE);
		return false;
	}

	/**
	 * Saves a document to the path in <code>filePath</code>.
	 * <p>
	 * <code>FileHandler.getSaveFilePath</code> is called to get the path to
	 * save the document to and the document is saved after which the
	 * <code>filePath</code> is updated to the path to which the file was saved
	 * 
	 * @return <code>true</code> if file was saved successfully;
	 *         <code>false</code> if the file could not be saved
	 */
	public boolean saveAs() {
		// Get path to save file to
		String path = FileHandler.getSaveFilePath(this,
				"Structured Query Language (SQL)", "sql").toString();
		if (path == null) {
			return false;
		}
		// save the file
		String origPath = filePath;
		filePath = path;
		if (!save()) {
			filePath = origPath;
			return false;
		}
		return true;
	}

	/**
	 * Opens the file at <code>filePath</code> and displays its contents in the
	 * <code>sqlArea</code> for editing
	 * 
	 * @return <code>true</code> if file was opened successfully;
	 *         <code>false</code> if the file could not be opened
	 */
	public boolean open() {
		if (!filePath.startsWith("Untitled")) {
			// read contents of file into text area
			sqlArea.setText(FileHandler.read(filePath));
			return true;
		}
		return false;
	}

	/**
	 * Close the file.
	 * <p>
	 * This entails checking if the document is edited, asking the user if the
	 * document should be saved, and returns true if the document is ready to be
	 * closed and returns false if the document is not to be closed
	 * 
	 * @return <code>true</code> if the document is ready to be closed;
	 *         <false>false</code> if the document is not to be closed
	 */
	public boolean close() {
		// check if document is already saved: not edited/modified
		if (!isEdited) {
			// remove tab
			return true;
		} else {
			// prompt user to save
			int save = JOptionPane.showConfirmDialog(this,
					"Do you want to save pending changes to the document?");
			if (save == JOptionPane.OK_OPTION) {
				// save the document
				if (saveAs()) {
					// remove the tab
					return true;
				}
			} else if (save == JOptionPane.NO_OPTION) {
				// don't save just close: remove tab
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the <code>filePath</code>
	 * 
	 * @param path
	 *            Absolute path to the current file opened in this SqlDocument
	 */
	public void setFilePath(String path) {
		filePath = path;
	}

	/**
	 * Returns the current value of <code>filePath</code>
	 * 
	 * @return the path of the current file
	 */
	public String getFilePath() {
		return filePath;
	}

	public void setQuery(String query) {
		sqlArea.setText(query);
	}

}
