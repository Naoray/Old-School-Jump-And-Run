package gameController;

import java.awt.Rectangle;
import java.util.ArrayList;

import gameModel.Level;
import levelController.LevelController;
import mainGUI.IMainGUI_GameController;

public class GameController implements Runnable, IGameController_MainGUI, IGameController_GameModel {

	Level level;
	LevelController levelController;
	CollisionEngine collisionEngine;
	Thread thread;
	IMainGUI_GameController gameView;
	KeyController keyController;
	boolean showMessage = false;
	boolean endGame = false;
	String message;

	int levelCounter = 1;

	// Konstruktor erwartet bereits ein GameView Objekt, damit es bereits in der
	// Start-Methode des GameControllers zum Weiterreichen des Players, der
	// Backgrounds und der LevelComponents verwendet werden kann. (Sonst musste
	// dieses Weiterreichen in einer unangebrachten Methode stattfinden!!)
	public GameController(IMainGUI_GameController gameView) {
		this.setGameView(gameView);
		levelController = new LevelController();
		level = new Level(this);
		collisionEngine = new CollisionEngine();
		keyController = new KeyController(this);
		this.start();
	}

	@Override
	public void setMessageText(String message) {
		this.showMessage = true;
		this.message = message;
	}

	public void stopGame() {
		thread.interrupt();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// nothing
		}
	}

	// Spielschleife
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int counter = 0;
		// TODO Auto-generated method stub
		while (!Thread.interrupted() && counter < 1) {
			if (showMessage) {
				gameView.showMessageText(message);
				showMessage = false;
			} else if (!endGame) {
				level.update();
				gameView.repaint();
			} else if (endGame) {
				this.stopGame();
				counter++;
				// level.clearGameComponents();
				gameView.initMainMenue();
				// gameView.closeDialog();
				// thread.interrupt();
			}

			try {
				if (!thread.isInterrupted())
					Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Bereitet die Elemente für das Spiel vor
	public void start() {

		levelController.loadData(levelCounter);
		level.createBackground(levelController.getLevelBackground());
		level.createGameComponents(levelController.getLevelComponents());

		this.sendLevelComponentReferences();

		thread = new Thread(this);
		thread.start();
	}

	// Übergibt dem GameView-Objekt alle notwendigen Referenzen auf die
	// LevelComponents (Player,Background,GameComponents)
	public void sendLevelComponentReferences() {
		gameView.setPlayer(level.getPlayer());
		gameView.setBackgrounds(level.getBg1(), level.getBg2());
		gameView.setGameComponents(level.getGameComponentList());
		gameView.loadImages();
	}

	@Override
	public void setGameView(IMainGUI_GameController gameView) {
		this.gameView = gameView;
	}

	// Gibt die Player-Koordinaten und alle GameComponent-Koordinaten an die
	// Kollisionsengine zur Überprüfung weiter
	public ArrayList<Integer[]> checkAllCollisions(ArrayList<Rectangle> playerHitboxes,
			ArrayList<ArrayList<Rectangle>> opponentHitboxes) {
		// TODO Auto-generated method stub
		return collisionEngine.searchCollisions(playerHitboxes, opponentHitboxes);
	}

	@Override
	public void nextLevel(int levelNumber) {
		levelCounter = levelNumber;
		boolean isNextLevel = levelController.loadData(levelCounter);

		if (isNextLevel) {
			this.setMessageText("Level beendet. \n" + "Nächtes Level mit Enter starten.");
			// Dem User anzeigen, dass sein Level beendet wird und es weitere
			// gibt.
			gameView.showMessageText(message);
			showMessage = false;
			level.createBackground(levelController.getLevelBackground());

			level.createGameComponents(levelController.getLevelComponents());

			this.sendLevelComponentReferences();
		} else {
			endGame = true;
			this.setMessageText("Glückwunsch! Sie haben gewonnen!.");
			showMessage = true;
		}

	}

	@Override
	public void resetLevel(int levelNumber) {
		levelCounter = levelNumber;
		levelController.loadData(levelCounter);
		level.createBackground(levelController.getLevelBackground());
		level.createGameComponents(levelController.getLevelComponents());
		this.sendLevelComponentReferences();
	}

	public boolean getShowMessage() {
		return this.showMessage;
	}

	public void setEndGame(boolean endGame) {
		this.endGame = endGame;
	}

	public int getLevelCounter() {
		return this.levelCounter;
	}

	@Override
	public void gameOver() {
		this.endGame = true;
		this.setMessageText("Game Over. :'-(");
		showMessage = true;
	}

	public void moveLeft() {
		level.moveLeft();
	}

	public void moveRight() {
		level.moveRight();
	}

	public void jump() {
		level.jump();
	}

	public void setDucked(boolean b) {
		level.setDucked(b);
	}
}
