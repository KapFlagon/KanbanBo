package entities.ui.custom_components.shared;

import entities.ui.custom_components.utils.ImageHelper;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.File;

public class ImageEditor extends PopUpWindow{

	private BorderPane layout;
	private AnchorPane anchor;
	private ScrollPane scrollPane;
	private ImageView sourceImage;
	private ImageView croppedImage;
	private Slider zoom;
	private ScrollBar hScroll;
	private ScrollBar vScroll;
	private Button cropBtn;
	private Button cancelBtn;
	private HBox btnBox;
	// TODO Need to implement an overlay which prompts the user on their image cropping selection
	private final int minZoom = 0;
	private final int maxZoom = 200;

	public ImageEditor(ImageView imageView) {
		super("Crop image",400,400);
		this.sourceImage = imageView;
		initScrollPane();
		initCropBtn();
		initCancelBtn();
		initBtnBox();
		initZoom();
		initDisplays();
		updateDisplay();
		initScene(layout);
	}

	public ImageEditor(File file) {
		super("Crop image", 400,400);
		try {
			this.sourceImage = ImageHelper.parseFile(file);
		} catch (Exception e) {
			System.out.println("Error parsing file for Image Editor:\t" + e.toString());
		}
		initScrollPane();
		initCropBtn();
		initCancelBtn();
		initBtnBox();
		initZoom();
		initDisplays();
		updateDisplay();
		initScene(layout);
	}


	public Button getCropBtn() {
		return cropBtn;
	}
	public Button getCancelBtn() {
		return cancelBtn;
	}
	public ImageView getCroppedImage() {
		return croppedImage;
	}


	private void initZoom() {
		zoom = new Slider(minZoom, maxZoom, 0);
		zoom.setOrientation(Orientation.VERTICAL);
		// TODO set behaviour of slider in relation to image zoom.
	}

	private void initScrollPane() {
		scrollPane = new ScrollPane();
		//scrollPane.setContent(sourceImage);
		scrollPane.setMaxSize(400,400);
		scrollPane.pannableProperty().set(true);
		//scrollPane.fitToWidthProperty().set(true);
		scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
		scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
	}

	private void updateDisplay() {
		layout.getChildren().clear();
		anchor.getChildren().clear();
		btnBox.getChildren().clear();

		scrollPane.setContent(sourceImage);

		btnBox.setSpacing(15);
		btnBox.getChildren().addAll(cropBtn, cancelBtn);
		BorderPane.setAlignment(btnBox, Pos.CENTER);
		BorderPane.setMargin(btnBox, new Insets(5,5,5,5));
		BorderPane.setAlignment(zoom, Pos.CENTER);
		BorderPane.setMargin(zoom, new Insets(5,5,5,5));
		layout.setCenter(scrollPane);
		layout.setRight(zoom);
		layout.setBottom(btnBox);
		layout.setPadding(new Insets(10,10,10,10));

		layout.setMaxSize(400,400);
		layout.setMinSize(100,100);
		//layout.getChildren().addAll(anchor, scrollPane);
	}

	private void initCropBtn() {
		cropBtn = new Button("Crop");
		cropBtn.setOnAction(event -> {
			triggerCropping();
			close();
		});
	}

	private void initCancelBtn() {
		cancelBtn = new Button("Cancel");
		cancelBtn.setOnAction(butEvent -> {
			close();
		});
	}

	private void initBtnBox() {
		btnBox = new HBox();
	}

	private void initDisplays() {
		layout = new BorderPane();
		layout.setPrefSize(400,400);
		anchor = new AnchorPane();
	}

	public void triggerCropping() {
		// TODO logic here to initialize the cropped ImageView variable with currently highlighted segments of image
	}
}
