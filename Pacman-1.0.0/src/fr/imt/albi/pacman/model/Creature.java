package fr.imt.albi.pacman.model;

import fr.imt.albi.pacman.main.PacManLauncher;
import fr.imt.albi.pacman.utils.Canvas;
import fr.imt.albi.pacman.utils.Figure;
import fr.imt.albi.pacman.utils.Food;
import fr.imt.albi.pacman.utils.Wall;

abstract class Creature {

	protected GameMap gameMap;

	public abstract void draw();

	public abstract int getX();

	public abstract int getY();

	public abstract int getWidth();

	public void setMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	public void setLocation(int x, int y) {
		int tmpx = x - this.getX();
		int tmpy = y - this.getY();

		this.move(tmpx, tmpy);
	}

	public abstract void move(String toward);

	public abstract void move(int dx, int dy);

	public abstract boolean checkCaseType(Figure f);

	protected abstract void interactWithFood(Figure[][] map, int i, int j);

	protected int[] checkCollision(String direction, int dx, int dy) {
		int[] result = new int[2];
		Figure[][] map = this.gameMap.getMap();

		int[] coordinates = this.getColumnAndRow();
		int xCoords = coordinates[0];
		int yCoords = coordinates[1];

		for (int i = xCoords - 1; i <= xCoords + 1; i++) {
			for (int j = yCoords - 1; j <= yCoords + 1; j++) {
				Figure f = map[j][i];
				if (this.checkCollisionWithFigure(f, dx, dy)) {
					if (f instanceof Wall) {
						if (direction.equals(PacManLauncher.UP)) {
							dy = this.getY() - (f.getY() + f.getHeight());
						} else if (direction.equals(PacManLauncher.DOWN)) {
							dy = this.getY() + this.getWidth() - f.getY();
						} else if (direction.equals(PacManLauncher.LEFT)) {
							dx = this.getX() - (f.getX() + f.getWidth());
						} else if (direction.equals(PacManLauncher.RIGHT)) {
							dx = this.getX() + this.getWidth() - f.getX();
						}
					} else if (f instanceof Food) {
						this.interactWithFood(map, j, i);
					}
				}
			}
		}

		result[0] = dx;
		result[1] = dy;
		return result;
	}

	protected int[] getColumnAndRow() {
		Figure[][] map = this.gameMap.getMap();
		int colonne = this.getX() / this.gameMap.getSizeCase();
		int ligne = this.getY() / this.gameMap.getSizeCase();
		if (colonne <= 0) {
			colonne = 1;
		} else if (colonne >= map.length - 1) {
			colonne = map.length - 2;
		}
		if (ligne <= 0) {
			ligne = 1;
		} else if (ligne >= map.length - 1) {
			ligne = map.length - 2;
		}

		int[] ret = { colonne, ligne };
		return ret;
	}

	public abstract int getSpeed();

	/**
	 * Cette méthode retourne la position à laquelle se retrouve la créature selon
	 * la direction qu'elle a choisie
	 *
	 * @param direction La direction choisie
	 * @return Un tableau {x, y} des nouvelles positions
	 */
	protected int[] navigateInMap(String direction) {
		// Le tableau qui contiendra le résultat
		int[] ret = new int[2];
		// Les coordonnées mises à jour de la créature
		int xMove = 0;
		int yMove = 0;
		// Les coordonnées actuelles de la créature
		int xPosition = this.getX();
		int yPosition = this.getY();

		// La vitesse de la créature
		int speed = this.getSpeed();
		// Sa largeur (on divise par 4 parce que connerie avec les arcs de cercle)
		int width = this.getWidth() / 4;
		// La hauteur de la map
		int heightMap = Canvas.HEIGHT;
		// La largeur
		int widthMap = Canvas.WIDTH;

		// TODO Ici, il faut gérer l'évolution des coordonnées en fonction de la
		// direction choisie :)
		if (direction.equals(PacManLauncher.UP) || direction.equals(PacManLauncher.DOWN)
				|| direction.equals(PacManLauncher.LEFT) || direction.equals(PacManLauncher.RIGHT)) {
		}

		ret[0] = xMove;
		ret[1] = yMove;
		return ret;
	}

	protected boolean checkCollisionWithFigure(Figure f, int dx, int dy) {
		boolean result = false;
		assert f != null : "Figure is null";
		if (f != null) {
			if (this.checkCaseType(f)) {
				int xf = f.getX();
				int yf = f.getY();
				int wf = f.getWidth();
				int hf = f.getHeight();

				int xt = this.getX() + dx;
				int yt = this.getY() + dy;
				int st = this.getWidth();

				boolean posMinX = xt < xf + wf || xt + st < xf + wf;
				boolean posMaxX = xt > xf || xt + st > xf;
				boolean posMinY = yt < yf + hf || yt + st < yf + hf;
				boolean posMaxY = yt > yf || yt + st > yf;

				return posMinX && posMaxX && posMinY && posMaxY;
			}
		}

		return result;
	}
}
