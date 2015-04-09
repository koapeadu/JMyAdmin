/**
 * 
 */
package mysqlbrowser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mysqlServer.DBHandler;

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
										+ browser.getDisplayTree()
												.getSelectedTable(), browser
										.getDisplayTree().getSelectedDatabase());
				if ((boolean) res[0][0] == true) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(mainPanel, res[0][1],
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mainPanel.add(new JLabel("Export the selected table to a csv file"),
				BorderLayout.NORTH);
		mainPanel.add(exportButton);
	}

	public JPanel getPanel() {
		return mainPanel;
	}

}
