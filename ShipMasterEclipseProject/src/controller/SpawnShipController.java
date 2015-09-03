package controller;

import model.Cargo;
import model.Map;
import model.Ship;
import model.ShipType;
import ch.judos.generic.data.geometry.DirectedPoint;

/**
 * @since 17.05.2015
 * @author Julian Schelker
 */
public class SpawnShipController {

	private static final float spawnShipEveryXSec = 7;

	private Map map;

	private int timer;

	public SpawnShipController(Map map) {
		this.map = map;
		this.timer = 0;
	}

	public void update() {
		this.timer++;
		if (this.timer > Game.FPS * spawnShipEveryXSec) {
			this.timer = 0;

			ShipType type = ShipType.getRandom();
			DirectedPoint spawn = this.map.getSpawnLocation(type.getSize());
			Cargo cargo = this.map.generateCargo(type);
			Ship ship = new Ship(spawn, type, cargo);
			this.map.addShip(ship);
		}
	}

}
