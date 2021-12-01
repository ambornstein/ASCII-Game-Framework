package rustGuardian.main;

import javafx.geometry.Point3D;

public abstract class AbstractMoveable implements IMoveable {
	protected boolean visible;
	protected char symbol;
	protected boolean passBarriers;
	protected Point3D absPosition;
	
	public AbstractMoveable(RelativePos relPoint) {
		setPos(relPoint);
		visible = false;
		symbol = ' ';
		passBarriers = true;
	}
	
	public AbstractMoveable(Point3D startPoint) {
		setPos(startPoint);
		visible = false;
		symbol = ' ';
		passBarriers = true;
	}
	
	public AbstractMoveable(RelativePos relPoint, boolean visible, char symbol, boolean passBarriers) {
		setPos(relPoint);
		this.visible = visible;
		this.symbol = symbol;
		this.passBarriers = passBarriers;
	}

	public AbstractMoveable(Point3D startPoint, boolean visible, char symbol, boolean passBarriers) {
		setPos(startPoint);
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
	
}
