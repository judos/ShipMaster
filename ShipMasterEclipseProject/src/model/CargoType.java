package model;

import java.awt.Color;
import java.util.HashSet;

import ch.judos.generic.data.DynHashSet;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public enum CargoType {

	YELLOW(Color.yellow, Direction.canBeImported),
	PURPLE(new Color(170, 80, 170), Direction.canBeImported),
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
