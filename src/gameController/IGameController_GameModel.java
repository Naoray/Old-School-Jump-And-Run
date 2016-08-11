package gameController;

import java.awt.Rectangle;
import java.util.ArrayList;

public interface IGameController_GameModel {
	ArrayList<Integer[]> checkAllCollisions(
			ArrayList<Rectangle> playerHitboxes,
			ArrayList<ArrayList<Rectangle>> opponentHitboxes);

	public void nextLevel(int levelNumber);

	// Methode zum Setzen des Texts der in einem Dialog angezeigt werden soll.
	// Währenddessen pausiert das Spiel.
	void setMessageText(String message);

	void resetLevel(int levelNumber);

	// Gibt das aktuelle Level zurück
	int getLevelCounter();

	void gameOver();
}
