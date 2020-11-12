package model;

import ch.judos.generic.data.RandomJS;

public enum ShipType {
	SMALL(15, 2.8f, 1), MEDIUM(22, 2f, 2), LARGE(30, 1.6f, 5);

	public static ShipType getRandom() {
		int index = RandomJS.getInt(values().length - 1);
		return values()[index];
	}

	public float speed;
	private int size;
	private int cargoSize;

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
