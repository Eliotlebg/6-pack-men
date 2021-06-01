package fr.imt.albi.pacman.main;

import java.util.ArrayList;

import fr.imt.albi.pacman.model.GameMap;
import fr.imt.albi.pacman.model.Ghost;
import fr.imt.albi.pacman.model.Pacman;
import fr.imt.albi.pacman.model.Score;
import fr.imt.albi.pacman.utils.Canvas;

public class PacManLauncher {

	public static final String UP = "UP";
	public static final String DOWN = "DOWN";
	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";
	private static final int NBR_LVL = 3;
	private GameMap maps;
	private final Pacman pacman;
	private Ghost[] ghost;

	public PacManLauncher() {
		this.maps = new GameMap(1);
		this.fillGhost();
		this.pacman = new Pacman(this.maps.getSizeCase(), this.maps.getPacmanX(), this.maps.getPacmanY());
		this.pacman.setMap(this.maps);
	}

	public static void main(String[] args) {
		Canvas.getCanvas();
		PacManLauncher pml = new PacManLauncher();
		pml.draw();
		pml.playGame();

		int i = 2;
		while (pml.getPacman().getCurrentLife() > 0) {
			pml.upLvl(i);
			pml.draw();
			pml.playGame();
			i++;
			if (i > PacManLauncher.NBR_LVL) {
				i = 1;
			}
		}

		if (Integer.parseInt(Score.getScore()) < pml.getPacman().getCurrentScore()) {
			Score.setScore(pml.getPacman().getCurrentScore() + "");
		}
		System.out.println("~~~END~~~");
	}

	public void upLvl(int lvl) {
		this.maps = new GameMap(lvl);
		this.fillGhost();
		this.pacman.setLocation(this.maps.getPacmanX(), this.maps.getPacmanY());
		this.pacman.setMap(this.maps);
	}

	public void fillGhost() {
		ArrayList<Integer[]> gs = this.maps.getGhosts();
		this.ghost = new Ghost[gs.size()];

		String[] color = { "redG", "blueG", "orangeG", "pinkG" };
		int cpt = 0;
		int cptGhost = 0;
		for (Integer[] t : gs) {
			this.ghost[cpt] = new Ghost(this.maps.getSizeCase(), t[0], t[1], color[cptGhost]);
			this.ghost[cpt].setMap(this.maps);

			cpt++;
			cptGhost++;
			if (cptGhost >= color.length) {
				cptGhost = 0;
			}
		}
	}

	public void draw() {
		this.maps.draw();
		this.pacman.draw();
		for (Ghost g : this.ghost) {
			if (g != null) {
				g.draw();
			}
		}
	}

	public Pacman getPacman() {
		return this.pacman;
	}

	public void playGame() {
		Canvas c = Canvas.getCanvas();
		c.resetMove();
		while (this.maps.getNbGom() > 0 && this.pacman.getCurrentLife() > 0) {
			if (c.isUpPressed()) {
				this.pacman.move(UP);
			} else if (c.isDownPressed()) {
				this.pacman.move(DOWN);
			} else if (c.isLeftPressed()) {
				this.pacman.move(LEFT);
			} else if (c.isRightPressed()) {
				this.pacman.move(RIGHT);
			}

			if (this.pacman.getIsEmpowered()) {
				for (Ghost g : this.ghost) {
					g.setFearState();
				}
				this.pacman.resetIsEmpowered();
			}

			this.collisionGhost();

			for (Ghost g : this.ghost) {
				g.move();
			}
			this.collisionGhost();
			Canvas.getCanvas().redraw(this.pacman.getCurrentScore(), this.pacman.getCurrentLife(), Score.getScore());
		}
	}

	private void collisionGhost() {
		boolean result = false;
		int i = 0;
		while (!result && i < this.ghost.length) {
			if (this.pacman.isPacmanCollidingWithGhost(this.ghost[i]) && this.ghost[i].getFearCounter() == 0) {
				this.pacman.removeLife();
				this.pacman.setLocation(this.maps.getPacmanX(), this.maps.getPacmanY());
				result = true;
			} else if (this.pacman.isPacmanCollidingWithGhost(this.ghost[i]) && this.ghost[i].getFearCounter() > 0) {
				ArrayList<Integer[]> gs = this.maps.getGhosts();
				int cpt = 0;
				for (Integer[] t : gs) {
					if (cpt == i) {
						this.ghost[cpt].setLocation(t[0], t[1]);
						this.ghost[cpt].setNormalState();
						this.pacman.updateScoreGhost();
					}
					cpt++;
				}
			}
			i++;
		}

		if (result) {
			ArrayList<Integer[]> gs = this.maps.getGhosts();
			int cpt = 0;
			for (Integer[] t : gs) {
				this.ghost[cpt].setLocation(t[0], t[1]);
				cpt++;
			}
		}
	}
}
