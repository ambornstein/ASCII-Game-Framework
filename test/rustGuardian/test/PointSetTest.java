package rustGuardian.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.awt.Point;

import org.junit.Test;

import javafx.geometry.Point3D;
import rustGuardian.domain.Generator;
import rustGuardian.domain.PointSet;
import rustGuardian.domain.RelativePos;
import rustGuardian.domain.World;

class PointSetTest {
	
	RelativePos gen = new RelativePos(new Point(2, 2), new Point3D(40, 25, 5));
	World world = new World(new Generator(gen));
	
	@Test
	void test() {
		PointSet sampleSet = new PointSet(new Point3D(0, 0, 0), new Point3D(3, 3, 3));
		System.out.println(sampleSet);
		assertFalse(sampleSet.isEmpty());
		assertEquals((int)sampleSet.size(), (int)Math.pow(4, 3));
	}
}
