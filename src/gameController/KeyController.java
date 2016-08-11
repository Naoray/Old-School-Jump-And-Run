package gameController;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class KeyController implements KeyEventDispatcher {

	GameController gameController;
	int left = KeyEvent.VK_LEFT;
	int right = KeyEvent.VK_RIGHT;
	int jump = KeyEvent.VK_SPACE;
	int down = KeyEvent.VK_DOWN;

	public KeyController(GameController gameController) {
		this.gameController = gameController;
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}

	// Es wird ein KeyEventDispatcher anstatt eines normalen KeyListeners
	// genutzt, damit jeder Key-Eingabe abgefangen wird, egal welche Komponente
	// gerade den Fokus hat
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			if (e.getKeyCode() == left) {
				gameController.moveLeft();
			}
			if (e.getKeyCode() == right) {
				gameController.moveRight();
			}
			if (e.getKeyCode() == jump) {
				gameController.jump();
			}
			if (e.getKeyCode() == down) {
				gameController.setDucked(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				gameController.setMessageText("Spiel pausiert! \n" + "Enter für weiter und Z zum Beenden des Spiels");
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER && gameController.getShowMessage()) {
				gameController.gameView.closeDialog();
			}
			if ((e.getKeyCode() == KeyEvent.VK_Z && gameController.getShowMessage())) {
				gameController.gameView.closeDialog();
				gameController.setEndGame(true);
				// gameController.gameView.initMainMenue();

			}

		} else if (e.getID() == KeyEvent.KEY_RELEASED) {
			if (e.getKeyCode() == left || e.getKeyCode() == right) {

				gameController.level.player.stop();
			}
			if (e.getKeyCode() == down) {
				gameController.level.player.setDucked(false);
			}
		}

		return false;
	}
}
