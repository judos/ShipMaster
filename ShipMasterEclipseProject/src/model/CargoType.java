package model;

import java.awt.Color;
import java.util.HashSet;

import ch.judos.generic.data.DynHashSet;

public enum CargoType {

	YELLOW(Color.yellow, Direction.canBeImported),
	BLUE(Color.blue, Direction.canBeImported),
	GREEN(Color.green, Direction.canBeImported),
	NONE(new Color(0, 0, 0, 0), Direction.canBeExported),
	RED(Color.red, Direction.canBeExported);

	public static HashSet<CargoType> getAllCargoTypes() {
		HashSet<CargoType> result = new DynHashSet<>(CargoType.values());
		result.remove(CargoType.NONE);
		return result;
	}

	private Color color;
	private Direction direction;

	private CargoType(Color color, Direction direction) {
		this.color = color;
		this.direction = direction;
	}

	public Color getColor() {
		return this.color;
	}

	public boolean canBeImported() {
		return this.direction == Direction.canBeImported;
	}

	public boolean canBeExported() {
		return this.direction == Direction.canBeExported;
	}

	enum Direction {
		canBeImported, canBeExported;
	}

}
