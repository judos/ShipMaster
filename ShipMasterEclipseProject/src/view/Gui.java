package view;

import java.awt.*;
import java.awt.event.*;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import model.input.InputProvider;
import ch.judos.generic.graphics.Drawable2d;
import ch.judos.generic.graphics.fullscreen.FullScreen;

/**
 * @since 27.01.2015
 * @author Julian Schelker
 */
public class Gui implements Drawable2d {

	GuiFrame						frame;
	private Timer				timer;
	private Drawable2d		drawable;
	private GraphicsDevice	deviceUsed;
	Runnable						update;

	public Gui(Optional<Runnable> onQuit) {
		GraphicsDevice[] dev = FullScreen.getDevices();
		this.deviceUsed = dev[0];
		this.update = null;

		this.frame = new GuiFrame(this, this.deviceUsed);
		if (onQuit.isPresent()) {
			this.frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					onQuit.get().run();
				}
			});
		}
	}

	public void setUpdate(Runnable r) {
		this.update = r;
	}

	public void startViewTimer(int fps) {
		this.frame.openFrame(true);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Gui.this.frame.renderScreen();
			}
		};
		this.timer = new Timer("Render thread", false);
		int delay = (int) (1000. / fps);
		this.timer.scheduleAtFixedRate(task, delay, delay);
	}

	public void startFullScreen(int fps) {
		this.frame.setUndecorated(true);
		this.deviceUsed.setFullScreenWindow(this.frame);
		this.frame.createBufferStrategy(2);
		startViewWithoutOpeningFrame(fps);
	}
	public void startView(int fps) {
		this.frame.openFrame(true);
		startViewWithoutOpeningFrame(fps);
	}

	private void startViewWithoutOpeningFrame(int fps) {
		Thread t = new Thread("Render thread") {
			@Override
			public void run() {
				long delay = 1000000000 / fps;
				long lastFrame = System.nanoTime();
				while (true) {
					long ns = System.nanoTime();
					if (ns - lastFrame >= delay) {
						lastFrame = ns;
						try {
							if (Gui.this.update != null)
								Gui.this.update.run();
							Gui.this.frame.renderScreen();
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					try {
						long remaining = lastFrame + delay - System.nanoTime() - 1000000;
						if (remaining > 0)
							Thread.sleep(remaining / 1000000, (int) (remaining % 1000000));
					}
					catch (InterruptedException e) {
						// empty
					}
				}
			}
		};
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}

	public void startViewWhileTrue() {
		this.frame.openFrame(true);
		Thread t = new Thread("Render thread") {
			@Override
			public void run() {
				while (true)
					Gui.this.frame.renderScreen();
			}
		};
		t.start();
	}

	public void setDrawable(Drawable2d m) {
		this.drawable = m;
	}

	public Dimension getSize() {
		return this.frame.getSize();
	}

	public InputProvider getInputProvider() {
		return new InputProvider() {
			@Override
			public void addMouseListener(MouseListener m) {
				Gui.this.frame.getContentPane().addMouseListener(m);
			}

			@Override
			public void addKeyListener(KeyListener k) {
				Gui.this.frame.addKeyListener(k);
			}

			@Override
			public void addMouseWheelListener(MouseWheelListener m) {
				Gui.this.frame.getContentPane().addMouseWheelListener(m);
			}

		};
	}

	@Override
	public void paint(Graphics2D g) {
		Rectangle size = g.getClipBounds();
		g.clearRect(0, 0, size.width, size.height);
		if (this.drawable != null)
			this.drawable.paint(g);
	}

	public Component getComponent() {
		return this.frame.getContentPane();
	}

	public void quit() {
		this.frame.setVisible(false);
		this.frame.dispose();
	}

}
