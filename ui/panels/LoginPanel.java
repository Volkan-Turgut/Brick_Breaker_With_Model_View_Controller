package ui.panels;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import ui.panels.RegisterPanel.RegisterHandler;
import ui.panels.RegisterPanel.ReturnButtonHandler;

public class LoginPanel extends JPanel {

    private static final long serialVersionUID = 1L;


	private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JButton btnRegButton;
    private JButton okButton;
    private JLabel label;
    private JPanel contentPane;
	private int panelWidth;
	private int panelHeight;
	private GameView gameView;
	private boolean logClicked = false;


    public LoginPanel(GameView gameView) {
		panelWidth = 1008;
		this.gameView = gameView;
		
		this.setOpaque(false);
   
		
        JLabel lblNewLabel = new JLabel("Login Page");
        lblNewLabel.setForeground(new Color(50, 50, 150));
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 46));
        lblNewLabel.setBounds(423, 13, 273, 93);
        
       
        


        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(250, 166, 193, 52);
        
        
        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(481, 170, 281, 68);
        textField.setColumns(10);


        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(250, 286, 193, 52);

        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(481, 286, 281, 68);
        passwordField.setColumns(10);
        
        

        
        okButton = new JButton("Okey");
        okButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        okButton.setPreferredSize(new Dimension(15, 15));
        okButton.setBackground(Color.white);
        okButton.setForeground(Color.blue);

        


        btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBounds(545, 392, 162, 73);
        btnNewButton.setBackground(new Color(31,61,90));
        btnNewButton.setForeground(Color.WHITE);

        

        
        btnRegButton = new JButton("Register Now");
        btnRegButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnRegButton.setPreferredSize(new Dimension(15, 15));
        btnRegButton.setBackground(Color.white);
        btnRegButton.setForeground(Color.blue);



        
        JPanel buttonPane = new JPanel(new GridLayout(0,1));
        buttonPane.add(lblNewLabel);
        buttonPane.add(lblUsername);
        buttonPane.add(textField);
        buttonPane.add(lblPassword);
        buttonPane.add(passwordField);
        buttonPane.add(btnNewButton);
        buttonPane.add(btnRegButton);
        this.add(buttonPane, BorderLayout.CENTER);
        buttonPane.setOpaque(false);

        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        this.add(label);
        
		LoginHandler loghandler = new LoginHandler();
		btnNewButton.addActionListener(loghandler);
		
		RegisHandler regisHandler = new RegisHandler();
		btnRegButton.addActionListener(regisHandler);
        
        
    }
    
    public JButton getBtnRegButton() {
		return btnRegButton;
	}
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
	class LoginHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent event) {
			String userName = textField.getText();
            String password = passwordField.getText();
            System.out.println(textField.getText());
            //gameView.switchBuildPanel();
            if(!textField.getText().equals("")) {
            
	            try {
	                Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mechatronic_demo",
	                    "root", "Edebve,haya34");
	
	                PreparedStatement st = (PreparedStatement) connection
	                    .prepareStatement("Select user_name, password from user where user_name=? and password=?");
	
	                st.setString(1, userName);
	                st.setString(2, password);
	                ResultSet rs = st.executeQuery();
	                if (rs.next()) {
	                    /*dispose();
	                    LoginPanel ah = new UserHome(userName);
	                    ah.setTitle("Welcome");
	                    ah.setVisible(true);
	                    JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");*/
	                	infoBox("You have successfully logged in","Welcome");
	                	System.out.println("You have successfully logged in");
	            		gameView.switchBuildPanel();
	                } else {
	                	
	                	infoBox("Invalid user name or password","Unsuccesful Join");
	                	System.out.println("Invalid user name or password");
	                	gameView.switchLoginPanel();
	                }
	            } catch (SQLException sqlException) {
	                sqlException.printStackTrace();
	            }
            }
	        else {
	        	infoBox("Username cannot be empty","Give Your user name");
	        	System.out.println("Username cannot be empty\",\"Give Your user name");	
	        }
			
		}
	}
    
    
    
    
    
    
    class RegisHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
        	gameView.switchRegisterPanel();
    			
        }
    }
    
    
    
    

}
