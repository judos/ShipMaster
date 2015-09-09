package model;

import ch.judos.generic.data.geometry.PointI;
import controller.Game;

/**
 * @since 25.05.2015
 * @author Julian Schelker
 */
public class Attention {

	public static final int signSize = 50;
	private static final float showTimeS = 2;

	private PointI position;
	private double created;

	public Attention(PointI position) {
		if (position.x < 0)
			position.x = signSize / 2;
		if (position.x > 1920)
			position.x = 1920 - signSize / 2;
		if (position.y < 0)
			position.y = signSize / 2;
		if (position.y > 1080)
			position.y = 1080 - signSize / 2;
		this.position = position;
		this.created = Game.getGameTime();
	}

	public boolean shouldBeDeleted() {
		return Game.getGameTime() - this.created >= showTimeS;
	}

	public PointI getPosition() {
		return this.position;
	}

}
