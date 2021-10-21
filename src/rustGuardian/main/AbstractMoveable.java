package rustGuardian.main;

import javafx.geometry.Point3D;

public abstract class AbstractMoveable implements IMoveable {
	protected boolean visible;
	protected char symbol;
	protected boolean passBarriers;
	protected Point3D absPosition;
	
	public AbstractMoveable(RelativePos relPoint) {
		placeMoveable(relPoint);
		visible = false;
		symbol = ' ';
		passBarriers = true;
	}
	
	public AbstractMoveable(Point3D startPoint) {
		placeMoveable(startPoint);
		visible = false;
		symbol = ' ';
		passBarriers = true;
	}
	
	public AbstractMoveable(RelativePos relPoint, boolean visible, char symbol, boolean passBarriers) {
		placeMoveable(relPoint);
		this.visible = visible;
		this.symbol = symbol;
		this.passBarriers = passBarriers;
	}

	public AbstractMoveable(Point3D startPoint, boolean visible, char symbol, boolean passBarriers) {
		placeMoveable(startPoint);
		this.visible = visible;
		this.symbol = symbol;
		this.passBarriers = passBarriers;
	}
	
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
	
	public void setPos(RelativePos newPos) {
		absPosition = new Point3D(newPos.toAbs().getX(), newPos.toAbs().getY(), newPos.toAbs().getZ());
	}
	

	@Override
	public boolean getVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean newVis) {
		visible = newVis;
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
		if (passBarriers || RelativePos.generalWorld().relativeFindTile(relPoint).passable()) {
			this.setPos(relPoint);
		} else {
			System.out.println("Constrained Moveable : " + this + "blocked from placement at + " + relPoint.toAbs() + " by "
					+ RelativePos.generalWorld().relativeFindTile(relPoint) + " : " + relPoint);
		}
	}

	@Override
	public void move(Direction d) {
		RelativePos rel = RelativePos.toRel(absPosition.add(d.offSet())); // Convert to relative in order to access the
																			// methods required for the next if
																			// statement
		if ((passBarriers || RelativePos.generalWorld().relativeFindTile(rel).passable()) && RelativePos.generator().compare(rel) == -1) { 
			// if object is constrained by boundaries, test the passability of the tile to move to, and if it is in bounds
			absPosition = absPosition.add(d.offSet()); // the test is passed, allowing the real position to be changed
			ApplicationMain.refresh();
		}
	}

	
}
