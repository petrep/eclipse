package com.kirshi;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Menu extends JFrame {
	JFileChooser fileChooser = new JFileChooser();
	private FileListener fileNameLister;
	
	public Menu() {

	}

	JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);

		JMenu fileMenu = new JMenu("File");
		JMenuItem createFile = new JMenuItem("Create a new file");
		final JMenuItem loadFile = new JMenuItem("Load from file");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem helpItem = new JMenuItem("Help");
		JMenuItem uploadItem = new JMenuItem("Upload");
		

		fileMenu.add(createFile);
		fileMenu.add(loadFile);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK)); // displays
																								// the
																								// shortcut

		JMenu toolsMenu = new JMenu("Tools");

		toolsMenu.add(uploadItem);

		toolsMenu.setMnemonic(KeyEvent.VK_T);
		uploadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));

		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(helpItem);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
		menuBar.add(fileMenu);
		menuBar.add(toolsMenu);
		menuBar.add(helpMenu);

		loadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//					System.out.println(fileChooser.getSelectedFile());
					//loadFile();
				//}
				Object source = e.getSource();
				if (source == loadFile){
				if (fileNameLister != null){
					fileNameLister.fileLoaded("C://test//englishwords.txt");
					System.out.println("hello");
				}
			}
			}
			
		});

		createFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println(fileChooser.getSelectedFile());
					createFile();
				}
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int action = JOptionPane.showConfirmDialog(null, "Do you really want to exit the application?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

				if (action == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});

		uploadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Upload();

			}
		});
		helpItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new HelpPanel();
			}
		});
		return menuBar;

	}

	// creates a file in a chosen format and saves it to the selected folder
	public void createFile() {
		File selectedFolder = fileChooser.getSelectedFile(); // this is the
																// selected
																// destination
		try {
			FileWriter writer = new FileWriter(selectedFolder); // saves the
																// file to the
																// selected
																// folder
			writer.close(); // the writer has to be closed
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			// displays a pop up with an error message if the file can not be
			// saved
			JOptionPane.showMessageDialog(fileChooser, "Unable to save file " + e1.getMessage(), "Save Dialog",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setFileListener(FileListener listener) {
		
		this.fileNameLister = listener;
	}

//		File chosenFile = fileChooser.getSelectedFile();
//
//		try {
//			Reader reader = new FileReader(chosenFile);
//			reader.close(); // closing the reader
//			String[] cmd = new String[5];
//			cmd[0] = "cmd.exe";
//			cmd[1] = "/C";
//			cmd[2] = "rundll32";
//			cmd[3] = "url.dll,FileProtocolHandler";
//			cmd[4] = chosenFile.toString();
//
//			Runtime.getRuntime().exec(cmd); // opens the file in the application
//											// the file is to be open from, e.g.
//											// xls-excel
//
//		} catch (FileNotFoundException e) {
//			System.out.println("The file is not found " + e.getMessage());
//		} catch (IOException eio) {
//			System.out.println("An error has occured: " + eio.getMessage());
//		}
	//}
}
