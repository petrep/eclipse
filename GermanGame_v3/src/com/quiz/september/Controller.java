package com.quiz.september;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Controller extends JFrame {
	
	private JPanel mainPanel;
	private JButton start;
	private JLabel label;

	QuizPanel qp = new QuizPanel();
	
	public Controller () {
		
		super("Game");
		
		label = new JLabel("Click Start to start the game");
		mainPanel = new JPanel();
		start = new JButton("Start");		
		
		mainPanel.add(label, BorderLayout.WEST);
		mainPanel.add(start, BorderLayout.EAST);		
		add(mainPanel, new BorderLayout().CENTER);
		
		// calls the quiz panel with the card layout
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().add(qp.panelCont);
				remove(mainPanel);
				repaint();
				add(qp.panelCont);
				setVisible(true);	
				
			}
		});
		
		
		getContentPane();	
		setVisible(true);
		setSize(500, 600);
		setMinimumSize(new Dimension(300, 200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
}
