package rustGuardian.main;

import java.util.ArrayList;
import java.util.HashSet;
import javafx.geometry.Point3D;

public class EntityContainer {
	public static HashSet<AbstractMoveable> entityDir; // All entities go here; Define and subdivide 'entity' to be more
														// specific
	public static AbstractMoveable activeEntity; // The active entity that will be controllable
	public static World world;

	public EntityContainer(World world) {
		entityDir = new HashSet<AbstractMoveable>();
		entityDir.add(new Player(new Point3D(2, 2, 1)));
		playerActivate();
		entityDir.add(new Cursor(activeEntity.getAbsPosition()));
		
	}

	public static void setWorld(World worldToSet) {
		world = worldToSet;
	}

	public static World getWorld() {
		return world;
	}

	public static void setActiveEntity(AbstractMoveable newEntity) {
		activeEntity = newEntity;
	}

	public static IMoveable activeEntity() {
		return activeEntity;
	}

	public static void cursorActivate() {
		setActiveEntity(getDefaultCursorEntity());
		activeEntity.setPos(getDefaultPlayerEntity().getAbsPosition());
	}

	public static void playerActivate() {
		setActiveEntity(getDefaultPlayerEntity());
	}

	public static AbstractMoveable getDefaultPlayerEntity() { // The system is bullshit and must be changed quick
		for (AbstractMoveable m : entityDir) {
			if (m.getClass() == Player.class) {
				return m;
			}
		}
		return null;
	}

	public static AbstractMoveable getDefaultCursorEntity() {
		for (AbstractMoveable m : entityDir) {
			if (m.getClass() == Cursor.class) {
				return m;
			}
		}
		return null;
	}

	public static ArrayList<AbstractMoveable> getAllVisibleEntity() {
		ArrayList<AbstractMoveable> visibleList = new ArrayList<AbstractMoveable>();
		for (AbstractMoveable m : entityDir) {
			if (m.getVisible()) {
				visibleList.add(m);
			}
		}
		return visibleList;
	}
}