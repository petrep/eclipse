package com.quiz.september;

import javax.swing.SwingUtilities;

public class MainClass {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				new Controller();

			}

		});

	}

}
