package rustGuardian.main;

import java.awt.Point;
import java.util.ArrayList;
import javafx.geometry.Point3D;

public class World extends AbstractGrid2D<MapChunk> {
	private static final long serialVersionUID = 1060623638149583738L;
	private static EntityContainer beings;
	
	public World(Generator gen) {
		super(gen.getBounds().chunkPoint());
		RelativePos.setGenerator(gen);
		create();
	}
	
	@Override
	public void fill() {
		for (int y = 0; y <= super.width(); y++) {
			add(new ArrayList<MapChunk>());
			for (int x = 0; x <= super.length(); x++) {
				get(y).add(new MapChunk(RelativePos.generator().getBounds().tilePoint())); // Each placement needs to be a new
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
		beings = new EntityContainer();
	}

	public MapChunk subsection(Point3D startCorner, Point3D endCorner) { //copies all of the tiles from the startCorner to the endCorner
		startCorner = RelativePos.correctOutOfBounds(startCorner); //Correct positions that are out of the bounds of the world
		RelativePos startCornerRel = RelativePos.toRel(startCorner); //Make Relative copies of both for future compare()
		endCorner = RelativePos.correctOutOfBounds(endCorner);
		RelativePos endCornerRel = RelativePos.toRel(endCorner);
		Point3D deltaPos = endCorner.subtract(startCorner);
		MapChunk returnChunk = new MapChunk(deltaPos);
		if(endCornerRel.compare(startCornerRel) == -1) {
			for(int z = 0; z <= deltaPos.getZ(); z++) {
				for(int y = 0; y <= deltaPos.getY(); y++) {
					for(int x = 0; x <= deltaPos.getX(); x++) {
						returnChunk.place(new Point3D(x,y,z), absoluteFindTile(new Point3D(x+startCorner.getX(),y+startCorner.getY(),z+startCorner.getZ())));
						}
					}
				}
			return returnChunk;
		} 
		return null;
		}
		 

	private void flagBorders() {
		PointSet borderSet;
		Point3D dims = RelativePos.generator().getBounds().toAbs();
		for (Direction dir : Direction.AXIS) {// Loop through NORTH, SOUTH, EAST, WEST, UP, DOWN
			Point3D originCorner = new Point3D(dims.getX() * dir.offSet().getX(), dims.getY() * dir.offSet().getY(),
					dims.getZ() * dir.offSet().getZ());
			Point3D destinationCorner = new Point3D(dims.getX() + (dims.getX() * dir.offSet().getX()),
					dims.getY() + (dims.getY() * dir.offSet().getY()),
					dims.getZ() + (dims.getZ() * dir.offSet().getZ()));
			borderSet = new PointSet(originCorner, destinationCorner);
			//borderSet.forEach(point -> System.out.println(point));
			borderSet.forEach(point -> {
				absolutePlace(point, Tile.WALL);
			});
		}
	}
}
