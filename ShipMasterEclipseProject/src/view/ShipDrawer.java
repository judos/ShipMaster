package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.Cargo;
import model.CargoType;
import model.Ship;
import model.ShipType;
import ch.judos.generic.data.geometry.PointF;
import ch.judos.generic.data.geometry.PointI;
import ch.judos.generic.graphics.GraphicsUtils;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class ShipDrawer {

	private static final Color pathColor = new Color(255, 255, 255, 128);

	private static boolean initialized = false;
	private static Dimension containerSize;

	private static void initalize() {
		if (initialized)
			return;
		containerSize = Assets.dimensionOf(Assets.container);
		initialized = true;
	}

	public static void drawShip(Graphics2D g, Ship s) {
		initalize();
		GraphicsUtils gUtil = () -> g;
		PointF pos = s.getPoint();
		g.translate(pos.getXI(), pos.getYI());
		g.rotate(s.getDirection().getRadian());
		ShipType type = s.getType();
		if (type == ShipType.SMALL) {
			gUtil.drawCentered(Assets.ship1);
			drawShipCargoSmall(g, s);
		}
		else {
			gUtil.drawCentered(Assets.ship5);
			drawShipCargoLarge(g, s);
		}
	}

	private static void drawShipCargoSmall(Graphics2D g, Ship s) {
		Cargo cargo = s.getCargo();
		if (cargo.getTypeAt(0) != CargoType.NONE) {
			BufferedImage im = Assets.containers.get(cargo.getColorOf(0));
			g.drawImage(im, -containerSize.width / 2, -containerSize.height / 2, null);
		}
	}

	private static void drawShipCargoLarge(Graphics2D g, Ship s) {
		Cargo cargo = s.getCargo();
		int total = cargo.getSize();

		float y = -(float) total / 2.f * containerSize.height;
		g.rotate(Math.PI / 2);
		for (int i = 0; i < cargo.getSize(); i++) {
			if (cargo.getTypeAt(i) != CargoType.NONE) {
				BufferedImage im = Assets.containers.get(cargo.getColorOf(i));
				g.drawImage(im, -containerSize.width / 2, (int) y, null);
			}
			y += containerSize.height;
		}
		g.rotate(-Math.PI / 2);
	}

	public static void drawPath(Graphics2D g, Ship s) {
		g.setColor(pathColor);
		ArrayList<PointI> path = s.getPath();
		PointI lastPoint = s.getPoint().i();
		GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, path.size());
		polygon.moveTo(lastPoint.x, lastPoint.y);
		for (PointI point : path) {
			polygon.lineTo(point.x, point.y);
			lastPoint = point;
		}
		g.draw(polygon);
	}

	public static void drawShipInDanger(Graphics2D g, Ship s) {
		PointF pos = s.getPoint();
		g.translate(pos.getXI(), pos.getYI());

		ShipType type = s.getType();
		int size = type.getSize();

		g.setColor(Color.red);
		int r = Ship.dangerRadius;
		g.fillOval(-size - r, -size - r, (size + r) * 2, (size + r) * 2);
		s.setInDanger(false);
	}

}
