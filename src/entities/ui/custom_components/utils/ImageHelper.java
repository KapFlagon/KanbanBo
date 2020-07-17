package entities.ui.custom_components.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageHelper {

	public static ImageView parseImagePath(String imagePath) throws FileNotFoundException {
		File file = newFile(imagePath);
		printPathData(file);
		InputStream inputStream = newInputStream(file);
		Image image = newImage(inputStream);
		ImageView imageView = newImageView(image);
		return imageView;
	}

	public static ImageView parseFile(File file) throws FileNotFoundException {
		printPathData(file);
		InputStream inputStream = newInputStream(file);
		Image image = newImage(inputStream);
		ImageView imageView = newImageView(image);
		return imageView;
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

	private static File newFile(String imagePath) {
		return new File(imagePath);
	}

	private static InputStream newInputStream(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}

	private static Image newImage(InputStream inputStream) {
		return new Image(inputStream);
	}

	private static ImageView newImageView(Image image){
		return new ImageView(image);
	}
}
