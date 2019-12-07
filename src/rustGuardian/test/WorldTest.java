package rustGuardian.test;

import javafx.geometry.Point3D;
import rustGuardian.main.Direction;
import rustGuardian.main.RelativePos;
import rustGuardian.main.Tile;

class WorldTest {

	@Test
	void test() {
		// RelativePos gen = new RelativePos(2,2,40,25,5);
		RelativePos gen = new RelativePos(1, 1, 3, 3, 3);
		System.out.println(gen);
		RelativePos.makeWorld(gen);
		System.out.println(gen.toAbs());
		System.out.println(gen.compare(new RelativePos(1, 1, 40, 25, 5)));
		System.out.println(new RelativePos(1, 1, 1, 1, 1).findTile());
		/*
		 * assertTrue(new RelativePos(1,1,1,1,1).findTile() == Tile.WALL);
		 * System.out.println(new RelativePos(1,1,40,25,5).findTile()); assertTrue(new
		 * RelativePos(1,1,40,25,5).findTile() == Tile.WALL); System.out.println(new
		 * RelativePos(1,1,40,25,1).findTile()); assertTrue(new
		 * RelativePos(1,1,40,25,1).findTile() == Tile.WALL); System.out.println(new
		 * RelativePos(1,1,40,1,3).findTile()); assertTrue(new
		 * RelativePos(1,1,40,1,3).findTile() == Tile.WALL); System.out.println(new
		 * RelativePos(2,1,40,25,3).findTile()); assertTrue(new
		 * RelativePos(2,1,40,25,3).findTile() == Tile.WALL); System.out.println(new
		 * RelativePos(1,2,40,25,3).findTile()); assertTrue(new
		 * RelativePos(1,2,40,25,3).findTile() == Tile.WALL); System.out.println(new
		 * RelativePos(1,1,1,25,3).findTile()); assertTrue(new
		 * RelativePos(1,1,1,25,3).findTile() == Tile.WALL);
		 */
		/*
		 * Point3D dims = RelativePos.generator().toAbs(); for (Direction dir :
		 * Direction.AXIS) { Point3D originCorner = new Point3D(dims.getX() *
		 * dir.offSet().getX(), dims.getY() * dir.offSet().getY(), dims.getZ() *
		 * dir.offSet().getZ()); Point3D destinationCorner = new Point3D(dims.getX() +
		 * (dims.getX() * dir.offSet().getX()), dims.getY() + (dims.getY() *
		 * dir.offSet().getY()), dims.getZ() + (dims.getZ() * dir.offSet().getZ()));
		 * System.out.println(); System.out.println("Direction of shift:" + dir);
		 * System.out.println(originCorner + "," +
		 * RelativePos.correctOutOfBounds(originCorner));
		 * System.out.println(destinationCorner + "," +
		 * RelativePos.correctOutOfBounds(destinationCorner)); }
		 */
	}
}
