package rustGuardian.main;

import java.awt.Point;

import javafx.geometry.Point3D;

public final class Util {
	public static int GCD(int a, int b) {
		if (b == 0)
			return a;
		else
			return (GCD(b, a % b));
	}

	public static int GCD(double a, double b) {
		if ((int) b == 0)
			return (int) a;
		else
			return (GCD((int) b, (int) a % (int) b));
	}

	public static Point decrement(Point point) {
		return new Point(point.x-1, point.y-1);
	}

	public static Point decrement(int x, int y) {
		return new Point(x-1, y-1);
	}

	public static Point decrement(double x, double y) {
		return new Point((int) x-1, (int) y-1);
	}

	public static Point3D decrement(Point3D point) {
		return new Point3D(point.getX()-1, point.getY()-1, point.getZ()-1);
	}

	public static Point3D decrement(int x, int y, int z) {
		return new Point3D(x-1, y-1, z-1);
	}

	public static Point3D decrement(double x, double y, double z) {
		return new Point3D(x-1, y-1, z-1);
	}
	public static int pointCompare(Point3D i, Point3D j) {
		if (i.equals(j)) {
			return 0;
		}
		else if (i.getX() > j.getX() && i.getY() > j.getY() && i.getZ() > j.getZ()) {
			return 1;
		}
		else {
			return -1;
		}
	}
}