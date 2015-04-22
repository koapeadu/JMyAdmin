/**
 * 
 */
package UI.browser;

//import java.awt.FlowLayout;
//import java.awt.Point;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import mysqlServer.DBHandler;

/**
 * @author Gifty Buah
 *
 */
public class CreateTableInterface extends JDialog implements ActionListener {
	JPanel mainPanel = new JPanel();

	JPanel tableNamePanel = new JPanel();
	JLabel databaseLabel = new JLabel("Database");
	JComboBox<Object> databaseBox = new JComboBox<Object>(DBHandler
			.showDatabases().toArray());
	JLabel tableNameLabel = new JLabel("Table Name");
	JTextField tableNameField = new JTextField();
	JScrollPane tablespecScrollPane = new JScrollPane();
	JPanel tableSpecifications = new JPanel();

	JPanel tableSpecLabel = new JPanel();
	JLabel columnName = new JLabel("Column Name");
	JLabel dataType = new JLabel("Data Type");
	JLabel length = new JLabel("Length");
	JLabel primaryKey = new JLabel("PK");
	JLabel nullspec = new JLabel("NN");
	JLabel uniquespec = new JLabel("UQ");
	JLabel autoIncrement = new JLabel("AI");
	JLabel defaultSpec = new JLabel("Default");

	JPanel actionPanel = new JPanel();
	JButton addnewColumn = new JButton("Add Column");
	JButton done = new JButton("Done");

	public CreateTableInterface(JFrame parent) {
		super(parent, "Create Table Wizard", true);

		if (parent != null) {
			Point origin = parent.getLocation();
			setBounds(origin.x + parent.getWidth() / 4,
					origin.y + parent.getHeight() / 4, parent.getWidth() / 2,
					parent.getHeight() / 2);
		}

		setUpInterface();
		setContentPane(mainPanel);
		addnewColumn.addActionListener(this);
		done.addActionListener(this);
		setPreferredSize(new Dimension(2 * 80, 3 * 25));
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void setUpInterface() {
		tablespecScrollPane.setViewportView(tableSpecifications);
		tableNameField.setPreferredSize(new Dimension(200, 25));
		tableNamePanel.setPreferredSize(new Dimension(300, 30));
		primaryKey.setPreferredSize(new Dimension(80, 25));
		nullspec.setPreferredSize(new Dimension(80, 25));
		uniquespec.setPreferredSize(new Dimension(80, 25));

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(tableNamePanel);
		mainPanel.add(tableSpecLabel);
		mainPanel.add(tablespecScrollPane);
		mainPanel.add(actionPanel);

		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
		actionPanel.add(Box.createHorizontalStrut(50));
		actionPanel.add(addnewColumn);
		actionPanel.add(Box.createHorizontalGlue());
		actionPanel.add(done);
		actionPanel.add(Box.createHorizontalStrut(50));

		tableNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		tableNamePanel.add(databaseLabel);
		tableNamePanel.add(databaseBox);
		tableNamePanel.add(tableNameLabel);
		tableNamePanel.add(tableNameField);

		tableSpecifications.setLayout(new BoxLayout(tableSpecifications,
				BoxLayout.Y_AXIS));
		for (int i = 0; i < 4; i++) {
			tableSpecifications.add(new tablerowModel());
		}

		tableSpecLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
		columnName.setPreferredSize(new Dimension(130, 30));
		dataType.setPreferredSize(new Dimension(110, 30));
		primaryKey.setPreferredSize(new Dimension(40, 30));
		nullspec.setPreferredSize(new Dimension(40, 30));
		uniquespec.setPreferredSize(new Dimension(40, 30));
		autoIncrement.setPreferredSize(new Dimension(40, 30));
		tableSpecLabel.add(columnName);
		tableSpecLabel.add(dataType);
		tableSpecLabel.add(length);
		tableSpecLabel.add(primaryKey);
		tableSpecLabel.add(nullspec);
		tableSpecLabel.add(uniquespec);
		tableSpecLabel.add(autoIncrement);
		tableSpecLabel.add(defaultSpec);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == addnewColumn) {
			tableSpecifications.add(new tablerowModel());
			validate();
		} else if (arg0.getSource() == done) {
			String query = getQuery();
			if (query != null) {
				Object[][] res = DBHandler.sqlQuery(query,
						(String) databaseBox.getSelectedItem());
				if ((boolean) res[0][0] == true) {
					JOptionPane.showMessageDialog(mainPanel, res[0][1],
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane
							.showMessageDialog(mainPanel,
									tableNameField.getText()
											+ " created successfully.",
									"Success", JOptionPane.INFORMATION_MESSAGE);
					Browser.getDisplayTree().showdatabases();
					CreateTableInterface.this.dispose();
				}
			}
		}
	}

	private String getQuery() {
		StringBuilder query = new StringBuilder();
		for (int i = 0; i < tableSpecifications.getComponentCount() ; i++) {
			tablerowModel currentRow = (tablerowModel) tableSpecifications
					.getComponent(i);
			String querySection = currentRow.toString();
			if (querySection != null) {
				query.append(querySection + ", ");
			}
		}
		if (query.toString().endsWith(", ")){
			query.replace(query.length()-2, query.length()-1, "");
		}

		if (query.length() != 0 && tableNameField.getText() != null
				&& !tableNameField.getText().trim().isEmpty()) {
			query.insert(0, "CREATE TABLE " + tableNameField.getText() + " ( ");
			query.append(" )");
		}
		return query.toString();
	}

	private class tablerowModel extends JPanel {
		String[] dataTypeText = { "INT", "DECIMAL", "DOUBLE", "VARCHAR",
				"CHAR", "TEXT", "DATETIME", "BLOB", "BOOLEAN" };
		// JPanel tableSpecEntry = new JPanel();
		JTextField columnNameField = new JTextField();
		JComboBox<String> dataTypebox = new JComboBox<String>(dataTypeText);
		JTextField lengthField = new JTextField();
		JCheckBox primaryKeybox = new JCheckBox();
		JCheckBox nullspecbox = new JCheckBox();
		JCheckBox uniquespecbox = new JCheckBox();
		JCheckBox autoIncrementbox = new JCheckBox();
		JTextField defaultField = new JTextField();

		public tablerowModel() {
			columnNameField.setPreferredSize(new Dimension(130, 20));
			dataTypebox.setPreferredSize(new Dimension(110, 20));
			lengthField.setPreferredSize(new Dimension(40, 20));
			primaryKeybox.setPreferredSize(new Dimension(40, 20));
			nullspecbox.setPreferredSize(new Dimension(40, 20));
			uniquespecbox.setPreferredSize(new Dimension(40, 20));
			autoIncrementbox.setPreferredSize(new Dimension(40, 20));
			defaultField.setPreferredSize(new Dimension(80, 20));
			setPreferredSize(new Dimension(80, 30));
			setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
			add(columnNameField);
			add(dataTypebox);
			add(lengthField);
			add(primaryKeybox);
			add(nullspecbox);
			add(uniquespecbox);
			add(autoIncrementbox);
			add(defaultField);
			// tableSpecifications.add(tableSpecEntry);
		}

		@Override
		public String toString() {
			if (columnNameField.getText() != null
					&& !columnNameField.getText().trim().isEmpty()) {
				StringBuilder specifications = new StringBuilder();
				specifications.append(columnNameField.getText() + " ");

				specifications.append(dataTypebox.getSelectedItem() + " ");
				if (lengthField.getText() != null
						&& !lengthField.getText().trim().isEmpty()) {
					specifications.append("(" + lengthField.getText() + ") ");
				}
				if (primaryKeybox.isSelected()) {
					specifications.append("PRIMARY KEY ");
				}
				if (nullspecbox.isSelected()) {
					specifications.append("NOT NULL ");
				}
				if (autoIncrementbox.isSelected()) {
					specifications.append("AUTO_INCREMENT ");
				}
				if (uniquespecbox.isSelected() && !primaryKeybox.isSelected()) {
					specifications.append("UNIQUE ");
				}
				return specifications.toString();
			} else {
				return null;
			}
		}
	}
}
