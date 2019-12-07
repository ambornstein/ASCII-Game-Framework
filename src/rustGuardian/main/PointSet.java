package rustGuardian.main;

import java.util.HashSet;
import javafx.geometry.Point3D;

/**
 * A class that collects Point3Ds and calls methods on the corresponding tile
 * positions in the world.
 * 
 * @author ambor
 */
public class PointSet extends HashSet<Point3D> {
	private static final long serialVersionUID = 1L;

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

	public PointSet(Point3D startCorner, Point3D endCorner) {
		construct(startCorner, endCorner);
	}

	// Should be rewritten to generalize later, once it becomes evident what other
	// grids should be copied
	public MapChunk copyGrid(Point3D startPoint, Point3D endPoint) {
		construct(startPoint, endPoint);
		Point3D deltaPoint = new Point3D(endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY(),
				endPoint.getZ() - startPoint.getZ());
		if (this.isEmpty()) {
			return new MapChunk(0, 0, 0);
		}
		MapChunk returnChunk = new MapChunk(deltaPoint);
		forEach((Point3D p) -> returnChunk.place(
				new Point3D(p.getX() - startPoint.getX(), p.getY() - startPoint.getY(), p.getZ() - startPoint.getZ()),
				RelativePos.toRel(p).findTile()));
		return returnChunk;
	}
}
