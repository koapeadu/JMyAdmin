/**
 * 
 */
package mysqlbrowser;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * Essentially comprises <code>resultsPane</code>, a <code>JTabbedPane</code>
 * which hosts the results of SQL queries that have been executed. It contains a
 * function for executing the SQL queries in a document and displays the result
 * in a table if a <code>ResultSet</code> is obtained or displays a message in a
 * text area if there is no result set or there is an error.
 * 
 * @author Quake
 *
 */
public class ResultsTabbedPane {
	JTabbedPane resultsPane = new JTabbedPane(JTabbedPane.TOP,
			JTabbedPane.SCROLL_TAB_LAYOUT);

	public ResultsTabbedPane() {

	}

	/**
	 * Returns the <code>resultsPane</code>, the <code>JTabbedPane</code> that
	 * hosts query results
	 * 
	 * @return <code>resultsPane</code>
	 */
	public JTabbedPane getTabbedPane() {
		return resultsPane;
	}

	/**
	 * Sets the <code>resultsPane</code>, the <code>JTabbedPane</code> that
	 * hosts query results
	 * 
	 * @param resTabs
	 *            The <code>JTabbedPane</code> to use as the
	 *            <code>resultsPane</code>
	 */
	public void setTabbedPane(JTabbedPane resTabs) {
		resultsPane = resTabs;
	}

	/**
	 * Executes a given SQL query by breaking it up into statements delimited by
	 * a semicolons, executing and displaying the results in displays the
	 * result in a table (obtained from <code>ResultsGrid</code>) if a
	 * <code>ResultSet</code> is obtained or displays a message in a text area
	 * if there is no result set or there is an error.
	 * 
	 * @param sqlQuery The query to be executed
	 * @param db The database to use for execution
	 */
	public void exec(String sqlQuery, String db) {
		String[] queries = sqlQuery.trim().split(";");
		for (String query : queries) {
			ResultsGrid results = new ResultsGrid();
			results.setDatabase(db);
			results.setQuery(query);

			resultsPane.addTab("Result", new JScrollPane(results.getResults()));
			results.showQueryResults();
		}
	}

}
