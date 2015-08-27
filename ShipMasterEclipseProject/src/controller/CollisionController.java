package controller;

import model.Map;
import model.Ship;
import model.Sounds;
import ch.judos.generic.sound.ClipSound;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class CollisionController {

	private Map map;
	private Runnable pauseGameMethod;

	public CollisionController(Map map, Runnable pauseGame) {
		this.map = map;
		this.pauseGameMethod = pauseGame;
	}

	public void update(boolean gameIsPaused) {
		for (Ship s1 : this.map.getShips()) {
			for (Ship s2 : this.map.getShips()) {
				if (s1 != s2) {
					int size1 = s1.getType().getSize();
					int size2 = s2.getType().getSize();

					double remainingSpace = s1.getPoint().distance(s2.getPoint())
						- (size1 + size2);

					if (remainingSpace < 2 * Ship.dangerRadius * 4) {
						s1.setInDanger(true);
						s2.setInDanger(true);
					}

					if (remainingSpace <= 0 && !gameIsPaused) {
						ClipSound.playClipAsync(Sounds.BLUB);
						this.pauseGameMethod.run();
					}
				}
			}
		}
	}

}
