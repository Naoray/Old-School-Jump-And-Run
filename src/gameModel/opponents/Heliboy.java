package gameModel.opponents;

import gameModel.Animation;
import gameModel.Level;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Heliboy extends Opponent {

	// Benötigte Images für die Animation in passender Reihenfolge
	private String[] heliboyImagePaths = { "src/gameModel/images/heliboy.png",
			"src/gameModel/images/heliboy2.png",
			"src/gameModel/images/heliboy3.png",
			"src/gameModel/images/heliboy4.png",
			"src/gameModel/images/heliboy5.png",
			"src/gameModel/images/heliboy4.png",
			"src/gameModel/images/heliboy3.png",
			"src/gameModel/images/heliboy2.png" };
	// Benötigte Anzeigedauer des entsprechenden Bildes in passender Reihenfolge
	private int[] heliboyImageAnimationDurations = { 100, 50, 50, 50, 50, 50,
			50, 50 };
	public int width = 96;
	public int height = 96;

	public Heliboy(int centerX, int centerY, Level level) {
		super(level);
		this.setAnimation();
		this.setCenterX(centerX);
		this.setCenterY(centerY);
		this.halfHeight = 48;
		this.halfWidth = 48;
		gameComponentImagePaths = heliboyImagePaths;
		gameComponentAnimationDurations = heliboyImageAnimationDurations;
		hitboxes.add(new Rectangle(centerX - halfWidth, centerY - halfHeight,
				width, height - 26));
	}

	public void setAnimation() {
		animation = new Animation();
	}

	// Liefert das aktuell zu zeichende Bild passend zur Animation
	public Image getCurrentImage() {
		return animation.getImage();
	}

	@Override
	public ArrayList<String> collisionHandling(int type) {
		// 0 = XLeftHand, 1 = XRightHand, 2 = YTop, 3 = YBottom, 4 = XLeftFoot,
		// 5 = XRightFoot
		ArrayList<String> types = new ArrayList<String>();
		switch (type) {
		case 4:
			types.add("setGround");
			types.add("jump");
			types.add("dieOpponent");
			break;
		case 5:
			types.add("setGround");
			types.add("jump");
			types.add("dieOpponent");
			break;
		case 3:
			break;
		default:
			types.add("looseLife");
		}
		return types;
	}
}
