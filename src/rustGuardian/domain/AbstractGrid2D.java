package rustGuardian.domain;

import java.awt.Point;
import java.util.ArrayList;

/**
 * 2D matrix of any class
 *
 * @author ambor
 *
 * @param <E> Class of structure that composes the grid
 */
public abstract class AbstractGrid2D<E> extends ArrayList<ArrayList<E>> implements GridInterface<E> {
	private static final long serialVersionUID = 1060623638149583738L;
	protected int length; // Xaxis
	protected int width; // Yaxis

	public AbstractGrid2D(Point dims) {
		length = dims.x;
		width = dims.y;
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
	 * Vertical (Z) dimension of this grid (Always 0 for 2d grid)
	 *
	 */
	@Override
	public int height() {
		return 0;
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
	@Override
	public E unitAt(Point loc) {
		try {
			return get(loc.y).get(loc.x);
		} catch (IndexOutOfBoundsException e) {
			// System.out.println("Out of bounds of 2D Grid:" + loc);
			return null;
		}
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
	public boolean unitConnect(Point loc, Direction dir) {
		E subUnit = unitAt(new Point((int) (loc.x + dir.offset().getX()), (int) (loc.y + dir.offset().getY())));
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
	public void place(Point loc, E unit) {
		try {
			get(loc.y).set(loc.x, unit);
		} catch (IndexOutOfBoundsException i) {
			System.out.println("Cannot place " + unit + " at " + loc);
		}
	}
}
