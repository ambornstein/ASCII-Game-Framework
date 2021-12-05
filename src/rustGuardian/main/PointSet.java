package rustGuardian.main;

import java.util.HashSet;
import javafx.geometry.Point3D;

/**
 * A class that collects Point3Ds. Since it is a collection of points, this class does not store information about the structures located at these locations.
 * 
 * @author ambor
 */
public class PointSet extends HashSet<Point3D> {
	private static final long serialVersionUID = 1L;
	
	public PointSet(Point3D startCorner, Point3D endCorner) {
		construct(startCorner, endCorner);
	}

	/**Creates a rectangular set of points from an initial and final point.
	 * 
	 * @param startCorner Point3D of the lower bound corner
	 * @param endCorner Point3D of the upper bound corner
	 */
	public void construct(Point3D startCorner, Point3D endCorner) { // copies all of the tiles from the startCorner to
																	// the endCorner to this PointSet
		startCorner = RelativePos.correctOutOfBounds(startCorner); // Correct positions that are out of the bounds of
																	// the world
		RelativePos startCornerRel = RelativePos.toRel(startCorner); // Make Relative copies of both for future
																		// compare()
		endCorner = RelativePos.correctOutOfBounds(endCorner);
		RelativePos endCornerRel = RelativePos.toRel(endCorner);
		if (endCornerRel.compare(startCornerRel) == -1) {
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
