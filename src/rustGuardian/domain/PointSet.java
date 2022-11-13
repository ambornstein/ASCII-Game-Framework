package rustGuardian.domain;

import java.util.HashSet;

import javafx.geometry.Point3D;
import rustGuardian.main.Util;

/**
 * A class that collects Point3Ds. Since it is a collection of points, this class does not store information about the structures located at these locations.
 *
 * @author ambor
 */
public class PointSet extends HashSet<Point3D> {
	private static final long serialVersionUID = 1L;
	private	Point3D startCorner;
	private Point3D endCorner;

	public PointSet(Point3D startCorner, Point3D endCorner) {
		construct(startCorner, endCorner);
	}

	public Point3D startCorner() {
		return startCorner;
	}

	public Point3D endCorner() {
		return endCorner;
	}

	public Point3D range() {
		return endCorner.subtract(startCorner);
	}

	public Point3D delta(Point3D p) {
		return p.subtract(startCorner);
	}

	public int deltaX(Point3D p) {
		return (int) delta(p).getX();
	}

	public int deltaX(int p) {
		return p-(int)startCorner.getX();
	}

	public int deltaY(Point3D p) {
		return (int) delta(p).getY();
	}

	public int deltaY(int p) {
		return p-(int)startCorner.getY();
	}

	public int deltaZ(Point3D p) {
		return (int) delta(p).getZ();
	}

	public int deltaZ(int p) {
		return p-(int)startCorner.getZ();
	}
	/**
	 * 
	 * @return
	 */
	public PointSet normalize() {
		Point3D newStart = startCorner, newEnd = endCorner;
		for (Point3D p : this) {
			if (Util.pointCompare(p, Point3D.ZERO) == -1) {
				newStart = Point3D.ZERO;
			}
			if (Util.pointCompare(RelativePos.generator().toAbs(), p) == 1) {
				newEnd = RelativePos.generator().toAbs();
			}
		}
		return new PointSet(newStart, newEnd);
	}

	/**Creates a rectangular set of points from an initial and final point.
	 *
	 * @param startCorner Point3D of the lower bound corner
	 * @param endCorner Point3D of the upper bound corner
	 */
	public void construct(Point3D start, Point3D end) { // copies all of the tiles from the startCorner to
																	// the endCorner to this PointSet
		this.startCorner = start;
		this.endCorner = end;
		if (Util.pointCompare(start, end) == -1) {
			for (int y = (int) startCorner.getY(); y <= endCorner.getY(); y++) {
				for (int x = (int) startCorner.getX(); x <= endCorner.getX(); x++) {
					for (int z = (int) startCorner.getZ(); z <= endCorner.getZ(); z++) {
						if (!contains(new Point3D(x, y, z))) {
							add(new Point3D(x, y, z));
						}
					}
				}
			}
		}
	}
}
