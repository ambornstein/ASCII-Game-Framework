package rustGuardian.main;

import java.awt.Point;
import java.util.ArrayList;
import javafx.geometry.Point3D;

public abstract class AbstractGrid3D<E> extends ArrayList<ArrayList<ArrayList<E>>> implements GridInterface<E> {
	private static final long serialVersionUID = 1060623638149583738L;
	protected int length; // Xaxis
	protected int width; // Yaxis
	protected int height; // Zaxis

	@Override
	public int length() {
		return length;
	}

	@Override
	public int width() {
		return width;
	}

	@Override
	public int height() {
		return height;
	}

	@Override
	public abstract void fill();

	public E unitAt(Point3D loc) {
		try {
			return get((int) loc.getY()).get((int) loc.getX()).get((int) loc.getZ());
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Out of bounds in 3D Grid:" + loc);
			return null;
		}
	}

	@Override
	public E unitAt(Point pos) {
		return unitAt(new Point3D(pos.x, pos.y, 0));
	}

	public boolean unitConnect(Point3D loc, Direction dir) {
		E subUnit = unitAt(loc.add(dir.offSet()));
		if (subUnit == null) {
			return false;
		} else {
			return true;
		}
	}

	public void place(Point3D loc, E unit) {
		try {
			get((int) loc.getY()).get((int) loc.getX()).set((int) loc.getZ(), unit);
		} catch (IndexOutOfBoundsException i) {
			place(RelativePos.correctOutOfBounds(loc), unit);
		}
	}

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
}
