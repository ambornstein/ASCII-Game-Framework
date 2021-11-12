package rustGuardian.main;

import java.util.ArrayList;
import java.util.HashSet;
import javafx.geometry.Point3D;

public class EntityContainer {
	private HashSet<AbstractMoveable> entityDir; // All entities go here; Define and subdivide 'entity' to be more
														// specific
	private AbstractMoveable activeEntity; // The active entity that will be controllable
	
	public EntityContainer() {
		entityDir = new HashSet<AbstractMoveable>();
		Player player = new Player(new RelativePos(1, 1, 3, 3, 3));
		addEntity(player);
		addEntity(new Cursor(player.getRelPosition()));
	}

	public void addEntity(AbstractMoveable entity) {
		if (entityDir.isEmpty()) {
			setActiveEntity(entity);
		}
		entityDir.add(entity);
	}

	public void setActiveEntity(AbstractMoveable newEntity) {
		activeEntity = newEntity;
	}
	
	public void playerActivate() {
		setActiveEntity(getDefaultPlayerEntity());
	}

	public IMoveable activeEntity() {
		return activeEntity;
	}

	public void cursorActivate() {
		setActiveEntity(getDefaultCursorEntity());
		activeEntity.setPos(getDefaultPlayerEntity().getAbsPosition());
	}

	public AbstractMoveable getDefaultPlayerEntity() { // The system is bullshit and must be changed quick
		for (AbstractMoveable m : entityDir) {
			if (m.getClass() == Player.class) {
				return m;
			}
		}
		return null;
	}

	public AbstractMoveable getDefaultCursorEntity() {
		for (AbstractMoveable m : entityDir) {
			if (m.getClass() == Cursor.class) {
				return m;
			}
		}
		return null;
	}

	public ArrayList<AbstractMoveable> getAllVisibleEntity() {
		ArrayList<AbstractMoveable> visibleList = new ArrayList<AbstractMoveable>();
		for (AbstractMoveable m : entityDir) {
			if (m.getVisible()) {
				visibleList.add(m);
			}
		}
		return visibleList;
	}
}