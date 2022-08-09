package rustGuardian.main;

import java.util.ArrayList;
import java.util.HashSet;
import javafx.geometry.Point3D;

public class EntityContainer {
	private HashSet<AbstractMoveable> entityDir; // All entities go here; Define and subdivide 'entity' to be more
														// specific
	private Cursor activeCursor; // The active entity that will be controllable
	private Player activePlayer;
	private World world;
	private AbstractMoveable unit;
	
	public EntityContainer(World world) {
		this.world = world;
		entityDir = new HashSet<AbstractMoveable>();
		activePlayer = new Player(new RelativePos(1, 1, 3, 3, 3));
		activatePlayer();
		activeCursor = new Cursor();
		addEntity(activePlayer);
		addEntity(activeCursor);
	}

	public void addEntity(AbstractMoveable entity) {
		entityDir.add(entity);
	}
	
	public Player activePlayer() {
		return activePlayer;
	}
	
	public void activatePlayer() {
		unit = activePlayer();
	}
	
	public Cursor activeCursor() {
		placeMoveable(activePlayer().getRelPosition());
		return activeCursor;
	}
	
	public void activateCursor() {
		unit = activeCursor();	
	}
	
	public AbstractMoveable activeUnit() {
		return unit;
	}
	/**
	 * 
	 * @param placePoint An absolute point representing what map tile the object will be placed on
	 */
	public void placeMoveable(Point3D placePoint) {
		System.out.println(placePoint);
		placeMoveable(RelativePos.toRel(placePoint));
	}
	
	private void placeMoveable(RelativePos relPoint) {
		if (unit.passBarriers || world.relativeFindTile(relPoint).passable()) {
			unit.setPos(relPoint);
		} else {
			System.out.println("Constrained Moveable : " + this + "blocked from placement at + " + relPoint.toAbs() + " by "
					+ world.relativeFindTile(relPoint) + " : " + relPoint);
		}
	}
	
	public void move(Direction d) {
		RelativePos rel = RelativePos.toRel(unit.absPosition.add(d.offset())); // Convert to relative in order to access the
																			// methods required for the next if
																			// statement
		if ((unit.passBarriers || world.relativeFindTile(rel).passable()) && world.getGenerator().compare(rel) == -1) { 
			// if object is constrained by boundaries, test the passability of the tile to move to, and if it is in bounds
			unit.setPos(unit.absPosition.add(d.offset())); // the test is passed, allowing the real position to be changed
			ApplicationMain.refresh();
		}
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