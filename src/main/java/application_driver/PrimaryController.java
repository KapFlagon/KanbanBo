package application_driver;


import entities.controllers.I_SubController;

public class PrimaryController {

	private ModelManager modelManager;
	private ViewManager viewManager;
	private PROGRAM_STATE program_state;
	private I_SubController currentSubController;

	public void PrimaryController(ModelManager modelManager, ViewManager viewManager) {
		this.modelManager = modelManager;
		this.viewManager = viewManager;

		program_state = PROGRAM_STATE.STARTUP;
		/*
		Have application_driver.ModelManager load and read properties.
		If there is data, load it and display the appropriate view (Start screen, or Home screen)
		 */
	}

	public void changeProgramState(PROGRAM_STATE new_state) {
		this.program_state = new_state;
	}

	public void loadStartUp() {

	}
}
