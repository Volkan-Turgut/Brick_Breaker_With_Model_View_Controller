package ui.panels;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ui.GameView;


public class RegisterPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField IDField;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JButton btnReturnButton;


	private boolean recClicked = false ;
    private JLabel label;
    private JPanel contentPane;
	private int panelWidth;
	private int panelHeight;
	private GameView gameView;


    public RegisterPanel(GameView gameView) {
		panelWidth = 1008;
		this.gameView = gameView;
		this.setOpaque(false);
		
		
   
		
        JLabel lblNewLabel = new JLabel("Register Page");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 46));
        lblNewLabel.setBounds(423, 13, 273, 93);
        this.add(lblNewLabel);
       
        


        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBackground(new Color(50, 50, 150));
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(250, 166, 193, 52);
        this.add(lblUsername);
        
        
        JLabel lblID = new JLabel("ID");
        lblID.setBackground(Color.BLACK);
        lblID.setForeground(Color.BLACK);
        lblID.setFont(new Font("Tahoma", Font.PLAIN, 31));
        this.add(lblID);
        
        IDField = new JTextField();
        IDField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        IDField.setBounds(481, 170, 281, 68);
        this.add(IDField);
        IDField.setColumns(10);
        
        
        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(481, 170, 281, 68);
        this.add(textField);
        textField.setColumns(10);


        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(250, 286, 193, 52);
        this.add(lblPassword);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(481, 286, 281, 68);
        this.add(passwordField);
        passwordField.setColumns(10);
        
        btnNewButton = new JButton("Register");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBackground(new Color(31,61,90));
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setFocusable(false);

        
        
        btnReturnButton = new JButton("Return Join Page");
        btnReturnButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnReturnButton.setBackground(Color.white);
        btnReturnButton.setForeground(Color.blue);
        btnReturnButton.setFocusable(false);
        btnReturnButton.setSelected(true);
        //btnNewButton.addActionListener(new ReturnListener());

        

        

        
        JPanel buttonPane = new JPanel(new GridLayout(0,1));
        buttonPane.add(lblNewLabel);
        buttonPane.add(lblID);
        buttonPane.add(IDField);
        buttonPane.add(lblUsername);
        buttonPane.add(textField);
        buttonPane.add(lblPassword);
        buttonPane.add(passwordField);
        buttonPane.add(btnNewButton);
        buttonPane.add(btnReturnButton);
        buttonPane.setOpaque(false);
        //btnReturnButton.requestFocus();
        
        this.add(buttonPane, BorderLayout.CENTER);

        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        this.add(label);
        
		RegisterHandler reghandler = new RegisterHandler();
		btnNewButton.addActionListener(reghandler);
		
		ReturnButtonHandler returnHandler = new ReturnButtonHandler();
		btnReturnButton.addActionListener(returnHandler);
		
        
        
        
    }
    
	private static boolean isCorrectInput(String s) {
		try{
			int n = Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException ex) {
			return false;
			
		}
		
	}
	
	
	
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public JButton getBtnReturnButton() {
		return this.btnReturnButton;
	}
    
    
	class RegisterHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent event) {
			String userName = textField.getText();
            String password = passwordField.getText();


            
    		if(isCorrectInput(IDField.getText())) {
    			int ID = Integer.parseInt(IDField.getText());
    			
                try {
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mechatronic_demo",
                        "root", "Edebve,haya34");

                    PreparedStatement st = (PreparedStatement) connection
                        .prepareStatement("Select user_name, password from user where id=? or user_name=?");

                    st.setString(1, IDField.getText());
                    st.setString(2, userName);
                    ResultSet rs = st.executeQuery();
                    
                    PreparedStatement stID = (PreparedStatement) connection
	                        .prepareStatement("Select user_name, password from user where id=?");

	                    stID.setString(1, IDField.getText());
	                    ResultSet rsID = stID.executeQuery();
	                    
                    PreparedStatement stUser = (PreparedStatement) connection
	                        .prepareStatement("Select user_name, password from user where user_name=?");

	                    stUser.setString(1, userName);
	                    ResultSet rsUser = stUser.executeQuery();
                    
                    if (rsID.next()) {
                    	infoBox("ID must be unique","Failed");
                    	System.out.println("ID must be unique");
                    }
                    else if (rsUser.next()) {
                    	infoBox("User name must be unique","Failed");
                    	System.out.println("User name must be unique");
                    }
                    
                    else if (rs.next()) {
                        /*dispose();
                        LoginPanel ah = new UserHome(userName);
                        ah.setTitle("Welcome");
                        ah.setVisible(true);
                        JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");*/
                    	infoBox("User name and ID must unique","Failed");
                    	System.out.println("User name and ID must unique");
                    } else {
                    	
                       // JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
                        PreparedStatement st2 = (PreparedStatement) connection
                                .prepareStatement("INSERT INTO user (id, user_name, password)\r\n"
                                		+ "VALUES (?, ?, ?);");
                        	st2.setString(1, IDField.getText());
                            st2.setString(2, userName);
                            st2.setString(3, password);
                            int i = st2.executeUpdate();
                            if (i > 0) {
                                System.out.println("ROW INSERTED");
                                infoBox("Register is succesful you can return to login","Registration");
    	                    	System.out.println("Register is succesful you can return to login");
                            } else {
                                infoBox("Register is unsuccesfu","Failed");
    	                    	System.out.println("Register is unseccesfull");
                                System.out.println("ROW NOT INSERTED");
                            }
                    	
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
	    			
	    		}else{
    				IDField.setText("ID must integer");
	    		}
	    		
                


            
			
		}
	}
    
    
    
    
    
    
    class ReturnButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
        	gameView.switchLoginPanel();
    			
        }
    }
    
    
    
}