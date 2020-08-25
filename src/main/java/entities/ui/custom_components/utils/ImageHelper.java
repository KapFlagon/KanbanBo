package entities.ui.custom_components.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.FileAndDirectoryHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

public class ImageHelper {

	public static ImageView parseImagePathString(String imagePathString) throws FileNotFoundException {
		File file = new File(imagePathString);
		return fileToImageView(file);
	}

	public static ImageView parseFile(File file) throws FileNotFoundException {
		return fileToImageView(file);
	}

	public static ImageView parsePath(Path path) throws FileNotFoundException {
		if(FileAndDirectoryHelper.fileExists(path)) {
			File file = path.toFile();
			return fileToImageView(file);
		} else {
			throw new FileNotFoundException();
		}
	}


	private static void printPathData(File file) {
		try {
			System.out.println("**Path Data**"
					+ "\nAbsolute Path: " + file.getAbsolutePath()
					+ "\nCanonical Path: " + file.getCanonicalPath()
					+ "\nRegular Path: " + file.getPath());
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	private static ImageView fileToImageView(File imageFile) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(imageFile);
		Image image = new Image(inputStream);
		ImageView imageView = new ImageView(image);
		return imageView;
	}
}
