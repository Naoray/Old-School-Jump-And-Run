package mainGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class KeyModel extends JDialog {
	// Create columns names
	String columnNames[] = { "Aktion", "Taste" };

	String dataValues[][] = { { "Nach Rechts laufen", "PfeilRechts" }, { "Nach Links laufen", "PfeilLinks" },
			{ "Springen", "Leertaste" }, { "Ducken", "PfeilUnten" } };

	private JTable table;
	private DefaultTableModel model;

	private int length = 600;
	private int height = 200;
	Font f = new Font("Arial", Font.BOLD, 24);

	public KeyModel() {
		setTitle("Einstellungen");
		setMinimumSize(new Dimension(length, height));
		setSize(length, height);
		calculateLocation(length, height);
		setLayout(new BorderLayout());

		// Model und Tabelle setzen
		model = new DefaultTableModel(dataValues, columnNames);
		table = new JTable(model) {
			public boolean isCellEditable(int x, int y) {
				switch (y) {
				case 0:
					return false;
				}
				return false;
			}
		};
		// table = new JTable(dataValues, columnNames);
		table.setFont(f);
		table.setRowHeight(24);
		// TableHeader auf die gleiche Font setzen
		table.getTableHeader().setFont(f);
		add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}

	// Setzt das Fenster immer in die Mitte des Bildschirms
	private void calculateLocation(int width, int height) {
		Dimension d = getToolkit().getScreenSize();
		int x = (d.width - width) / 2;
		int y = (d.height - height) / 2;

		setLocation(x, y);
	}

}
