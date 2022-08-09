package rustGuardian.main;

import javafx.geometry.Point3D;

/**
 * An enum of offsets that would be used if you wanted to move in a certain
 * direction on a grid
 */
public enum Direction {
	NORTH(0, -1, 0), SOUTH(0, 1, 0), EAST(1, 0, 0), WEST(-1, 0, 0), NORTHEAST(1, -1, 0), NORTHWEST(-1, -1,
			0), SOUTHEAST(1, 1, 0), SOUTHWEST(-1, 1, 0), UP(0, 0, 1), DOWN(0, 0, -1);
	public static final Direction[] ALLDIR = { NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST, UP,
			DOWN };
	public static final Direction[] CARDINAL = { NORTH, SOUTH, EAST, WEST };
	public static final Direction[] AXIS = { NORTH, SOUTH, EAST, WEST, UP, DOWN };
	public static final Direction[] DIAGONAL = { NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST };

	private final Point3D offset;

	public Point3D offset() {
		return offset;
	}

	public static final boolean inDirCategory(Direction subjectDir, Direction[] dirCategory) {
		for (Direction currDir : dirCategory) {
			if (currDir.equals(subjectDir)) {
				return true;
			}
		}
		return false;
	}

	Direction(int x, int y, int z) {
		offset = new Point3D(x, y, z);
	}

	Direction(Point3D offSet) {
		this.offset = offSet;
	}

	@Override
	public String toString() {
		return name() + ": " + offset();
	}
}