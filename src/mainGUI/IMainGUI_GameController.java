package mainGUI;

import java.util.ArrayList;

import gameModel.Background;
import gameModel.GameComponent;
import gameModel.player.Player;

public interface IMainGUI_GameController {

	/*
	 * Methode, die aus dem GameController aufgerufen wird und daf�r sorgt, dass
	 * auf der GUI neu gezeigt wird.
	 */
	public void repaint();

	/*
	 * Methode, die aus dem GameController aufgerufen wird und der MainGUI den
	 * Player �bergibt, damit daraus die Bilder geladen werden k�nnen.
	 */
	public void setPlayer(Player player);

	/*
	 * Methode, die aus dem GameController aufgerufen wird und der MainGUI die
	 * Hintergrundbilder �bergibt, damit daraus die Bilder geladen werden
	 * k�nnen.
	 */
	public void setBackgrounds(Background background1, Background background2);

	public void loadImages();

	public void setGameController();

	public void setGameComponents(ArrayList<GameComponent> gameComponentList);

	void showMessageText(String message);

	void closeDialog();

	void initMainMenue();

}
