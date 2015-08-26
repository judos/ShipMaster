package model;

import ch.judos.generic.data.RandomJS;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public enum ShipType {
	SMALL(15, 1f, 1), MEDIUM(30, 0.6f, 2), LARGE(45, 0.4f, 4);

	public static ShipType getRandom() {
		int index = RandomJS.getInt(values().length - 1);
		return values()[index];
	}

	private float	speed;
	private int		size;
	private int		cargoSize;

	private ShipType(int size, float speed, int cargo) {
		this.size = size;
		this.speed = speed;
		this.cargoSize = cargo;
	}

	public int getSize() {
		return this.size;
	}

	public float getSpeed() {
		return this.speed;
	}

	public int getCargoSize() {
		return this.cargoSize;
	}

}
