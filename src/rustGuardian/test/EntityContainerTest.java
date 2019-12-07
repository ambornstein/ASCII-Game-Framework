package rustGuardian.test;

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
