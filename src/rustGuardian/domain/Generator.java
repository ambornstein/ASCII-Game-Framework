package rustGuardian.domain;

/**
 * Singleton Subclass of RelativePos that stores parameters of world generation.
 */
public class Generator extends RelativePos {
	private static Generator instance;

	private Generator() {
		super(2, 2, 40, 25, 5);
	};
	
	public static Generator getInstance() {
		if (instance == null) {
			instance = new Generator();
		}
		return instance;
	}
	
	public Generator(RelativePos bounds) {
		super(bounds);
	}

	public Generator(int chunkX, int chunkY, int tileX, int tileY, int tileZ) {
		super(chunkX, chunkY, tileX, tileY, tileZ);
	}
}
