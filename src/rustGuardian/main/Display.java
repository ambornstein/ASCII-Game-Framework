package rustGuardian.main;

import java.awt.Point;

import asciiPanel.AsciiPanel;
import javafx.geometry.Point3D;
import rustGuardian.domain.Direction;
import rustGuardian.domain.EntityContainer;
import rustGuardian.domain.MapChunk;
import rustGuardian.domain.PointSet;
import rustGuardian.domain.RelativePos;
import rustGuardian.domain.World;

public class Display {
	private static World world;
	private static AsciiPanel terminal;
	private static EntityContainer beings;
	private static Point3D frameOrigin;
	private static Point3D frameDestination;
	private static Point3D screenSize;
	private static int visX;
	private static int visY;
	private static int visRad;
	private static int visWidth;
	private static int visHeight;
	private static float[][] visMap;
	private static int[][] resistanceMap;
	private static AbstractMoveable focusObject;
	private static Point3D focusLocation;

	public Display(AsciiPanel terminal, World world, EntityContainer beings, Point screenSize) {
		Display.world = world;
		Display.terminal = terminal;
		Display.beings = beings;
		Display.screenSize = new Point3D(screenSize.x, screenSize.y, 0);
		focusObject = beings.activeUnit();
	}

	public static Point3D getFrameOrigin() {
		return frameOrigin;
	}

	public static void playerPerspectiveDisplay() {
		focusLocation = focusObject.getAbsPosition();
		frameOrigin = RelativePos.correctOutOfBounds(focusLocation.subtract(screenSize));
		frameDestination = RelativePos.correctOutOfBounds(frameOrigin.add(screenSize));
		PointSet frame = new PointSet(frameOrigin, frameDestination);
		MapChunk viewChunk = world.copyGrid(frame);
		calculateFOV(viewChunk.opaqueScan()[0], (int) focusLocation.getX(), (int) focusLocation.getY(),
				focusObject.sightRad());
		Point3D playerSite = focusLocation.subtract(frameOrigin);
		for (int j = (int) frameOrigin.getY(); j < visMap.length; j++) {
			for (int i = (int) frameOrigin.getX(); i < visMap[0].length; i++) {
				if (visMap[j][i] == 1.0f) {
					Point screenTile = new Point((int) (i - frameOrigin.getX()), (int) (j - frameOrigin.getY()));
					try {
						terminal.write(viewChunk.unitAt(new Point3D(i, j, 0)).symbol(), screenTile.x, screenTile.y);
					} catch (NullPointerException e) {
						terminal.write(' ', screenTile.x, screenTile.y);
					}
				}
			}
		}
		terminal.write(focusObject.getSymbol(), (int) (playerSite.getX() - frameOrigin.getX()),
				(int) (playerSite.getY() - frameOrigin.getY()));
		terminal.write(String.valueOf(frameOrigin.getZ()), 0, 0);
	}

	/**
	 * Calculates the Field Of View for the provided map from the given x, y
	 * coordinates. Returns a lightmap for a result where the values represent a
	 * percentage of fully lit.
	 *
	 * A value equal to or below 0 means that cell is not in the field of view,
	 * whereas a value equal to or above 1 means that cell is in the field of view.
	 *
	 * @param resistanceMap  the grid of cells to calculate on where 0 is
	 *                       transparent and 1 is opaque
	 * @param startx         the horizontal component of the starting location
	 * @param starty         the vertical component of the starting location
	 * @param radius         the maximum distance to draw the FOV
	 * @param radiusStrategy provides a means to calculate the radius as desired
	 * @return the computed light grid
	 */
	public static float[][] calculateFOV(int[][] opaque, int startx, int starty, int radius) {
		visX = startx;
		visY = starty;
		visRad = radius;
		visWidth = opaque.length;
		visHeight = opaque[0].length;
		resistanceMap = opaque;
		visMap = new float[visHeight + 1][visWidth + 1];
		visMap[starty][startx] = 1.0f;
		for (Direction d : Direction.DIAGONAL) {
			castLight(1, 1.0f, 0.0f, 0, (int) d.offset().getX(), (int) d.offset().getY(), 0);
			castLight(1, 1.0f, 0.0f, (int) d.offset().getX(), 0, 0, (int) d.offset().getY());
		}
		return visMap;
	}

	/**
	*
	*/

	private static void castLight(int row, float start, float end, int xx, int xy, int yx, int yy) {
		float newStart = 0.0f;
		if (start < end) {
			return;
		}
		boolean blocked = false;
		for (int distance = row; distance <= visRad && !blocked; distance++) {
			int deltaY = -distance;
			for (int deltaX = -distance; deltaX <= 0; deltaX++) {
				int currentX = visX + deltaX * xx + deltaY * xy;
				int currentY = visY + deltaX * yx + deltaY * yy;
				float leftSlope = (deltaX - 0.5f) / (deltaY + 0.5f);
				float rightSlope = (deltaX + 0.5f) / (deltaY - 0.5f);
				if (!(currentX >= 0 && currentY >= 0 && currentX < visWidth && currentY < visHeight)
						|| start < rightSlope) {
					continue;
				} else if (end > leftSlope) {
					break;
				}

				if (Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) <= visRad) {
					visMap[currentY][currentX] = 1.0f;
				}
				if (blocked) {
					if (resistanceMap[currentY][currentX] == 1) {
						newStart = rightSlope;
						continue;
					} else {
						blocked = false;
						start = newStart;
					}
				} else {
					if (resistanceMap[currentY][currentX] == 1 && distance < visRad) {
						System.out.println('0');
						blocked = true;
						castLight(distance + 1, start, leftSlope, xx, xy, yx, yy);
						newStart = rightSlope;
					}
				}
			}
		}
	}
}
