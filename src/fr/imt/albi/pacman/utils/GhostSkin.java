package fr.imt.albi.pacman.utils;

public class GhostSkin {

	private final Figure[] figures;

	public GhostSkin(int size, int x, int y, String color) {

		int headDiameter = size;
		int bodyHeight = (int) (size / 2.6);
		int legSize = size / 5;
		int eyeSize = (int) (size / 3.5);
		int innerEyeSize = eyeSize / 2;

		this.figures = new Figure[9];

		this.figures[0] = new ArcCircle(size, x, y, color, 0, 180); // headF
		this.figures[1] = new Rect(headDiameter, bodyHeight, x, y + headDiameter / 2, color); // body

		this.figures[2] = new ArcCircle(legSize, x, y + headDiameter / 2 + bodyHeight - legSize / 2, color, 180, 180); // leg
		this.figures[3] = new ArcCircle(legSize, x + 4 * legSize, y + headDiameter / 2 + bodyHeight - legSize / 2,
				color, 180, 180); // leg
		this.figures[4] = new ArcCircle(legSize, x + 2 * legSize, y + headDiameter / 2 + bodyHeight - legSize / 2,
				color, 180, 180); // leg

		this.figures[5] = new Circle(eyeSize, x + eyeSize / 2, y + headDiameter / 2 - eyeSize, "white"); // eye
		this.figures[6] = new Circle(eyeSize, (int) (x + size - 1.5 * eyeSize), y + headDiameter / 2 - eyeSize,
				"white"); // eye
		this.figures[7] = new Circle(innerEyeSize, x + eyeSize / 2 + innerEyeSize / 2,
				y + headDiameter / 2 - eyeSize + innerEyeSize / 2, "black"); // eye
		this.figures[8] = new Circle(innerEyeSize, (int) (x + size - 1.5 * eyeSize + innerEyeSize / 2),
				y + headDiameter / 2 - eyeSize + innerEyeSize / 2, "black"); // eye
	}

	public Figure[] getFigures() {
		return this.figures;
	}

	public int getX() {
		return this.figures[0].getX();
	}

	public int getY() {
		return this.figures[0].getY();
	}

	public int getWidth() {
		return this.figures[0].getWidth();
	}

	public void draw() {
		for (Figure figure : this.figures) {
			figure.draw();
		}
	}
}
