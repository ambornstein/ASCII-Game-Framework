package rustGuardian.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import javafx.geometry.Point3D;
import rustGuardian.domain.Generator;
import rustGuardian.domain.RelativePos;
import rustGuardian.domain.World;

class RelativePosTest {
	RelativePos gen = new RelativePos(new Point(2, 2), new Point3D(40, 25, 5));
	World world = new World(new Generator(gen));
	RelativePos outOfBounds = new RelativePos(new Point(1, 1), new Point3D(0, 37, 2));
	RelativePos inBounds = new RelativePos(new Point(1, 1), new Point3D(24, 0, 2));
	RelativePos negative1 = new RelativePos(new Point(1, 1), new Point3D(-2, -2, -2));
	RelativePos negative2 = new RelativePos(new Point(1, 1), new Point3D(-2, 0, 0));
	RelativePos negative3 = new RelativePos(new Point(1, 1), new Point3D(-0, -2, 0));
	RelativePos negative4 = new RelativePos(new Point(1, 1), new Point3D(0, 0, -2));
	RelativePos relPos1 = new RelativePos(new Point(2, 2), new Point3D(23, 4, 2)); // 62, 28, 1
	RelativePos relPos2 = new RelativePos(new Point(2, 2), new Point3D(36, 4, 5)); // 75, 28, 4
	RelativePos relPos3 = new RelativePos(new Point(1, 1), new Point3D(1, 1, 1)); // 0, 0, 0
	RelativePos relPos4 = new RelativePos(new Point(2, 2), new Point3D(40, 25, 5)); // 79, 49, 4
	RelativePos relPos5 = new RelativePos(new Point(1, 1), new Point3D(40, 25, 5)); // 39, 24, 4
	Point3D absPos1 = new Point3D(62, 28, 1);
	Point3D absPos2 = new Point3D(75, 28, 4);
	Point3D absPos3 = new Point3D(0, 0, 0);
	Point3D absPos4 = new Point3D(79, 49, 4);
	Point3D absPos5 = new Point3D(39, 24, 4);

	@Test
	void test() { // onePos.compare(otherPos) cheat sheet
					// -2 = otherPos is less than 0 (minimum bounds for all worlds)
					// -1 = otherPos is less than onePos but not below 0
					// 0 = otherPos and onePos are equivalent
					// 1 = otherPos is greater than onePos
		assertTrue(gen.compare(outOfBounds) == 1);
	}
	@Test
	void test1() {
		assertTrue(gen.compare(inBounds) == -1);
	}
	@Test
	void test2() {
		assertTrue(gen.compare(negative1) == -2);
	}
	@Test
	void test3() {
		assertTrue(gen.compare(negative2) == -2);
	}
	@Test
	void test4() {
		assertTrue(gen.compare(negative3) == -2);
	}
	@Test
	void test5() {
		assertTrue(gen.compare(negative4) == -2);
	}
	@Test
	void test6() {
		assertTrue(relPos1.toAbs().equals(new Point3D(62, 28, 1)));
	}
	@Test
	void test7() {
		assertTrue(relPos2.toAbs().equals(new Point3D(75, 28, 4)));
	}
	@Test
	void test8() {
		assertTrue(relPos3.toAbs().equals(new Point3D(0, 0, 0)));
	}
	@Test
	void test9() {
		assertTrue(relPos4.toAbs().equals(new Point3D(79, 49, 4)));
	}
	@Test
	void test10() {
		assertTrue(relPos5.toAbs().equals(new Point3D(39, 24, 4)));
	}
	@Test
	void test11() {
		System.out.println(RelativePos.toRel(absPos1));
		assertEquals(RelativePos.toRel(absPos1), (new RelativePos(new Point(2, 2), new Point3D(23, 4, 2))));
	}
	@Test
	void test12() {
		System.out.println(RelativePos.toRel(absPos2));
		assertEquals(RelativePos.toRel(absPos2), (new RelativePos(new Point(2, 2), new Point3D(36, 4, 5))));
	}
	@Test
	void test13() {
		assertEquals(RelativePos.toRel(absPos3), (new RelativePos(new Point(1, 1), new Point3D(1, 1, 1))));
	}
	@Test
	void test14() {
		System.out.println(RelativePos.toRel(absPos4));
		assertEquals(RelativePos.toRel(absPos4), (new RelativePos(new Point(2, 2), new Point3D(40, 25, 5))));
	}
	@Test
	void test15() {
		assertEquals(RelativePos.toRel(absPos5), (new RelativePos(new Point(1, 1), new Point3D(40, 25, 5))));
	}
	@Test
	void test16() { //x over bounds
		assertTrue(RelativePos.correctOutOfBounds(new Point3D(99, 49, 4)).equals(new Point3D(79, 49, 4)));
		assertTrue(RelativePos.correctOutOfBounds(new Point3D(80, 49, 4)).equals(new Point3D(79, 49, 4)));
	}
	@Test
	void test17() { //y over bounds
		assertTrue(RelativePos.correctOutOfBounds(new Point3D(79, 99, 4)).equals(new Point3D(79, 49, 4)));
	}
	@Test
	void test18() { // z over max
		assertTrue(RelativePos.correctOutOfBounds(new Point3D(79, 49, 99)).equals(new Point3D(79, 49, 4)));
	}
	@Test
	void test19() { // x under min
		assertTrue(RelativePos.correctOutOfBounds(new Point3D(-99, 49, 4)).equals(new Point3D(0, 49, 4)));
	}
	@Test
	void test20() {// y under min
		assertTrue(RelativePos.correctOutOfBounds(new Point3D(79, -99, 4)).equals(new Point3D(79, 0, 4)));
	}
	@Test
	void test21() {// z under min
		assertTrue(RelativePos.correctOutOfBounds(new Point3D(79, 49, -99)).equals(new Point3D(79, 49, 0)));
	}
}
