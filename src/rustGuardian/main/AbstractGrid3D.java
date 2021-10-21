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
			return get((int)loc.getZ()).get((int) loc.getY()).get((int) loc.getX());
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Out of bounds in 3D Grid:" + loc + " | Limit: " + new Point3D(length, width, height));
			return null;
		}
	}

	@Override
	public E unitAt(Point loc) {
		return unitAt(new Point3D(loc.x, loc.y, 0));
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
			get((int) loc.getZ()).get((int) loc.getY()).set((int) loc.getX(), unit);
		} catch (IndexOutOfBoundsException i) {
			System.out.println("Unable to place tile " + unit + " in 3D Grid:" + loc + " | Limit: " + new Point3D(length, width, height));
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
