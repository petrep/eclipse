package com.kirshi;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HelpPanel {

	JTextArea helpArea = new JTextArea();
	JDialog dialog = new JDialog();
	JPanel helpPanel = new JPanel();
	
	public HelpPanel(){
		dialog.setSize(500, 600);
		dialog.setVisible(true);
		dialog.setTitle("Game manual");
		dialog.add(helpPanel);
		helpArea.setText("instructions will be here");
		helpPanel.add(helpArea);
	}
}
