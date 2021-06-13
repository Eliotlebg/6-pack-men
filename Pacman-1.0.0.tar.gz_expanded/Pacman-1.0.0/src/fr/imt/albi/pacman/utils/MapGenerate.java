package fr.imt.albi.pacman.utils;

public class MapGenerate {

	private final Figure[][] gameMap;

	public MapGenerate(int length) {
		this.gameMap = new Figure[length][length];
	}

	public void setFigure(int i, int j, Figure f) {
		this.gameMap[i][j] = f;
	}

	public Figure[][] getGameMap() {
		return this.gameMap;
	}

	public void draw() {
		for (Figure[] fl : this.gameMap) {
			for (Figure f : fl) {
				if (f != null) {
					f.draw();
				}
			}
		}
	}
}
