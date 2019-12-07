package rustGuardian.main;

import java.util.HashMap;
import java.awt.event.KeyEvent;

public class Control {
	private static HashMap<Integer, String> lookScheme; // control scheme when in look mode
	private static HashMap<Integer, String> standardScheme; // control scheme default
	private static HashMap<Integer, String> currentScheme;

	public Control() {
		Integer[] keyCodes = { KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_LESS,
				KeyEvent.VK_GREATER, KeyEvent.VK_L, KeyEvent.VK_ESCAPE }; // All potential keystroke mappings
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
		HashMap<Integer, String> returnMap = new HashMap<Integer, String>();
		for (int i = 0; i < Math.min(keyCodes.length, actionCodes.length); i++) { // if unequal sizes, stop mapping as
																					// soon as one of the list's
																					// capacities are reached
			returnMap.put(keyCodes[i], actionCodes[i]);
		}
		return returnMap;
	}

	public static void handleInput(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED && currentScheme.containsKey(e.getKeyCode())) {
			switch (currentScheme.get(e.getKeyCode())) {
			case "north":
				EntityContainer.activeEntity().move(Direction.NORTH);
				break;
			case "south":
				EntityContainer.activeEntity().move(Direction.SOUTH);
				break;
			case "west":
				EntityContainer.activeEntity().move(Direction.WEST);
				break;
			case "east":
				EntityContainer.activeEntity().move(Direction.EAST);
				break;
			case "up":
				EntityContainer.activeEntity().move(Direction.UP);
				break;
			case "down":
				EntityContainer.activeEntity().move(Direction.DOWN);
				break;
			case "look_mode":
				switchScheme(lookScheme);
				EntityContainer.getDefaultCursorEntity().setVisible(true);
				EntityContainer.cursorActivate();
				ApplicationMain.refresh();
				break;
			case "standard_mode":
				switchScheme(standardScheme);
				EntityContainer.getDefaultCursorEntity().setVisible(false);
				EntityContainer.playerActivate();
				ApplicationMain.refresh();
				break;
			}
		}
	}
}
