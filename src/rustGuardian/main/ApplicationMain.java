package rustGuardian.main;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import rustGuardian.domain.EntityContainer;
import rustGuardian.domain.Generator;
import rustGuardian.domain.RelativePos;
import rustGuardian.domain.Tile;
import rustGuardian.domain.World;

/**
 * Roguelike prototype using AsciiPanel for display. Currently has minimal
 * features including a arrow key control system and a map class constructed out
 * of nested character arrays.
 */
public class ApplicationMain extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1060623638149583738L;
	public static AsciiPanel terminal;
	public EntityContainer beings;
	public World world;

	public ApplicationMain() {
		super();
		terminal = new AsciiPanel(40, 40, AsciiFont.CP437_12x12);
		terminal.setFocusable(true);
		terminal.addKeyListener(this);
		terminal.addMouseListener(this);
		terminal.addMouseMotionListener(this);
		add(terminal);
		world = new World(new Generator(new RelativePos(2,2,20,20,5)));
		beings = new EntityContainer(world);
		new Control(beings);
		new Display(terminal, world, beings, new Point(terminal.getWidthInCharacters(), terminal.getHeightInCharacters()));
		this.setTitle("Rust Guardian");
		pack();
		refresh();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Control.handleInput(e);
		refresh();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println(e);
		world.absolutePlace(Control.handleMouse(e, terminal.getCharWidth(), terminal.getCharHeight(),  Display.getFrameOrigin()), Tile.WALL);
		refresh();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		world.absolutePlace(Control.handleMouse(e, terminal.getCharWidth(), terminal.getCharHeight(),  Display.getFrameOrigin()), Tile.WALL);
		refresh();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Handles the passing of turns. This should only be called when the player makes
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