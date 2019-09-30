package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
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

	private static final Color pathColor = new Color(255, 255, 255);

	public static void drawShip(Graphics2D g, Ship s) {
		GraphicsUtils gUtil = () -> g;
		PointF pos = s.getPoint();
		g.translate(pos.getXI(), pos.getYI());
		g.rotate(s.getDirection().getRadian());
		ShipType type = s.getType();
		if (type == ShipType.LARGE)
			gUtil.drawCentered(Assets.ship5);
		else
			gUtil.drawCentered(Assets.ship5);
		drawShipCargo(g, s);
	}

	private static void drawShipCargo(Graphics2D g, Ship s) {
		Cargo cargo = s.getCargo();
		int total = cargo.getSize();

		Dimension size = Assets.dimensionOf(Assets.container);
		float y = -(float) total / 2.f * size.height;
		g.rotate(Math.PI / 2);
		for (int i = 0; i < cargo.getSize(); i++) {
			if (cargo.getTypeAt(i) != CargoType.NONE) {
				BufferedImage im = Assets.containers.get(cargo.getColorOf(i));
				g.drawImage(im, -size.width / 2, (int) y, null);
			}
			y += size.height;
		}
		g.rotate(-Math.PI / 2);
	}

	public static void drawPath(Graphics2D g, Ship s) {
		ArrayList<PointI> path = s.getPath();
		PointI lastPoint = s.getPoint().i();
		g.setColor(pathColor);
		for (PointI point : path) {
			g.drawLine(lastPoint.x, lastPoint.y, point.x, point.y);
			lastPoint = point;
		}
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
