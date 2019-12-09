package rustGuardian.main;

import java.awt.Point;
import java.util.ArrayList;
import javafx.geometry.Point3D;

public class World extends AbstractGrid2D<MapChunk> {
	private static final long serialVersionUID = 1060623638149583738L;

	@Override
	public void fill() {
		for (int y = 0; y < super.width(); y++) {
			add(new ArrayList<MapChunk>());
			for (int x = 0; x < super.length(); x++) {
				get(y).add(new MapChunk(RelativePos.generator().tilePoint())); // Each placement needs to be a new
																				// MapChunk
			}
		}
	}

	private void create() { // Wrapper function that calls all functions that generate the world
		// fill() must be called here and not in Abst
		fill();
		flagBorders();
	}

	public World(RelativePos gen) {
		super(gen.chunkPoint());
		create();
	}


	public MapChunk subsection(Point3D startCorner, Point3D endCorner) { //copies all of the tiles from the startCorner to the endCorner
		startCorner = RelativePos.correctOutOfBounds(startCorner); //Correct positions that are out of the bounds of the world
		RelativePos startCornerRel = RelativePos.toRel(startCorner); //Make Relative copies of both for future compare()
		endCorner = RelativePos.correctOutOfBounds(endCorner);
		RelativePos endCornerRel = RelativePos.toRel(endCorner);
		Point3D deltaPos = new Point3D(endCorner.getX()-startCorner.getX(), endCorner.getY()-startCorner.getY(), endCorner.getZ()-startCorner.getZ());
		MapChunk
		returnChunk = new MapChunk(deltaPos.getX(), deltaPos.getY(),deltaPos.getZ());
		if(endCornerRel.compare(startCornerRel) == -1) {
			for(int z = (int) startCorner.getZ(); z < endCorner.getZ(); z++) {
				for(int y = (int)startCorner.getY(); y < endCorner.getY(); y++) {
					for(int x = (int)startCorner.getX(); x < endCorner.getX(); x++) {
						RelativePos findTile = RelativePos.toRel(new Point3D(x, y, z));
						returnChunk.place(new Point3D(x-startCorner.getX(),y - startCorner.getY(),z - startCorner.getZ()),findTile.findTile());
						}
					}
				}
			return returnChunk;
		} 
		return null;
		}
		 

	private void flagBorders() {
		PointSet borderSet;
		Point3D dims = RelativePos.generator().toAbs();
		for (Direction dir : Direction.AXIS) {// Loop through NORTH, SOUTH, EAST, WEST, UP, DOWN
			Point3D originCorner = new Point3D(dims.getX() * dir.offSet().getX(), dims.getY() * dir.offSet().getY(),
					dims.getZ() * dir.offSet().getZ());
			Point3D destinationCorner = new Point3D(dims.getX() + (dims.getX() * dir.offSet().getX()),
					dims.getY() + (dims.getY() * dir.offSet().getY()),
					dims.getZ() + (dims.getZ() * dir.offSet().getZ()));
			borderSet = new PointSet(originCorner, destinationCorner);
			borderSet.forEach(point -> System.out.println(point));
			borderSet.forEach(point -> {
				RelativePos pointRel = RelativePos.toRel(point);
				unitAt(new Point(pointRel.chunkPoint().x - 1, pointRel.chunkPoint().y - 1))
						.place(new Point3D(pointRel.tilePoint().getX() - 1, pointRel.tilePoint().getY() - 1,
								pointRel.tilePoint().getZ() - 1), Tile.WALL);
			});
		}
	}
}
