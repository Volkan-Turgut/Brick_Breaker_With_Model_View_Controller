package ui.buttons;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ui.buttons.PauseButton.ButtonHandler;

public class AsteroidButtons extends JPanel{
	private JButton simpleButton;
	private JTextArea simple;
	private JButton firmButton;
	private JTextArea firm;
	private JButton explosiveButton;
	private JButton giftButton;
	private JTextArea gift;
	private JTextArea explosive;
	//private boolean[] flag = {true,true,true};
	
	private HashMap<String,Integer> asteroidNumbers = new HashMap<String,Integer>(); 
	
	public AsteroidButtons() {
		simpleButton = new JButton("Simple-asteroids->");
		firmButton = new JButton("Firm-asteroids->");
		explosiveButton = new JButton("Explosive-asteroids->");
		giftButton= new JButton("Gift-Button->");
		simpleButton.setFocusable(false);
		firmButton.setFocusable(false);
		explosiveButton.setFocusable(false);
		giftButton.setFocusable(false);
		this.setFocusable(false);
		
		simple = new JTextArea(1,1);
		asteroidNumbers.put("simple",75);
		simple.setEnabled(false);
		simple.setText("75");
		simple.setFont(simple.getFont().deriveFont(24f));
		
		firm = new JTextArea(1,1);
		asteroidNumbers.put("firm",10);
		firm.setEnabled(false);
		firm.setText("10");
		firm.setFont(firm.getFont().deriveFont(24f));
		
		explosive = new JTextArea(1,1);
		asteroidNumbers.put("explosive",5);
		explosive.setEnabled(false);
		explosive.setText("5");
		explosive.setFont(explosive.getFont().deriveFont(24f));
		
		gift = new JTextArea(1,1);
		asteroidNumbers.put("gift",10);
		gift.setEnabled(false);
		gift.setText("10");
		gift.setFont(gift.getFont().deriveFont(24f));

		setLayout(new GridLayout(3,2));
		this.add(simpleButton);
		this.add(simple);
		this.add(firmButton);
		this.add(firm);
		this.add(explosiveButton);
		this.add(explosive);
		this.add(giftButton);
		this.add(gift);
		
		//ButtonHandler handler = new ButtonHandler();
		//simpleButton.addActionListener(handler);
		//firmButton.addActionListener(handler);
		//explosiveButton.addActionListener(handler);
		//giftButton.addActionListener(handler);
		
	}
	
	public static boolean isCorrectInput(String s) {
		//@requires: any type of string
		//@modifies:
		//@effects: if the given string can be converted to an integer without NumberFormatException it returns true else false
		try{
			int n = Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException ex) {
			return false;
			
		}
		
	}
	
	public ArrayList<JTextArea> getTextAreas() {
		ArrayList<JTextArea> buttonList = new ArrayList<>();
		buttonList.add(simple);
		buttonList.add(firm);
		buttonList.add(explosive);
		buttonList.add(gift);
		return buttonList;
	}
	
	public ArrayList<JButton> getButtons() {
		ArrayList<JButton> buttonList = new ArrayList<>();
		buttonList.add(simpleButton);
		buttonList.add(firmButton);
		buttonList.add(explosiveButton);
		buttonList.add(giftButton);
		return buttonList;
	}
	
	class ButtonHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent event) {
		
			  JButton actionSource = (JButton) event.getSource();

			    if(actionSource.equals(simpleButton)){
			    	if(simple.isEnabled()) {
			    		simple.setEnabled(false);
			    		if(isCorrectInput(simple.getText())) {
			    			int numberofitem = Integer.parseInt(simple.getText());
			    			
			    			if (numberofitem >= 75) {
				    			asteroidNumbers.put("simple",numberofitem );
				    		}
			    			
			    			else{
				    			simple.setText("Not Valid");
				    		}
			    		}
			    		
			    		else {
			    			simple.setText("Not valid");
			    		}
			    		
			    		
			    		
			    		
			    	
			    	}
			    	
			    	else {
			    		simple.setEnabled(true);
			    		
			    	}
			    } 
			    
			    else if (actionSource.equals(firmButton)) {
			    	if(firm.isEnabled()) {
			    		firm.setEnabled(false);
			    		if(isCorrectInput(firm.getText())) {
			    			int numberofitem = Integer.parseInt(firm.getText());
			    			
			    			if (numberofitem >= 10) {
				    			asteroidNumbers.put("firm",numberofitem );
				    		}
			    			
			    			else{
				    			firm.setText("Not Valid");
				    		}
			    		}
			    		
			    		else {
			    			firm.setText("Not valid");
			    		}
			    		
			    	}
			    
			    	else {
			    		firm.setEnabled(true);
			    		
			    	}
			        
			    }
			    else if (actionSource.equals(explosiveButton)){
			    	if(explosive.isEnabled()) {
			    		explosive.setEnabled(false);
			    		if(isCorrectInput(explosive.getText())) {
			    			int numberofitem = Integer.parseInt(explosive.getText());
			    			
			    			if (numberofitem >= 5) {
				    			asteroidNumbers.put("explosive",numberofitem );
				    		}
			    			
			    			else{
				    			explosive.setText("Not Valid");
				    		}
			    		}
			    		
			    		else {
			    			explosive.setText("Not valid");
			    		}
			    	}
			    	
			    	
			    	else {
			    		explosive.setEnabled(true);
			    		
			    	}
			    }
			    
			    else if (actionSource.equals(giftButton)){
			    	if(gift.isEnabled()) {
			    		gift.setEnabled(false);
			    		if(isCorrectInput(gift.getText())) {
			    			int numberofitem = Integer.parseInt(gift.getText());
			    			
			    			if (numberofitem >= 10) {
				    			asteroidNumbers.put("gift",numberofitem );
				    		}
			    			
			    			else{
				    			gift.setText("Not Valid");
				    		}
			    		}
			    		
			    		else {
			    			gift.setText("Not valid");
			    		}
			    	}
			    	
			    	
			    	else {
			    		gift.setEnabled(true);
			    		
			    	}
			    }
			    	
		} 
			
			
	}
	
	public HashMap<String, Integer> getAsteroidNumbers() {
		return asteroidNumbers;
	}

}
