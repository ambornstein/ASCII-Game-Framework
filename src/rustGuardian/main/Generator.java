package rustGuardian.main;

/**
 * Subclass of RelativePos that contains parameters to be used in the generation of a world
 */
public class Generator extends RelativePos {
	Generator() {
		super(2, 2, 40, 25, 5);
	}
	
	public Generator(RelativePos bounds) {
		super(bounds.chunkPoint(), bounds.tilePoint());
	}
}
