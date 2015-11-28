package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.Map;
import view.Gui;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class Game extends KeyAdapter {

	public static final int FPS = 60;

	private Map map;
	private Gui gui;
	private MouseController mouseController;
	private CollisionController collisionController;
	private ShipController shipController;
	private boolean gameIsPaused;
	private boolean slowMotion;
	private DockingController dockingController;
	private UnloadingController unloadingController;
	private SpawnShipController spawnShipController;

	private AttentionController attentionController;
	private double gameTime;

	private UpdateController updateController;

	private static Game instance;

	public static int containersTransfered;

	public synchronized static Game initializeGame(Map m, Gui gui) {
		if (instance != null)
			throw new RuntimeException("Game can only be initialized once");
		return instance = new Game(m, gui);
	}

	public static double getGameTime() {
		if (instance == null)
			return 0;
		return instance.gameTime;
	}

	private Game(Map m, Gui gui) {
		this.map = m;
		this.gui = gui;
		this.gameIsPaused = false;
		this.slowMotion = false;

		this.mouseController = new MouseController(this.map, gui.getComponent());
		this.gui.getInputProvider().addMouseListener(this.mouseController);
		this.gui.getInputProvider().addKeyListener(this);

		this.collisionController = new CollisionController(this.map, this::pauseGame);
		this.shipController = new ShipController(this.map);
		this.dockingController = new DockingController(this.map);
		this.unloadingController = new UnloadingController(this.map);
		this.spawnShipController = new SpawnShipController(this.map);
		this.attentionController = new AttentionController(this.map);
		this.updateController = new UpdateController(this.map, this::pauseGame);
		this.gameTime = 0d;
	}

	private void pauseGame() {
		this.gameIsPaused = true;
	}

	public void start() {
		this.gui.setDrawable(this.map.getDrawable());
		this.gui.startFullScreen(FPS);
		// this.gui.startViewUndecorated(FPS);
		// this.gui.startView(FPS);
		this.gui.setUpdate(this::updateBeforeDrawing);
	}

	int x = 0;
	private void updateBeforeDrawing() {
		if (!this.gameIsPaused) {
			this.mouseController.update();

			if (this.slowMotion && x++ < 25)
				return;
			x = 0;

			this.dockingController.update();
			this.shipController.update();
			this.unloadingController.update();
			this.spawnShipController.update();
			this.attentionController.update();
			this.updateController.update();
			this.gameTime += 1. / FPS;
		}
		this.collisionController.update(this.gameIsPaused);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			this.slowMotion = !this.slowMotion;
	}

}
