package gameModel.obstacles;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Tile extends StaticObstacles {

	private String[] tileImagePaths = { "src/gameModel/images/tile_grass_top.png",
			"src/gameModel/images/tile_grass_bot.png", "src/gameModel/images/tile_grass_right.png",
			"src/gameModel/images/tile_grass_left.png", "src/gameModel/images/tile_dirt.png" };
	private int width = 40;
	private int height = 40;

	public Tile(int x, int y, String type) {
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
			types.add("correctPositionLeftHand");
			break;
		case 1:
			types.add("stopRight");
			types.add("correctPositionRightHand");
			break;
		case 2:
			types.add("stopTop");
			break;
		case 3:
			types.add("setGround");
			break;
		case 4:
			types.add("stopLeft");
			types.add("correctPositionLeftFoot");
			break;
		case 5:
			types.add("stopRight");
			types.add("correctPositionRightFoot");
			break;
		default:
			types.add("stop");
		}
		return types;
	}

	public String toString() {
		return "Tile";
	}
}
