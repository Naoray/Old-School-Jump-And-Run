package mainGUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainMenueView extends JFrame {
	JPanel ges;
	MainMenuePanel menu;
	IMainGUI_GameController gameView;

	public MainMenueView() {

		setTitle("Old-School-Jump-And-Run-Game");
		setSize(400, 550);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		ges = new JPanel();
		menu = new MainMenuePanel();
		ges.setLayout(new GridBagLayout());
		ges.setBackground(new Color(51, 153, 255));

		ges.add(new MainMenuePanel(), new GridBagConstraints());

		add(ges, BorderLayout.CENTER);

		setVisible(true);

	}

	public void initMainMenue() {
		setSize(400, 550);
		ges.setVisible(true);

		remove((Component) gameView);
		gameView = null;
	}

	// Startet das Spiel/Erzeugt die zentrale Steuerklasse GameController
	private void spielStarten() {
		// TODO Auto-generated method stub
		gameView = new GameView(this);
		ges.setVisible(false);
		// remove(ges);
		drawGameViewPanel(gameView, 800, 480);
		// WIRD NICHT BENÖTIGT!!!
		// gameView.setGameController(gameController);
		// gameView.start();
	}

	// Ändert die Fenstergröße für das Spiel und setzt das GameView-Panel
	// darauf.
	// Die Casts sind notwendig, da die Methoden (add(), requestFocusInWindow())
	// nicht mit dem Interface kompatibel sind
	public void drawGameViewPanel(IMainGUI_GameController gameView, int width, int height) {
		setWindowSize(width, height);
		add((Component) gameView);
		((JComponent) gameView).requestFocusInWindow();
	}

	// Setzt die Fenstergröße
	private void setWindowSize(int width, int height) {
		setSize(width, height);
		calculateLocation(width, height);
	}

	// Setzt das Fenster immer in die Mitte des Bildschirms
	private void calculateLocation(int width, int height) {
		Dimension d = getToolkit().getScreenSize();
		int x = (d.width - width) / 2;
		int y = (d.height - height) / 2;

		setLocation(x, y);
	}

	public class MainMenuePanel extends JPanel implements ActionListener {

		JButton spielStart, einstellungen, credits, beenden;

		public MainMenuePanel() {

			spielStart = new MenueButton("Spiel starten");
			spielStart.addActionListener(this);
			spielStart.setFocusPainted(false);
			einstellungen = new MenueButton("Einstellungen");
			einstellungen.addActionListener(this);
			einstellungen.setFocusPainted(false);
			credits = new MenueButton("Credits");
			credits.addActionListener(this);
			credits.setFocusPainted(false);
			beenden = new MenueButton("Beenden");
			beenden.addActionListener(this);
			beenden.setFocusPainted(false);
			add(spielStart);
			add(einstellungen);
			add(credits);
			add(beenden);
			setBackground(new Color(0, 100, 200));

			setPreferredSize(new Dimension(200, 200));
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getActionCommand()) {

			case "Spiel starten":
				spielStarten();
				break;

			case "Einstellungen":
				new KeyModel();
				break;

			case "Credits":
				new Credits();
				break;

			case "Beenden":
				System.exit(0);
				break;

			default:

			}
		}
	}

	public class MenueButton extends JButton {

		public MenueButton(String name) {

			super(name);
			setPreferredSize(new Dimension(200, 40));
			setAlignmentY(Component.CENTER_ALIGNMENT);
			Font f = new Font("Arial", Font.BOLD, 24);
			setFont(f);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 100, 200));
			g2.setStroke(new BasicStroke(7));
			g2.drawRect(0, 0, getWidth(), getHeight());

		}
	}

	public class Credits extends JDialog {
		private int length = 600;
		private int height = 230;
		Font f = new Font("Arial", Font.BOLD, 24);
		JTextArea textArea = new JTextArea();

		public Credits() {
			super();
			setTitle("Credits");
			setMinimumSize(new Dimension(length, height));
			setSize(length, height);
			calculateLocation(length, height);
			setLayout(new BorderLayout());

			add(textArea);
			textArea.setFont(f);
			textArea.setText(
					"Dieses Spiel wird Ihnen präsentiert von: \n\nKatja Kubitta,\nKrishan König,\nJulian Schmidt, \nNico Weber");
			textArea.setEditable(false);
			textArea.setBackground(Color.lightGray);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setModal(true);
			setResizable(false);
			setVisible(true);
		}

		private void calculateLocation(int width, int height) {
			Dimension d = getToolkit().getScreenSize();
			int x = (d.width - width) / 2;
			int y = (d.height - height) / 2;

			setLocation(x, y);
		}
	}
}
