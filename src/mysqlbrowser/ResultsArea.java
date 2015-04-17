/**
 * 
 */
package mysqlbrowser;

import javax.swing.JEditorPane;

/**
 * Provides <code>resultsPane</code>, a <code>JEditorPane</code> which serves as
 * styled text area for displaying messages from executed SQL statements that
 * return no <code>ResultSet</code> or result in some error
 * 
 * @author Quake
 *
 */
public class ResultsArea {

	JEditorPane resultsPane = new JEditorPane();

	public ResultsArea() {
		resultsPane.setContentType("text/html; charset=utf8");
		intializeStyles();
	}

	private void intializeStyles() {
		String css = getClass().getResource("/resources/Styles.css").toString();
		resultsPane.setText("<link rel='stylesheet' media='screen' href='"
				+ css + "' /> ");
	}

	/**
	 * Appends <code>text</code> to the text in <code>resultsPane</code>
	 * 
	 * @param text
	 *            The message to be appended to the
	 */
	public void addMessage(String text) {
		resultsPane.setText(resultsPane.getText() + "<p>" + text + "</p>");
	}

	/**
	 * Appends <code>text</code> to the text in <code>resultsPane</code> and
	 * sets the color to green as indicative of a successful query
	 * 
	 * @param text
	 *            The message to be appended to the
	 */
	public void addSuccessMessage(String text) {
		resultsPane.setText(resultsPane.getText() + "<p class='text-success'>"
				+ text + "</p>");
	}

	/**
	 * Appends <code>text</code> to the text in <code>resultsPane</code> and
	 * sets the color to red as indicative of an error
	 * 
	 * @param text
	 *            The message to be appended to the
	 */
	public void addFailureMessage(String text) {
		resultsPane.setText(resultsPane.getText() + "<p class='text-danger'>"
				+ text + "</p>");
	}

	public void clearAll() {
		resultsPane.setText("");
	}

}
