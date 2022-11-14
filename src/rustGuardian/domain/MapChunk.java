package rustGuardian.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.geometry.Point3D;

public class MapChunk extends AbstractGrid3D<Tile> {
	private static final long serialVersionUID = 1060623638149583738L;
	Tile fillStruct = Tile.FLOOR;
	Tile border = Tile.WALL;

	public Tile border() {
		return border;
	}

	public MapChunk(double length, double width, double height) {
		super((int) length, (int) width, (int) height);
		fill();
	}

	public MapChunk(int length, int width, int height) {
		super(length, width, height);
		fill();
		// drawLine(new Point(5,5), new Point(80, 24));
	}

	public MapChunk(Point3D fullPoint) {
		super(fullPoint.getX(), fullPoint.getY(), fullPoint.getZ());
		fill();
	}

	MapChunk(MapChunk copyChunk) {
		super(copyChunk.length(), copyChunk.width(), copyChunk.height());
		fill();
	}

	/**
	 * Fills the entire map with specified tile
	 *
	 * @param c tile which fills the entire map
	 */
	@Override
	public void fill() {
		for (int a = 0; a <= height(); a++) {
			add(new ArrayList<ArrayList<Tile>>());
			for (int b = 0; b <= width(); b++) {
				get(a).add(new ArrayList<Tile>());
				for (int c = 0; c <= length(); c++) {
					get(a).get(b).add(fillStruct);
				}
			}
		}
	}

	/**
	 * Returns a char Array of the symbols of the tiles on this MapChunk
	 */
	public char[][] toCharArray() {
		char[][] charReturn = new char[super.width()][super.length()];
		for (int y = 0; y < super.width(); y++) {
			for (int x = 0; x < super.width(); x++) {
				charReturn[y][x] = this.unitAt(new Point(x, y)).symbol();
			}
		}
		return charReturn;
	}

	/**
	 * A 2d line drawing algorithm that takes two points an draws a line between
	 * them It determines the slope of the segment and then simplifies the slope
	 * With a nested for loop it draws a segment as many times as the slope has been
	 * simplified It then breaks the slope segments up by each square and adds the
	 * proper slice of the slope From here, tilePlace is called to place the tile
	 *
	 * @param startPoint Line goes from this point on the map
	 * @param endPoint   to this point on the map
	 */
	/*
	 * public void drawLine(Point startPoint, Point endPoint) { double dx =
	 * endPoint.x - startPoint.x; // System.out.println("dx:" + dx); double dy =
	 * endPoint.y - startPoint.y; // System.out.println("dy:" + dy); double rise;
	 * double run; int simpFactor = Util.GCD(Math.abs(dx), Math.abs(dy)); //
	 * System.out.println("simpFactor:" + simpFactor); if (dx < 0 && dy < 0) { rise
	 * = Math.abs(dy); run = Math.abs(dx); } else if (dx < 0 && !(dy < 0)) { rise =
	 * (int) dy * -1; run = Math.abs(dx); } else { rise = dy; run = dx; } rise /=
	 * simpFactor; run /= simpFactor; double slopeMax = Math.max(Math.abs(rise),
	 * Math.abs(run)); // System.out.println("slopeMax:" + slopeMax); //
	 * System.out.println("rise:" + rise); System.out.println("run:" +run); double x
	 * = startPoint.x; double y = startPoint.y; place(startPoint, Tile.WALL); for
	 * (int n = 1; n <= simpFactor; n++) { for (int m = 1; m <= slopeMax; m++) { try
	 * { y += rise / slopeMax; x += run / slopeMax; // System.out.println("x:" + x);
	 * System.out.println("y:" + y); place(new Point((int) x, (int) y), Tile.WALL);
	 * } catch (IndexOutOfBoundsException e) { break; } } } }
	 */

	/**
	 * Scans the map to create a list of 1 or 0 for each tile, representing whether
	 * or not the tile is opaque or transparent
	 *
	 * @return
	 */
	public int[][][] opaqueScan() {
		return (scanTiles(tile -> tile.transparent()));
	}

	/**
	 * Scans the map to create a list of 1 or 0 for each tile, representing whether
	 * the tile satisfies the condition
	 */
	public int[][][] scanTiles(Predicate<Tile> cond) {
		int[][][] returnMatrix = new int[height() + 1][width() + 1][length() + 1];
		for (int z = 0; z < height(); z++) {
			for (int y = 0; y < width(); y++) {
				for (int x = 0; x < length(); x++) {
					if (cond.test(unitAt(new Point3D(x, y, z)))) {
						returnMatrix[z][y][x] = 0;
					} else {
						returnMatrix[z][y][x] = 1;
					}
				}
			}
		}
		return returnMatrix;
	}
}
