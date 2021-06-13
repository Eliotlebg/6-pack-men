package fr.imt.albi.pacman.model;

import fr.imt.albi.pacman.main.PacManLauncher;
import fr.imt.albi.pacman.utils.ArcCircle;
import fr.imt.albi.pacman.utils.Figure;
import fr.imt.albi.pacman.utils.Food;
import fr.imt.albi.pacman.utils.Wall;

public class Pacman extends Creature {
	
	/* L'angle d'ouverture mini de sa bouche quand il avance */
	public static final int MIN_MOUTH_ANGLE = 10;
	
	/* L'angle d'ouverture max de sa bouche quand il avance */
	public static final int MAX_MOUTH_ANGLE = 40;
	
	/* Sa vitesse sur la grille */
	public static final int SPEED_PACMAN = 20;
	
	/* La couleur de Pacman */
	private static final String PACMAN_COLOR = "yellow";
	
	/* Le nombre initial de vies de Pacman */
	private static final int LIFE_START = 3;
	
	/* Le nombre de points pour qu'il obtienne une vie */
	private static final int LIFE_POINT_THRESHOLD = 10000;

	Sound noise;
	boolean statenoise = true;
	
	
	
	private final ArcCircle pacman;
	private int mouthAngle;
	private boolean isMouthOpen;
	private boolean isEmpowered;
	private String lastPosition;
	private String lastMovement;
	private int currentLife;
	private int currentScore;
	private int nextLifeThreshold;

	//constructeur
	public Pacman(int size, int x, int y) {
		this.pacman = new ArcCircle(size, x, y, PACMAN_COLOR, 0, 360);

		this.lastPosition = PacManLauncher.LEFT;
		this.mouthAngle = MIN_MOUTH_ANGLE;
		this.isMouthOpen = false;
		this.handleMouthOpening(PacManLauncher.LEFT);
		this.currentLife = LIFE_START;
		this.isEmpowered = false;
		this.lastMovement = PacManLauncher.LEFT;
		this.nextLifeThreshold = Pacman.LIFE_POINT_THRESHOLD;
		this.noise = new Sound();
		this.noise.se.setFile("./resources/pacman_chomp.wav");
		
	}
	

	public void removeLife() {
		// TODO Méthode qui gère le retrait d'une vie à Pacman
		this.currentLife -= 1;
		
	}

	public int getCurrentLife() {
		return this.currentLife;
	}


	public void updateScoreFoodPowerUp(Food food) {
		// TODO Là, si Pacman a reçu un power-up, faut incrémenter le score comme il se
		// doit.
		this.currentScore += food.POWER_UP_SCORE; //on ajoute le score définit dans la classe Food
	}
	
	
	public void updateScoreFood(Food food) {
		this.currentScore += food.POWER_UP_SCORE/100; //on gagne beaucoup moins de point pour une food classique
	}
	

	private void checkIfNewLife() {
		// TODO Là, faut vérifier si le Pacman a atteint la limite pour avoir une vie
		// supplémentaire :)
	}

	public void updateScoreGhost(Ghost g) {
		// TODO Là, si Pacman bouffe un fantome, faut incrémenter le score comme il faut
		// aussi.
		this.currentScore += g.GHOST_SCORE;
	}

	public int getCurrentScore() {
		return this.currentScore;
	}

	@Override
	public int getSpeed() {
	return Pacman.SPEED_PACMAN;
	}

	@Override
	public int getX() {
		return this.pacman.getX();
	}

	@Override
	public int getY() {
		return this.pacman.getY();
	}

	@Override
	public int getWidth() {
		return this.pacman.getWidth();
	}

	//on définit la méthode draw() car elle est abstract dans creature
	// or, pacman est une instance de ArcCircle, une classe qui hérite de Figure. 
	// sauf que Figure définit draw() comme une méthode abstract!
	// Finalement, on a bien une méthode draw() définie dans ArcCircle et c'est pas fini ! (cf Canevas...)
	@Override
	public void draw() {
		this.pacman.draw();
	}

	@Override
	public void move(String direction) {
		int xMove = 0;
		int yMove = 0;
		
		//System.out.println(this.isMovePossible(direction));
		
		if (this.isMovePossible(direction)) {
			/*
			 * TODO Si le déplacement est possible, il faut : 
			 * - récupérer les nouvelles coordonnées, 
			 * - voir avec quoi on risque de se percuter avec ces nouvelles
			 * coordonnées et agir en conséquence (i.e. remettre à jour les coords) 
			 * - se déplacer 
			 * - garder une trace du dernier déplacement effectué (y a un attribut de classe pour ça) 
			 * - Animer sa bouche ;)
			 */
			
	        int[] crossMap = this.navigateInMap(direction); //navigateInMap from Creature
	        xMove = crossMap[0];
	        yMove = crossMap[1];
	        
	        crossMap = this.checkCollision(direction, xMove, yMove); // c'est ici qu'on regarde s'il tombe sur une boulle, de la food (ou un ghost? il semblerait que non) )
	        xMove = crossMap[0];
	        yMove = crossMap[1];
	        
	        this.lastMovement = direction;
	        
	        this.animateMouth(xMove, yMove);
	       // System.out.println(xMove+","+yMove);
	        this.move(xMove,yMove);

	        
	        
	        
		} else {
			/*
			 * TODO Si le déplacement n'est possible, il faut pouvoir récupérer les
			 * coordonnées en partant du principe que sa direction sera égale à la dernière
			 * direction qui avait marché. Quasiment la même chose, juste que ça sera pas
			 * direction qui sera utilisée, mais autre chose :) Faut toujours animer sa
			 * bouche ceci dit !
			 */
			
			
	        int[] crossMap = this.navigateInMap(this.lastMovement); //il va se déplacer selon la dernière position "acceptable" i.e. "physiquement" possible
	        xMove = crossMap[0];
	        yMove = crossMap[1];
	        
	        crossMap = this.checkCollision(this.lastMovement, xMove, yMove); // c'est ici qu'on regarde s'il tombe sur une boulle, de la food (ou un ghost? il semblerait que non) )
	        xMove = crossMap[0];
	        yMove = crossMap[1];
	        
	        this.animateMouth(xMove, yMove);
	        //System.out.println(xMove+","+yMove);
	        this.move(xMove,yMove);
			
	        
		}
	}
	
	

	/**
	 * Cette méthode permet de vérifier si le déplacement demandé est effectivement
	 * faisable.
	 *
	 * @param direction La direction choisie
	 * @return true si possible, false sinon
	 */
	private boolean isMovePossible(String direction) {
        boolean canMove = false;
        Figure[][] map = this.gameMap.getMap();

        if (this.getX() % this.gameMap.getSizeCase() == 0 && this.getY() % this.gameMap.getSizeCase() == 0) {
            int[] position = this.getColumnAndRow();
            int xPosition = position[0];
            int yPosition = position[1];

            // on regarde ce qu'il se passe autour de la position (xPosition,yPosition) de l'instance de Pacman
            Figure fUp = map[yPosition - 1][xPosition];
            Figure fDown = map[yPosition + 1][xPosition];
            Figure fleft = map[yPosition][xPosition - 1];
            Figure fRight = map[yPosition][xPosition + 1];

            
            
            switch (direction) {
            
            	//si direction == PacManLauncher.UP i.e. si direction = "UP"
                case PacManLauncher.UP: 
                    if (!(fUp instanceof Wall)) {
                        canMove = true;
                    }
                    break;
                case PacManLauncher.DOWN:
                    if (!(fDown instanceof Wall)) {
                        canMove = true;
                    }
                    break;
                case PacManLauncher.LEFT:
                    if (!(fleft instanceof Wall)) {
                        canMove = true;
                    }
                    break;
                case PacManLauncher.RIGHT:
                    if (!(fRight instanceof Wall)) {
                        canMove = true;
                    }
                    break;
            }
        }

        return canMove;
    }
	
	@Override
	public void move(int xMove, int yMove) {
		this.pacman.move(xMove, yMove);
	}

	/**
	 * Anime la bouche du petit aussi, mais avec les calculs qui vont bien
	 *
	 * @param direction La direction à laquelle pointe Pacman
	 */
	private void handleMouthOpening(String direction) {
		int startAngle = 0;
		int extentAngle = 0;

		if (direction.equals(PacManLauncher.UP)) {
			startAngle = 90 - this.mouthAngle;
			extentAngle = -360 + 2 * this.mouthAngle;
		} else if (direction.equals(PacManLauncher.LEFT)) {
			startAngle = 180 - this.mouthAngle;
			extentAngle = -360 + 2 * this.mouthAngle;
		} else if (direction.equals(PacManLauncher.DOWN)) {
			startAngle = 270 - this.mouthAngle;
			extentAngle = -360 + 2 * this.mouthAngle;
		} else if (direction.equals(PacManLauncher.RIGHT)) {
			startAngle = -this.mouthAngle;
			extentAngle = -360 + 2 * this.mouthAngle;
		}

		this.pacman.setAngleStart(startAngle);
		this.pacman.setAngleExtent(extentAngle);
		
	}

	@Override
	protected void interactWithFood(Figure[][] map, int i, int j) {
		Figure f = map[i][j];
		if (f instanceof Food) {
			Food food = (Food) f;
			if (food.getFood() != null) { 
				
				
				//on veut jouer l'effet sonore seulement une fois sur deux (sinon l'extrait audio n'est pas joué jusqu'au bout...)
				if (statenoise) {
				
				this.noise.se.play();
				//this.noise.startSong("./resources/pacman_chomp.wav");
				//songstart.startSong("./resources/pacman_chomp.wav");
				}
				this.statenoise = !this.statenoise;
				
				
				/*
				 * TODO Ici, il faut: 
				 * - Changer le food en null (y a un setFood...) 
				 * - Redessiner
				 * le food (.draw()) 
				 * - Et après, remettre à jour la map en updatant la bouffe
				 * qu'il y avait dedans 
				 * - Mettre à jour le score 
				 * - Sachant qu'un food peut être
				 * un powerup, y a un truc à gérer :)
				 */
				if (food.isPowerUp()) {
					this.isEmpowered = true;
					this.updateScoreFoodPowerUp(food);
				}else {
					this.updateScoreFood(food);
				}
				this.checkIfNewLife();
				
				//fait disparaître food (graphiquement)
				food.setFood(null); 
				food.draw();
				
				// efface la food ("numériquement")
				this.gameMap.pickFood();
				
			}
		}
	}

	public boolean getIsEmpowered() {
		return this.isEmpowered;
	}

	public void resetIsEmpowered() {
		this.isEmpowered = false;
	}

	@Override
	public boolean checkCaseType(Figure f) {
		return f instanceof Wall || f instanceof Food;
	}

	/**
	 * Anime la bouche du petit.
	 */
	public void animateMouth(int dx, int dy) {
		
		//si le pacman est à l'arrêt, sa bouche ne bouge pas
		if (dx==0 && dy==0) {
			this.pacman.setAngleStart(0);
			this.pacman.setAngleExtent(360);
			//on lui ferme la bouche juste avant qu'il s'arrête
			return;
		}
		if (this.isMouthOpen) {
			this.mouthAngle = MAX_MOUTH_ANGLE;
		} else {
			this.mouthAngle = MIN_MOUTH_ANGLE;
		}
		this.handleMouthOpening(this.lastMovement);
		this.isMouthOpen = !this.isMouthOpen;
	}

	/**
	 * Méthode qui permet de dire s'il se pète la gueule avec un fantome.
	 *
	 * @param f Le fantome en question
	 * @return true ou false
	 */
	public boolean isPacmanCollidingWithGhost(Ghost f) {
		int xGhost = f.getX();
		int yGhost = f.getY();
		int sizeGhost = f.getWidth();

		int xPacman = this.getX();
		int yPacman = this.getY();
		int sizePacman = this.getWidth();

		boolean posMinX = xPacman < xGhost + sizeGhost || xPacman + sizePacman < xGhost + sizeGhost;
		boolean posMaxX = xPacman > xGhost || xPacman + sizePacman > xGhost;
		boolean posMinY = yPacman < yGhost + sizeGhost || yPacman + sizePacman < yGhost + sizeGhost;
		boolean posMaxY = yPacman > yGhost || yPacman + sizePacman > yGhost;

		return posMinX && posMaxX && posMinY && posMaxY;
	}
}
