package fr.imt.albi.pacman.utils;

import java.awt.geom.Arc2D;

public class ArcCircle extends Figure {
	private Double start;
	private Double extent;

	public ArcCircle(int size, int x, int y, String color, double start, double extent) {
		super(size, size, x, y, color);
		this.start = start;
		this.extent = extent;
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
	public void draw() {
		Canvas canvas = Canvas.getCanvas();
		canvas.draw(this, this.getColor(), new Arc2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight(),
				this.start, this.extent, Arc2D.PIE));
	}

	public void setAngleStart(double angSt) {
		this.start = angSt;
	}

	public void setAngleExtent(double angExt) {
		this.extent = angExt;
	}
}
