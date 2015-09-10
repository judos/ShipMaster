package controller;

import model.ContainerStack;
import model.Map;

/**
 * @since 11.09.2015
 * @author Julian Schelker
 */
public class UpdateController {

	private Map map;

	public UpdateController(Map map) {
		this.map = map;
	}

	public void update() {
		for (ContainerStack stack : this.map.getStacks()) {
			stack.update();
		}
	}

}
