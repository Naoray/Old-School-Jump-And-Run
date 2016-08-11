package gameModel;

import java.awt.Rectangle;
import java.util.ArrayList;

public interface IGameModel_GameController {

	// Erstellt die GameComponents
	void createGameComponents(ArrayList<String> list);

	// Löscht alle GameComponents
	void clearGameComponents();

	// Erstellt den Background des Levels
	void createBackground(String backgoundDateiName);

	// Update-Methode des Levels
	void update();

	// trigger collision check
	ArrayList<Integer[]> checkCollision(ArrayList<Rectangle> playerHitboxes);

	public void moveLeft();

	public void moveRight();

	public void jump();

	public void setDucked(boolean b);
}
