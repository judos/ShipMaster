package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import model.CargoType;
import ch.judos.generic.graphics.filter.ColorTintFilter;

/**
 * @since 15.09.2015
 * @author Julian Schelker
 */
public class Assets {

	public static BufferedImage grass;
	public static BufferedImage[] water;
	public static BufferedImage rock;
	public static BufferedImage container;
	public static HashMap<Color, BufferedImage> containers;
	public static BufferedImage ship5;

	private static boolean isLoaded = false;
	private static boolean isLoading = false;

	public static boolean isLoaded() {
		return isLoaded;
	}

	public static void waitUntilAssetsAreLoaded() {
		synchronized (Assets.class) {
			while (!isLoaded) {
				try {
					Assets.class.wait();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * checked method, synchronous
	 * 
	 * @throws IllegalStateException
	 *             when the assets are already being loaded or loaded
	 */
	public static void load() {
		synchronized (Assets.class) {
			if (isLoading)
				throw new IllegalStateException("Loading the assets is already in progress");
			if (isLoaded)
				throw new IllegalStateException("The assets are already loaded");
			isLoading = true;
		}
		loadAllAssets();
		synchronized (Assets.class) {
			isLoading = false;
			isLoaded = true;
			Assets.class.notifyAll();
		}
	}

	/**
	 * unsynchronized, unchecked method
	 */
	private static void loadAllAssets() {
		System.out.println("load assets");
		grass = load("grass.png");
		water = new BufferedImage[16];
		rock = load("rockwall.png");
		container = load("container.png");
		ship5 = load("ship5.png");
		BufferedImage waterFramed = load("water2-q40.jpg");
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				water[y * 4 + x] = waterFramed.getSubimage(x * 1024, y * 1024, 1024, 1024);
			}
		}
		containers = new HashMap<>();
		for (CargoType type : CargoType.getAllCargoTypes()) {
			Color c = type.getColor();
			ColorTintFilter filter = new ColorTintFilter(c, 0.75f);
			containers.put(c, filter.filter(container));
		}
	}

	public static BufferedImage load(String name) {
		try {
			return ImageIO.read(new File("data/" + name));
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Dimension dimensionOf(BufferedImage image) {
		return new Dimension(image.getWidth(), image.getHeight());
	}
}
