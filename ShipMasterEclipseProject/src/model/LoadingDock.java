package model;

import java.awt.Color;

import ch.judos.generic.data.geometry.DirectedPoint;

/**
 * @since 09.09.2015
 * @author Julian Schelker
 */
public class LoadingDock extends Dock {

	protected ContainerStack stack;

	public LoadingDock(DirectedPoint directedPoint, ContainerStack stack) {
		super(directedPoint);
		this.stack = stack;
	}

	@Override
	public boolean canAccept(Cargo c) {
		for (CargoType t : c.loaded) {
			if (t == CargoType.NONE)
				return true;
		}
		return false;
	}

	@Override
	protected boolean doSingleActionOnCargo(Cargo cargo) {
		if (this.stack.hasAvailableContainers())
			for (int i = 0; i < cargo.loaded.length; i++) {
				if (cargo.loaded[i] == CargoType.NONE) {
					cargo.loaded[i] = this.stack.removeContainer();
					return true;
				}
			}
		return false;
	}

	@Override
	public Color getColor() {
		return CargoType.RED.getColor();
	}
}
