package rustGuardian.domain;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.geometry.Point3D;
import rustGuardian.main.AbstractMoveable;
import rustGuardian.main.ApplicationMain;
import rustGuardian.main.Cursor;
import rustGuardian.main.Player;

public class EntityContainer {
	private HashSet<AbstractMoveable> entityDir; // All entities go here; Define and subdivide 'entity' to be more
													// specific
	private Cursor activeCursor; // The active entity that will be controllable
	private Player activePlayer;
	private World world;
	private AbstractMoveable unit;

	public EntityContainer(World world) {
		this.world = world;
		entityDir = new HashSet<>();
		activePlayer = new Player(new RelativePos(1, 1, 3, 3, 3));
		activatePlayer();
		activeCursor = new Cursor();
		addEntity(activePlayer);
		addEntity(activeCursor);
	}

	public void addEntity(AbstractMoveable entity) {
		entityDir.add(entity);
	}
	
	public Player activatePlayer() {
		unit = activePlayer;
		return activePlayer;
	}
	
	public Cursor activateCursor() {
		unit = activeCursor;
		placeMoveable(activePlayer.getRelPosition());
		return activeCursor;
	}

	public AbstractMoveable activeUnit() {
		return unit;
	}

	/**
	 *
	 * @param placePoint An absolute point representing what map tile the object
	 *                   will be placed on
	 */
	public void placeMoveable(Point3D placePoint) {
		System.out.println(placePoint);
		placeMoveable(RelativePos.toRel(placePoint));
	}

	private void placeMoveable(RelativePos relPoint) {
		if (unit.passesBarriers() || world.relativeFindTile(relPoint).passable()) {
			unit.setPos(relPoint);
		} else {
			System.out.println("Constrained Moveable : " + this + "blocked from placement at + " + relPoint.toAbs()
					+ " by " + world.relativeFindTile(relPoint) + " : " + relPoint);
		}
	}

	public void move(Direction d) {
		RelativePos rel = RelativePos.toRel(unit.absPosition.add(d.offset())); // Convert to relative in order to access
																				// the
																				// methods required for the next if
																				// statement
		if ((unit.passesBarriers() || world.relativeFindTile(rel).passable())
				&& world.getGenerator().compare(rel) == -1) {
			// if object is constrained by boundaries, test the passability of the tile to
			// move to, and if it is in bounds
			unit.setPos(unit.absPosition.add(d.offset())); // the test is passed, allowing the real position to be
															// changed
			ApplicationMain.refresh();
		}
	}

	public ArrayList<AbstractMoveable> getAllVisibleEntity() {
		ArrayList<AbstractMoveable> visibleList = new ArrayList<>();
		for (AbstractMoveable m : entityDir) {
			if (m.getVisible()) {
				visibleList.add(m);
			}
		}
		return visibleList;
	}
}