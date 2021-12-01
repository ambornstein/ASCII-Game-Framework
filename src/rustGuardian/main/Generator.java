package rustGuardian.main;

public class Generator extends RelativePos {
	Generator() {
		super(2, 2, 40, 25, 5);
	}
	
	public Generator(RelativePos bounds) {
		super(bounds.chunkPoint(), bounds.tilePoint());
	}
}
