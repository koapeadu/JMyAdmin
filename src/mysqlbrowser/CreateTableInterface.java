/**
 * 
 */
package mysqlbrowser;

//import java.awt.FlowLayout;
//import java.awt.Point;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * @author Gifty Buah
 *
 */
public class CreateTableInterface extends JDialog implements ActionListener {

	/**
	 * @param args
	 */
	JPanel mainPanel = new JPanel();

	JPanel tableNamePanel = new JPanel();
	JLabel tableNameLabel = new JLabel("Table Name");
	JTextField tableNameField = new JTextField();
    JScrollPane tablespecScrollPane =new JScrollPane();
	JPanel tableSpecifications = new JPanel();
	JPanel tableSpecLabel = new JPanel();
	JLabel columnName = new JLabel("Column Name");
	JLabel dataType = new JLabel("Data Type");
	JLabel primaryKey = new JLabel("PK");
	JLabel nullspec = new JLabel("NN");
	JLabel uniquespec = new JLabel("UQ");

	JButton addnewColumn = new JButton("Add Column");

	public CreateTableInterface(JFrame parent) {
		super(parent, "Create Table", true);

		// position dialog
		if (parent != null) {
			Point origin = parent.getLocation();
			setBounds(origin.x + parent.getWidth() / 4,
					origin.y + parent.getHeight() / 4, parent.getWidth() ,
					parent.getHeight() /2);
		}

		setUpInterface();
		setContentPane(mainPanel);
		addnewColumn.addActionListener(this);
		pack();
		setPreferredSize(new Dimension(2*80, 2*25));
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}

	public void setUpInterface() {
		//tablespecScrollPane.setPreferredSize(new Dimension(150,500));
		tablespecScrollPane.setViewportView(tableSpecifications);
		tableNameField.setPreferredSize(new Dimension(130, 25));
		//columnNameField.setPreferredSize(new Dimension(80, 25));
		//dataTypebox.setPreferredSize(new Dimension(80, 25));
		primaryKey.setPreferredSize(new Dimension(80, 25));
		nullspec.setPreferredSize(new Dimension(80, 25));
		uniquespec.setPreferredSize(new Dimension(80, 25));

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		tableSpecifications.setPreferredSize(new Dimension(80, 50));
		mainPanel.add(tableNamePanel);
		mainPanel.add(tablespecScrollPane);
		mainPanel.add(addnewColumn);

		tableNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tableNamePanel.add(tableNameLabel);
		tableNamePanel.add(tableNameField);

		tableSpecifications.setLayout(new BoxLayout(tableSpecifications,
				BoxLayout.Y_AXIS));
		tableSpecifications.add(tableSpecLabel);
		for(int i=0;i<5;i++){
			tableSpecifications.add((new tablerowModel()).getModel());
		}
		
		tableSpecifications.add(Box.createHorizontalGlue());

		tableSpecLabel
				.setLayout(new BoxLayout(tableSpecLabel, BoxLayout.X_AXIS));
		tableSpecLabel.add(columnName);
		tableSpecLabel.add(dataType);
		tableSpecLabel.add(primaryKey);
		tableSpecLabel.add(nullspec);
		tableSpecLabel.add(uniquespec);

		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("something");
		if (arg0.getSource() == addnewColumn) {
			tableSpecifications.add((new tablerowModel()).getModel());

			//pack();
			revalidate();
			pack();
			//repaint();
			//mainPanel.add(tableSpecifications);
		}
	}
	public class tablerowModel{
		JPanel tableSpecEntry = new JPanel();
		JTextField columnNameField = new JTextField();
		JComboBox dataTypebox = new JComboBox();
		JCheckBox primaryKeybox = new JCheckBox();
		JCheckBox nullspecbox = new JCheckBox();
		JCheckBox uniquespecbox = new JCheckBox();
		
		
		public tablerowModel() {
			tableSpecEntry.setPreferredSize(new Dimension(150,40));
			tableSpecEntry.setLayout(new BoxLayout(tableSpecEntry,
					BoxLayout.X_AXIS));
			tableSpecEntry.add(columnNameField);
			tableSpecEntry.add(dataTypebox);
			tableSpecEntry.add(primaryKeybox);
			tableSpecEntry.add(nullspecbox);
			tableSpecEntry.add(uniquespecbox);
			tableSpecifications.add(tableSpecEntry);
		}
		
		private JPanel getModel() {
			return tableSpecEntry;
		}
	}

}
