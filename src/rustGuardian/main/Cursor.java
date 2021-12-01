package rustGuardian.main;

import javafx.geometry.Point3D;

public class Cursor extends AbstractMoveable {
	public Cursor() {
		super(new Point3D(0, 0, 0), false, 'X', true);
	}
	
	public Cursor(Point3D loc) {
		super(loc, false, 'X', true);
	}
	
	public Cursor(RelativePos loc) {
		super(loc.toAbs(), false, 'X', true);
	}
}
