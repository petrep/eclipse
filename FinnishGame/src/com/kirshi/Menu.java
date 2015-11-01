package com.kirshi;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	JFileChooser fileChooser = new JFileChooser();
	private FileListener fileNameListener;
	boolean newGameStarted;
	JMenuItem loadFile = null;

	public Menu() {

	}

	JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);

		JMenu fileMenu = new JMenu("File");
		loadFile = new JMenuItem("Load Custom Words");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem helpItem = new JMenuItem("Instructions");
		JMenuItem uploadItem = new JMenuItem("Create Custom Dictionary");

		fileMenu.add(loadFile);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.ALT_MASK)); // displays
										// the
										// shortcut

		JMenu toolsMenu = new JMenu("Tools");

		toolsMenu.add(uploadItem);

		toolsMenu.setMnemonic(KeyEvent.VK_T);
		uploadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.ALT_MASK));
		loadFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.ALT_MASK));

		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(helpItem);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				ActionEvent.ALT_MASK));
		menuBar.add(fileMenu);
		menuBar.add(toolsMenu);
		menuBar.add(helpMenu);

		fileChooser.setDialogTitle("Select the english file you want to load");
		loadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// creates an event if the loadFile menuItem is clicked
				
				Object source = e.getSource();
				if (source == loadFile) {
					loadGame();
				}
			}
		});
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int action = JOptionPane.showConfirmDialog(null,
						"Do you really want to exit the application?",
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

	// listening to the selected file name and passing the information to the
	// Words class
	public void setFileListener(FileListener listener) {

		this.fileNameListener = listener;
	}

		// Loads 2 txt files with the dictionaries
		public  void loadGame() {
			System.out.println("test");
			newGameStarted = true;
			
			// shows the open dialog
			int showOpenDialog = fileChooser.showOpenDialog(Menu.this);
			// shows which file the user selected from the Open dialog
			File selectedFile = fileChooser.getSelectedFile();
			// prevents the user from choosing the wrong file. It has to
			// be the english file
			while (showOpenDialog == JFileChooser.APPROVE_OPTION
					&& !fileChooser.getSelectedFile().getName()
							.endsWith("en.txt")) {

				JOptionPane.showMessageDialog(null, "The file "
						+ fileChooser.getSelectedFile()
						+ " should end with en.txt", "Open Error",
						JOptionPane.ERROR_MESSAGE);
				// if the user chooses a wrong file, the Open dialog
				// appears again
				showOpenDialog = fileChooser.showOpenDialog(Menu.this);
				// exits the while loop once a correct file is selected
				if (fileChooser.getSelectedFile().getName()
						.endsWith("en.txt")) {
					break;
				}
				// sets the selected file value to null until the user
				// chooses a correct file
				selectedFile = null;
			}

			
				// if (fileNameListener != null){ not needed for now
				// saves the chosen file name as a string. it is used in
				// Words class as a name for the english custom file
				if (selectedFile != null) {
					fileNameListener.fileLoaded(fileChooser
							.getSelectedFile().toString());
				}
			
		

			
		}
	
}
