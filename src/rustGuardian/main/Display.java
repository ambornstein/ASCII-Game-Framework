package rustGuardian.main;

import java.awt.Point;
import asciiPanel.AsciiPanel;
import javafx.geometry.Point3D;

public class Display {
	private static World world;
	private static AsciiPanel terminal;
	private static Point3D frameOrigin;
	private static Point3D frameDestination;
	private static Point viewMargin;

	public Display(AsciiPanel terminal) {
		Display.world = RelativePos.generalWorld();
		Display.terminal = terminal;
		viewMargin = new Point(39,24);//(int)RelativePos.generator().tilePoint().getX(), (int)RelativePos.generator().tilePoint().getY()
	}

	/**
	 * Wrapper which loops and calls display() over all the chunks that exist in the
	 * world
	 */
	public static void fullDisplay() { // world parameter is irrelevant because a World instance is created in
										// ApplicationMain
		// Loops through each chunk and uses display to display it on it's map portion
		for (int y = 0; y < RelativePos.generator().chunkPoint().y; y++) {
			for (int x = 0; x < RelativePos.generator().chunkPoint().x; x++) {
				display(new RelativePos(x, y, 0, 0, 0)); // display is supplied with the chunk coordinates only
															// any input of tile coordinates is ignored
			}
		}
	}

	/**
	 * Primary function displaying the screen by iteration over the
	 * {@link rustGuardian.main.MapChunk} that is given in arguments. Before
	 * displaying the {@link rustGuardian.main.Tile} that it finds, the method
	 * checks to see if the square it is currently iterating over is also the square
	 * that the player's position is on. If the player is not on the square, it
	 * proceeds to display the symbol of the tile that occupies the square. Use an
	 * alternative because this one features broken features from fov scanning
	 * 
	 * @param map
	 *            The map that the method uses to display to the terminal.
	 * @param fov
	 *            A scan of the visibility of all of the tiles in the visible
	 *            radius. Broken in {@link rustGuardian.main.MapChunk.calculateFOV}
	 */
	/*
	 * public static void display(MapChunk map, int[][] fov) { for (int i = 0; i <
	 * world.generator().worldHeight(); i++) { for (int j = 0; j <
	 * world.generator().worldWidth(); j++) { if (i ==
	 * player.getAbsPosition().getY() && j == player.getAbsPosition().getX()) {
	 * terminal.write(player.symbol(), j, i); } else if (!(i ==
	 * player.getAbsPosition().getY() && j == player.getAbsPosition().getX())) { if
	 * (fov[i][j] == 1) terminal.write(map.get(i).get(j).symbol(), j, i); } } } }
	 */

	/*
	 * public static void display(MapChunk map) { for (int i = 0; i < map.width();
	 * i++) { for (int j = 0; j < map.length(); j++) { if (i ==
	 * player.getAbsPosition().getY() && j == player.getAbsPosition().getX()) {
	 * terminal.write(player.symbol(), j, i); } else { terminal.write(map.unitAt(new
	 * Point(j, i)).symbol(), j, i); } } } }
	 */

	/**Returns the truth value of if 
	 * 
	 * @param xProximity
	 * @param yProximity
	 * @param focusObject
	 * @return
	 */
	private static boolean inViewLimit(IMoveable focusObject) {
		if (frameOrigin == null || frameDestination == null) {
			return true;
		}
		if (focusObject.getAbsPosition().getX() >= frameOrigin.getX()
				&& focusObject.getAbsPosition().getY() >= frameOrigin.getY()
				&& focusObject.getAbsPosition().getX() <= frameDestination.getX()
				&& focusObject.getAbsPosition().getY() <= frameDestination.getY()) {
			return true;
		}
		return false;
	}

	public static void playerPerspectiveDisplay() {
		IMoveable focusObject = EntityContainer.activeEntity();
		frameOrigin = new Point3D(focusObject.getAbsPosition().getX() - viewMargin.x,
				focusObject.getAbsPosition().getY() - viewMargin.y, focusObject.getAbsPosition().getZ());
		frameOrigin = RelativePos.correctOutOfBounds(frameOrigin);
		frameDestination = new Point3D((viewMargin.x*2)+1, (viewMargin.y*2)+1, frameOrigin.getZ());
		MapChunk viewChunk = world.subsection(frameOrigin, frameDestination);
		for (int y = 0; y <= viewChunk.width(); y++) {
			for (int x = 0; x <= viewChunk.length(); x++) {
				Point currentPoint = new Point(x, y);
				try {
					terminal.write(viewChunk.unitAt(new Point3D(currentPoint.getX(), currentPoint.getY(), 0)).symbol(), x, y);
				}
				catch (NullPointerException e) {
					terminal.write(' ', x, y);
				}
			}
		}
		for (AbstractMoveable f : EntityContainer.getAllVisibleEntity()) {
			if (inViewLimit(f)) {
				Point3D focusInScope = f.getAbsPosition().subtract(frameOrigin);
				//System.out.println(f + "Focus In Scope:" + focusInScope);
				if (focusInScope.getX() <= viewChunk.length() && focusInScope.getY() <= viewChunk.width() && focusInScope.getZ() == 0) {
					terminal.write(f.getSymbol(), (int)focusInScope.getX(), (int)focusInScope.getY());
				}
			}
		}
		terminal.write(String.valueOf(frameOrigin.getZ()), 0, 0);
	}

	/**
	 * Scans a GameMap to display the symbols of its Tiles and the Player
	 * Encapsulated by fullDisplay(), which calls display for every MapChunk within
	 * the world This method makes use of the RelativePos object to determine the
	 * position of Tiles relative to MapChunks
	 * 
	 * @param actChunk
	 *            GameMap that is of tiles for active gameplay display
	 */
	private static void display(RelativePos fullPos) {
		for (int i = 0; i < RelativePos.generator().chunkPoint().y; i++) {
			for (int j = 0; j < RelativePos.generator().chunkPoint().x; j++) {
				RelativePos thisPos = fullPos.readOnlyShift(j, i, 0);
				// System.out.println(thisPos.toString());
				Point3D absPos = thisPos.toAbs();
				// System.out.println(absPos);
				for (AbstractMoveable f : EntityContainer.getAllVisibleEntity()) {
					if (absPos.equals(f.getAbsPosition())) {
						terminal.write(f.getSymbol(), (int)absPos.getX(), (int)absPos.getY());
					} else {
						terminal.write(RelativePos.generalWorld().relativeFindTile(thisPos).symbol(), (int)absPos.getX(), (int)absPos.getY());
					}
				}
			}
		}
		// terminal.write("Map Chunk:" + player.actChunkPoint().x + "," +
		// player.actChunkPoint().y + ";" + player.pos().x + "," + player.pos().y, 40,
		// 12);
	}

	/**
	 * An alternate display method which displays only the MapChunk that the player
	 * is located in
	 */
	/*
	 * private static void oneScreenDisplay() { //Caves of Qud style display Point
	 * chunkPoint = player.getRelPosition().chunkPoint(); for (int i = 0; i <
	 * world.generator().chunkHeight(); i++) { for (int j = 0; j <
	 * world.generator().chunkWidth(); j++) { Point tilePoint = new Point(j, i); if
	 * (tilePoint.equals(player.getRelPosition().tilePoint())) {//the equality check
	 * between player's point and tilePoint terminal.write(player.symbol(),
	 * tilePoint.x, tilePoint.y); } else {
	 * terminal.write(world.unitAt(chunkPoint).unitAt(tilePoint).symbol(), j, i); }
	 * } } }
	 */

	/**
	 * Scans an Array to display all of its characters, totally static character
	 * display Debug function
	 * 
	 * @param actChunk
	 *            Array list of characters for experimental display
	 */
	/*
	 * public static void display(char[][] actChunk) { for (int i = 0; i <
	 * actChunk.length; i++) { for (int j = 0; j < actChunk[0].length; j++) { if (i
	 * == player.getAbsPosition().getY() && j == player.getAbsPosition().getX()) {
	 * terminal.write(player.symbol(), j, i); } else {
	 * terminal.write(actChunk[i][j], j, i); } } } }
	 */
}
