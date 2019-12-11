package rustGuardian.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import rustGuardian.main.EntityContainer;

class EntityContainerTest {

	@Test
	void test() {
		new EntityContainer();
		EntityContainer.getDefaultPlayerEntity();
		System.out.println(EntityContainer.activeEntity);
		assertNotNull(EntityContainer.activeEntity);
	}

}
