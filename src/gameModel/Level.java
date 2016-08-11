package gameModel;

import java.awt.Rectangle;
import java.util.ArrayList;

import gameController.GameController;
import gameController.IGameController_GameModel;
import gameModel.obstacles.LevelEnd;
import gameModel.obstacles.Tile;
import gameModel.opponents.Heliboy;
import gameModel.player.Player;

public class Level implements IGameModel_GameController {

	IGameController_GameModel gameController;
	ArrayList<GameComponent> gameComponentsList;
	Background background1, background2;
	public Player player;

	public Level(GameController gameController) {
		this.gameController = gameController;
		gameComponentsList = new ArrayList<GameComponent>();
		this.createPlayer();
	}

	public void createPlayer() {
		this.player = new Player(this);
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<GameComponent> getGameComponentList() {
		return gameComponentsList;
	}

	public GameComponent getGameComponent(int index) {
		return gameComponentsList.get(index);
	}

	@Override
	public void createGameComponents(ArrayList<String> list) {
		// TODO Auto-generated method stub
		this.clearGameComponents();
		// gameComponentsList.add(new Heliboy(340, 360));
		// gameComponentsList.add(new Heliboy(500, 360));
		// gameComponentsList.add(new Heliboy(660, 360));
		gameComponentsList = readGameComponentsData(list);
		for (GameComponent gameComponent : gameComponentsList) {
			gameComponent.setBackground(background1);
		}
	}

	@Override
	public void clearGameComponents() {
		// getPlayer().setCenterX(100);
		gameComponentsList.clear();
	}

	@Override
	public void createBackground(String backgroundDataName) {
		this.background1 = new Background(0, 0, backgroundDataName);
		this.background2 = new Background(2160, 0, backgroundDataName);

		this.setBackground();

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		background1.update();
		background2.update();

		player.update();
		player.animate();

		for (GameComponent gameComponent : gameComponentsList) {

			gameComponent.update();
			gameComponent.animate();
		}
	}

	// Übergibt der Player-Klasse die verwendeten Backgrounds, damit diese sie
	// scrollen kann
	public void setBackground() {

		player.setBg1(background1);
		player.setBg2(background2);
	}

	public Background getBg1() {

		return background1;
	}

	public Background getBg2() {

		return background2;
	}

	// Nimmt den geparsten String und erzeugt daraus alle enthatenen
	// GameComponents an den entsprechenden Stellen
	public ArrayList<GameComponent> readGameComponentsData(ArrayList<String> levelComponents) {
		ArrayList<GameComponent> gC = new ArrayList<GameComponent>();
		for (int i = 0; i < levelComponents.size(); i++) {
			String[] row = levelComponents.get(i).split("");
			for (int j = 0; j < row.length; j++) {
				switch (row[j]) {
				case "h":
					gC.add(new Heliboy(j * 40, (i - 1) * 40, this));
					break;
				case "1":
					gC.add(new Tile(j * 40, i * 40, "1"));
					break;
				case "2":
					gC.add(new Tile(j * 40, i * 40, "2"));
					break;
				case "3":
					gC.add(new Tile(j * 40, i * 40, "3"));
					break;
				case "4":
					gC.add(new Tile(j * 40, i * 40, "4"));
					break;
				case "5":
					gC.add(new Tile(j * 40, i * 40, "5"));
					break;
				case "e":
					gC.add(new LevelEnd(j * 40, i * 40, "1"));
					break;
				case "p":
					player.setJumped(true);
					player.setCenterX(j * 40);
					player.setCenterY(i * 40 - 40);
					player.setGround(player.getYBottom());
					player.setLevelCompleted(false);
					break;
				}

			}
		}
		return gC;
	}

	// Gibt die Koordinaten des Players + die Koordinaten aller GameComponents
	// an den GameController weiter, damit dieser die Daten an die
	// Kollisions-Engine weitergeben kann
	public ArrayList<Integer[]> checkCollision(ArrayList<Rectangle> playerHitboxes) {
		ArrayList<ArrayList<Rectangle>> opponentHitboxes = new ArrayList<ArrayList<Rectangle>>();
		for (GameComponent gc : gameComponentsList) {

			if (!gc.hitboxes.isEmpty()) {
				opponentHitboxes.add(gc.hitboxes);
			}
		}
		return gameController.checkAllCollisions(playerHitboxes, opponentHitboxes);

	}

	public void nextLevel() {
		gameController.nextLevel(gameController.getLevelCounter() + 1);
	}

	public void resetLevel() {
		gameController.resetLevel(gameController.getLevelCounter());
	}

	public void gameOver() {
		gameController.gameOver();
	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		player.moveLeft();
	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub
		player.moveRight();
	}

	@Override
	public void jump() {
		// TODO Auto-generated method stub
		player.jump();
	}

	@Override
	public void setDucked(boolean b) {
		// TODO Auto-generated method stub
		player.setDucked(b);
	}
}
