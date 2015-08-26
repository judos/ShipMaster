package model.border;

/**
 * @since 26.08.2015
 * @author Julian Schelker
 */
public enum ScreenSide {
	LEFT(0), TOP(90), RIGHT(180), BOTTOM(270);

	private int angleInDegreePointingIn;

	private ScreenSide(int angle) {
		this.angleInDegreePointingIn = angle;
	}

	public int getAnglePointingInInDegree() {
		return this.angleInDegreePointingIn;
	}
}
