package model;

import java.io.File;

/**
 * @since 26.08.2015
 * @author Julian Schelker
 */
public class Sounds {
	private static final String path = "data/";

	public static final File BLUB = initSound("blub");

	private static File initSound(String string) {
		return new File(path + string + ".wav");
	}
}
