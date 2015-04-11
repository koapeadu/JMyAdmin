/**
 * 
 */
package mysqlbrowser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * This class contains the tabbed pane that hosts and controls all opened
 * <code>SqlDocument</code>s.
 * 
 * @author Quake
 *
 */
public class SqlTabbedPane {

	private static JTabbedPane sqlPanel = new JTabbedPane(JTabbedPane.BOTTOM,
			JTabbedPane.SCROLL_TAB_LAYOUT);

	private static int untitledNum = 1;

	public SqlTabbedPane() {
		// setup default document
		createBlankDocument();
	}

	/**
	 * Generates a title for a new blank document as "Untitled"+untitledNum and
	 * increases untitledNum by one
	 * 
	 * @return {@literal "Untitled"}+untitledNum
	 */
	private static String getNextUntitled() {
		untitledNum++;
		return "Untitled" + (untitledNum - 1);
	}

	/**
	 * Creates a new blank <code>SqlDocument</code> and adds it to the
	 * <code>sqlPanel</code>
	 */
	public static void createBlankDocument() {
		String defaultPath = getNextUntitled();
		SqlDocument doc = new SqlDocument(defaultPath);
		int index = ((sqlPanel.getTabCount() == 0) ? 0 : (sqlPanel
				.getTabCount()));

		sqlPanel.add(defaultPath, doc);
		sqlPanel.setTabComponentAt(index, getTabTitle(defaultPath));
		sqlPanel.setSelectedIndex(index);
	}

	/**
	 * Creates a new <code>SqlDocument</code> with contents read from an SQL
	 * file and adds it to <code>sqlPanel</code>
	 */
	public static void createFileDocument() {
		String path = FileHandler.getOpenFilePath(sqlPanel).toString();
		if (path == null) {
			return;
		}
		String title = path.substring(path.lastIndexOf(File.separator) + 1);
		SqlDocument doc = new SqlDocument(path);

		sqlPanel.add(title, doc);
		sqlPanel.setTabComponentAt(sqlPanel.getTabCount() - 1,
				getTabTitle(title));
		sqlPanel.setSelectedIndex(sqlPanel.getTabCount() - 1);
	}

	/**
	 * Saves the currently selected document
	 */
	public static void saveDocument() {
		// save the file
		SqlDocument doc = ((SqlDocument) sqlPanel.getComponentAt(sqlPanel
				.getSelectedIndex()));
		if (doc.getFilePath().startsWith("Untitled")) {
			doc.saveAs();
		} else {
			doc.save();
		}
	}

	/**
	 * Saves the currently selected document to a new file
	 */
	public static void saveDocumentAs() {
		// save the file as an SQL file
		((SqlDocument) sqlPanel.getComponentAt(sqlPanel.getSelectedIndex()))
				.saveAs();
	}

	/**
	 * Closes a document: checks if the <code>SqlDocument</code> is ready to be
	 * saved and removes it from the <code>sqlPanel</code>
	 */
	public static void closeDocument() {
		boolean closed = ((SqlDocument) sqlPanel.getComponentAt(sqlPanel
				.getSelectedIndex())).close();
		if (closed) {
			sqlPanel.removeTabAt(sqlPanel.getSelectedIndex());
		}
	}

	/**
	 * Returns <code>sqlPanel</code>
	 * 
	 * @return <code>sqlPanel</code>
	 */
	public static JTabbedPane getTabbedPane() {
		return sqlPanel;
	}

	/**
	 * Creates a tab title for a tab as a panel with a label that contains the
	 * title and a close button
	 * 
	 * @param title
	 *            The text to be used as the title of the tab
	 * @return The title of the tab
	 */
	private static JPanel getTabTitle(String title) {
		JPanel titlePanel = new JPanel(new BorderLayout());
		// Add the tab's title
		titlePanel.add(new JLabel(title), BorderLayout.WEST);
		// create the close button
		JButton closeBtn = new JButton() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				// paint close sign
				g.drawLine(2, 2, 10, 10);
				g.drawLine(2, 10, 10, 2);
			}
		};
		closeBtn.setPreferredSize(new Dimension(17, 17));
		closeBtn.setToolTipText("Close");
		// add action listener to close the document if the close button is
		// clicked
		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeDocument();
			}
		});
		// add the close button
		titlePanel.add(closeBtn, BorderLayout.EAST);

		return titlePanel;
	}

}
