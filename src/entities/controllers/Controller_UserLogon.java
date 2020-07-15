package entities.controllers;


import entities.models.Model_UserLogon;
import entities.ui.views.View_UserLogon;


public class Controller_UserLogon {


	private View_UserLogon view;
	private Model_UserLogon model;


	public Controller_UserLogon(View_UserLogon view, Model_UserLogon model) {
		this.view = view;
		this.model = model;
		initializeViewActions();
	}


	public View_UserLogon getView() {
		return view;
	}
	public void setView(View_UserLogon view) {
		this.view = view;
	}
	public Model_UserLogon getModel() {
		return model;
	}
	public void setModel(Model_UserLogon model) {
		this.model = model;
	}


	public void initializeViewActions() {
		view.getLogInButton().setOnMouseClicked(event -> {
			System.out.println("Log in");
		});
		view.getNewUserButton().setOnMouseClicked(event -> {
			System.out.println("New User");
		});
		view.getRememberUserCheckbox().setOnAction(event -> {
			System.out.println("checkbox");
		});
		view.getForgottenPasswordButton().setOnMouseClicked(event -> {
			System.out.println("forgot password");
		});
	}
}
