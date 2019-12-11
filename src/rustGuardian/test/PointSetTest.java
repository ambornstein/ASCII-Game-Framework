package rustGuardian.test;

import static org.junit.Assert.assertFalse;
import org.junit.*;

import javafx.geometry.Point3D;
import rustGuardian.main.PointSet;

class PointSetTest {

	@Test
	void test() {
		PointSet sampleSet = new PointSet(new Point3D(0, 0, 0), new Point3D(3, 3, 3));
		System.out.println(sampleSet);
		assertFalse(sampleSet.isEmpty());
	}
}
