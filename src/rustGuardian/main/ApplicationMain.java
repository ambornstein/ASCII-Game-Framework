package rustGuardian.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

/**
 * Roguelike prototype using AsciiPanel for display. Currently has minimal
 * features including a arrow key control system and a map class constructed out
 * of nested character arrays.
 */
public class ApplicationMain extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1060623638149583738L;
	public static AsciiPanel terminal;
	public static Player player;
	public static World world;
	public static IMoveable activeObject;

	public ApplicationMain() {
		super();
		terminal = new AsciiPanel(80, 50, AsciiFont.CP437_12x12);
		terminal.setFocusable(true);
		terminal.addKeyListener(this);
		add(terminal);
		RelativePos.makeWorld();
		new Control();
		new Display(terminal);
		this.setTitle("Rust Guardian");
		pack();
		refresh();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Control.handleInput(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Handles the passing of turns This should only be called when the player makes
	 * a valid move or if another condition requires time to pass
	 */
	// turnPass should only call when a valid move is made by the player
	// public static void turnPass() {}

	public static void refresh() {
		terminal.clear();
		Display.playerPerspectiveDisplay();
		// fullDisplay();
		// oneScreenDisplay(world);
		terminal.repaint();
	}

	public static void main(String[] args) {
		ApplicationMain app = new ApplicationMain();
		app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}