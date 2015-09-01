package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.Cargo;
import model.Ship;
import model.ShipType;
import ch.judos.generic.data.geometry.PointF;
import ch.judos.generic.data.geometry.PointI;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class ShipDrawer extends DrawingClass {

	private static final Color pathColor = new Color(255, 255, 255);
	private static BufferedImage ship = load("ship.png");

	public static void drawShip(Graphics2D g, Ship s) {
		PointF pos = s.getPoint();
		g.translate(pos.getXI(), pos.getYI());
		g.rotate(s.getDirection().getRadian());
		ShipType type = s.getType();
		int size = type.getSize();

		g.setColor(Color.darkGray);
		// g.fillOval(-size, -size, 2 * size, 2 * size);
		g.drawImage(ship, -ship.getWidth() / 2, -ship.getHeight() / 2, null);

		drawShipCargo(g, s);
	}

	private static void drawShipCargo(Graphics2D g, Ship s) {
		Cargo cargo = s.getCargo();
		int total = 2 * s.getType().getSize();
		if (s.getType() == ShipType.SMALL)
			total *= 0.5;
		else
			total *= 0.8;
		int sizeContainer = total / cargo.getSize();
		int x = -total / 2;
		for (int i = 0; i < cargo.getSize(); i++) {
			g.setColor(cargo.getColorOf(i));
			g.fillRect(x + 2, -9, sizeContainer - 1, 20);
			x += sizeContainer;
		}
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
