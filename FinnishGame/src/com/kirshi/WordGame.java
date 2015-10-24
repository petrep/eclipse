package com.kirshi;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;



public class WordGame {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new Words();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
	}

}
