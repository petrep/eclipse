package com.kirshi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpPanel {

	JTextArea helpArea = new JTextArea(29, 51);
	JDialog dialog = new JDialog();
	JPanel helpPanel = new JPanel();

	public HelpPanel() {
		dialog.setSize(600, 530);
		dialog.setPreferredSize(new Dimension(600, 530));
		dialog.setMinimumSize(dialog.getPreferredSize());
		dialog.setMaximumSize(new Dimension(800, 650));
		dialog.setVisible(true);
		dialog.setTitle("Game manual");
		dialog.add(helpPanel);
		helpPanel.setBackground(new Color(21, 144, 189));
		helpArea.setPreferredSize(new Dimension(600, 500));
		helpPanel.add(new JScrollPane(helpArea), new BorderLayout().CENTER);
		InputStream helpfile = Words.class.getResourceAsStream("resources/helpfile.txt");
		BufferedReader helpfileReader = new BufferedReader(new InputStreamReader(helpfile, StandardCharsets.UTF_8));
		String lineReader = null;
		try {
			while ((lineReader = helpfileReader.readLine()) != null) {
				helpArea.append(lineReader + "\n");
			}
		} catch (IOException e) {
		} finally {
			try {
				helpfileReader.close();
			} catch (IOException e) {
			}
		}
		
	}
}
