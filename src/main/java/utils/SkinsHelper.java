package utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;

public class SkinsHelper {

	// Other methods
	private static boolean doesSkinsFolderExist(Path skinsFolderPath) {
		return FileAndDirectoryHelper.directoryExists(skinsFolderPath);
	}

	public static void createSkinsFolder() throws IOException, URISyntaxException {
		Path userSkinsFolderPath = ProgramDirectoryHelper.parseCssFolderPath();
		FileAndDirectoryHelper.createDirectory(userSkinsFolderPath);
	}

	public static ArrayList<Path> getCustomSkinsFilePaths() throws URISyntaxException {
		Path cssFolderPath = ProgramDirectoryHelper.parseCssFolderPath();
		File directoryFile = new File(cssFolderPath.toString());
		File[] fileArray = directoryFile.listFiles();
		ArrayList<Path> arrayListOfPaths = new ArrayList<Path>();
		for (int arrayIterator = 0; arrayIterator < fileArray.length; arrayIterator++) {
			Path tempPath = Paths.get(fileArray[arrayIterator].getPath());
			if(isCSSFile(tempPath)) {
				arrayListOfPaths.add(tempPath);
			}
		}
		return arrayListOfPaths;
	}

	private static boolean isCSSFile(Path passedFile) {
		PathMatcher pathExtensionMatcher = FileSystems.getDefault().getPathMatcher("glob:*.{css,CSS}");
		if (pathExtensionMatcher.matches(passedFile)) {
			return true;
		} else {
			return false;
		}
	}

	private static void createTemplate_LightSkinFile() {

		//createTemplateSkin();
	}

	public static void createTemplate_DarkSkinFile() {

		//createTemplateSkin();
	}

	private static void createTemplateSkin(File originalFile) {
		// TODO decide on name of template file
		// TODO create a new Path, extending the stylesFolderPath value with the new file name
		// TODO Push data into the File object, copying a standard template style file
		// TODO Create the actual system file
	}

}
