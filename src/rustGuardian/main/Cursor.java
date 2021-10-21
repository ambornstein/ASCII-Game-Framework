package rustGuardian.main;

import javafx.geometry.Point3D;

public class Cursor extends AbstractMoveable {
	public Cursor() {
		super(new Point3D(0, 0, 0), false, 'X', true);
		Point3D posToClone = EntityContainer.getDefaultPlayerEntity().getAbsPosition();
		super.setPos(new Point3D(posToClone.getX(), posToClone.getY(), posToClone.getZ())); 
		//must dissassemble this Point3D because Point3D has no copy-constructor or clone()
	}
	
	public Cursor(Point3D loc) {
		super(loc, false, 'X', true);
	}
	
	public Cursor(RelativePos loc) {
		super(loc.toAbs(), false, 'X', true);
	}

	@Override
	public void move(Direction d) {
		RelativePos rel = RelativePos.toRel(absPosition.add(d.offSet())); // rel is a relative copy of the player's projected movement
		if (RelativePos.generator().compare(rel) == -1) { // rel is less than generator bounds while still being a whole number (in world bounds)
			absPosition = absPosition.add(d.offSet()); // the test is passed, allowing the real position to be changed
			ApplicationMain.refresh();
		}
	}
}
