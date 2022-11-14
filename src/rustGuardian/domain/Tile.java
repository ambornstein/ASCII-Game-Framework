package rustGuardian.domain;

import java.awt.Color;

public enum Tile {
	WALL(false, false, '#'), FLOOR(true, true, '.'), NULL(false, false, ' '),
	OPEN_SPACE(true, true, '–', Color.BLUE, Color.WHITE);

	/** Determines Player's ability to make a valid move onto this tile */
	private boolean passable;

	public boolean passable() {
		return passable;
	}

	/** Determine's light's ability to pass through the tile */
	private boolean transparent;

	public boolean transparent() {
		return transparent;
	}

	/** Determines the char used to display this tile on ASCII grid */
	private char symbol;

	public char symbol() {
		return symbol;
	}

	private Color foregroundColor;

	public Color foregroundColor() {
		return foregroundColor;
	}

	private Color backgroundColor;

	public Color backgroundColor() {
		return backgroundColor;
	}

	Tile(boolean passable, boolean transparent, char symbol) {
		this.passable = passable;
		this.transparent = transparent;
		this.symbol = symbol;
		this.foregroundColor = Color.WHITE;
		this.backgroundColor = Color.BLACK;
	}

	Tile(boolean passable, boolean transparent, char symbol, Color foregroundColor, Color backgroundColor) {
		this.passable = passable;
		this.transparent = transparent;
		this.symbol = symbol;
		this.foregroundColor = Color.WHITE;
		this.backgroundColor = Color.BLACK;
	}
}