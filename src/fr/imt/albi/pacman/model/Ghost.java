package fr.imt.albi.pacman.model;

import fr.imt.albi.pacman.main.PacManLauncher;
import fr.imt.albi.pacman.utils.Figure;
import fr.imt.albi.pacman.utils.GhostSkin;
import fr.imt.albi.pacman.utils.Wall;

import java.util.ArrayList;

public class Ghost extends Creature {

    public static final int SPEED_GHOST = 10;
    public static final int GHOST_SCORE = 100;
    private final GhostSkin ghostSkin;
    private final String ghostColor;
    private String previousMove;
    private int counterUTurn;
    private int counterFear;

    public Ghost(int size, int x, int y, String color) {
        this.previousMove = PacManLauncher.UP;
        this.initUTurnCounter();

        this.counterFear = 0;
        this.ghostColor = color;

        this.ghostSkin = new GhostSkin(size, x, y, color);
    }

    public void move() {
        if (this.counterFear == 0) {
            this.setNormalState();
        }
        if (this.counterFear % 2 == 1 || this.counterFear == 0) {
            if (this.counterFear > 0) {
                this.counterFear--;
            }
            this.counterUTurn--;
            if (this.counterUTurn == 0) {
                switch (this.previousMove) {
                    case PacManLauncher.UP:
                        this.move(PacManLauncher.DOWN);
                        break;
                    case PacManLauncher.DOWN:
                        this.move(PacManLauncher.UP);
                        break;
                    case PacManLauncher.LEFT:
                        this.move(PacManLauncher.RIGHT);
                        break;
                    case PacManLauncher.RIGHT:
                        this.move(PacManLauncher.LEFT);
                        break;
                }
                this.initUTurnCounter();
            } else {
                checkCrossing(this.previousMove);
            }
        } else {
            this.counterFear--;
        }
    }

    public void initUTurnCounter() {
        this.counterUTurn = (int) (Math.random() * 30) + 20;
    }

    public void move(String direction) {
        this.previousMove = direction;
        int xMove = 0;
        int yMove = 0;

        int[] crossMap = this.navigateInMap(direction);
        xMove = crossMap[0];
        yMove = crossMap[1];

        crossMap = this.checkCollision(direction, xMove, yMove);
        xMove = crossMap[0];
        yMove = crossMap[1];

        this.move(xMove, yMove);
    }

    public void move(int dx, int dy) {
        for (Figure figure : this.getSkin()) {
            figure.move(dx, dy);
        }
    }

    private Figure[] getSkin() {
        return this.ghostSkin.getFigures();
    }

    public void setFearState() {
        this.counterFear = 60;
        Figure[] figures = this.getSkin();
        for (int i = 0; i < 5; i++) {
            figures[i].setColor("blue");
        }
    }

    public void setNormalState() {
        this.counterFear = 0;
        Figure[] figures = this.getSkin();
        for (int i = 0; i < 5; i++) {
            figures[i].setColor(this.ghostColor);
        }
    }

    public int getX() {
        return this.ghostSkin.getX();
    }

    public int getY() {
        return this.ghostSkin.getY();
    }

    public int getWidth() {
        return this.ghostSkin.getWidth();
    }

    public int getSpeed() {
        return Ghost.SPEED_GHOST;
    }

    public int getFearCounter() {
        return this.counterFear;
    }

    public void checkCrossing(String toward) {
        boolean haveMoved = false;
        Figure[][] map = this.gameMap.getMap();

        if (this.getX() % this.gameMap.getSizeCase() == 0 && this.getY() % this.gameMap.getSizeCase() == 0) {
            int[] position = this.getColumnAndRow();
            int xPos = position[0];
            int yPos = position[1];

            Figure fUp = map[yPos - 1][xPos];
            Figure fDown = map[yPos + 1][xPos];
            Figure fLeft = map[yPos][xPos - 1];
            Figure fRight = map[yPos][xPos + 1];

            ArrayList<Figure> caseAround = new ArrayList<Figure>();
            caseAround.add(fUp);
            caseAround.add(fDown);
            caseAround.add(fLeft);
            caseAround.add(fRight);

            switch (toward) {
                case PacManLauncher.UP:
                    if (fLeft.getClass().getName().compareTo("view.Wall") != 0 || fRight.getClass().getName().compareTo("view.Wall") != 0) {
                        caseAround.remove(fDown);
                        this.chooseMove(toward, caseAround, fUp, fDown, fLeft, fRight);
                        haveMoved = true;
                    } else if (fUp.getClass().getName().compareTo("view.Wall") == 0) {
                        this.chooseMove(toward, caseAround, fUp, fDown, fLeft, fRight);
                        haveMoved = true;
                    }
                    break;
                case PacManLauncher.DOWN:
                    if (fLeft.getClass().getName().compareTo("view.Wall") != 0 || fRight.getClass().getName().compareTo("view.Wall") != 0) {
                        caseAround.remove(fUp);
                        this.chooseMove(toward, caseAround, fUp, fDown, fLeft, fRight);
                        haveMoved = true;
                    } else if (fDown.getClass().getName().compareTo("view.Wall") == 0) {
                        this.chooseMove(toward, caseAround, fUp, fDown, fLeft, fRight);
                        haveMoved = true;
                    }
                    break;
                case PacManLauncher.LEFT:
                    if (fUp.getClass().getName().compareTo("view.Wall") != 0 || fDown.getClass().getName().compareTo("view.Wall") != 0) {
                        caseAround.remove(fRight);
                        this.chooseMove(toward, caseAround, fUp, fDown, fLeft, fRight);
                        haveMoved = true;
                    } else if (fLeft.getClass().getName().compareTo("view.Wall") == 0) {
                        this.chooseMove(toward, caseAround, fUp, fDown, fLeft, fRight);
                        haveMoved = true;
                    }
                    break;
                case PacManLauncher.RIGHT:
                    if (fUp.getClass().getName().compareTo("view.Wall") != 0 || fDown.getClass().getName().compareTo("view.Wall") != 0) {
                        caseAround.remove(fLeft);
                        this.chooseMove(toward, caseAround, fUp, fDown, fLeft, fRight);
                        haveMoved = true;
                    } else if (fRight.getClass().getName().compareTo("view.Wall") == 0) {
                        this.chooseMove(toward, caseAround, fUp, fDown, fLeft, fRight);
                        haveMoved = true;
                    }
                    break;
            }
        }

        if (!haveMoved) {
            this.move(this.previousMove);
        }
    }

    public void chooseMove(String toward, ArrayList<Figure> listF, Figure fUp, Figure fDown, Figure fLeft, Figure fRight) {
        boolean result = false;
        ArrayList<Figure> toGo = new ArrayList<Figure>();

        for (Figure f : listF) {
            if (f.getClass().getName().compareTo("view.Wall") != 0) {
                toGo.add(f);
            }
        }

        Figure nextMove = null;
        double ran = Math.random() * toGo.size();
        for (int i = 0; i < toGo.size(); i++) {
            if (ran >= i && ran < i + 1) {
                nextMove = toGo.get(i);
            }
        }

        if (nextMove == null) {
            this.move(toward);
        } else if (nextMove == fUp) {
            this.move(PacManLauncher.UP);
        } else if (nextMove == fDown) {
            this.move(PacManLauncher.DOWN);
        } else if (nextMove == fLeft) {
            this.move(PacManLauncher.LEFT);
        } else if (nextMove == fRight) {
            this.move(PacManLauncher.RIGHT);
        }
    }

    public boolean checkCaseType(Figure f) {
        return (f instanceof Wall);
    }

    protected void interactWithFood(Figure[][] map, int i, int j) {
    }

    public void draw() {
        this.ghostSkin.draw();
    }
}
