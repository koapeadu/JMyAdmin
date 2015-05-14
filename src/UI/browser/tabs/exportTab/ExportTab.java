/**
 * 
 */
package UI.browser.tabs.exportTab;

import globals.FileHandler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mysqlServer.DBHandler;
import UI.browser.Browser;

/**
 * @author Quake
 *
 */
public class ExportTab {

	JPanel mainPanel = new JPanel(new BorderLayout());
	JButton exportButton = new JButton("Export");

	/**
	 * 
	 */
	public ExportTab() {
		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String savePath = FileHandler.getSaveFilePath(exportButton,
						"Comma Separated Values (CSV)", "csv").toString();
				if (!savePath.endsWith(".csv")) {
					savePath = savePath + ".csv";
				}
				System.out.println(savePath);
				Object[][] res = DBHandler
						.sqlQuery(
								"SELECT * "
										+ " INTO OUTFILE '"
										+ savePath.replace("\\", "\\\\")
										+ "' FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' "
										+ "FROM "
										+ Browser.getDisplayTree()
												.getSelectedTable(), Browser
										.getDisplayTree().getSelectedDatabase());
				if ((boolean) res[0][0] == true) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(mainPanel, res[0][1],
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(mainPanel, "Data in "+Browser.getDisplayTree().getSelectedTable()+" exported successfully to\n"+savePath,
							"Success", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mainPanel.add(new JLabel("Export the selected table to a csv file"),
				BorderLayout.NORTH);
		exportButton.setPreferredSize(new Dimension(100, 40));
		mainPanel.add(exportButton,BorderLayout.PAGE_END);
	}

	public JPanel getPanel() {
		return mainPanel;
	}

}
