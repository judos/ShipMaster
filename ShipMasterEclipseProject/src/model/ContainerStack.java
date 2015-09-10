package model;

import java.awt.Color;
import java.util.function.Consumer;

import ch.judos.generic.data.geometry.PointI;
import controller.Game;

/**
 * @since 09.09.2015
 * @author Julian Schelker
 */
public class ContainerStack {

	private int size;
	private int maxSize;
	private float stackUpSpeed;
	private int timer;
	private Consumer<ContainerStack> overfillAction;
	private PointI position;

	public ContainerStack(int maxSize, Consumer<ContainerStack> overfillAction, PointI position) {
		this.size = 0;
		this.maxSize = maxSize;
		this.stackUpSpeed = 4.f;
		this.overfillAction = overfillAction;
		this.position = position;
	}

	public boolean hasAvailableContainers() {
		return this.size > 0;
	}

	public CargoType removeContainer() {
		if (this.size > 0) {
			this.size--;
			return CargoType.RED;
		}
		return null;
	}

	public void update() {
		this.timer++;
		if (this.timer >= Game.FPS * this.stackUpSpeed) {
			this.timer = 0;
			this.size++;
			if (this.size > this.maxSize) {
				this.overfillAction.accept(this);
			}
		}
	}

	public int getSize() {
		return this.size;
	}

	public int getMaxSize() {
		return this.maxSize;
	}

	public PointI getPosition() {
		return this.position;
	}

	public Color getColor() {
		return CargoType.RED.getColor();
	}

}
