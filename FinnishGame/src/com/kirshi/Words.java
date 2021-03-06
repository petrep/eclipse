package com.kirshi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

//http://randomfinnishlesson.blogspot.hu/2014/02/100-very-common-finnish-words.html
//good examples on JTextField http://www.codejava.net/java-se/swing/jtextfield-basic-tutorial-and-examples
// good site for choosing colors http://www.webme.co.uk/web-tools/color-picker.php

public class Words {

	Random random = new Random();
	JFrame frame = new JFrame("Finnish Vocabulary Check");
	JTextArea questionArea = new JTextArea();
	JTextField userAnswer = new JTextField(18);
	JButton check = new JButton("Check");
	JButton showAnswer = new JButton("Show Answer");
	JButton nextQuestion = new JButton("New Question");
	JTextArea result = new JTextArea();
	JPanel panel = new JPanel();
	String randomQuestion;
	int x;
	int points;
	int sum;
	JButton score = new JButton("Score");
	List<String> englishList;
	List<String> finnishList;
	Menu menu = new Menu();
	String englishWords;
	String finnishWords;
	String fileName;
	ArrayList<Integer> alreadyAskedQuestionNumbers = new ArrayList<Integer>();

	public Words() throws FileNotFoundException {

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 350);
		frame.setMinimumSize(new Dimension(500, 300));
		frame.add(panel);
		frame.add(menu.createMenuBar(), new BorderLayout().NORTH);
		englishList = new ArrayList<String>();

		// designing the GUI
		panel.setLayout(new GridBagLayout());
		showAnswer.setBackground(Color.white);
		showAnswer.setEnabled(false);
		nextQuestion.setBackground(Color.white);
		check.setBackground(Color.white);
		showAnswer.setMnemonic(KeyEvent.VK_S); // Alt + S to click showAnswer
		check.setMnemonic(KeyEvent.VK_C); // Alt + C to click Check
		nextQuestion.setMnemonic(KeyEvent.VK_N); // Alt + N to click
													// nextQuestion

		// creates a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.BLUE, 2);
		questionArea.setBorder(border);
		userAnswer.setBorder(border);
		// sets Font

		Font font = new Font("Verdana", Font.PLAIN, 12);
		questionArea.setFont(font);

		questionArea.setEditable(false);
		panel.setBackground(new Color(21, 144, 189));

		GridBagConstraints gc = new GridBagConstraints();

		// first row
		gc.weightx = 1; // these 2 values do not need to be copied below if they
						// don`t change
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy = 0; // the first row begins with 0
		gc.fill = GridBagConstraints.NONE; // this means there will be no
											// filling color
		gc.anchor = GridBagConstraints.CENTER; // position in the center
		gc.insets = new Insets(10, 0, 0, 0);
		panel.add(questionArea, gc);

		// second row
		gc.gridx = 0;
		gc.gridy = 1; // the second row is 1
		gc.insets = new Insets(30, 0, 30, 0); // 30 means distance from bottom
												// to
												// top
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.CENTER;
		panel.add(userAnswer, gc);

		// //////////third row ///////////////////////////////////

		gc.gridx = 0;
		gc.gridy = 2; // the third row is 2

		gc.insets = new Insets(0, 0, 0, 120);
		gc.anchor = GridBagConstraints.CENTER;
		panel.add(check, gc);

		gc.gridx = 0;
		gc.gridy = 2;
		gc.insets = new Insets(0, 88, 0, 0);
		gc.anchor = GridBagConstraints.CENTER;
		panel.add(nextQuestion, gc);

		// fourth row
		gc.gridx = 0;
		gc.gridy = 3; // the fourth row is 3

		gc.insets = new Insets(0, 0, 10, 0);
		gc.anchor = GridBagConstraints.CENTER;
		panel.add(result, gc);

		// fifth row
		gc.gridx = 0;
		gc.gridy = 4;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.NORTH;
		panel.add(showAnswer, gc);

		// sixth
		gc.gridx = 0;
		gc.gridy = 5;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.NORTH;
		panel.add(score, gc);

		// using the setFileListener method from Menu class to receive the name
		// of the selected custom english file
		menu.setFileListener(new FileListener() {
			// overriding the fileLoaded method from the FileListener interface
			@Override
			public void fileLoaded(String fileName) {
				System.out.println(fileName);
				// sets the name of the english custom words
				englishWords = fileName;
				// gets the respective finnish custom file
				finnishWords = fileName.substring(0, fileName.length() - 6)
						+ "fi.txt";
			}
		});

		AskQuestion(); // the game begins with a default question

		nextQuestion.addActionListener(new ActionListener() { // asks a new
																// question from
																// the user

					public void actionPerformed(ActionEvent e) {
						// clears the user output area
						userAnswer.setText("");
						result.setText("");
						showAnswer.setEnabled(false); // disables the showAnswer
						AskQuestion(); // asks the next question
					}
				});

		check.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (userAnswer.getText().trim().equals(finnishList.get(x))) {
					result.setText("Correct");
					// adds a point to the user`s score for each correct answer
					points++;
					result.setBackground(new Color(56, 194, 93)); // the answer
																	// is
																	// correct,
					// sets the background
					// to green
				} else {
					result.setText("False");
					result.setForeground(Color.WHITE);
					result.setBackground(new Color(240, 103, 93)); // sets the
																	// background
																	// to
					// red if the answer is
					// wrong
					// if the user area is empty, the showAnswer button is
					// disabled
					if (userAnswer.getText().equals("")) {
						showAnswer.setEnabled(false);
						System.out.println("empty");
					} else {
						showAnswer.setEnabled(true);
						System.out.println("not empty");
					}
				}

			}
		});

		score.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showScore();
			}
		});
		showAnswer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e1) {
				String correctAnswer = finnishList.get(x);
				result.setText(correctAnswer); // shows the correct answer
				result.setForeground(Color.BLUE);
				result.setBackground(Color.WHITE);
				showAnswer.setEnabled(false);// disables the showAnswer for the
												// next question

			}
		});

	}

	public void AskQuestion() {

		InputStream englishFile = null;
		// System.out.println("englishWords: " + englishWords);
		// if no custom words list is selected, the default list is used
		if (englishWords == null) {
			englishFile = Words.class
					.getResourceAsStream("resources/englishwords.txt");
		} else {
			// when the user clicks on NextQuestion, the custom list is loaded
			try {
				englishFile = new FileInputStream(englishWords);

			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),
						"File Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		// unsupported in java6, ignore for now
		BufferedReader readerEng = new BufferedReader(new InputStreamReader(
				englishFile, StandardCharsets.UTF_8));
		// BufferedReader readerEng = new BufferedReader(new InputStreamReader(
		// englishFile));
		String lineEng = null;
		// empties the list if it already has elements
		// if this function is not used, the program adds the new list to the
		// old list and ArrayOutOfBounds is thrown
		if (englishList != null) {
			englishList.clear();
		}
		try {
			while ((lineEng = readerEng.readLine()) != null) {
				englishList.add(lineEng);
			}
		} catch (IOException e) {
		} finally {
			try {
				readerEng.close();
			} catch (IOException e) {
			}
		}

		// InputStream finnishFile =
		// Words.class.getResourceAsStream("resources/finnishwords.txt");
		InputStream finnishFile = null;
		if (finnishWords == null) {
			finnishFile = Words.class
					.getResourceAsStream("resources/finnishwords.txt");
		} else {
			try {
				finnishFile = new FileInputStream(finnishWords);

			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),
						"File Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		BufferedReader readerFin = new BufferedReader(new InputStreamReader(
				finnishFile, StandardCharsets.UTF_8));
		// BufferedReader readerFin = new BufferedReader(new InputStreamReader(
		// finnishFile));

		finnishList = new ArrayList<String>();
		String lineFin = null;
		if (finnishList != null) {
			finnishList.clear();
		}
		try {
			while ((lineFin = readerFin.readLine()) != null) {
				finnishList.add(lineFin);
			}
		} catch (IOException e) {
		} finally {
			try {
				readerFin.close();
			} catch (IOException e) {
			}
		}
		System.out.println("is this a newly loaded game: "
				+ menu.newGameStarted);
		if (menu.newGameStarted == true) {
			// englishList.clear();
			// finnishList.clear();
			alreadyAskedQuestionNumbers.clear();
			menu.newGameStarted = false;
		}
		System.out.println("englishlist size: " + englishList);
		System.out.println("alreadyAskedQuestionNumbers size: "
				+ alreadyAskedQuestionNumbers);
		x = random.nextInt(this.englishList.size());
		if ((alreadyAskedQuestionNumbers.size()) == englishList.size()) {
			System.out.println("all were asked");
			endGame();
		}
		while (alreadyAskedQuestionNumbers.contains(x)) {
			System.out.println("this question was already asked!");
			x = random.nextInt(this.englishList.size());
		}

		alreadyAskedQuestionNumbers.add(x);
		randomQuestion = this.englishList.get(x);
		questionArea.setText("What is '" + randomQuestion + "' in Finnish?");
		userAnswer.setEditable(true);
		userAnswer.requestFocusInWindow(); // sets the cursor to appear in the
											// text field
		userAnswer.setHorizontalAlignment(JTextField.CENTER); // aligns the
																// typed text
		// into the

		// center
		result.setEditable(false);
	}

	// sums up the point the user got for each correct answer
	public void showScore() {
		for (int y = 0; y <= points; y++) {
			sum = +y;
		}
		 JOptionPane.showMessageDialog(null, "Your score is: " + sum
		 + " out of " + finnishList.size());
		
	}
	// Offers 3 options to the player: start a new quiz, load a custom dictionary or quit the game
	public void endGame() {
		for (int y = 0; y <= points; y++) {
			sum = +y;
		}
		JOptionPane.showMessageDialog(null, "Your score is: " + sum
				+ " out of " + finnishList.size());
		alreadyAskedQuestionNumbers.clear();
		String[] customButtons = {"Try Again", "Load another Dictionary", "Quit Game"};

		int action = JOptionPane.showOptionDialog(null, "Do you want to retry the the game or load a new custom dictionary?",
				"Game finished", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, customButtons, null);
//		if (action == JOptionPane.YES_OPTION) {
//			JOptionPane.showMessageDialog(null, "try");
//		}
		if (action == JOptionPane.NO_OPTION) {
			menu.loadGame();
			
		}
		if (action == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
	}

}
