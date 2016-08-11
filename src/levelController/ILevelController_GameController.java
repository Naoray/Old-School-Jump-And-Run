package levelController;

import java.util.ArrayList;

public interface ILevelController_GameController {

	// Liefert das Ergebnis(Strings) der ausgelesenen Level-Text-Datei
	ArrayList<String> getLevelComponents();

	// Liefert den Dateien-Namen f�r den zu verwendenden Background
	String getLevelBackground();

	// Liefert den gr��ten/l�ngsten Wert der Liste mit den LevelComponenten
	// zur�ck
	int getWidth();

	// Liefert die Anzahl Zeilen der LevelComponenten zur�ck
	int getHeight();

	boolean loadData(int levelnummer);
}
