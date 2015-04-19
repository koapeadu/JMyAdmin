/**
 * 
 */
package mysqlbrowser;

import globals.AppConstants;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
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

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		ResultsTabbedPane res = new ResultsTabbedPane();
		JTabbedPane downResultsTabs = res.getTabbedPane();
		doc.setPreferredSize(new Dimension(
				AppConstants.PROGRAM_WIDTH * 8 / 10 - 10,
				AppConstants.PROGRAM_HEIGHT / 2 - 10));
		downResultsTabs.setPreferredSize(new Dimension(
				AppConstants.PROGRAM_WIDTH * 8 / 10 - 10,
				AppConstants.PROGRAM_HEIGHT / 2 - 10));

		doc.setMinimumSize(new Dimension(
				AppConstants.PROGRAM_WIDTH * 2 / 10 - 10,
				AppConstants.PROGRAM_HEIGHT * 2 / 10 - 10));

		splitPane.setTopComponent(doc);
		splitPane.setBottomComponent(downResultsTabs);

		splitPane.setDividerLocation(0.5);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.5);

		sqlPanel.add(defaultPath, splitPane);
		sqlPanel.setTabComponentAt(index, getTabTitle(defaultPath));
		sqlPanel.setSelectedIndex(index);
	}

	/**
	 * Creates a new <code>SqlDocument</code> with contents read from an SQL
	 * file and adds it to <code>sqlPanel</code>
	 */
	public static void createFileDocument() {
		String path = FileHandler.getOpenFilePath(sqlPanel, "Structured Query Language (SQL)", "sql").toString();
		if (path == null) {
			return;
		}
		String title = path.substring(path.lastIndexOf(File.separator) + 1);
		SqlDocument doc = new SqlDocument(path);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		ResultsTabbedPane res = new ResultsTabbedPane();
		JTabbedPane downResultsTabs = res.getTabbedPane();
		doc.setPreferredSize(new Dimension(
				AppConstants.PROGRAM_WIDTH * 8 / 10 - 10,
				AppConstants.PROGRAM_HEIGHT / 2 - 10));
		downResultsTabs.setPreferredSize(new Dimension(
				AppConstants.PROGRAM_WIDTH * 8 / 10 - 10,
				AppConstants.PROGRAM_HEIGHT / 2 - 10));

		doc.setMinimumSize(new Dimension(
				AppConstants.PROGRAM_WIDTH * 2 / 10 - 10,
				AppConstants.PROGRAM_HEIGHT * 2 / 10 - 10));

		splitPane.setTopComponent(doc);
		splitPane.setBottomComponent(downResultsTabs);

		splitPane.setDividerLocation(0.5);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.5);

		sqlPanel.add(title, splitPane);
		sqlPanel.setTabComponentAt(sqlPanel.getTabCount() - 1,
				getTabTitle(title));
		sqlPanel.setSelectedIndex(sqlPanel.getTabCount() - 1);
	}

	/**
	 * Saves the currently selected document
	 */
	public static void saveDocument() {
		// save the file
		SqlDocument doc = ((SqlDocument) ((JSplitPane) sqlPanel
				.getComponentAt(sqlPanel.getSelectedIndex())).getTopComponent());
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
		((SqlDocument) ((JSplitPane) sqlPanel.getComponentAt(sqlPanel
				.getSelectedIndex())).getTopComponent()).saveAs();
	}

	/**
	 * Closes a document: checks if the <code>SqlDocument</code> is ready to be
	 * saved and removes it from the <code>sqlPanel</code>
	 */
	public static void closeDocument() {
		boolean closed = ((SqlDocument) ((JSplitPane) sqlPanel
				.getComponentAt(sqlPanel.getSelectedIndex())).getTopComponent())
				.close();
		if (closed) {
			sqlPanel.removeTabAt(sqlPanel.getSelectedIndex());
		}
	}

	/**
	 * Attempts closing all open documents and returns <code>true</code> only if
	 * all the documents were successfully closed or stops and returns
	 * <code>false</code> if cancelled.
	 * 
	 * @return <code>true</code> if all the documents were successfully closed;
	 *         <code>false</code>if closing a document was cancelled
	 */
	public static boolean saveAllTabsOnWindowClose() {
		int numofTabs = sqlPanel.getTabCount();
		for (int i = 0; i < numofTabs; i++) {
			if (!((SqlDocument) ((JSplitPane) sqlPanel.getComponentAt(i))
					.getTopComponent()).close()) {
				return false;
			}
			i++;
		}
		return true;
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
		JPanel titlePanel = new JPanel(new BorderLayout(3, 1));
		titlePanel.setOpaque(false);
		titlePanel.setMinimumSize(new Dimension(0, 23));
		// Add the tab's title
		titlePanel.add(new JLabel(title), BorderLayout.WEST);
		// create the close button
		JButton closeBtn = new JButton() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				// paint close sign
				Graphics2D g2 = (Graphics2D) g;
				// shift the image for pressed buttons
				setPreferredSize(new Dimension(21, 21));
				if (getModel().isPressed()) {
					g2.translate(1, 1);
				}
				g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_ROUND));
				g2.setColor(Color.BLACK);
				if (getModel().isRollover()) {
					g2.setColor(Color.RED);
				}
				int delta = 6;
				g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight()
						- delta - 1);
				g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight()
						- delta - 1);
				g2.dispose();
			}
		};
		closeBtn.setFocusable(false);
		closeBtn.setBorder(BorderFactory.createEtchedBorder());
		closeBtn.setBorderPainted(false);
		closeBtn.setContentAreaFilled(false);
		closeBtn.setRolloverEnabled(true);
		// closeBtn.setPreferredSize(new Dimension(18, 18));
		closeBtn.setToolTipText("Close");
		// add action listener to close the document if the close button is
		// clicked
		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeDocument();
			}
		});
		// add animation on mouse motion
		closeBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				AbstractButton button = (AbstractButton) e.getComponent();
				button.setBorderPainted(true);
			}

			public void mouseExited(MouseEvent e) {
				AbstractButton button = (AbstractButton) e.getComponent();
				button.setBorderPainted(false);
			}
		});
		// add the close button
		titlePanel.add(closeBtn, BorderLayout.EAST);

		return titlePanel;
	}

}
