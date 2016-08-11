package mainGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import gameController.GameController;
import gameController.IGameController_MainGUI;
import gameModel.Background;
import gameModel.GameComponent;
import gameModel.player.Player;

public class GameView extends JPanel implements IMainGUI_GameController {

	IGameController_MainGUI gameController;
	ArrayList<GameComponent> gameComponentsList;
	Player player;
	Background background1, background2;
	MainMenueView mainMenue = null;
	showMessage dialog;
	// Font für die Lebensanzeige
	Font fontText = new Font("Arial", Font.BOLD, 28);

	public GameView(MainMenueView mainMenue) {

		this.setGameController();
		setBackground(Color.pink);
		this.mainMenue = mainMenue;
	}

	// Erstellt den GameController
	public void setGameController() {
		this.gameController = new GameController(this);
	}

	// Stellt Verknüpfung mit dem Player-Objekt her
	public void setPlayer(Player player) {
		this.player = player;
	}

	// Stellt Verknüpfungen mit den Background-Objekten her
	public void setBackgrounds(Background background1, Background background2) {
		this.background1 = background1;
		this.background2 = background2;
	}

	// Erstellt die Images aus den zu den jeweiligen Objekten gehörigen
	// Datei-Pfaden,
	// ordnet diese Images dem passenden Objekt direkt zu,
	// beim Player werden die Images mit ihrer Anzeigedauer der zugehörigen
	// Animations-Klasse übergeben,
	// läd anschließend alle Images komplett in den Speicher
	@Override
	public void loadImages() {

		Toolkit t = getToolkit();
		MediaTracker mediatracker = new MediaTracker(this);

		Image image = t.getImage(player.playerImageJumpPath);
		mediatracker.addImage(image, 0);
		player.jumping = image;

		image = t.getImage(player.playerImageDownPath);
		mediatracker.addImage(image, 0);
		player.down = image;

		for (int i = 0; i < player.getPlayerImagePaths().length; i++) {

			image = t.getImage(player.getPlayerImagePaths()[i]);

			mediatracker.addImage(image, 0);

			player.getAnimationClass().addFrame(image, player.getPlayerImageAnimationDurations()[i]);

		}

		for (int i = 0; i < gameComponentsList.size(); i++) {
			for (int j = 0; j < gameComponentsList.get(i).getGameComponentImagePaths().length; j++) {
				image = t.getImage(gameComponentsList.get(i).getGameComponentImagePaths()[j]);

				mediatracker.addImage(image, 0);

				if (gameComponentsList.get(i).getAnimationClass() != null) {
					gameComponentsList.get(i).getAnimationClass().addFrame(image,
							gameComponentsList.get(i).getAnimationDurations()[j]);
				} else {
					gameComponentsList.get(i).getStaticImageClass().addImage(image);
				}
			}
		}

		Image backgroundImage = t.getImage(background1.backgroundPath);
		background1.setImage(backgroundImage);
		background2.setImage(backgroundImage);

		mediatracker.addImage(backgroundImage, 0);

		try {
			mediatracker.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.drawImage(background1.getImage(), background1.getBgX(), background1.getBgY(), this);
		g.drawImage(background2.getImage(), background2.getBgX(), background2.getBgY(), this);

		for (int i = 0; i < gameComponentsList.size(); i++) {
			if (gameComponentsList.get(i).getAnimationClass() != null) {
				g.drawImage(gameComponentsList.get(i).getAnimationClass().getImage(),
						gameComponentsList.get(i).getXLeft(), gameComponentsList.get(i).getYTop(), this);
				// g.drawRect((int) gameComponentsList.get(i).hitboxes.get(0).x,
				// (int) gameComponentsList.get(i).hitboxes.get(0).y,
				// (int) gameComponentsList.get(i).hitboxes.get(0).width,
				// (int) gameComponentsList.get(i).hitboxes.get(0).height);
			} else {
				g.drawImage(gameComponentsList.get(i).getStaticImageClass().getImage(),
						gameComponentsList.get(i).getXLeft(), gameComponentsList.get(i).getYTop(), this);
				// g.drawRect((int) gameComponentsList.get(i).hitboxes.get(0).x,
				// (int) gameComponentsList.get(i).hitboxes.get(0).y,
				// (int) gameComponentsList.get(i).hitboxes.get(0).width,
				// (int) gameComponentsList.get(i).hitboxes.get(0).height);
			}
		}

		// -61 bzw -63 notwndig, da die X- und Y-koordinaten des Players die
		// Koordinaten des Zentrums sind!!

		g.drawImage(player.getCurrentImage(), player.getXLeft(), player.getYTop(), this);
		// g.drawRect((int) player.hitboxUpperBody.getX(), (int)
		// player.hitboxUpperBody.getY(),
		// (int) player.hitboxUpperBody.getWidth(), (int)
		// player.hitboxUpperBody.getHeight());
		// g.drawRect((int) player.hitboxLowerBody.getX(), (int)
		// player.hitboxLowerBody.getY(),
		// (int) player.hitboxLowerBody.getWidth(), (int)
		// player.hitboxLowerBody.getHeight());
		// g.drawRect((int) player.hitboxRightFoot.getX(), (int)
		// player.hitboxRightFoot.getY(),
		// (int) player.hitboxRightFoot.getWidth(), (int)
		// player.hitboxRightFoot.getHeight());
		// g.drawRect((int) player.hitboxRightArm.getX(), (int)
		// player.hitboxRightArm.getY(),
		// (int) player.hitboxRightArm.getWidth(), (int)
		// player.hitboxRightArm.getHeight());
		// g.drawRect((int) player.hitboxLeftArm.getX(), (int)
		// player.hitboxLeftArm.getY(),
		// (int) player.hitboxLeftArm.getWidth(), (int)
		// player.hitboxLeftArm.getHeight());
		// g.drawRect((int) player.hitboxLeftFoot.getX(), (int)
		// player.hitboxLeftFoot.getY(),
		// (int) player.hitboxLeftFoot.getWidth(), (int)
		// player.hitboxLeftFoot.getHeight());

		g.setFont(fontText);
		g.drawString("Leben: " + player.getLives(), 650, 40);
	}

	@Override
	public void setGameComponents(ArrayList<GameComponent> gameComponentsList) {
		this.gameComponentsList = gameComponentsList;
	}

	@Override
	public void initMainMenue() {
		mainMenue.initMainMenue();
	}

	@Override
	public void showMessageText(String message) {
		// Wir lassen unseren JDialog anzeigen
		dialog = new showMessage(message);
		dialog.pack();
		dialog.setVisible(true);
	}

	@Override
	public void closeDialog() {
		dialog.setVisible(false);
		dialog.dispose();
	}

	class showMessage extends JDialog {

		public showMessage(String message) {
			super(mainMenue, true);
			JTextArea textArea = new JTextArea(message);
			textArea.setEditable(false);
			add(textArea);

			Dimension d = getToolkit().getScreenSize();
			int x = (d.width - this.getWidth()) / 2;
			int y = (d.height - this.getHeight()) / 2;

			setLocation(x, y);

		}

	}

}
