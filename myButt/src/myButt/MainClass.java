package myButt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

class ActionEventDemo implements ActionListener {
	InetAddress address = null;
	
    JFrame frame=new JFrame();
    JButton buttonTestReachable=new JButton("Test Reachability");
    JButton buttonClearFields=new JButton("Clear Fields");
    JTextField textFieldOut=new JTextField();
    JTextField textFieldIn=new JTextField();
    JLabel lblTestThis=new JLabel();
    JLabel lblURL=new JLabel();
    JTextField textURL=new JTextField();
    JLabel lblIP=new JLabel();
    JTextField textIP=new JTextField();
    Boolean reachable;
    
    ActionEventDemo(){
        prepareGUI();
        buttonTestReachableProperties();
        textFieldOutProperties();
        textFieldInProperties();
    }

    public void prepareGUI(){
        frame.setTitle("My Reachable Test Window");
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setBounds(200,200,400,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.cyan);
        
        
        lblTestThis.setBounds(30,20,150,30);
        lblTestThis.setText("Enter the URL to test:  ");
        frame.add(lblTestThis);
        
        lblURL.setBounds(30,100,100,30);
        lblURL.setText("URL to test:  ");
        frame.add(lblURL);
        
        textURL.setBackground(Color.WHITE);
    	textURL.setBounds(120,100,200,30);
    	frame.add(textURL);
        
    	lblIP.setBounds(30,150,100,30);
        lblIP.setText("IP Address:  ");
        frame.add(lblIP);
        
        textIP.setBackground(Color.WHITE);
    	textIP.setBounds(120,150,200,30);
    	frame.add(textIP);
    	
    	buttonClearFields.setBounds(200,320,150,40);
    	buttonClearFields.setActionCommand("ClearFields");
        frame.add(buttonClearFields);
        buttonClearFields.addActionListener(this);
        
    }
    public void buttonTestReachableProperties(){
    	buttonTestReachable.setBounds(30,320,150,40);
    	buttonTestReachable.setActionCommand("TestReachable");
        frame.add(buttonTestReachable);
        buttonTestReachable.addActionListener(this);
    }
    
    public void textFieldOutProperties() {
    	textFieldOut.setBackground(Color.WHITE);
    	textFieldOut.setBounds(20,200,300,100);
    	frame.add(textFieldOut);
    }

    public void textFieldInProperties() {
    	textFieldIn.setBackground(Color.WHITE);
    	textFieldIn.setBounds(170,20,200,30);
    	frame.add(textFieldIn);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	String action = e.getActionCommand();
    	String target = "";
    	String outText = "";
    	String targetIP = "";
    	address = null;
    	reachable = null;
        //Changing Background Color
        //frame.getContentPane().setBackground(Color.pink);
        
    	//TESTING ACTION
    	if (action.equals("ClearFields")) {
    		System.out.println("Clear Fields pressed!");
    	}
    	else {
    		System.out.println("Test Reachability pressed!");
    	}
    	
    	
    	
        target = textFieldIn.getText();
        System.out.println(target);
        
        try {
			address = InetAddress.getByName(target);
		} catch (UnknownHostException e1) {
			frame.getContentPane().setBackground(Color.pink);
			outText = target + "     " + "is NOT valid";
			textFieldOut.setText (outText);
		}
        
        System.out.println(address);
        //get the IP address
        String strOutPing = address.toString();
		int start = strOutPing.indexOf("/");
		int end = strOutPing.length();
		char buf[] = new char[end - start];
		strOutPing.getChars(start+1 , end, buf, 0);
		targetIP = String.valueOf(buf);
		
		System.out.println(targetIP);
		textURL.setText(target);
		textIP.setText(targetIP);
		
        try {
			reachable = address.isReachable(5000);
		} catch (IOException e1) {
			e1.printStackTrace();
			
		}
        
        System.out.println(reachable);
        
        outText = target;
        if(reachable) {
        	frame.getContentPane().setBackground(Color.green);
        	outText = outText + "     " + "is reachable";
        }
        else {
        	frame.getContentPane().setBackground(Color.pink);
        	outText = outText + "     " + "is NOT reachable";
        }
        
        if(reachable) {
        	textFieldOut.setText (outText); //("target is reachable");
        }

    }
}

public class MainClass {
    public static void main(String[] args)
    {
        new ActionEventDemo();
    }
}