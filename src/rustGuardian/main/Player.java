package rustGuardian.main;

import javafx.geometry.Point3D;
import rustGuardian.domain.RelativePos;

/**
 * Player class which currently represents the character that can be controlled.
 * <p>
 * Can only move now. Stores position in a {@link java.awt.Point}
 */
public class Player extends AbstractMoveable {

	public Player(Point3D initPos) {
		super(initPos, true, '@', false, 7);
	}

	public Player(RelativePos initPos) {
		super(initPos, true, '@', false, 7);
	}
}
