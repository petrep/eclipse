package com.kirshi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.table.DefaultTableModel;

// about table editing https://tips4java.wordpress.com/2008/12/12/table-stop-editing/
public class Upload {
	JDialog dialog = new JDialog();
	JPanel u_panel = new JPanel();
	JButton addRow = new JButton("Add Row");
	String[] columnNames = { "English", "Finnish" }; // table headers
	String[][] data = {};// table data
	JTable table = new JTable(new DefaultTableModel(data, columnNames));
	JButton save = new JButton("Save");
	DefaultTableModel model;

	public Upload() {

		dialog.setSize(500, 600);
		dialog.setMinimumSize(new Dimension(500, 600));
		dialog.setPreferredSize(new Dimension(500, 600));
		dialog.setVisible(true);
		dialog.setTitle("Upload new words into the database");
		dialog.add(u_panel);
		// u_panel.setPreferredSize(new Dimension(500, 600));

		table.setRowHeight(40);
		table.setCellSelectionEnabled(true);
		table.setBackground(Color.WHITE);
		save.setVisible(false); // at first the Save button is not visible
		u_panel.add(addRow);
		u_panel.add(save);
		u_panel.add(new JScrollPane(table)); // displays the headers of the
												// table
		u_panel.setBackground(new Color(21, 144, 189));
		saveFile();
		enter();

		addRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// add row
				save.setVisible(true);// if the addRow button is clicked, Save
										// becomes visible
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
				// checks if all fields are filled up. if an empty field is
				// found, the saving process can not be completed
				if (validateCells()) {

					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify a folder where the two files will be saved");
					fileChooser.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
					fileChooser.setAcceptAllFileFilterUsed(false);
					// String savingPath = null;
					File savingFileName = null;

					if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

						System.out.println("getSelectedFile() : " + fileChooser.getSelectedFile());
						// savingPath = fileChooser.getSelectedFile().;
						savingFileName = fileChooser.getSelectedFile();
						System.out.println("!!" + savingFileName + savingFileName.getAbsolutePath());
					}

					PrintWriter writer = null;
					// Saving the English words
					try {
						// writer = new PrintWriter(savingPath + File.separator
						// + "english1.txt", "UTF-8");
						writer = new PrintWriter(savingFileName + "_en" + ".txt", "UTF-8");
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(fileChooser, "Unable to save file " + e1.getMessage(),
								"Save Dialog", JOptionPane.ERROR_MESSAGE);

					} catch (UnsupportedEncodingException e1) {
						JOptionPane.showMessageDialog(fileChooser,
								"Unable to save file because the text contains unsupported characters "
										+ e1.getMessage(),
								"Save Dialog", JOptionPane.ERROR_MESSAGE);

					}
					if (writer != null) { // checks if the file has been created
						System.out.println("rowcount finnish: " + table.getRowCount());
						for (int englishRowCount = 0; englishRowCount <= table.getRowCount() - 1; englishRowCount++) {
							model.fireTableDataChanged();
							model.fireTableRowsInserted(0, table.getRowCount());
							Vector<?> tableVector = model.getDataVector();
							Object currentEnglishWord = ((Vector<?>) tableVector.elementAt(englishRowCount))
									.elementAt(0);

							table.repaint();
							writer.println(currentEnglishWord);
						}
						writer.close();
					}

					// Saving the Finnish words
					try {
						// writer = new PrintWriter(savingPath + File.separator
						// + "finnish1.txt", "UTF-8");
						writer = new PrintWriter(savingFileName + "_fi" + ".txt", "UTF-8");
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(fileChooser, "Unable to save file " + e1.getMessage(),
								"Save Dialog", JOptionPane.ERROR_MESSAGE);

					} catch (UnsupportedEncodingException e1) {
						JOptionPane.showMessageDialog(fileChooser,
								"Unable to save file because the text contains unsupported characters "
										+ e1.getMessage(),
								"Save Dialog", JOptionPane.ERROR_MESSAGE);

					}
					if (writer != null) { // checks if the file has been created
						for (int finnishRowCount = 0; finnishRowCount <= table.getRowCount() - 1; finnishRowCount++) {

							Vector tableVector = model.getDataVector();
							System.out.println("vectordata: " + model.getDataVector().elementAt(finnishRowCount));
							Object currentFinnishWord = ((Vector) tableVector.elementAt(finnishRowCount)).elementAt(1);
							writer.println(currentFinnishWord);
							
						}
						writer.close();

					}
				} // display a warning pop up if a field is empty
				else {
					JOptionPane.showMessageDialog(null,
							"One or more fields are empty. Please make sure to fill up all fields", "Cell validation",
							JOptionPane.WARNING_MESSAGE);
				}

			}

		});

	}

	public Object getValueAt(int row, int col) {
		model.fireTableDataChanged();// gets the newest data from the table
		return data[row][col];
	}

	// configuring the Enter key
	public void enter() {
		table.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					model = (DefaultTableModel) table.getModel();
					String[] newCol = (new String[] { "", "" });
					model.addRow(newCol);
					// ensures all data is saved to the file
					table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
					table.repaint();

				}
			}
		});
	}

	// validates the cells. no empty fields are allowed
	public boolean validateCells() {
		if (table.getCellEditor() != null) {
			table.getCellEditor().stopCellEditing();
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				String om = table.getValueAt(i, j).toString();
				if (om.trim().length() == 0) {
					return false;
				}
			}
		}
		return true;
	}
}
