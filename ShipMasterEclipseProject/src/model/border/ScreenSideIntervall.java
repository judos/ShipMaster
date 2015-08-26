package model.border;

import model.Screen;
import ch.judos.generic.data.RandomJS;
import ch.judos.generic.data.geometry.PointI;

/**
 * @since 26.08.2015
 * @author Julian Schelker
 */
public class ScreenSideIntervall {
	private int max;
	private int min;
	private ScreenSide side;

	public ScreenSideIntervall(ScreenSide side, int min, int max) {
		this.side = side;
		this.min = min;
		this.max = max;
	}

	public ScreenSideIntervall(ScreenSide side) {
		this.side = side;
		this.min = 0;
		this.max = (side == ScreenSide.LEFT || side == ScreenSide.RIGHT) ? Screen.height
			: Screen.width;
	}

	public ScreenSide getSide() {
		return this.side;
	}

	public PointI getRandomPoint(int moveOut) {
		switch (this.side) {
			case LEFT : {
				int y = RandomJS.getInt(this.min + moveOut, this.max - moveOut);
				return new PointI(-moveOut, y);
			}
			case BOTTOM : {
				int x = RandomJS.getInt(this.min + moveOut, this.max - moveOut);
				return new PointI(x, Screen.height + moveOut);
			}
			case RIGHT : {
				int y = RandomJS.getInt(this.min + moveOut, this.max - moveOut);
				return new PointI(Screen.width + moveOut, y);
			}
			case TOP :
			default : {
				int x = RandomJS.getInt(this.min + moveOut, this.max - moveOut);
				return new PointI(x, -moveOut);
			}
		}
	}
}
