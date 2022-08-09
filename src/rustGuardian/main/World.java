package rustGuardian.main;

import java.awt.Point;
import java.util.ArrayList;
import javafx.geometry.Point3D;

/**A region composed of many chunks
 * 
 * @author ambor
 *
 */
public class World extends AbstractGrid2D<MapChunk> {
	private static final long serialVersionUID = 1060623638149583738L;
	private Generator gen;
	
	public World(Generator gen) {
		super(gen.chunkPoint());
		this.gen = gen;
		RelativePos.setGenerator(gen);
		create();
	}
	
	public Generator getGenerator() {
		return gen;
	}
	
	@Override
	public void fill() {
		for (int y = 0; y <= super.width(); y++) {
			add(new ArrayList<MapChunk>());
			for (int x = 0; x <= super.length(); x++) {
				get(y).add(new MapChunk(gen.tilePoint())); // Each placement needs to be a new
																				// MapChunk
			}
		}
	}
	
	public void absolutePlace(Point3D loc, Tile unit) {
		relativePlace(RelativePos.toRel(loc), unit);
	}
	
	public void relativePlace(RelativePos loc, Tile unit) {
		Point chunkLoc = Util.decrement(loc.chunkPoint());
		try {
			unitAt(chunkLoc).place(Util.decrement(loc.tilePoint()), unit);
		}
		catch (IndexOutOfBoundsException i) {
			absolutePlace(RelativePos.correctOutOfBounds(loc.toAbs()), unit);
		}
	}
	
	public Tile absoluteFindTile(Point3D loc) {
		return relativeFindTile(RelativePos.toRel(loc));
	}
	
	public Tile relativeFindTile(RelativePos loc) {
		Point chunkLoc = Util.decrement(loc.chunkPoint());
		Point3D tileLoc = Util.decrement(loc.tilePoint());
		try {
			return unitAt(chunkLoc).unitAt(tileLoc);
		}
		catch (IndexOutOfBoundsException i) {
			System.out.println("Location out of bounds:" + loc);
			return null;
		}
	}

	private void create() { // Wrapper function that calls all functions that generate the world
		// fill() must be called here and not in Abst
		fill();
		flagBorders();
	}		 

	private void flagBorders() {
		PointSet borderSet;
		Point3D dims = gen.toAbs();
		for (Direction dir : Direction.AXIS) {// Loop through NORTH, SOUTH, EAST, WEST, UP, DOWN
			Point3D originCorner = new Point3D(dims.getX() * dir.offset().getX(), dims.getY() * dir.offset().getY(),
					dims.getZ() * dir.offset().getZ());
			Point3D destinationCorner = new Point3D(dims.getX() + (dims.getX() * dir.offset().getX()),
					dims.getY() + (dims.getY() * dir.offset().getY()),
					dims.getZ() + (dims.getZ() * dir.offset().getZ()));
			borderSet = new PointSet(originCorner, destinationCorner);
			//borderSet.forEach(point -> System.out.println(point));
			borderSet.forEach(point -> {
				absolutePlace(point, Tile.WALL);
			});
		}
	}
	
	public MapChunk copyGrid(Point3D startPoint, Point3D endPoint) {
		PointSet set = new PointSet(startPoint, endPoint);
		Point3D deltaPoint = set.range();
		if (this.isEmpty()) {
			return new MapChunk(0, 0, 0);
		}
		MapChunk returnChunk = new MapChunk(deltaPoint);
		set.forEach((Point3D p) -> returnChunk.place(p.subtract(set.startCorner()), absoluteFindTile(p)));
		return returnChunk;
	}
	
	public MapChunk copyGrid(PointSet set) {
		Point3D deltaPoint = set.range();
		if (this.isEmpty()) {
			return new MapChunk(0, 0, 0);
		}
		MapChunk returnChunk = new MapChunk(deltaPoint);
		set.forEach((Point3D p) -> returnChunk.place(p.subtract(set.startCorner()), absoluteFindTile(p)));
		return returnChunk;
	}
}
