package gameController;

import mainGUI.IMainGUI_GameController;

public interface IGameController_MainGUI {

	public void setGameView(IMainGUI_GameController gameView);

	public void sendLevelComponentReferences();

}