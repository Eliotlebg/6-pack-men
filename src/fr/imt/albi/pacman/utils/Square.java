package fr.imt.albi.pacman.utils;

import java.awt.Rectangle;

/**
 * A square that can be manipulated and that draws itself on a canvas.
 *
 * @author launay
 * @version 2017.01.01
 * @inv getWidth() == getHeight()
 */

public class Square extends Figure {

	public Square(int size, int x, int y, String color) {
		super(size, size, x, y, color);
	}

	public int getSize() {
		return this.getWidth();
	}

	public void setSize(int size) {
		super.setSize(size, size);
	}

	@Override
	public void setSize(int width, int height) {
		assert width >= 0 && height == width : "Wrong dimensions";
		super.setSize(width, height);
	}

	@Override
	protected void draw() {
		Canvas canvas = Canvas.getCanvas();
		canvas.draw(this, this.getColor(), new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
	}
}
