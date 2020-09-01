package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FileAndDirectoryHelper {


	private static boolean pathItemExists(Path itemPath) {
		File itemTester = itemPath.toFile();
		return itemTester.exists();
	}

	public static boolean fileExists(Path filePath) {
		if(pathItemExists(filePath)) {
			File fileTester = filePath.toFile();
			return fileTester.isFile();
		} else {
			return false;
		}
	}

	public static boolean directoryExists(Path directoryPath) {
		if(pathItemExists(directoryPath)) {
			File directoryTester = directoryPath.toFile();
			return directoryTester.isDirectory();
		} else {
			return false;
		}
	}

	public static void createFile(Path path) throws IOException {
		File file = path.toFile();
		file.createNewFile();
	}

	public static void createDirectory(Path path) throws IOException {
		File file = path.toFile();
		if(!file.mkdirs()) {
			throw new IOException();
		}
	}

	public static Path getFileName(Path path) {
		if (fileExists(path)) {
			return path.getFileName();
		} else {
			return null;
		}
	}
}
