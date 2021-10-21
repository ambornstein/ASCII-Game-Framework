package rustGuardian.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import rustGuardian.main.EntityContainer;
import rustGuardian.main.RelativePos;
import rustGuardian.main.World;

class EntityContainerTest {
	
	RelativePos gen = new RelativePos(1, 1, 3, 3, 3);
	World world = RelativePos.makeWorld(gen);
	
	@Test
	void test() {
		new EntityContainer(world);
		EntityContainer.getDefaultPlayerEntity();
		System.out.println(EntityContainer.activeEntity);
		assertNotNull(EntityContainer.activeEntity);
	}

}
