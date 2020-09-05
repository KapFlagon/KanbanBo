package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;

public class SkinsHelper {

	// Other methods
	public static boolean doesSkinsFolderExist() throws URISyntaxException {
		Path userSkinsFolderPath = ProgramDirectoryHelper.parseCssFolderPath();
		return FileAndDirectoryHelper.directoryExists(userSkinsFolderPath);
	}

	public static void createSkinsFolder() throws IOException, URISyntaxException {
		Path userSkinsFolderPath = ProgramDirectoryHelper.parseCssFolderPath();
		FileAndDirectoryHelper.createDirectory(userSkinsFolderPath);
	}

	public static ArrayList<Path> getCustomSkinsFilePaths() throws URISyntaxException {
		Path userSkinsFolderPath = ProgramDirectoryHelper.parseCssFolderPath();
		File directoryFile = new File(userSkinsFolderPath.toString());
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

	private static void createTemplate_LightSkinFile() throws URISyntaxException, IOException {
		InputStream lightFileSourceInputStream = getLightSourceSkinAsInputStream();
		Path targetPath = buildTargetTemplateFileName("KanbanBo_Light_Template");
		createTemplateSkin(lightFileSourceInputStream, targetPath);
	}

	public static void createTemplate_DarkSkinFile() throws URISyntaxException, IOException {
		InputStream darkFileSourceInputStream = getDarkSourceSkinAsInputStream();
		Path targetPath = buildTargetTemplateFileName("KanbanBo_Dark_Template");
		createTemplateSkin(darkFileSourceInputStream, targetPath);
	}

	private static void createTemplateSkin(InputStream sourceFileInputStream, Path targetFilePath) throws IOException {
		Files.copy(sourceFileInputStream, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
	}

	private static Path buildTargetTemplateFileName(String fileNameString) throws URISyntaxException {
		Path userSkinsFolderPath = ProgramDirectoryHelper.parseCssFolderPath();
		Path fullFilePath = Paths.get(userSkinsFolderPath.toString(), fileNameString);
		return fullFilePath;
	}

	private static InputStream getLightSourceSkinAsInputStream() {
		return SkinsHelper.class.getClassLoader().getResourceAsStream("default_skins/KanbanBo_Light.css");
	}

	private static InputStream getDarkSourceSkinAsInputStream() {
		return SkinsHelper.class.getClassLoader().getResourceAsStream("default_skins/KanbanBo_Dark.css");
	}

}
