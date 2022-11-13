package rustGuardian.main;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javafx.geometry.Point3D;
import rustGuardian.domain.Direction;
import rustGuardian.domain.EntityContainer;

public class Control {
	private static HashMap<Integer, String> lookScheme; // control scheme when in look mode
	private static HashMap<Integer, String> standardScheme; // control scheme default
	private static HashMap<Integer, String> currentScheme;
	private static EntityContainer beings;

	public Control(EntityContainer beings) {
		Control.beings = beings;
		Integer[] keyCodes = { KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_COMMA,
				KeyEvent.VK_PERIOD, KeyEvent.VK_L, KeyEvent.VK_ESCAPE }; // All potential keystroke mappings
		// go here
		String[] lookActionCodes = { "north", "south", "west", "east", "up", "down", "", "standard_mode" };
		String[] standardActionCodes = { "north", "south", "west", "east", "up", "down", "look_mode", "" };
		standardScheme = constructScheme(keyCodes, standardActionCodes);
		lookScheme = constructScheme(keyCodes, lookActionCodes);
		switchScheme(standardScheme);
	}

	public static void switchScheme(HashMap<Integer, String> newScheme) { // change currentScheme
		currentScheme = newScheme;
	}

	public static HashMap<Integer, String> constructScheme(Integer[] keyCodes, String[] actionCodes) { // From a list of
																										// keycodes and
																										// actioncodes,
																										// construct a
																										// HashMap
		HashMap<Integer, String> returnMap = new HashMap<>();
		for (int i = 0; i < Math.min(keyCodes.length, actionCodes.length); i++) { // if unequal sizes, stop mapping as
																					// soon as one of the list's
																					// capacities are reached
			returnMap.put(keyCodes[i], actionCodes[i]);
		}
		return returnMap;
	}

	public static void handleInput(KeyEvent e) {
		String press = currentScheme.get(e.getKeyCode());
		if (e.getID() == KeyEvent.KEY_PRESSED && currentScheme.containsKey(e.getKeyCode())) {
			if (e.isShiftDown()) {
				switch (press) {
					case "up":
						beings.move(Direction.UP);
						break;
					case "down":
						beings.move(Direction.DOWN);
						break;
				}
			}
			else {
				switch (press) {
				case "north":
					beings.move(Direction.NORTH);
					break;
				case "south":
					beings.move(Direction.SOUTH);
					break;
				case "west":
					beings.move(Direction.WEST);
					break;
				case "east":
					beings.move(Direction.EAST);
					break;
				case "look_mode":
					switchScheme(lookScheme);
					beings.activateCursor();
					beings.activeCursor().setVisible(true);
					break;
				case "standard_mode":
					switchScheme(standardScheme);
					beings.activatePlayer();
					beings.activeCursor().setVisible(false);
					break;
				}
			}
		}
	}

	public static Point3D handleMouse(MouseEvent e, Point termLoc, int originX, int originY, int originZ) {
		return handleMouse(e, termLoc, new Point3D(originX, originY, originZ));
	}

	public static Point3D handleMouse(MouseEvent e, int x, int y, Point3D origin) {
		return handleMouse(e, new Point(x,y), origin);
	}

	public static Point3D handleMouse(MouseEvent e, int x, int y, int originX, int originY, int originZ) {
		return handleMouse(e, new Point(x,y), new Point3D(originX, originY, originZ));
	}

	public static Point3D handleMouse(MouseEvent e, Point termLoc, Point3D origin) {
		Point3D mouseLoc = new Point3D(Math.floor(e.getX()/termLoc.getX()), Math.floor(e.getY()/termLoc.getY()), 0);
		return new Point3D(origin.getX()+mouseLoc.getX(), origin.getY()+mouseLoc.getY(), origin.getZ());
	}
}
