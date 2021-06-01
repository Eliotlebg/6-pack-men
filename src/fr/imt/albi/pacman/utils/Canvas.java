package fr.imt.albi.pacman.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends java.awt.Canvas {
	public static final int WIDTH = 500, HEIGHT = 500;

	private static Canvas canvasSingleton;
	private final JFrame frame;
	private final CanvasPane canvas;
	private Graphics2D graphic;
	private final Color backgroundColor;
	private Image canvasImage;
	private final List<Object> objects;
	private final HashMap<Object, ShapeDescription> shapes;
	private boolean upPressed, downPressed, leftPressed, rightPressed;

	private Canvas(String title, int width, int height, Color bgColor) {
		this.frame = new JFrame();
		this.canvas = new CanvasPane();
		this.frame.setContentPane(this.canvas);
		this.frame.setTitle(title);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocation(30, 30);
		this.canvas.setPreferredSize(new Dimension(width, height));
		this.backgroundColor = bgColor;
		this.frame.pack();
		this.objects = new ArrayList<>();
		this.shapes = new HashMap<>();

		this.canvas.addKeyListener(new KeyboardListener());
		this.canvas.setFocusable(true);
	}

	public static Canvas getCanvas() {
		if (canvasSingleton == null) {
			canvasSingleton = new Canvas("Pac-Monsieur", WIDTH, HEIGHT, Color.white);
		}
		canvasSingleton.setVisible(true);
		return canvasSingleton;
	}

	public boolean isUpPressed() {
		return this.upPressed;
	}

	public boolean isDownPressed() {
		return this.downPressed;
	}

	public boolean isLeftPressed() {
		return this.leftPressed;
	}

	public boolean isRightPressed() {
		return this.rightPressed;
	}

	public void resetMove() {
		this.rightPressed = false;
		this.leftPressed = false;
		this.upPressed = false;
		this.downPressed = false;
	}

	public void printString(String text, int x, int y) {
		this.graphic.setFont(new Font("Arial", Font.BOLD, 20));
		this.graphic.setColor(Color.WHITE);
		this.graphic.drawString(text, x, y);
		this.canvas.repaint();
	}

	@Override
	public void setVisible(boolean visible) {
		if (this.graphic == null) {
			Dimension size = this.canvas.getSize();
			this.canvasImage = this.canvas.createImage(size.width, size.height);
			this.graphic = (Graphics2D) this.canvasImage.getGraphics();
			this.graphic.setColor(this.backgroundColor);
			this.graphic.fillRect(0, 0, size.width, size.height);
			this.graphic.setColor(Color.black);
		}
		this.frame.setVisible(visible);
	}

	public void draw(Object referenceObject, String color, Shape shape) {
		this.objects.remove(referenceObject);
		this.objects.add(referenceObject);
		this.shapes.put(referenceObject, new ShapeDescription(shape, color));
	}

	public void erase(Object referenceObject) {
		this.objects.remove(referenceObject);
		this.shapes.remove(referenceObject);
	}

	public void setForegroundColor(String colorString) {
		if (colorString.equals("red")) {
			this.graphic.setColor(new Color(235, 25, 25));
		} else if (colorString.equals("black")) {
			this.graphic.setColor(Color.black);
		} else if (colorString.equals("blue")) {
			this.graphic.setColor(new Color(0, 0, 204));
		} else if (colorString.equals("violet")) {
			this.graphic.setColor(new Color(130, 0, 140));
		} else if (colorString.equals("yellow")) {
			this.graphic.setColor(new Color(255, 230, 0));
		} else if (colorString.equals("green")) {
			this.graphic.setColor(new Color(80, 160, 60));
		} else if (colorString.equals("pink")) {
			this.graphic.setColor(new Color(255, 0, 127));
		} else if (colorString.equals("white")) {
			this.graphic.setColor(Color.white);
		} else if (colorString.equals("redG")) {
			this.graphic.setColor(new Color(239, 7, 7));
		} else if (colorString.equals("blueG")) {
			this.graphic.setColor(new Color(102, 254, 255));
		} else if (colorString.equals("orangeG")) {
			this.graphic.setColor(new Color(250, 156, 0));
		} else if (colorString.equals("pinkG")) {
			this.graphic.setColor(new Color(255, 152, 153));
		} else {
			this.graphic.setColor(Color.black);
		}
	}

	public void wait(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {

		}
	}

	public void redraw(int score, int life, String meilleurScore) {
		this.erase();
		for (Object shape : this.objects) {
			this.shapes.get(shape).draw(this.graphic);
		}
		this.printString("Score : " + score, 10, 20);
		this.printString("Vie : " + life, 10, 40);
		this.printString("Meilleur score : " + meilleurScore, 240, 20);
		this.canvas.repaint();
		this.wait(125);
	}

	private void erase() {
		Color original = this.graphic.getColor();
		this.graphic.setColor(this.backgroundColor);
		Dimension size = this.canvas.getSize();
		this.graphic.fill(new Rectangle(0, 0, size.width, size.height));
		this.graphic.setColor(original);
	}

	private class CanvasPane extends JPanel {
		@Override
		public void paint(Graphics g) {
			g.drawImage(Canvas.this.canvasImage, 0, 0, null);
		}
	}

	private class ShapeDescription {
		private final Shape shape;
		private final String colorString;

		public ShapeDescription(Shape shape, String color) {
			this.shape = shape;
			this.colorString = color;
		}

		public void draw(Graphics2D graphic) {
			Canvas.this.setForegroundColor(this.colorString);
			graphic.fill(this.shape);
		}
	}

	private class KeyboardListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent event) {
			switch (event.getKeyCode()) {
				case KeyEvent.VK_UP:
					Canvas.this.upPressed = true;
					Canvas.this.downPressed = false;
					Canvas.this.leftPressed = false;
					Canvas.this.rightPressed = false;
					break;
				case KeyEvent.VK_DOWN:
					Canvas.this.upPressed = false;
					Canvas.this.downPressed = true;
					Canvas.this.leftPressed = false;
					Canvas.this.rightPressed = false;
					break;
				case KeyEvent.VK_LEFT:
					Canvas.this.upPressed = false;
					Canvas.this.downPressed = false;
					Canvas.this.leftPressed = true;
					Canvas.this.rightPressed = false;
					break;
				case KeyEvent.VK_RIGHT:
					Canvas.this.upPressed = false;
					Canvas.this.downPressed = false;
					Canvas.this.leftPressed = false;
					Canvas.this.rightPressed = true;
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent event) {

			switch (event.getKeyCode()) {
				/*
				 * case KeyEvent.VK_UP: upPressed = false; break; case KeyEvent.VK_DOWN:
				 * downPressed = false; break; case KeyEvent.VK_LEFT: leftPressed = false;
				 * break; case KeyEvent.VK_RIGHT: rightPressed = false; break;
				 */
			}

		}
	}
}
