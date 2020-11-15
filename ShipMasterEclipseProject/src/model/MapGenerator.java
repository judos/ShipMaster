package model;

import model.border.ScreenBorder;
import model.border.ScreenSide;
import model.border.ScreenSideIntervall;
import ch.judos.generic.data.DynHashSet;
import ch.judos.generic.data.geometry.Angle;
import ch.judos.generic.data.geometry.DirectedPoint;
import ch.judos.generic.data.geometry.PointI;

public class MapGenerator {

	public static Map getMap2() {
		Map m = new Map();
		Dock dock1 = new Dock(new DirectedPoint(700, 110, Angle.fromDegree(90)));
		m.addDock(dock1);
		Dock dock2 = new Dock(new DirectedPoint(900, 110, Angle.fromDegree(90)), DockType.FAST);
		dock2.setAcceptedCargo(CargoType.GREEN);
		m.addDock(dock2);
		Dock dock3 = new Dock(new DirectedPoint(1100, 110, Angle.fromDegree(90)), DockType.FAST);
		dock3.setAcceptedCargo(CargoType.BLUE);
		m.addDock(dock3);
		ContainerStack stack = new ContainerStack(10, (ContainerStack s) -> {}, new PointI(1400, 110));
		LoadingDock d1 = new LoadingDock(new DirectedPoint(1300, 110, Angle.fromDegree(90)), stack);
		m.addDock(d1);

		DynHashSet<CargoType> accepted = new DynHashSet<CargoType>(CargoType.YELLOW, CargoType.GREEN, CargoType.BLUE);

		Cargo c1 = Cargo.getSingleColor(5, accepted);
		Cargo c3 = Cargo.getMixed(5, accepted);
		Cargo c2 = Cargo.getSingleColor(1, accepted);

		m.addShip(new Ship(new DirectedPoint(700, 300, Angle.fromDegree(270)), ShipType.LARGE, c3));
		m.addShip(new Ship(new DirectedPoint(900, 300, Angle.fromDegree(270)), ShipType.SMALL, c2));
		m.addShip(new Ship(new DirectedPoint(1100, 300, Angle.fromDegree(270)), ShipType.LARGE, c1));

		m.setSpawnGenerator((shipSize) -> {
			return SpawnLocation.generateSpawnFromBorder(shipSize, new ScreenSideIntervall(ScreenSide.BOTTOM),
				new ScreenSideIntervall(ScreenSide.LEFT, 150, 1080), new ScreenSideIntervall(ScreenSide.RIGHT, 150, 1080));
		});

		m.addBorder(ScreenBorder.leftBorder, ScreenBorder.rightBorder, ScreenBorder.topBorder, ScreenBorder.bottomBorder);

		m.addLand(new PointI(2020, 140), new PointI(1300, 160), new PointI(1100,160), new PointI(-100,120), new PointI(-100, 0), new PointI(2020, 0));

		return m;
	}

	public static Map getMap1() {
		Map m = new Map();
		Dock d1 = new Dock(new DirectedPoint(1000, 100, Angle.fromDegree(90)));
		d1.setAcceptedCargo(CargoType.YELLOW);
		m.addDock(new Dock(new DirectedPoint(600, 110, Angle.fromDegree(80))));
		m.addDock(d1);
		m.addDock(new Dock(new DirectedPoint(1400, 110, Angle.fromDegree(100))));

		DynHashSet<CargoType> accepted = new DynHashSet<CargoType>(CargoType.YELLOW, CargoType.GREEN);

		Cargo c1 = Cargo.getSingleColor(2, accepted);
		Cargo c3 = Cargo.getMixed(8, accepted);
		Cargo c2 = Cargo.getSingleColor(1, accepted);

		m.addShip(new Ship(new DirectedPoint(500, 300, Angle.fromDegree(270)), ShipType.MEDIUM, c3));
		// m.addShip(new Ship(new DirectedPoint(700, 1080,
		// Angle.fromDegree(270)),
		// ShipType.SMALL, c2));
		// m.addShip(new Ship(new DirectedPoint(1000, 1080,
		// Angle.fromDegree(270)),
		// ShipType.SMALL, c1));

		m.setSpawnGenerator((shipSize) -> {
			return SpawnLocation.generateSpawnFromBorder(shipSize, new ScreenSideIntervall(ScreenSide.BOTTOM),
				new ScreenSideIntervall(ScreenSide.LEFT, 150, 1080), new ScreenSideIntervall(ScreenSide.RIGHT, 150, 1080));
		});

		m.addBorder(ScreenBorder.leftBorder, ScreenBorder.rightBorder, ScreenBorder.topBorder, ScreenBorder.bottomBorder);

		m.addLand(new PointI(2020, 140), new PointI(-100, 140), new PointI(-100, 0), new PointI(2020, 0));

		return m;
	}
}
