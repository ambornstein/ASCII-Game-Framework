package rustGuardian.main;

import asciiPanel.AsciiPanel;
import javafx.geometry.Point3D;

public class Display {
	private static World world;
	private static AsciiPanel terminal;
	private static EntityContainer beings;
	private static Point3D frameOrigin;
	private static Point3D frameDestination;
	private static Point3D viewMargin;
	private static int visX;
	private static int visY;
	private static int visRad;
	private static int visWidth;
	private static int visHeight;
	private static float[][] visMap;
	private static int[][] resistanceMap;

	public Display(AsciiPanel terminal, World world, EntityContainer beings) {
		this.world = world;
		this.terminal = terminal;
		this.beings = beings;
		viewMargin = new Point3D(world.getGenerator().tilePoint().getX()-1, world.getGenerator().tilePoint().getY()-1, 0);
	}
	
	public static Point3D getFrameOrigin() {
		return frameOrigin;
	}

	public static void playerPerspectiveDisplay() {
		AbstractMoveable focusObject = beings.activeUnit();
		Point3D focusLocation = focusObject.getAbsPosition();
		Point3D focusBounds= new Point3D(focusObject.sightRad, focusObject.sightRad, 0);
		frameOrigin = RelativePos.correctOutOfBounds(focusLocation.subtract(viewMargin));
		frameDestination = new Point3D((viewMargin.getX()*2)+1, (viewMargin.getY()*2)+1, frameOrigin.getZ());
		MapChunk viewSlice = world.copyGrid(focusLocation.subtract(focusBounds), focusLocation.add(focusBounds));
		MapChunk viewChunk = world.copyGrid(frameOrigin, frameDestination);
		if(focusObject.sightRad() != 0) {
			//calculateFOV(viewSlice.opaqueScan()[(int)frameOrigin.getZ()], (int)focusLocation.getX(), (int)focusLocation.getY(), focusObject.sightRad());
		}
		Point3D playerSite = focusLocation.subtract(frameOrigin);
		for (int y = 0; y <= viewChunk.width(); y++) {
			for (int x = 0; x <= viewChunk.length(); x++) {
				//if (x == frameOrigin.getX() && y == frameOrigin.getY()) {
					//for (int j = 0; j < visMap.length; j++) {
						//for (int i = 0; i < visMap[0].length; i++) {
							//if (visMap[j][0] == 1.0f) {
								try {
									terminal.write(viewChunk.unitAt(new Point3D(x,y,0)).symbol(), x, y);
								}
								catch (NullPointerException e) {
									terminal.write(' ', x, y);
								}
							//}
						//}
					//}
				//}
			}
		}
		terminal.write(focusObject.getSymbol(), (int)playerSite.getX(), (int)playerSite.getY());
		terminal.write(String.valueOf(frameOrigin.getZ()), 0, 0);
	}

	/**Calculates the Field Of View for the provided map from the given x, y
	* coordinates. Returns a lightmap for a result where the values represent a
	* percentage of fully lit.
	*
	* A value equal to or below 0 means that cell is not in the
	* field of view, whereas a value equal to or above 1 means that cell is
	* in the field of view.
	*
	* @param resistanceMap the grid of cells to calculate on where 0 is transparent and 1 is opaque
	* @param startx the horizontal component of the starting location
	* @param starty the vertical component of the starting location
	* @param radius the maximum distance to draw the FOV
	* @param radiusStrategy provides a means to calculate the radius as desired
	* @return the computed light grid
	*/
	public static float[][] calculateFOV(int[][] opaque, int startx, int starty, int radius) {
		visX = startx;
		visY = starty;
		visRad = radius;
		visWidth = opaque.length;
		visHeight = opaque[0].length;
		resistanceMap = opaque;
		visMap = new float[visHeight+1][visWidth+1];
		visMap[starty][startx] = 1.0f;
		for (Direction d : Direction.DIAGONAL) {
			castLight(1, 1.0f, 0.0f, 0, (int)d.offset().getX(), (int)d.offset().getY(), 0);
			castLight(1, 1.0f, 0.0f, (int)d.offset().getX(), 0, 0, (int)d.offset().getY()); 
		}
		return visMap;
	}
	
	 /**
	 * 
	 * @TODO Stop the scan from exceeding the boundaries of the map. This causes the
	 * program to screw up. After this the algorithm should function correctly
	 */

	private static void castLight(int row, float start, float end, int xx, int xy, int yx, int yy) {
		float newStart = 0.0f; 
		if (start < end) { return; }
		boolean blocked = false;
		for (int distance = row; distance <= visRad && distance < visWidth + visHeight; distance++) {
			int deltaY =-distance;
			for (int deltaX = -distance; deltaX <= 0; deltaX++) {
				int currentX = visX + (deltaX * xx) + (deltaY * xy);
				int currentY = visY + (deltaX * yx) + (deltaY * yy);
				float leftSlope = (deltaX - 0.5f) / (deltaY + 0.5f);
				float rightSlope = (deltaX + 0.5f) / (deltaY - 0.5f);
				
				if (!(currentX >= 0 && currentY >= 0 && currentX < visWidth && currentY < visHeight || start < rightSlope)) { 
					continue;
				}
				else if (end > leftSlope || currentX < 0|| currentY < 0) {
					break;
				}
				
				if (Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2)) <= visRad) {
					visMap[currentY][currentX] = 1.0f;
				}
				if (blocked) {
					if (resistanceMap[currentY][currentX] >= 1) { newStart = rightSlope; }
					else { 
						blocked = false;
						start = newStart;
					}  
				}
				else {
					if (resistanceMap[currentY][currentX] >= 1 && distance < visRad) { 
						blocked = true;
						castLight(distance + 1, start, leftSlope, xx, xy, yx, yy); 
						newStart = rightSlope; 
					} 
				} 
			}
		}
	}
}
