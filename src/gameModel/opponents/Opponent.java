package gameModel.opponents;

import java.util.ArrayList;

import gameModel.DynamicGameComponent;
import gameModel.Level;

public abstract class Opponent extends DynamicGameComponent {

	private int maxHealth, currentHealth, power, speedX;
	private Level level;
	// private Background bg = ZeichenPanel.getBg1();

	public Opponent(Level level) {
		this.level = level;
	}

	public void die() {
		level.getGameComponentList().remove(this);
	}

	public void attack() {
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getPower() {
		return power;
	}

	public int getSpeedX() {
		return speedX;
	}

	// public int getCenterX() {
	// return centerX;
	// }
	//
	// public int getCenterY() {
	// return CenterY;
	// }

	// public Background getBg() {
	// return bg;
	// }

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	//
	// public void setCenterX(int centerX) {
	// this.centerX = centerX;
	// }
	//
	// public void setCenterY(int centerY) {
	// CenterY = centerY;
	// }

	// public void setBg(Background bg) {
	// this.bg = bg;
	// }

	@Override
	public ArrayList<String> collisionHandling(int type) {
		// 0 = XLeft, 1 = XRight, 2 = YTop, 3 = YBottom
		ArrayList<String> types = new ArrayList<String>();
		switch (type) {
		case 3:
			types.add("setGround");
			this.die();
			break;
		case 0:
			types.add("stopLeft");
			types.add("looseLife");
			break;
		case 1:
			types.add("stopRight");
			types.add("looseLife");
			break;
		case 2:
			types.add("stopTop");
			types.add("looseLife");
			break;
		default:
			types.add("stop");
		}
		return types;
	}
}
