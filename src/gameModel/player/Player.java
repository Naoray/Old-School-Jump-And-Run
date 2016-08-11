package gameModel.player;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import gameModel.Animation;
import gameModel.Background;
import gameModel.GameComponent;
import gameModel.Level;

public class Player {

	private Animation animation;
	public String playerImageJumpPath = "src/gameModel/images/jumped.png";
	public Image jumping;
	public String playerImageDownPath = "src/gameModel/images/down.png";
	public Image down;
	// Benötigte Images für die Animation in passender Reihenfolge
	private String[] playerImagePaths = { "src/gameModel/images/character.png", "src/gameModel/images/character2.png",
			"src/gameModel/images/character3.png", "src/gameModel/images/character2.png" };
	// Benötigte Anzeigedauer des entsprechenden Bildes in passender Reihenfolge
	private int[] playerImageAnimationDurations = { 1250, 50, 50, 50 };

	final int JUMPSPEED = -10;
	final int MOVESPEED = 5;
	private int ground = 0;
	final int halfWidth = 61;
	final int halfHeight = 63;
	final int DEATHGROUND = 600;
	final int LEFTBORDER = 170;
	final int RIGHTBORDER = 450;

	private int centerX = 100;
	private int centerY = ground;

	private boolean jumped = false;
	private boolean falling = false;
	private boolean wasJumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean allowedToMoveLeft = true;
	private boolean allowedToMoveRight = true;
	private boolean ducked = false;
	private boolean onGround = true;
	private boolean levelCompleted = false;
	private boolean opponentDeath = false;
	// Variable für Unverwundbarkeit
	private boolean invulnerable = false;
	private int lives = 3;

	private Background bg1, bg2;
	private Level level;

	private int speedX = 0;
	private int speedY = 0;
	private int oldSpeedY = 0;
	private int maxFallSpeed = 6;

	// for collision detection purposes
	public ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();
	public static Rectangle hitboxUpperBody = new Rectangle(0, 0, 0, 0);
	public static Rectangle hitboxLowerBody = new Rectangle(0, 0, 0, 0);
	public static Rectangle hitboxLeftArm = new Rectangle(0, 0, 0, 0);
	public static Rectangle hitboxRightArm = new Rectangle(0, 0, 0, 0);
	public static Rectangle hitboxLeftFoot = new Rectangle(0, 0, 0, 0);
	public static Rectangle hitboxRightFoot = new Rectangle(0, 0, 0, 0);

	public Player(Level level) {

		this.animation = new Animation();
		this.level = level;
		hitboxes.add(hitboxUpperBody);
		hitboxes.add(hitboxLowerBody);
		hitboxes.add(hitboxLeftArm);
		hitboxes.add(hitboxRightArm);
		hitboxes.add(hitboxLeftFoot);
		hitboxes.add(hitboxRightFoot);
	}

	// Animiert den Player bzw. stößt die zugehörige Animationsklasse an, damit
	// sie das aktuelle Bild passend zur Animation setzt
	public void animate() {
		animation.animate(10);
	}

	public Animation getAnimationClass() {
		return animation;
	}

	// Liefert das aktuell zu zeichende Bild passend zur Animation
	public Image getCurrentImage() {
		if (this.isJumped()) {
			return this.jumping;
		} else if (this.isDucked()) {
			return this.down;
		} else {
			return animation.getImage();
		}
	}

	// Liefert die Dateipfade zu den für die Animation benötigten Bilder
	public String[] getPlayerImagePaths() {
		return playerImagePaths;
	}

	// Liefert die Dauer der Bilder, die für die Animation benötigt werden
	public int[] getPlayerImageAnimationDurations() {
		return playerImageAnimationDurations;
	}

	// Aktualisiert die Koordinaten des Roboters
	public void update() {
		if (lives == 0) {
			level.gameOver();

		} else {
			hitboxUpperBody.setRect(centerX - 34, centerY - this.getHalfHeight(), 68, 63);
			hitboxLowerBody.setRect(hitboxUpperBody.getX(), hitboxUpperBody.getY() + this.getHalfHeight(), 68, 64);
			hitboxLeftArm.setRect(hitboxUpperBody.getX() - 26, hitboxUpperBody.getY() + 32, 26, 20);
			hitboxRightArm.setRect(hitboxUpperBody.getX() + 68, hitboxUpperBody.getY() + 32, 26, 20);

			hitboxLeftFoot.setRect(centerX - 34, centerY + 30, 33, 15);
			hitboxRightFoot.setRect(centerX + 1, centerY + 30, 33, 15);

			if (onGround && !jumped) {
				setSpeedY(0);
			}

			// Wenn der Player läuft, stößt er die Kollisionsprüfung an und
			// übergibt
			// dabei seine Koordinaten in einem Array
			if (this.speedX != 0 || this.speedY != 0 || wasJumped) {
				ArrayList<Integer[]> collision = level.checkCollision(this.getHitboxes());

				for (int i = 0; i < collision.size(); i++) {
					int type = collision.get(i)[0];
					// System.out.println("typexx: " + type);
					GameComponent component = level.getGameComponent(collision.get(i)[1]);
					ArrayList<String> collisionEvents = component.collisionHandling(type);
					if (collisionEvents != null) {
						for (String collisionEvent : collisionEvents) {
							switch (collisionEvent) {
							case "setGround":
								setGround(component.getYTop() - halfHeight);
								onGround = true;
								falling = false;
								oldSpeedY = 0;
								break;
							case "stopRight":
								allowedToMoveRight = false;
								this.stopRight();
								break;
							case "correctPositionRightHand":
								this.setCenterX(collision.get(i)[2] - 60);
								break;
							case "correctPositionRightFoot":
								this.setCenterX(collision.get(i)[2] - 34);
								break;
							case "stopLeft":
								allowedToMoveLeft = false;
								this.stopLeft();
								break;
							case "correctPositionLeftHand":
								this.setCenterX(collision.get(i)[2] + 40 + 60);
								break;
							case "correctPositionLeftFoot":
								this.setCenterX(collision.get(i)[2] + 40 + 35);
								break;
							case "end":
								level.nextLevel();
								setLevelCompleted(true);
								break;
							case "stop":
								this.stop();
								break;
							case "looseLife":
								if (!invulnerable) {
									this.looseLife();
									this.invulnerable = true;
									this.invulnerableTimerCount();
								}
								break;
							case "jump":
								this.jumped = false;
								this.jump();
								break;
							case "dieOpponent":
								level.getGameComponentList().remove(component);
								this.opponentDeath = true;
								break;
							}

						}
						if (opponentDeath) {
							opponentDeath = false;
							break;
						}
					}

					if (this.levelCompleted) {
						setLevelCompleted(false);
						break;
					}
				}

				if (speedY > oldSpeedY) {
					oldSpeedY = speedY;
				}

				if (ground >= DEATHGROUND) {
					setSpeedY(0);
					level.resetLevel();
					this.looseLife();
				} else if (collision.size() == 0 && !jumped) {
					falling = true;
					setSpeedY(oldSpeedY + 1);
					setGround(ground + speedY);
					onGround = false;
				}
				wasJumped = false;
			}

			// Bewegt den Charakter bzw. scrollt den Hintergrund
			if (centerX >= LEFTBORDER && speedX < 0) {
				centerX = centerX + speedX; // Ändert centerX indem speedX
											// addiert
											// wird.
			}

			if (speedX < 0 && centerX < LEFTBORDER) {
				bg1.setSpeedX(MOVESPEED);
				bg2.setSpeedX(MOVESPEED);
			}

			if (speedX == 0) {
				bg1.setSpeedX(0);
				bg2.setSpeedX(0);
			}

			if (centerX <= RIGHTBORDER && speedX > 0) {
				centerX = centerX + speedX;
			}

			if (speedX > 0 && centerX > RIGHTBORDER) {
				bg1.setSpeedX(-MOVESPEED);
				bg2.setSpeedX(-MOVESPEED);
			}

			// Updatet die Y-Position
			centerY = centerY + speedY; // Addiere speedY auf centerY, um die
			// neue Position festzulegen.
			if (centerY + speedY >= ground) { // 382/GROUND ist da wo das
				// centerY des
				// Charakters ist, wenn er auf dem Boden steht.
				centerY = ground;
			}

			// Verarbeitet das Springen
			if (jumped == true) {
				speedY = speedY + 1; // Während der Charakter in der Luft ist
										// addiere 1 zu speedY: HINWEIS: Das
										// Bringt
										// den Charakter wieder herunter!!
				//
				if (speedY > maxFallSpeed) {
					speedY = maxFallSpeed;
				}
				if (centerY + speedY >= ground) {
					centerY = ground;
					speedY = 0;
					jumped = false;
				}
				wasJumped = true;
			}

			// // Verhindert das Gehen hinter X-Koordinate von 0
			// if (centerX + speedX <= 0) { // Wenn centerX + speedX den
			// // Charakter
			// // außerhalb des Bildschirms bringen
			// // würde.
			// centerX = 61; // Fixiert centerX auf 60 Pixel.
			// }
		}
	}

	public void moveRight() {
		if (ducked == false && allowedToMoveRight) {
			allowedToMoveLeft = true;
			speedX = 6;
		}
	}

	public void moveLeft() {
		if (ducked == false && allowedToMoveLeft) {
			allowedToMoveRight = true;
			speedX = -6;
		}
	}

	public void stop() {
		speedX = 0;
	}

	public void stopRight() {
		setMovingRight(false);
		if (speedX > 0)
			speedX = 0;
		// stop();
	}

	public void stopLeft() {
		setMovingLeft(false);
		if (speedX < 0)
			speedX = 0;
		// stop();
	}

	public void jump() {
		if (jumped == false && falling == false) {
			allowedToMoveRight = true;
			allowedToMoveLeft = true;
			speedY = -15;
			jumped = true;
		}
	}

	public int getCenterX() {
		return centerX;
	}

	public int getHalfWidth() {
		return halfWidth;
	}

	public int getXRight() {
		return centerX + halfWidth;
	}

	public int getXLeft() {
		return centerX - halfWidth;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getHalfHeight() {
		return halfHeight;
	}

	public int getYTop() {
		return centerY - halfHeight;
	}

	public int getYBottom() {
		return centerY + halfHeight;
	}

	public boolean isJumped() {
		return jumped;
	}

	public boolean isFalling() {
		return this.falling;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	// Erstellt ein Array mit allen Koordinaten (X left,right und Y top,bottom)
	public ArrayList<Rectangle> getHitboxes() {
		return hitboxes;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public int getJUMPSPEED() {
		return JUMPSPEED;
	}

	public int getMOVESPEED() {
		return MOVESPEED;
	}

	public int getGround() {
		return ground;
	}

	public void setGround(int ground) {
		this.ground = ground;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public boolean isDucked() {
		return ducked;
	}

	public Background getBg1() {
		return bg1;
	}

	public Background getBg2() {
		return bg2;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public void setDucked(boolean ducked) {
		this.ducked = ducked;
		if (ducked == true) {
			this.stop();
		}
	}

	public void setBg1(Background background1) {
		this.bg1 = background1;
	}

	public void setBg2(Background background2) {
		this.bg2 = background2;
	}

	public void setWasJumped(boolean b) {
		this.wasJumped = b;
	}

	public void setLevelCompleted(boolean b) {
		this.levelCompleted = b;
	}

	public boolean getLevelCompleted() {
		return this.levelCompleted;
	}

	public int getLives() {
		return lives;
	}

	private void looseLife() {
		lives -= 1;
	}

	public void invulnerableTimerCount() // ausgeführt durch button
	{

		new Thread() {
			{
				start();
			}

			public void run() {
				try {
					sleep(3000); // Schleife "schläft" für 3 sec
				} catch (InterruptedException ixo) {
				}
				invulnerable = false;
			}
		};

	}

}
