/**
 * 
 */
package globals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The FileHandler class contains file manipulation tasks
 * <ul>
 * <li>Reading from SQL files</li>
 * <li>Writing to SQL files</li>
 * <li>Obtaining a path to save a file to</li>
 * <li>Obtaining a path to open a file from</li>
 * </ul>
 * 
 * @author Quake
 *
 */
public class FileHandler {

	/**
	 * Returns the contents of the file whose absolute path is in path
	 * 
	 * @param path
	 *            The absolute path to the file to be read
	 * @return The contents of the file that is read
	 */
	public static String read(String path) {
		String contentsOfFile = "";
		try {
			BufferedReader input = new BufferedReader(new FileReader(path));
			String temp = input.readLine();
			while (temp != null) {
				contentsOfFile = contentsOfFile + temp + System.lineSeparator();
				temp = input.readLine();
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentsOfFile;
	}

	/**
	 * Writes the string to a file whose absolute path is in path
	 * 
	 * @param path
	 *            The absolute path to the file to write
	 * @param contentsOfFile
	 *            The text to write to the file
	 * @return <code>true</code> if contentsOfFile was successfully written to
	 *         file specified in path; <code>false</code> if writing
	 *         contentsOfFile to file specified in path failed;
	 */
	public static boolean write(String path, String contentsOfFile) {
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(path));
			output.write(contentsOfFile);
			output.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Opens a dialog for the user to choose an SQL file to open and returns the
	 * path
	 * 
	 * @param open
	 *            The component to use to open the file chooser dialog
	 * @return The absolute path to the selected file
	 */
	public static Path getOpenFilePath(JComponent open, String filterName, String filterExtension) {

		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				filterName, filterExtension);
		// fileChooser.addChoosableFileFilter(filter);
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showOpenDialog(open);

		if (result == JFileChooser.CANCEL_OPTION) {
			return null;
		}
		return fileChooser.getSelectedFile().toPath().toAbsolutePath();
	}

	/**
	 * Opens a dialog for the user to specify a location to save an SQL file and
	 * returns the path
	 * 
	 * @param save
	 *            The component to use to open the file chooser dialog
	 * @return The absolute path to the chosen save location
	 */
	public static Path getSaveFilePath(JComponent save,  String filterName, String filterExtension) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				filterName, filterExtension);
		// fileChooser.addChoosableFileFilter(filter);
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showSaveDialog(save);

		if (result == JFileChooser.CANCEL_OPTION) {
			return null;
		}
		return fileChooser.getSelectedFile().toPath().toAbsolutePath();
	}
}
