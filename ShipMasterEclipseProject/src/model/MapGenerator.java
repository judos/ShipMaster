package model;

import model.border.ScreenBorder;
import model.border.ScreenSide;
import model.border.ScreenSideIntervall;
import ch.judos.generic.data.geometry.Angle;
import ch.judos.generic.data.geometry.DirectedPoint;
import ch.judos.generic.data.geometry.PointI;

/**
 * @since 17.05.2015
 * @author Julian Schelker
 */
public class MapGenerator {

	public static Map getMap1() {
		Map m = new Map();
		Dock d1 = new Dock(new DirectedPoint(1000, 100, Angle.fromDegree(90)));
		d1.setAcceptedCargo(CargoType.YELLOW);
		m.addDock(new Dock(new DirectedPoint(600, 110, Angle.fromDegree(80))));
		m.addDock(d1);
		m.addDock(new Dock(new DirectedPoint(1400, 110, Angle.fromDegree(100))));

		Cargo c1 = Cargo.getSingleColor(CargoType.YELLOW, 2);
		Cargo c3 = Cargo.getMixed(8, CargoType.YELLOW, CargoType.GREEN);
		Cargo c2 = Cargo.getSingleColor(CargoType.PURPLE, 1);

		m.addShip(new Ship(new DirectedPoint(500, 300, Angle.fromDegree(270)),
			ShipType.MEDIUM, c3));
		m.addShip(new Ship(new DirectedPoint(700, 1080, Angle.fromDegree(270)),
			ShipType.SMALL, c2));
		m.addShip(new Ship(new DirectedPoint(1000, 1080, Angle.fromDegree(270)),
			ShipType.SMALL, c1));

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
