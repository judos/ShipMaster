package model;

import java.awt.Color;
import java.util.HashSet;

import ch.judos.generic.data.DynHashSet;
import ch.judos.generic.data.RandomJS;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public enum CargoType {
	YELLOW(Color.yellow), PURPLE(new Color(170, 80, 170)), GREEN(Color.green), NONE(new Color(
		0, 0, 0, 0));

	public static HashSet<CargoType> getAllCargoTypes() {
		HashSet<CargoType> result = new DynHashSet<>(CargoType.values());
		result.remove(CargoType.NONE);
		return result;
	}

	public static CargoType randomOf(CargoType[] colors) {
		int index = RandomJS.getInt(0, colors.length - 1);
		return colors[index];
	}

	private Color	color;

	private CargoType(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}
}
