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
	private FileListener fileNameListener;
	
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
				// shows the open dialog
				int showOpenDialog = 0;
				//shows which file the user selected from the Open dialog
				File selectedFile = fileChooser.getSelectedFile();
				fileChooser.setDialogTitle("Select the english file you want to load");
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println(fileChooser.getSelectedFile());
					// prevents the user from choosing the wrong file. It has to be the english file
					 while(showOpenDialog == JFileChooser.APPROVE_OPTION &&
						       !fileChooser.getSelectedFile().getName().endsWith("en.txt")){
						      JOptionPane.showMessageDialog(null,
						       "The file " + fileChooser.getSelectedFile()
						       + " should end with en.txt", "Open Error",
						       JOptionPane.ERROR_MESSAGE);
						      // if the user chooses a wrong file, the Open dialog appears again
						      showOpenDialog = fileChooser.showOpenDialog(null);
						      //sets the selected file value to null until the user chooses a correct file
						      selectedFile = null;
						    }
				}
				// creates an event if the loadFile menuItem is clicked
				Object source = e.getSource();
				if (source == loadFile){
				//if (fileNameListener != null){ not needed for now
					// saves the chosen file name as a string. it is used in Words class as a name for the english custom file
					if (selectedFile != null){
					fileNameListener.fileLoaded(fileChooser.getSelectedFile().toString());
					
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
	// listening to the selected file name and passing the information to the Words class 
	public void setFileListener(FileListener listener) {
		
		this.fileNameListener = listener;
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
