package view;

import java.awt.*;
import java.awt.geom.AffineTransform;

import model.*;
import model.border.Border;
import model.border.RestrictionBorder;
import ch.judos.generic.data.geometry.DirectedPoint;
import ch.judos.generic.data.geometry.PointI;
import ch.judos.generic.graphics.Drawable2d;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class MapDrawer implements Drawable2d {

	private Map				map;
	private static Color	landColor	= new Color(140, 70, 0);
	private static Color	waterColor	= new Color(80, 170, 255);
	private static Font	text			= new Font("Arial", 0, 24);

	public MapDrawer(Map map) {
		this.map = map;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(waterColor);
		g.fillRect(0, 0, 1920, 1080);

		drawLand(g);

		drawBorders(g);

		drawDocks(g);
		drawShipPaths(g);
		drawShipInDanger(g);
		drawShips(g);
		drawAttentionSigns(g);

		int ships = this.map.getShips().size();
		g.setColor(Color.white);
		g.setFont(text);
		g.drawString("Ships: " + ships, 20, 20);
	}

	private void drawAttentionSigns(Graphics2D g) {
		for (Attention t : this.map.getAttentions()) {
			PointI p = t.getPosition();
			g.setColor(Color.red);
			int size = Attention.signSize;
			g.fillOval(p.x - size / 2, p.y - size / 2, size, size);
			g.setColor(Color.white);
			g.drawString("!", p.x,p.y);
		}
	}

	private void drawLand(Graphics2D g) {
		for (Polygon poly : this.map.getLandPolygons()) {
			g.setColor(landColor);
			g.fillPolygon(poly);
		}
	}

	private void drawBorders(Graphics2D g) {
		for (Border b : this.map.getBorders()) {
			if (b instanceof RestrictionBorder) {
				RestrictionBorder rb = (RestrictionBorder) b;
				g.setColor(new Color(128, 64, 0));
				g.drawLine(rb.getStart().x, rb.getStart().y, rb.getEnd().x, rb.getEnd().y);
			}
		}
	}

	private void drawShipInDanger(Graphics2D g) {
		AffineTransform transformOriginal = g.getTransform();
		for (Ship s : this.map.getShips()) {
			if (s.isInDanger()) {
				ShipDrawer.drawShipInDanger(g, s);
				g.setTransform(transformOriginal);
			}
		}
	}

	private void drawShipPaths(Graphics2D g) {
		g.setStroke(new BasicStroke(5));
		for (Ship s : this.map.getShips()) {
			ShipDrawer.drawPath(g, s);
		}
	}

	private void drawShips(Graphics2D g) {
		AffineTransform transformOriginal = g.getTransform();
		for (Ship s : this.map.getShips()) {
			ShipDrawer.drawShip(g, s);
			g.setTransform(transformOriginal);
		}
	}

	private void drawDocks(Graphics2D g) {
		AffineTransform transformOriginal = g.getTransform();
		for (Dock d : this.map.getDocks()) {

			DirectedPoint position = d.getPoint();

			g.translate(position.getX(), position.getY());
			g.rotate(position.getAAngle().getRadian());

			g.setColor(waterColor);
			g.fillRect(-40, -40, 90, 80);

			g.setColor(d.getColor());
			g.drawRect(0, 0, 1, 1);
			g.fillRect(-50, -50, 100, 10);
			g.fillRect(-50, -50, 10, 100);
			g.fillRect(-50, 40, 100, 10);
			g.setTransform(transformOriginal);
		}
	}

}
