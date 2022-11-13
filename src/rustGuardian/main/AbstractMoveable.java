package rustGuardian.main;

import javafx.geometry.Point3D;
import rustGuardian.domain.RelativePos;

public abstract class AbstractMoveable implements IMoveable {
	protected boolean visible;
	protected char symbol;
	private boolean passBarriers;
	public Point3D absPosition;
	protected int sightRad;

	public AbstractMoveable(RelativePos relPoint) {
		setPos(relPoint);
		visible = false;
		symbol = ' ';
		setPassBarriers(true);
	}

	public AbstractMoveable(Point3D startPoint) {
		setPos(startPoint);
		visible = false;
		symbol = ' ';
		setPassBarriers(true);
	}

	public AbstractMoveable(RelativePos relPoint, boolean visible, char symbol, boolean passBarriers, int sightRad) {
		setPos(relPoint);
		this.visible = visible;
		this.symbol = symbol;
		this.setPassBarriers(passBarriers);
		this.sightRad = sightRad;
	}

	public AbstractMoveable(Point3D startPoint, boolean visible, char symbol, boolean passBarriers, int sightRad) {
		setPos(startPoint);
		this.visible = visible;
		this.symbol = symbol;
		this.setPassBarriers(passBarriers);
		this.sightRad = sightRad;
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

	@Override
	public int sightRad() {
		return sightRad;
	}

	public boolean passesBarriers() {
		return passBarriers;
	}

	public void setPassBarriers(boolean passBarriers) {
		this.passBarriers = passBarriers;
	}
}
