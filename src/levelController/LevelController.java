package levelController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LevelController implements ILevelController_GameController {
	private ArrayList<String> levelComponents = new ArrayList<String>();
	private String rootPath = "src/levelModel/map"; // Anpassen!!!!
	private String background;
	// private String tmp_Components;
	private int width, height;

	// public static void main(String[] args) {
	// LevelController x = new LevelController();
	// x.loadData("src/gameModel/images/map1.txt");
	// }

	public LevelController() {

	}

	// Liefert das Ergebnis(Strings) der ausgelesenen Level-Text-Datei
	@Override
	public ArrayList<String> getLevelComponents() {
		// TODO Auto-generated method stub
		return levelComponents;
	}

	// Liefert den Namen der BackgroundDatei aus der Level-Text-Datei
	@Override
	public String getLevelBackground() {
		// TODO Auto-generated method stub
		// loadData(rootPath + levelnummer + ".txt");
		return background;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	// Zuerst ausführen und dann die benötigten Elemente mit get... holen
	public boolean loadData(int levelnummer) {
		String datName = rootPath + levelnummer + ".txt";
		File file = new File(datName);

		if (!file.canRead() || !file.isFile())
			// Offen, was passieren soll, wenn die Daten nicht lesbar ist.
			return false;

		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(datName));
			String row = null;
			levelComponents.clear();
			while ((row = in.readLine()) != null) {
				// Die letzte Zeile, die mit "background=" anfängt, enthält den
				// Namen des Backgrounds für das Level
				if (row.toLowerCase().startsWith("background=")) {
					background = row.toLowerCase().replace("background=", "");
					// Zeilen die mit "!" anfangen sind Kommentar und werden
					// nicht benötigt
				} else if (!row.startsWith("!")) {
					levelComponents.add(row);
					// System.out.println(row);
					width = Math.max(width, row.length());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
		height = levelComponents.size();
		// stringToList(tmp_Components);
		return true;
	}

	// private void stringToList(String s) {
	// char[] chars = tmp_Components.toCharArray();
	// for (int i = 0; i < chars.length; i++) {
	// levelComponents.add("" + chars[i]);
	// // System.out.print(levelComponents.get(i));
	// }
	// }

}
