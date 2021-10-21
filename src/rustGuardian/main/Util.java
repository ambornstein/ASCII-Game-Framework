package rustGuardian.main;

import java.awt.Point;

import javafx.geometry.Point3D;

public class Util {
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
	
	public static Point3D decrement(Point3D point) {
		return new Point3D(point.getX()-1, point.getY()-1, point.getZ()-1);
	}
}