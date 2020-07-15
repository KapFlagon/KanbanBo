package entities.controllers;

import entities.models.Model_NewUser;
import entities.ui.views.View_NewUser;

public class Controller_NewUser {

	private View_NewUser view;
	private Model_NewUser model;

	public Controller_NewUser(View_NewUser view, Model_NewUser model) {
		this.view = view;
		this.model = model;
		initializeViewActions();
	}


	public View_NewUser getView() {
		return view;
	}

	public void setView(View_NewUser view) {
		this.view = view;
	}

	public Model_NewUser getModel() {
		return model;
	}

	public void setModel(Model_NewUser model) {
		this.model = model;
	}

	public void initializeViewActions() {
		view.getCreateUserButton().setOnMouseClicked(event -> {
			System.out.println("Create new user");
		});

	}
}
