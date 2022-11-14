package rustGuardian.domain;

import java.awt.Point;
import java.util.ArrayList;

import javafx.geometry.Point3D;

/**
 * Abstract for the purpose of creating 3D grids
 *
 * @author ambor
 *
 * @param <E> Class of structure that composes the grid
 */
public abstract class AbstractGrid3D<E> extends ArrayList<ArrayList<ArrayList<E>>> implements GridInterface<E> {
	private static final long serialVersionUID = 1060623638149583738L;
	protected int length; // Xaxis
	protected int width; // Yaxis
	protected int height; // Zaxis

	public AbstractGrid3D(int length, int width, int height) {
		this.length = length;
		this.width = width;
		this.height = height;
	}

	public AbstractGrid3D(double length, double width, double height) {
		this.length = (int) length;
		this.width = (int) width;
		this.height = (int) height;
	}

	/**
	 * Horizontal (X) dimension of this grid
	 *
	 */
	@Override
	public int length() {
		return length;
	}

	/**
	 * Lateral (Y) dimension of this grid
	 *
	 */
	@Override
	public int width() {
		return width;
	}

	/**
	 * Vertical (Z) dimension of this grid
	 *
	 */
	@Override
	public int height() {
		return height;
	}

	/**
	 * Fills the Grid with generic placeholder units to mark space that will be used
	 * by more specific units in the future. It is difficult to place a unique
	 * Object on every space (Troubles with cloning an object which type could be
	 * anything), so fill() must be abstracted and implemented by individual
	 * subclasses
	 */
	@Override
	public abstract void fill();

	/**
	 * Return a unit that is located at the provided Point
	 *
	 * @param loc Point of the unit that should be returned.
	 * @return Tile of either a valid unit in case of it being found, or null if not
	 */
	public E unitAt(Point3D loc) {
		try {
			return get((int) loc.getZ()).get((int) loc.getY()).get((int) loc.getX());
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Out of bounds in 3D Grid:" + loc + " | Limit: " + new Point3D(length, width, height));
			return null;
		}
	}

	@Override
	public E unitAt(Point loc) {
		return unitAt(new Point3D(loc.x, loc.y, 0));
	}

	/**
	 * Test of whether a unit connects to the unit at the specified Point, in a
	 * certain Direction. It does not have an Object argument because at the current
	 * state, it tests whether a unit EXISTS, not if it matches a certain Object.
	 *
	 * @param loc Point that the test originates from.
	 * @param dir Direction from the origin point that should be tested for the
	 *            presence of a grid unit
	 * @return Boolean where true represents a unit being present in the test, and
	 *         where false represents a unit not being present
	 */
	public boolean unitConnect(Point3D loc, Direction dir) {
		E subUnit = unitAt(loc.add(dir.offset()));
		if (subUnit == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Places or replaces a unit on a specified location.
	 *
	 * @param loc  Point to place the unit at
	 * @param unit Unit to place (must be the type that the AbstractGrid2D was)
	 *             provided at initialization
	 */
	public void place(Point3D loc, E unit) {
		try {
			get((int) loc.getZ()).get((int) loc.getY()).set((int) loc.getX(), unit);
		} catch (IndexOutOfBoundsException i) {
			System.out.println("Unable to place tile " + unit + " in 3D Grid:" + loc + " | Limit: "
					+ new Point3D(length, width, height));
		}
	}
}
