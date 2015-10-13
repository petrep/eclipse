import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

// about table editing https://tips4java.wordpress.com/2008/12/12/table-stop-editing/
public class Upload {
	JDialog dialog = new JDialog();
	JPanel u_panel = new JPanel();
	JButton addRow = new JButton("Add Row");
	String[] columnNames = { "English", "Finnish" };
	// String[][] data = { { "1r - 1c", "1r - 2c" }, { "2r - 1c", "2r - 2c" },
	// { "3r - 1c", "3r - 2c" } };
	String[][] data = {};
	JTable table = new JTable(new DefaultTableModel(data, columnNames));
	JButton save = new JButton("Save");
	DefaultTableModel model;

	public Upload() {

		dialog.setSize(600, 600);
		dialog.setVisible(true);
		dialog.setTitle("Upload new words into the database");
		dialog.add(u_panel);

		table.setRowHeight(40);
		table.setCellSelectionEnabled(true);
		u_panel.add(addRow);
		u_panel.add(save);
		u_panel.add(new JScrollPane(table)); // displays the headers of the
												// table
		saveFile();
		enter();

		addRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// add row
				model = (DefaultTableModel) table.getModel();
				model.addRow(new String[] { "", "" });
				table.repaint();
				// this is good for Strings only. This way the last row the user
				// edited is saved and recorded in the file
				table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			}
		});

	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public void saveFile() {
		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// model.fireTableDataChanged();
				// table.repaint();

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a folder where the two files will be saved");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);
				File savingPath = null;

				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					System.out.println("getSelectedFile() : " + fileChooser.getSelectedFile());
					savingPath = fileChooser.getSelectedFile();
				}

				PrintWriter writer = null;
				// Saving the English words
				try {
					writer = new PrintWriter(savingPath + File.separator + "english1.txt", "UTF-8");
					System.out.println("will save to: " + savingPath + File.separator + "the-file-name.txt");
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(fileChooser, "Unable to save file " + e1.getMessage(), "Save Dialog",
							JOptionPane.ERROR_MESSAGE);

				} catch (UnsupportedEncodingException e1) {
					JOptionPane.showMessageDialog(fileChooser,
							"Unable to save file because the text contains unsupported characters " + e1.getMessage(),
							"Save Dialog", JOptionPane.ERROR_MESSAGE);

				}
				if (writer != null) { // checks if the file has been created
					System.out.println("rowcount finnish: " + table.getRowCount());
					// for (Object x : data) {
					// fw.write(x.toString());
					for (int englishRowCount = 0; englishRowCount <= table.getRowCount() - 1; englishRowCount++) {
						model.fireTableDataChanged();
						model.fireTableRowsInserted(0, table.getRowCount());
						Vector<?> tableVector = model.getDataVector();
						System.out.println("vectordata: " + model.getDataVector().elementAt(englishRowCount));

						Object currentEnglishWord = ((Vector<?>) tableVector.elementAt(englishRowCount)).elementAt(0);

						table.repaint();
						// newArray = Arrays.copyOf(data, 4);
						// newArray[3] = new String[]{"Column 1", "Column 2"};
						System.out.println("rowcount: " + englishRowCount);
						// System.out.println(getValueAt(englishRowCount, 0));
						writer.println(currentEnglishWord);
					}

					// }
					writer.close();
				}

				// Saving the Finnish words
				try {
					writer = new PrintWriter(savingPath + File.separator + "finnish1.txt", "UTF-8");
					System.out.println("will save to: " + savingPath + File.separator + "the-file-name.txt");
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(fileChooser, "Unable to save file " + e1.getMessage(), "Save Dialog",
							JOptionPane.ERROR_MESSAGE);

				} catch (UnsupportedEncodingException e1) {
					JOptionPane.showMessageDialog(fileChooser,
							"Unable to save file because the text contains unsupported characters " + e1.getMessage(),
							"Save Dialog", JOptionPane.ERROR_MESSAGE);

				}
				if (writer != null) { // checks if the file has been created

					for (int finnishRowCount = 0; finnishRowCount <= table.getRowCount() - 1; finnishRowCount++) {

						Vector tableVector = model.getDataVector();
						System.out.println("vectordata: " + model.getDataVector().elementAt(finnishRowCount));
						Object currentFinnishWord = ((Vector) tableVector.elementAt(finnishRowCount)).elementAt(1);
						if (currentFinnishWord.toString().isEmpty()){
							System.out.println("The current cell is empty");
							
						}
						// table.repaint();
						// model.fireTableDataChanged();
						// model.fireTableRowsInserted(0,table.getRowCount());
						System.out.println("rowcount: " + finnishRowCount);
						writer.println(currentFinnishWord);
					}
					writer.close();
				}

			}

		});
	}

	public Object getValueAt(int row, int col) {
		model.fireTableDataChanged();
		// model.fireTableRowsInserted(1,table.getRowCount()+1);
		// table.repaint();
		System.out.println("rowcount here: " + table.getRowCount());

		return data[row][col];
	}

	public void enter() {
		table.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					model = (DefaultTableModel) table.getModel();
					String[] newCol = (new String[] { "", "" });
					model.addRow(newCol);
					table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
					table.repaint();

				}
			}
		});
	}
	
	// This feature needs to be developed -> ftp connection:
	// URL url = new URL("ftp://user:pass@myftp.abc.com/myFile.txt;type=i");
	// URLConnection urlc = url.openConnection();
	// OutputStream os = urlc.getOutputStream(); // To upload
	// OutputStream buffer = new BufferedOutputStream(os);
	// ObjectOutput output = new ObjectOutputStream(buffer);
	// output.writeObject(myObject);
	// buffer.close();
	// os.close();
	// output.close();

}
