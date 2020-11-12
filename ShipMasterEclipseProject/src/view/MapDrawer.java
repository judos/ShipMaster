package view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import model.*;
import ch.judos.generic.data.geometry.DirectedPoint;
import ch.judos.generic.data.geometry.PointI;
import ch.judos.generic.graphics.Drawable2d;
import ch.judos.generic.graphics.drawing.TexturePaintJS;
import ch.judos.generic.math.MathJS;
import controller.Game;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class MapDrawer implements Drawable2d {

	private Map map;
	private int waterIndex;
	private static Font text = new Font("Arial", 0, 24);

	public MapDrawer(Map map) {
		this.map = map;
		this.waterIndex = 0;
	}

	@Override
	public void paint(Graphics2D g) {
		float slowed = 6;
		this.waterIndex++;
		this.waterIndex = this.waterIndex % (int) (16 * slowed);

		int actualIndex = (int) ((float) this.waterIndex / slowed);
		TexturePaint waterPaint = TexturePaintJS.getPaintForImage(Assets.water[actualIndex]);
		// timer.printMS("set texturepaint");
		g.setPaint(waterPaint);
		g.fillRect(0, 0, 1920, 1080);
		drawLand(g);

		drawBorders(g);
		drawDocks(g);
		drawContainerStacks(g);
		drawShipPaths(g);
		drawShipInDanger(g);
		drawShips(g);
		drawAttentionSigns(g);

		int ships = this.map.getShips().size();
		g.setColor(Color.white);
		g.setFont(text);
		g.drawString("Ships around: " + ships, 20, 20);
		g.drawString("Containers transfered: " + Game.containersTransfered, 20, 50);
		g.drawString("Spawntime: " + String.format("%.2f", map.spawnShipEveryXSec), 20, 80);

	}

	private void drawAttentionSigns(Graphics2D g) {
		for (Attention t : this.map.getAttentions()) {
			PointI p = t.getPosition();
			g.setColor(Color.red);
			int size = Attention.signSize;
			g.fillOval(p.x - size / 2, p.y - size / 2, size, size);
			g.setColor(Color.white);
			g.drawString("!", p.x, p.y);
		}
	}

	private void drawLand(Graphics2D g) {
		TexturePaint grassPaint = TexturePaintJS.getPaintForImage(Assets.grass);
		g.setPaint(grassPaint);
		Area a = new Area();
		for (Polygon poly : this.map.getLandPolygons()) {
			a.add(new Area(poly));
		}
		for (Dock d : this.map.getDocks()) {
			Area dockArea = new Area(new Rectangle(-50, -50, 100, 100));
			DirectedPoint p = d.getPoint();
			dockArea.transform(AffineTransform.getRotateInstance(d.getDirection().getRadian()));
			dockArea.transform(AffineTransform.getTranslateInstance(p.getX(), p.getY()));
			a.subtract(dockArea);
		}
		g.fill(a);
	}

	private void drawBorders(Graphics2D g) {
		// TexturePaint sand = new TexturePaint(beach, new Rectangle(0, 0,
		// beach.getWidth(),
		// beach.getHeight()));
		// g.setPaint(sand);
		// Stroke s = g.getStroke();
		// g.setStroke(new BasicStroke(100));
		// for (Border b : this.map.getBorders()) {
		// if (b instanceof RestrictionBorder) {
		// RestrictionBorder rb = (RestrictionBorder) b;
		// g.drawLine(rb.getStart().x, rb.getStart().y, rb.getEnd().x,
		// rb.getEnd().y);
		// }
		// }
		// ArrayList<RestrictionBorder> borders = new
		// ArrayList<RestrictionBorder>();
		// borders.add(new RestrictionBorder(new PointI(300, 300), new
		// PointI(600, 600)));
		// for (Border b : borders) {
		// if (b instanceof RestrictionBorder) {
		// RestrictionBorder rb = (RestrictionBorder) b;
		// g.drawLine(rb.getStart().x, rb.getStart().y, rb.getEnd().x,
		// rb.getEnd().y);
		// }
		// }
		// g.setStroke(s);
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
		g.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
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
		TexturePaint rockPaint = TexturePaintJS.getPaintForImage(Assets.rock);

		for (Dock d : this.map.getDocks()) {
			Color c = d.getColor();
			c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 128);
			DirectedPoint position = d.getPoint();

			g.translate(position.getX(), position.getY());
			g.rotate(position.getAAngle().getRadian());

			g.setPaint(rockPaint);
			drawDockRects(g);
			g.setColor(c);
			drawDockRects(g);

			g.setTransform(transformOriginal);
		}
	}

	private void drawContainerStacks(Graphics2D g) {
		AffineTransform t = g.getTransform();
		Dimension containerSize = Assets.dimensionOf(Assets.container);
		for (ContainerStack stack : this.map.getStacks()) {
			PointI pos = stack.getPosition();
			g.translate(pos.x, pos.y);

			int square = MathJS.ceil(Math.sqrt((float) stack.getMaxSize() / 3));
			int width = square;
			int height = square * 3;
			float f = 1.2f; // how much space between Containers
			g.translate(-(float) width / 2. * (f * containerSize.width), -(float) height / 2. * (f * containerSize.height));
			BufferedImage container = Assets.containers.get(stack.getColor());
			int amount = 1;
			outer: for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (amount > stack.getSize())
						break outer;
					g.drawImage(container, (int) (x * (containerSize.width * 1.2)), (int) (y * (containerSize.height * 1.2)),
						null);
					amount++;
				}
			}
			g.setTransform(t);
		}
	}

	private void drawDockRects(Graphics2D g) {
		g.fillRect(-50, -50, 100, 10);
		g.fillRect(-50, -50, 10, 100);
		g.fillRect(-50, 40, 100, 10);
	}

}
