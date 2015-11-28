package model;

import model.border.ScreenBorder;
import model.border.ScreenSide;
import model.border.ScreenSideIntervall;
import ch.judos.generic.data.DynHashSet;
import ch.judos.generic.data.geometry.Angle;
import ch.judos.generic.data.geometry.DirectedPoint;
import ch.judos.generic.data.geometry.PointI;

/**
 * @since 17.05.2015
 * @author Julian Schelker
 */
public class MapGenerator {

	public static Map getMap2() {
		Map m = new Map();
		m.addDock(new Dock(new DirectedPoint(800, 110, Angle.fromDegree(80))));

		ContainerStack stack = new ContainerStack(10, (ContainerStack s) -> {
		}, new PointI(1100, 100));
		LoadingDock d1 = new LoadingDock(new DirectedPoint(1200, 100, Angle.fromDegree(100)),
			stack);
		m.addDock(d1);

		DynHashSet<CargoType> accepted = new DynHashSet<CargoType>(CargoType.YELLOW,
			CargoType.GREEN);

		Cargo c1 = Cargo.getSingleColor(2, accepted);
		Cargo c3 = Cargo.getMixed(5, accepted);
		Cargo c2 = Cargo.getSingleColor(1, accepted);

		m.addShip(new Ship(new DirectedPoint(500, 300, Angle.fromDegree(270)), ShipType.LARGE,
			c3));
		// m.addShip(new Ship(new DirectedPoint(700, 1080,
		// Angle.fromDegree(270)),
		// ShipType.SMALL, c2));
		// m.addShip(new Ship(new DirectedPoint(1000, 1080,
		// Angle.fromDegree(270)),
		// ShipType.SMALL, c1));

		m.setSpawnGenerator((shipSize) -> {
			return SpawnLocation.generateSpawnFromBorder(shipSize, new ScreenSideIntervall(
				ScreenSide.BOTTOM), new ScreenSideIntervall(ScreenSide.LEFT, 150, 1080),
				new ScreenSideIntervall(ScreenSide.RIGHT, 150, 1080));
		});

		m.addBorder(ScreenBorder.leftBorder, ScreenBorder.rightBorder, ScreenBorder.topBorder,
			ScreenBorder.bottomBorder);

		m.addLand(new PointI(2020, 140), new PointI(-100, 140), new PointI(-100, 0),
			new PointI(2020, 0));

		ShipType.SMALL.speed *= 2;
		ShipType.MEDIUM.speed *= 2;
		ShipType.LARGE.speed *= 2;

		return m;
	}

	public static Map getMap1() {
		Map m = new Map();
		Dock d1 = new Dock(new DirectedPoint(1000, 100, Angle.fromDegree(90)));
		d1.setAcceptedCargo(CargoType.YELLOW);
		m.addDock(new Dock(new DirectedPoint(600, 110, Angle.fromDegree(80))));
		m.addDock(d1);
		m.addDock(new Dock(new DirectedPoint(1400, 110, Angle.fromDegree(100))));

		DynHashSet<CargoType> accepted = new DynHashSet<CargoType>(CargoType.YELLOW,
			CargoType.GREEN);

		Cargo c1 = Cargo.getSingleColor(2, accepted);
		Cargo c3 = Cargo.getMixed(8, accepted);
		Cargo c2 = Cargo.getSingleColor(1, accepted);

		m.addShip(new Ship(new DirectedPoint(500, 300, Angle.fromDegree(270)),
			ShipType.MEDIUM, c3));
		// m.addShip(new Ship(new DirectedPoint(700, 1080,
		// Angle.fromDegree(270)),
		// ShipType.SMALL, c2));
		// m.addShip(new Ship(new DirectedPoint(1000, 1080,
		// Angle.fromDegree(270)),
		// ShipType.SMALL, c1));

		m.setSpawnGenerator((shipSize) -> {
			return SpawnLocation.generateSpawnFromBorder(shipSize, new ScreenSideIntervall(
				ScreenSide.BOTTOM), new ScreenSideIntervall(ScreenSide.LEFT, 150, 1080),
				new ScreenSideIntervall(ScreenSide.RIGHT, 150, 1080));
		});

		m.addBorder(ScreenBorder.leftBorder, ScreenBorder.rightBorder, ScreenBorder.topBorder,
			ScreenBorder.bottomBorder);

		m.addLand(new PointI(2020, 140), new PointI(-100, 140), new PointI(-100, 0),
			new PointI(2020, 0));

		return m;
	}
}
