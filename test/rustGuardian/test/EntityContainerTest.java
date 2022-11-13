package rustGuardian.test;

import static org.junit.Assert.assertNotNull;

import java.awt.Point;

import org.junit.Test;

import javafx.geometry.Point3D;
import rustGuardian.domain.EntityContainer;
import rustGuardian.domain.Generator;
import rustGuardian.domain.RelativePos;
import rustGuardian.domain.World;

class EntityContainerTest {

	RelativePos gen = new RelativePos(new Point(2, 2), new Point3D(40, 25, 5));
	World world = new World(new Generator(gen));

	@Test
	void test() {
		new EntityContainer(world);
		EntityContainer.getDefaultPlayerEntity();
		System.out.println(EntityContainer.activeEntity);
		assertNotNull(EntityContainer.activeEntity);
	}

}
