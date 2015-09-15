package controller;

import model.ContainerStack;
import model.Map;

/**
 * @since 11.09.2015
 * @author Julian Schelker
 */
public class UpdateController {

	private Map map;
	private Runnable actionContainerStackFull;

	public UpdateController(Map map, Runnable actionContainerStackFull) {
		this.map = map;
		this.actionContainerStackFull = actionContainerStackFull;
	}

	public void update() {
		for (ContainerStack stack : this.map.getStacks()) {
			stack.update();
			if (stack.isOverloaded())
				this.actionContainerStackFull.run();
		}
	}

}
