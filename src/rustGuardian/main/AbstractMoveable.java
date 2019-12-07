package rustGuardian.main;

import javafx.geometry.Point3D;

public abstract class AbstractMoveable implements IMoveable {
	protected boolean visible;
	protected char symbol;
	protected boolean passBarriers;
	protected Point3D absPosition;

	@Override
	public char getSymbol() {
		return symbol;
	}

	@Override
	public Point3D getAbsPosition() {
		return absPosition;
	}

	@Override
	public RelativePos getRelPosition() {
		return RelativePos.toRel(absPosition);
	}

	@Override
	public void setPos(Point3D newPos) {
		absPosition = new Point3D(newPos.getX(), newPos.getY(), newPos.getZ());
	}

	@Override
	public boolean getVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean newVis) {
		visible = newVis;
	}

	public void placeMoveable(Point3D placePoint) {
		if (passBarriers || RelativePos.toRel(placePoint).findTile().passable()) {
			this.setPos(placePoint);
		} else {
			System.out.println("Constrained Moveable : " + this + "blocked from placement at + " + placePoint + " by "
					+ RelativePos.toRel(placePoint));
		}
	}

	@Override
	public void move(Direction d) {
		RelativePos rel = RelativePos.toRel(absPosition.add(d.offSet())); // Convert to relative in order to access the
																			// methods required for the next if
																			// statement
		if ((passBarriers || rel.findTile().passable()) && RelativePos.generator().compare(rel) == -1) { // if object is
																											// constrained
																											// by
																											// boundaries,
																											// test the
																											// passability
																											// of
																											// the tile
																											// to move
																											// to, and
																											// if it is
																											// in bounds
			absPosition = absPosition.add(d.offSet()); // the test is passed, allowing the real position to be changed
			ApplicationMain.refresh();
		}
	}

	public AbstractMoveable(Point3D startPoint) {
		placeMoveable(startPoint);
		visible = false;
		symbol = ' ';
		passBarriers = true;
	}

	public AbstractMoveable(Point3D startPoint, boolean visible, char symbol, boolean passBarriers) {
		placeMoveable(startPoint);
		this.visible = visible;
		this.symbol = symbol;
		this.passBarriers = passBarriers;
	}
}
