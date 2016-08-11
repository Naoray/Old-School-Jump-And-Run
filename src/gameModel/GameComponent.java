package gameModel;

import java.awt.Rectangle;
import java.util.ArrayList;

import gameModel.obstacles.StaticObstacles;

public abstract class GameComponent {

	public String[] gameComponentImagePaths;
	private int speedX;

	// Jedes GameComponent besitzt von der Grafik/Image her einen Mittelpunkt
	// (X/Y)
	public int centerX;
	public int centerY;

	// Jedes GameComponent besitzt von der Grafik/Image her eine halbe Breite
	// und eine halbe Höhe
	public int halfWidth;
	public int halfHeight;

	public Background background;

	// hitbox ArrayList
	public ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();

	public void update() {
		speedX = background.getSpeedX();
		centerX += speedX;
		for (Rectangle hitbox : hitboxes) {
			hitbox.x += speedX;
		}
	}

	public int getSpeedX() {
		return speedX;
	}

	// Liefert die Dateipfade zu den für die Animation benötigten Bilder
	public String[] getGameComponentImagePaths() {

		return gameComponentImagePaths;
	}

	public Animation getAnimationClass() {

		return null;
		// System.out.println("gameComponent übergeordnet");
	}

	public StaticObstacles getStaticImageClass() {
		return null;
	}

	public int[] getAnimationDurations() {

		return null;
	}

	public int[] getComponentCoordinates() {

		return null;
	}

	// Erstellt ein Array mit allen Koordinaten (X left,right und Y top,bottom)
	public Integer[] createPositionArray() {
		return new Integer[] { this.getXLeft(), this.getXRight(), this.getYTop(), this.getYBottom() };
	}

	public int getXLeft() {
		return centerX - halfWidth;
	}

	public int getXRight() {
		return centerX + halfWidth;
	}

	public int getYTop() {
		return centerY - halfHeight;
	}

	public int getYBottom() {
		return centerY + halfHeight;
	}

	public int getCenterX() {

		return centerX;
	}

	public int getCenterY() {

		return centerY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void animate() {
	}

	public void setBackground(Background background) {

		this.background = background;
	}

	public ArrayList<String> collisionHandling(int type) {

		return null;
	}
}
