package model;

import ch.judos.generic.data.RandomJS;

public enum ShipType {
	SMALL(15, 2.8f, 1, 10), MEDIUM(22, 2f, 2, 6), LARGE(30, 1.6f, 5, 2);

	public static ShipType getRandom() {
		int index = RandomJS.getInt(values().length - 1);
		return values()[index];
	}

	public float speed;
	private int size;
	private int cargoSize;
	private int turnSpeedDgr;

	private ShipType(int size, float speed, int cargo, int turnSpeedDgr) {
		this.size = size;
		this.speed = speed;
		this.cargoSize = cargo;
		this.turnSpeedDgr = turnSpeedDgr;
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
	
	/**
	 * @return in degree
	 */
	public int getTurnSpeed() {
		return this.turnSpeedDgr;
	}

}
