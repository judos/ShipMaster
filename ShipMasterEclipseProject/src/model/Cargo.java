package model;

import java.awt.Color;
import java.util.Iterator;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class Cargo implements Iterable<CargoType> {

	public static Cargo getSingleColor(CargoType c, int amount) {
		CargoType[] load = new CargoType[amount];
		for (int i = 0; i < amount; i++)
			load[i] = c;
		return new Cargo(load);
	}

	public static Cargo getMixed(int amountTotal, CargoType... colors) {
		CargoType[] load = new CargoType[amountTotal];
		for (int i = 0; i < amountTotal; i++) {
			load[i] = CargoType.randomOf(colors);
		}
		return new Cargo(load);
	}

	CargoType[]	loaded;

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
			private int	index	= 0;
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
}
