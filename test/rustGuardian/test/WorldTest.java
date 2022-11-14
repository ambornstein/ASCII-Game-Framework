package rustGuardian.test;

import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Test;

import javafx.geometry.Point3D;
import rustGuardian.domain.Generator;
import rustGuardian.domain.RelativePos;
import rustGuardian.domain.Tile;
import rustGuardian.domain.World;

public class WorldTest {
	RelativePos gen = new RelativePos(new Point(2, 2), new Point3D(40, 25, 5));
	World world = new World(new Generator(gen));

	/*
	 * RelativePos gen = new RelativePos(2,2,40,25,5); Point3D dims =
	 * RelativePos.generator().toAbs(); for (Direction dir : Direction.AXIS) {
	 * Point3D originCorner = new Point3D(dims.getX() * dir.offSet().getX(),
	 * dims.getY() * dir.offSet().getY(), dims.getZ() * dir.offSet().getZ());
	 * Point3D destinationCorner = new Point3D(dims.getX() + (dims.getX() *
	 * dir.offSet().getX()), dims.getY() + (dims.getY() * dir.offSet().getY()),
	 * dims.getZ() + (dims.getZ() * dir.offSet().getZ())); System.out.println();
	 * System.out.println("Direction of shift:" + dir);
	 * System.out.println(originCorner + "," +
	 * RelativePos.correctOutOfBounds(originCorner));
	 * System.out.println(destinationCorner + "," +
	 * RelativePos.correctOutOfBounds(destinationCorner)); }
	 */
	@Test
	public void test() {
		assertTrue(world.relativeFindTile(new RelativePos(1, 1, 1, 1, 1)) == Tile.WALL);
	}

	@Test
	public void test2() {
		assertTrue(world.relativeFindTile(new RelativePos(2, 2, 40, 25, 5)) == Tile.WALL);
	}

	@Test
	public void test3() {
		assertTrue(world.relativeFindTile(new RelativePos(1, 1, 40, 25, 1)) == Tile.WALL);
	}

	@Test
	public void test4() {
		assertTrue(world.relativeFindTile(new RelativePos(1, 1, 40, 1, 1)) == Tile.WALL);
	}

	@Test
	public void test5() {
		assertTrue(world.relativeFindTile(new RelativePos(2, 1, 40, 25, 3)) == Tile.WALL);
	}

	@Test
	public void test6() {
		assertTrue(world.relativeFindTile(new RelativePos(1, 2, 40, 25, 3)) == Tile.WALL);
	}

	@Test
	public void test7() {
		assertTrue(world.relativeFindTile(new RelativePos(1, 1, 1, 50, 3)) == Tile.WALL);
	}
}
