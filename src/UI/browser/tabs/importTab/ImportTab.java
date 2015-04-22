/**
 * 
 */
package UI.browser.tabs.importTab;

import globals.FileHandler;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import UI.browser.Browser;
import mysqlServer.DBHandler;

/**
 * @author Quake
 *
 */
public class ImportTab {

	JPanel mainPanel = new JPanel(new BorderLayout());
	JButton importButton = new JButton("Import");

	/**
	 * 
	 */
	public  ImportTab() {
		importButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String savePath = FileHandler.getOpenFilePath(importButton,
						"Comma Separated Values (CSV)", "csv").toString();
				
				Object[][] res = DBHandler
						.sqlQuery(
								"LOAD DATA INFILE '"
										+ savePath.replace("\\", "\\\\")
										+ "' INTO TABLE "+Browser.getDisplayTree()
										.getSelectedTable()+" FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' "
										+ "IGNORE 1 LINES"
										, Browser
										.getDisplayTree().getSelectedDatabase());
				if ((boolean) res[0][0] == true) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(mainPanel, res[0][1],
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(mainPanel, res[1][2]+" rows imported successfully",
							"Success", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mainPanel.add(new JLabel("Import data from a csv file into the selected table"),
				BorderLayout.NORTH);
		importButton.setPreferredSize(new Dimension(100, 40));
		mainPanel.add(importButton,BorderLayout.PAGE_END);
	}

	public JPanel getPanel() {
		return mainPanel;
	}

}
