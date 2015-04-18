/**
 * 
 */
package mysqlbrowser;

import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

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
		intializeDocument();
	}

	private void intializeDocument() {
		URL initDoc = getClass().getResource("/resources/InitResultsArea.html");
		try {
			resultsPane.setPage(initDoc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JEditorPane getArea() {
		return resultsPane;
	}

	/**
	 * Appends <code>text</code> to the text in <code>resultsPane</code>
	 * 
	 * @param text
	 *            The message to be appended to the
	 */
	public void addMessage(String text) {
		HTMLDocument d = (HTMLDocument) resultsPane.getDocument();
		try {
			d.insertBeforeEnd(d.getElement("BOX"), "<p>" + text + "</p>");
		} catch (BadLocationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Appends <code>text</code> to the text in <code>resultsPane</code> and
	 * sets the color to green as indicative of a successful query
	 * 
	 * @param text
	 *            The message to be appended to the
	 */
	public void addSuccessMessage(String text) {
		HTMLDocument d = (HTMLDocument) resultsPane.getDocument();
		try {
			d.insertBeforeEnd(d.getElement("BOX"), "<p class='text-success'>"
					+ text + "</p>");
		} catch (BadLocationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Appends <code>text</code> to the text in <code>resultsPane</code> and
	 * sets the color to red as indicative of an error
	 * 
	 * @param text
	 *            The message to be appended to the
	 */
	public void addFailureMessage(String text) {
		HTMLDocument d = (HTMLDocument) resultsPane.getDocument();
		try {
			d.insertBeforeEnd(d.getElement("BOX"), "<p class='text-danger'>"
					+ text + "</p>");
			System.out.println(resultsPane.getText());

		} catch (BadLocationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void clearAll() {
		HTMLDocument d = (HTMLDocument) resultsPane.getDocument();
		try {
			d.setInnerHTML(d.getElement("BOX"), "");
		} catch (BadLocationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
