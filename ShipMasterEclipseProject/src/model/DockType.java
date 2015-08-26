package model;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public enum DockType {

	NORMAL(2f), FAST(1.5f);

	private float	unloadPerSecond;

	private DockType(float unloadPerSecond) {
		this.unloadPerSecond = unloadPerSecond;
	}

	/**
	 * @return the unloadPerSecond
	 */
	public float getUnloadPerSecond() {
		return this.unloadPerSecond;
	}
}
