package model;

import model.border.ScreenSideIntervall;
import ch.judos.generic.data.RandomJS;
import ch.judos.generic.data.geometry.Angle;
import ch.judos.generic.data.geometry.DirectedPoint;
import ch.judos.generic.data.geometry.PointI;

/**
 * @since 17.05.2015
 * @author Julian Schelker
 */
public class SpawnLocation {

	public static DirectedPoint generateSpawnFromBorder(int shipSize,
		ScreenSideIntervall... borders) {
		int spawnBorderIndex = RandomJS.getInt(0, borders.length - 1);
		ScreenSideIntervall spawnBorder = borders[spawnBorderIndex];

		PointI pos = spawnBorder.getRandomPoint(shipSize);
		int angleDegree = spawnBorder.getSide().getAnglePointingInInDegree();
		angleDegree += RandomJS.getInt(-45, 45);
		return new DirectedPoint(pos, Angle.fromDegree(angleDegree));
	}
}
