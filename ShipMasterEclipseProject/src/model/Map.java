package model;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Function;

import model.border.Border;
import model.border.RestrictionBorder;
import view.MapDrawer;
import ch.judos.generic.data.DynamicList;
import ch.judos.generic.data.RandomJS;
import ch.judos.generic.data.geometry.DirectedPoint;
import ch.judos.generic.data.geometry.PointI;
import ch.judos.generic.graphics.Drawable2d;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class Map {

	private ArrayList<Dock> docks;
	private ArrayList<Ship> ships;
	private Function<Integer, DirectedPoint> spawnGenerator;
	private DynamicList<Border> borders;
	private DynamicList<Polygon> landPolygons;
	private DynamicList<Attention> attentions;

	public Map() {
		this.docks = new ArrayList<>();
		this.ships = new ArrayList<>();
		this.borders = new DynamicList<>();
		this.landPolygons = new DynamicList<>();
		this.attentions = new DynamicList<>();
	}

	public DynamicList<Attention> getAttentions() {
		return this.attentions;
	}

	public DynamicList<Polygon> getLandPolygons() {
		return this.landPolygons;
	}

	public void addShip(Ship ship) {
		this.ships.add(ship);
		this.attentions.add(new Attention(ship.getPoint().i()));
	}

	void addDock(Dock dock) {
		this.docks.add(dock);
	}

	public Drawable2d getDrawable() {
		return new MapDrawer(this);
	}

	public ArrayList<Dock> getDocks() {
		return this.docks;
	}

	public ArrayList<Ship> getShips() {
		return this.ships;
	}

	public DirectedPoint getSpawnLocation(int sizeBoat) {
		return this.spawnGenerator.apply(sizeBoat);
	}

	public Cargo generateCargo(ShipType type) {
		HashSet<CargoType> cargoTypes = new HashSet<>();
		for (Dock d : this.docks) {
			cargoTypes.addAll(d.getAcceptedCargo());
		}
		CargoType[] acceptedCargoArr = cargoTypes.toArray(new CargoType[]{});
		int cargoAmount = type.getCargoSize();
		if (RandomJS.getTrueWithProb(25))
			return Cargo.getMixed(cargoAmount, acceptedCargoArr);
		int cargoIndex = RandomJS.getInt(0, cargoTypes.size() - 1);
		CargoType selectedCargo = acceptedCargoArr[cargoIndex];
		return Cargo.getSingleColor(selectedCargo, cargoAmount);
	}

	public void setSpawnGenerator(Function<Integer, DirectedPoint> generator) {
		this.spawnGenerator = generator;
	}

	public void addBorder(Border... newBorders) {
		if (newBorders == null)
			throw new NullPointerException("newBorders can not be null");
		this.borders.addAll(newBorders);
	}

	public void addLand(PointI... points) {
		Polygon poly = new Polygon();
		PointI last = null;
		for (PointI point : points) {
			poly.addPoint(point.x, point.y);
			if (last != null) {
				addBorder(new RestrictionBorder(last, point));
			}
			last = point;
		}
		this.landPolygons.add(poly);
	}

	public DynamicList<Border> getBorders() {
		return this.borders;
	}
}
