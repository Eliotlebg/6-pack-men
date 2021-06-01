package fr.imt.albi.pacman.utils;

public abstract class Figure {
	private int width;
	private int height;
	private int x;
	private int y;
	private String color;

	public Figure(int width, int height, int x, int y, String color) {
		assert width >= 0 && height >= 0 : "Wrong dimensions";
		assert color.equals("white") || color.equals("black") || color.equals("red") || color.equals("blue")
				|| color.equals("yellow") || color.equals("green") : "Wrong color";
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		assert color.equals("white") || color.equals("black") || color.equals("red") || color.equals("blue")
				|| color.equals("yellow") || color.equals("green") : "Wrong color";
		this.color = color;
		this.draw();
	}

	public void move(int dx, int dy) {
		this.erase();
		this.x += dx;
		this.y += dy;
		this.draw();
	}

	public void setSize(int width, int height) {
		assert width >= 0 && height >= 0 : "Wrong dimensions";
		this.erase();
		this.width = width;
		this.height = height;
		this.draw();
	}

	protected abstract void draw();

	protected void erase() {
		Canvas canvas = Canvas.getCanvas();
		canvas.erase(this);
	}
}
