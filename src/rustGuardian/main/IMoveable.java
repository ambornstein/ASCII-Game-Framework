package rustGuardian.main;

import javafx.geometry.Point3D;

public interface IMoveable {

	public void setVisible(boolean vis);

	public boolean getVisible();

	public char getSymbol();

	public void setPos(Point3D newPos);

	public RelativePos getRelPosition();

	public Point3D getAbsPosition();
}
