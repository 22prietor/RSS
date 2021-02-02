package feeder;

import feeder.controller.Controller;
import feeder.views.View;

public class Feeder {
	/**
	 * Main method of the application.
	 * 
	 * @param args	Not used.
	 */
	public static void main(String[] args) {
		new View(new Controller());
	}
}
