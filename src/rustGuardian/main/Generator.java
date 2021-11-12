package rustGuardian.main;

public class Generator {
	private RelativePos bounds;
	
	Generator() {
		bounds = new RelativePos(2, 2, 40, 25, 5);
	}
	
	Generator(RelativePos bounds) {
		this.bounds = bounds;
	}
	
	public RelativePos getBounds() {
		return bounds;
	}
}
