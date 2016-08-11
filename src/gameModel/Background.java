package gameModel;

import java.awt.Image;

public class Background {

	private int bgX;
	private int bgY;
	private int speedX;
	public String backgroundPath;// = "src/gameModel/images/background.png";
	private String rootPath = "src/gameModel/images/";
	private Image image;

	public Background(int x, int y, String backgroundPath) {

		this.backgroundPath = rootPath + backgroundPath;
		this.bgX = x;
		this.bgY = y;
		speedX = 0;
	}

	public void update() {

		bgX = bgX + speedX;

		if (bgX <= -2160) {
			bgX = bgX + 4320;
		} else if (bgX >= 2160) {
			bgX = bgX - 4320;
		}
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return this.image;
	}

	public int getBgX() {
		return bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
}