package fr.imt.albi.pacman.utils;

import java.awt.Rectangle;

public class Rect extends Figure {

	public Rect(int width, int height, int x, int y, String color) {
		super(width, height, x, y, color);
	}

	@Override
	protected void draw() {
		Canvas canvas = Canvas.getCanvas();
		canvas.draw(this, this.getColor(), new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
	}
}
