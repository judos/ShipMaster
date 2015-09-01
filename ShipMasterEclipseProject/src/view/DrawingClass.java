package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @since 01.09.2015
 * @author Julian Schelker
 */
public abstract class DrawingClass {

	protected static BufferedImage load(String name) {
		try {
			return ImageIO.read(new File("data/" + name));
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
