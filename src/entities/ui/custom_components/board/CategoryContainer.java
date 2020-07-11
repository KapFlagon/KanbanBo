package entities.ui.custom_components.board;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class CategoryContainer extends HBox {

	private String titleString;
	private TextField titleTextField;
	private Category categoryItems;

	public CategoryContainer() {
		titleString = "Name your Category...";
		titleTextField = new TextField(titleString);
		categoryItems = new Category();
	}

	public CategoryContainer(String containerTitle) {

	}

	public void setTitleString(String newTitle) {
		this.titleString = newTitle;
		this.titleTextField.setText(titleString);
	}
	public String getTitleString() {
		return titleString;
	}
	public void setTitleTextField(TextField newTitleTextField) {
		this.titleTextField = newTitleTextField;
		this.titleString = titleTextField.getText();
	}
	public TextField getTitleTextField() {
		return titleTextField;
	}
	public void setCategoryItems(Category newCategoryItems) {
		this.categoryItems = newCategoryItems;
	}
	public Category getCategoryItems() {
		return categoryItems;
	}
}
