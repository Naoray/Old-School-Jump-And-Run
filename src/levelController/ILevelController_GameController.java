package levelController;

import java.util.ArrayList;

public interface ILevelController_GameController {

	// Liefert das Ergebnis(Strings) der ausgelesenen Level-Text-Datei
	ArrayList<String> getLevelComponents();

	// Liefert den Dateien-Namen für den zu verwendenden Background
	String getLevelBackground();

	// Liefert den größten/längsten Wert der Liste mit den LevelComponenten
	// zurück
	int getWidth();

	// Liefert die Anzahl Zeilen der LevelComponenten zurück
	int getHeight();

	boolean loadData(int levelnummer);
}
