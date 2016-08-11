package gameModel.obstacles;

import java.awt.Rectangle;
import java.util.ArrayList;

public class LevelEnd extends StaticObstacles {

	// private String[] tileImagePaths = { "src/gameModel/images/fahne.png" };
	// private String[] tileImagePaths = { "src/gameModel/images/tiledirt.png"
	// };
	private String[] tileImagePaths = { "src/gameModel/images/test3.png" };
	private int width = 70;
	private int height = 240;

	public LevelEnd(int x, int y, String type) {
		this.centerX = x;
		this.centerY = y;
		this.halfWidth = 10;
		this.halfHeight = 216;
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
		default:
			types.add("end");
		}
		return types;
	}
}