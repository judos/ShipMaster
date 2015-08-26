import java.util.Optional;
import javax.swing.SwingUtilities;
import model.Map;
import model.MapGenerator;
import view.Gui;
import controller.Game;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class Launcher {
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "True");
		System.setProperty("sun.java2d.accthreshold", "0");

		Launcher launcher = new Launcher();
		SwingUtilities.invokeLater(launcher::init);
	}

	private void init() {

		Map map = MapGenerator.getMap1();
		Optional<Runnable> shutdown = Optional.of(() -> System.exit(0));
		Gui gui = new Gui(shutdown);

		Game.initializeGame(map, gui).start();
	}
}
