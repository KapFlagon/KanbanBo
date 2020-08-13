package entities.ui.custom_components.menus;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class FileMenu extends Menu {

	MenuItem newFile;
	MenuItem openFile;
	Menu recentItemsHeader;
	ArrayList<MenuItem> recentItemsList;
	MenuItem closeFile;
	MenuItem saveFile;
	MenuItem saveFileAs;
	MenuItem exitApp;


	public FileMenu() {
		this.setText("File");
		initNewFile();
		initOpenFile();
		initRecentItemsHeader();
		// TODO Need to provide the "Recent Items" menu with data
		initRecentItemsList();
		updateRecentItemsHeader();
		initCloseFile();
		initSaveFile();
		initSaveFileAs();
		initExitApp();
		addAllItemsToMenu();
	}


	// Getters and Setters
	public MenuItem getNewFile() {
		return newFile;
	}

	public void setNewFile(MenuItem newFile) {
		this.newFile = newFile;
	}

	public MenuItem getOpenFile() {
		return openFile;
	}

	public void setOpenFile(MenuItem openFile) {
		this.openFile = openFile;
	}

	public Menu getRecentItemsHeader() {
		return recentItemsHeader;
	}

	public void setRecentItemsHeader(Menu recentItemsHeader) {
		this.recentItemsHeader = recentItemsHeader;
	}

	public ArrayList<MenuItem> getRecentItemsList() {
		return recentItemsList;
	}

	public void setRecentItemsList(ArrayList<MenuItem> recentItemsList) {
		this.recentItemsList = recentItemsList;
	}

	public MenuItem getCloseFile() {
		return closeFile;
	}

	public void setCloseFile(MenuItem closeFile) {
		this.closeFile = closeFile;
	}

	public MenuItem getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(MenuItem saveFile) {
		this.saveFile = saveFile;
	}

	public MenuItem getSaveFileAs() {
		return saveFileAs;
	}

	public void setSaveFileAs(MenuItem saveFileAs) {
		this.saveFileAs = saveFileAs;
	}

	public MenuItem getExitApp() {
		return exitApp;
	}

	public void setExitApp(MenuItem exitApp) {
		this.exitApp = exitApp;
	}


	// Initialisation methods
	private void initNewFile() {
		newFile = new MenuItem("New");
	}

	private void initOpenFile() {
		openFile = new MenuItem("Open");
	}

	private void initRecentItemsHeader() {
		recentItemsHeader = new Menu("Recent Items");
	}

	private void initRecentItemsList() {
		// TODO get list of recent items, convert to sub items
		recentItemsList = new ArrayList<MenuItem>();
	}

	private void initCloseFile() {
		closeFile = new MenuItem("Close");
	}

	private void initSaveFile() {
		saveFile = new MenuItem("Save");
	}

	private void initSaveFileAs() {
		saveFileAs = new MenuItem("Save As");
	}

	private void initExitApp() {
		exitApp = new MenuItem("Exit");
	}


	// Other methods
	private void updateRecentItemsHeader() {
		for (MenuItem menuItem : recentItemsList) {
			recentItemsHeader.getItems().add(menuItem);
		}
	}

	private void addAllItemsToMenu() {
		this.getItems().add(newFile);
		this.getItems().add(openFile);
		this.getItems().add(recentItemsHeader);
		this.getItems().add(closeFile);
		this.getItems().add(saveFile);
		this.getItems().add(saveFileAs);
		this.getItems().add(exitApp);
	}

	private void updateRecentItemsList() {
		// TODO Maybe use this method to insert a new item
	}
}
