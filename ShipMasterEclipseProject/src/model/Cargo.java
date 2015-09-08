package model;

import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;

import ch.judos.generic.data.DynamicList;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class Cargo implements Iterable<CargoType> {

	/**
	 * @return only imported cargo types
	 */
	public static Cargo getSingleColor(int amount, HashSet<CargoType> allowedCargoType) {
		// check pre condition: one of the allowed types can be imported
		DynamicList<CargoType> canBeImported = getCargoTypeThatCanBeImportedFrom(allowedCargoType);
		CargoType c = canBeImported.getRandomElement();
		CargoType[] load = new CargoType[amount];
		for (int i = 0; i < amount; i++)
			load[i] = c;
		return new Cargo(load);
	}

	private static DynamicList<CargoType> getCargoTypeThatCanBeImportedFrom(
		Iterable<CargoType> allowed) {
		DynamicList<CargoType> canBeImported = new DynamicList<CargoType>();
		for (CargoType c : allowed) {
			if (c.canBeImported())
				canBeImported.add(c);
		}
		if (canBeImported.size() == 0)
			throw new RuntimeException("No allowed cargo can be imported.");
		return canBeImported;
	}

	/**
	 * @return only imported cargo types
	 */
	public static Cargo getMixed(int amountTotal, HashSet<CargoType> colors) {
		DynamicList<CargoType> possibleCargoType = getCargoTypeThatCanBeImportedFrom(colors);
		CargoType[] load = new CargoType[amountTotal];
		for (int i = 0; i < amountTotal; i++) {
			load[i] = possibleCargoType.getRandomElement();
		}
		return new Cargo(load);
	}

	CargoType[] loaded;

	private Cargo(CargoType[] loaded) {
		this.loaded = loaded;
	}

	public int getSize() {
		return this.loaded.length;
	}

	public Color getColorOf(int index) {
		return this.loaded[index].getColor();
	}

	public CargoType getTypeAt(int i) {
		return this.loaded[i];
	}

	public void setEmpty(int i) {
		this.loaded[i] = CargoType.NONE;
	}

	public boolean isEmpty() {
		for (CargoType t : this.loaded) {
			if (t != CargoType.NONE)
				return false;
		}
		return true;
	}

	@Override
	public Iterator<CargoType> iterator() {
		return new Iterator<CargoType>() {
			private int index = 0;
			@Override
			public boolean hasNext() {
				return this.index < getSize();
			}

			@Override
			public CargoType next() {
				return Cargo.this.loaded[this.index++];
			}

		};
	}

	public boolean isAllowedToBeExported() {
		for (CargoType t : this.loaded) {
			if (!t.canBeExported())
				return false;
		}
		return true;
	}
}
