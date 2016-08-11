package gameModel.obstacles;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Wall extends StaticObstacles {
	private String[] tileImagePaths = { "src/gameModel/images/tiledirt.png" };
	private int width = 40;
	private int height = 40;

	public Wall(int x, int y, String type) {
		this.centerX = x;
		this.centerY = y;
		this.halfWidth = 20;
		this.halfHeight = 20;
		gameComponentImagePaths = getType(type);
		hitboxes.add(new Rectangle(centerX - halfWidth, centerY - halfHeight, width, height));
	}

	private String[] getType(String type) {
		return new String[] { tileImagePaths[Integer.parseInt(type) - 1] };
	}

	@Override
	public ArrayList<String> collisionHandling(int type) {
		// 0 = XLeft, 1 = XRight, 2 = YTop, 3 = YBottom
		ArrayList<String> types = new ArrayList<String>();
		switch (type) {
		case 0:
			types.add("stopLeft");
			break;
		case 1:
			types.add("stopRight");
			break;
		case 2:
			break;
		case 3:
			break;
		default:
			types.add("stop");
		}
		return types;
	}
}
