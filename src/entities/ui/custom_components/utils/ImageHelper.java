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
		System.out.println(imagePath);
		File file = new File(imagePath);
		InputStream inputStream = new FileInputStream(file);
		Image image = new Image(inputStream);
		ImageView imageView = new ImageView(image);
		return imageView;
	}
}
