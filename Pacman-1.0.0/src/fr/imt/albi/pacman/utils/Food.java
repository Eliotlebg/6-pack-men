package fr.imt.albi.pacman.utils;

public class Food extends Figure {

	public static final int POWER_UP_SCORE = 10;
	private static final String COLOR_WALK = "black";
	private static final String COLOR_GOM = "white";
	private final Figure[] figures;
	private boolean isPowerUp;

	public Food(int size, int x, int y) {
		super(size, size, x, y, "black");
		this.figures = new Figure[2];
		this.figures[0] = new Square(size, x, y, COLOR_WALK);
	}

	public Food(int size, int x, int y, boolean isPowerUp) {
		this(size, x, y);

		this.isPowerUp = isPowerUp;
		int sg;
		if (this.isPowerUp) {
			sg = size / 2;
		} else {
			sg = size / 5;
		}
		int xg = x + size / 2 - sg / 2;
		int yg = y + size / 2 - sg / 2;
		this.figures[1] = new Circle(sg, xg, yg, COLOR_GOM);
	}

	public Figure getFood() {
		return this.figures[1];
	}

	public void setFood(Circle c) {
		this.figures[1] = c;
	}

	public boolean isPowerUp() {
		return this.isPowerUp;
	}

	@Override
	public void draw() {
		for (Figure f : this.figures) {
			if (f != null) {
				f.draw();
			}
		}
	}

}
