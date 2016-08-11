package gameModel.obstacles;

import java.awt.Image;
import java.util.ArrayList;

import gameModel.GameComponent;

public abstract class StaticObstacles extends GameComponent {

	private Image img;

	@Override
	public StaticObstacles getStaticImageClass() {
		return this;
	}

	public void addImage(Image img) {
		this.img = img;
	}

	public Image getImage() {
		return img;
	}

	@Override
	public ArrayList<String> collisionHandling(int type) {
		// 0 = XLeft, 1 = XRight, 2 = YTop, 3 = YBottom
		ArrayList<String> types = new ArrayList<String>();
		switch (type) {
		case 3:
			types.add("setGround");
			break;
		case 0:
			types.add("stopLeft");
			break;
		case 1:
			types.add("stopRight");
			break;
		case 2:
			types.add("stopTop");
			break;
		default:
			types.add("stop");
		}
		return types;
	}
}
