package com.quiz.september;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class QuizPanel extends JPanel implements ActionListener {
	// declaring all panels2
	JPanel panelCont = new JPanel();	
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	JPanel panel4 = new JPanel();
	JPanel panel5 = new JPanel();
	JPanel panel6 = new JPanel();
	JPanel panel7 = new JPanel();
	JPanel panel8 = new JPanel();
	JPanel panel9 = new JPanel();
	JPanel panel10 = new JPanel();
	JPanel panel11 = new JPanel();
	JPanel panel12 = new JPanel();
	JPanel panel13 = new JPanel();
	JPanel panel14 = new JPanel();
	JPanel panel15 = new JPanel();
	JPanel panel16 = new JPanel();
	JPanel panel17 = new JPanel();
	JPanel panel18 = new JPanel();
	JPanel panel19 = new JPanel();
	JPanel panel20 = new JPanel();
	JPanel panel21 = new JPanel();
	
	// declaring all Check buttons
	JButton check1 = new JButton("Check");
	JButton check2 = new JButton("Check");
	JButton check3 = new JButton("Check");
	JButton check4 = new JButton("Check");
	JButton check5 = new JButton("Check");
	JButton check6 = new JButton("Check");
	JButton back = new JButton("Back");
	
	// declaring all text areas
	JTextArea textArea = new JTextArea();
	
	CardLayout cards = new CardLayout();
	
	public QuizPanel(){
		panelCont.setLayout(cards);
		
		textArea.append("this is the first question in the first panel");
		panel1.setLayout(new GridBagLayout());
		
		// adding the buttons to the panels
		panel1.add(textArea);
		panel1.add(check1);
		panel2.add(check2);
		panel3.add(check3);
		panel4.add(check4);
		panel5.add(check5);
		panel6.add(check6);
		panel6.add(back);
		
		//setting  a different background for the panels for a better distinguishing
		panel1.setBackground(Color.BLUE);
		panel2.setBackground(Color.GREEN);
		panel3.setBackground(Color.CYAN);
		panel4.setBackground(Color.DARK_GRAY);
		panel5.setBackground(Color.MAGENTA);
		panel6.setBackground(Color.RED);
		
		// adding all the panels to the main controller panel- panelCont
		panelCont.add(panel1, "1");
		panelCont.add(panel2, "2");
		panelCont.add(panel3, "3");
		panelCont.add(panel4, "4");
		panelCont.add(panel5, "5");
		panelCont.add(panel6, "6");
		panelCont.add(panel7, "7");
		panelCont.add(panel8, "8");
		panelCont.add(panel9, "9");
		panelCont.add(panel10, "10");
		panelCont.add(panel11, "11");
		
		
		//shows the first main panel when the method is invoked
		cards.show(panelCont, "0");
		
		//adds action listener to all check buttons
		check1.addActionListener(this);
		check2.addActionListener(this); 
		check3.addActionListener(this);
		check4.addActionListener(this);
		check5.addActionListener(this);
		back.addActionListener(this);
	}
	// switching to the next panel
	@Override
	public void actionPerformed(ActionEvent e) {
		//cards.show(panelCont, "4");
		// goes back to the first panel if the back button is clicked
		JButton test = (JButton) e.getSource();
		if (test.equals(back)){
			cards.last(panelCont);
			System.out.println("test");
		}
		cards.next(panelCont);
	}

}
