package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProgramDirectoryHelper {

	// https://stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file

	// Variables
	private Path classFilePath;
	private Path executedDirectoryPath;
	private Path cssFolderPath;
	private Path propertiesFilePath;


	// Constructors
	public ProgramDirectoryHelper() throws URISyntaxException, IOException {
		classFilePath = Paths.get(ProgramDirectoryHelper.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		//System.out.println("classFilePath: " + classFilePath.toString());
		parseProgramDirectory();
		//System.out.println("executedDirectoryPath: " + executedDirectoryPath);
		parseCssFolderPath();
		//System.out.println("cssFolderPath: " + cssFolderPath);
		parsePropertiesPath();
		//System.out.println("propertiesFilePath: " + propertiesFilePath);
	}


	// Getters and Setters
	public Path getClassFilePath() {
		return classFilePath;
	}

	public Path getExecutedDirectoryPath() {
		return executedDirectoryPath;
	}

	public Path getCssFolderPath() {
		return cssFolderPath;
	}

	public Path getPropertiesFilePath() {
		return propertiesFilePath;
	}


	// Initialisation methods


	// Other methods
	private boolean parseProgramDirectory() {
		String classFileUrlString =  classFilePath.toString();
		String parentFolderString;
		if(classIsInJar(classFileUrlString)) {
			int indexOfFileName = classFileUrlString.lastIndexOf(".jar");
			parentFolderString = classFileUrlString.substring(0, indexOfFileName);
		} else {
			int indexOfFileName = classFileUrlString.lastIndexOf("KanbanBo");
			parentFolderString = classFileUrlString.substring(0, indexOfFileName);
		}
		//System.out.println("parent folder string: " + parentFolderString);
		executedDirectoryPath = Paths.get(parentFolderString);
		return FileAndDirectoryHelper.directoryExists(executedDirectoryPath);
	}

	private void parseCssFolderPath() throws IOException {
		cssFolderPath = Paths.get(executedDirectoryPath.toString(), "styles");
	}

	private void parsePropertiesPath() throws IOException {
		propertiesFilePath = Paths.get(executedDirectoryPath.toString(), "KanbanBo.properties");
		if(!FileAndDirectoryHelper.fileExists(propertiesFilePath)) {
			FileAndDirectoryHelper.createFile(propertiesFilePath);
		}
	}

	private boolean classIsInJar(String filePath) {
		int indexOfFileName = filePath.lastIndexOf(".jar");
		if(indexOfFileName < 1) {
			return false;
		} else {
			return true;
		}
	}

}
