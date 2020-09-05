package utils;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProgramDirectoryHelper {

	// https://stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file

	// Other methods
	private static Path parseProgramDirectory() throws URISyntaxException {
		Path classFilePath = classFilePath = Paths.get(ProgramDirectoryHelper.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		String classFileUrlString =  classFilePath.toString();
		String parentFolderString;
		if(classIsInJar(classFileUrlString)) {
			int indexOfFileName = classFileUrlString.lastIndexOf(".jar");
			parentFolderString = classFileUrlString.substring(0, indexOfFileName);
		} else {
			int indexOfFileName = classFileUrlString.lastIndexOf("KanbanBo");
			parentFolderString = classFileUrlString.substring(0, indexOfFileName);
		}
		Path executedDirectoryPath = Paths.get(parentFolderString);
		return executedDirectoryPath;
	}

	public static Path parseCssFolderPath() throws URISyntaxException {
		Path executedDirectoryPath = parseProgramDirectory();
		Path cssFolderPath = Paths.get(executedDirectoryPath.toString(), "styles");
		return cssFolderPath;
	}

	public static Path parsePropertiesPath() throws URISyntaxException {
		Path executedDirectoryPath = parseProgramDirectory();
		Path propertiesFilePath = Paths.get(executedDirectoryPath.toString(), "KanbanBo.properties");
		return propertiesFilePath;
	}

	public static Path parseUserTemplatesDbPath() throws URISyntaxException{
		Path executedDirectoryPath = parseProgramDirectory();
		Path userTemplatesDbFilePath = Paths.get(executedDirectoryPath.toString(), "UserTemplates.db");
		return userTemplatesDbFilePath;
	}

	private static boolean classIsInJar(String filePath) {
		int indexOfFileName = filePath.lastIndexOf(".jar");
		if(indexOfFileName < 1) {
			return false;
		} else {
			return true;
		}
	}

}
