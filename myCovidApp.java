package myCovidApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class ActionEventCovidApp implements ActionListener {
	
    JFrame frame=new JFrame();
	
	JLabel lblTodayDate=new JLabel();
	JTextField textTodayDate=new JTextField();
	
	JLabel lblWhatCountry=new JLabel();
	JTextField textFieldInputCountry=new JTextField();
	
	JLabel lblThisCountry=new JLabel();
	JTextField textThisCountry=new JTextField();
	
	JLabel lblLastUpdate=new JLabel();
	JTextField textLastUpdate=new JTextField();
	
	JLabel lblNewCases=new JLabel();
	JTextField textNewCases=new JTextField();
	
	JLabel lblNewDeaths=new JLabel();
	JTextField textNewDeaths=new JTextField();
	
	JLabel lblTotalCases=new JLabel();
	JTextField textTotalCases=new JTextField();
	
	JLabel lblTotalDeaths=new JLabel();
	JTextField textTotalDeaths=new JTextField();
	
	
	JButton buttonGetCovidData=new JButton("Get Data");
	JButton buttonClose=new JButton("CLOSE");
	
	JTextField textFieldOut=new JTextField();
	
	String strTodayDate = "";
	String strRootCovidDataURL="https://covid-19.dataflowkit.com/v1/";
	String strCovidDataURL="";
	//String strCovidDataURL="https://covid-19.dataflowkit.com/v1/china";
	//String strCovidDataURL = "https://coronavirus-tracker-api.herokuapp.com/v2";
	String strCovidData = "";

	ActionEventCovidApp() throws IOException{
        prepareGUI();
 
	}
	
	public void prepareGUI() {
		setTodayDate();
		
		frame.setTitle("My Covid App");
        frame.getContentPane().setLayout(null);
        frame.setBounds(200,200,1000,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.cyan);
        
        lblTodayDate.setOpaque(true);
        lblTodayDate.setBounds(30,20,120,30);
        lblTodayDate.setText("Today's Date:  ");
        frame.add(lblTodayDate);
        
        textTodayDate.setBackground(Color.cyan);
        textTodayDate.setBorder(null);
        textTodayDate.setBounds(170,20,200,30);
        textTodayDate.setText(strTodayDate);
    	frame.add(textTodayDate);
    	
    	lblWhatCountry.setOpaque(true);
    	lblWhatCountry.setBounds(30,60,120,30);
    	lblWhatCountry.setText("What Country:  ");
        frame.add(lblWhatCountry);
                
        textFieldInputCountry.setBackground(Color.WHITE);
        textFieldInputCountry.setBounds(170,60,200,30);
    	frame.add(textFieldInputCountry);
    	
    	lblThisCountry.setOpaque(true);
    	lblThisCountry.setBounds(130,120,120,30);
    	lblThisCountry.setText("Country:  ");
        frame.add(lblThisCountry);
                
        textThisCountry.setBackground(Color.WHITE);
        textThisCountry.setBounds(270,120,200,30);
    	frame.add(textThisCountry);
    	
    	lblLastUpdate.setOpaque(true);
    	lblLastUpdate.setBounds(530,120,120,30);
    	lblLastUpdate.setText("Last Update:  ");
        frame.add(lblLastUpdate);
                
        textLastUpdate.setBackground(Color.WHITE);
        textLastUpdate.setBounds(670,120,200,30);
    	frame.add(textLastUpdate);
    	
    	lblNewCases.setOpaque(true);
    	lblNewCases.setBounds(130,160,120,30);
    	lblNewCases.setText("New Cases:  ");
        frame.add(lblNewCases);
                
        textNewCases.setBackground(Color.WHITE);
        textNewCases.setBounds(270,160,200,30);
    	frame.add(textNewCases);
    	
    	lblNewDeaths.setOpaque(true);
    	lblNewDeaths.setBounds(530,160,120,30);
    	lblNewDeaths.setText("New Deaths:  ");
        frame.add(lblNewDeaths);
                
        textNewDeaths.setBackground(Color.WHITE);
        textNewDeaths.setBounds(670,160,200,30);
    	frame.add(textNewDeaths);
    	   	
    	
    	
    	
    	
    	
    	lblTotalCases.setOpaque(true);
    	lblTotalCases.setBounds(130,200,120,30);
    	lblTotalCases.setText("Total Cases:  ");
        frame.add(lblTotalCases);
                
        textTotalCases.setBackground(Color.WHITE);
        textTotalCases.setBounds(270,200,200,30);
    	frame.add(textTotalCases);
    	
    	lblTotalDeaths.setOpaque(true);
    	lblTotalDeaths.setBounds(530,200,120,30);
    	lblTotalDeaths.setText("Total Deaths:  ");
        frame.add(lblTotalDeaths);
                
        textTotalDeaths.setBackground(Color.WHITE);
        textTotalDeaths.setBounds(670,200,200,30);
    	frame.add(textTotalDeaths);
    	   	
    	
    	
    	
    	
    	
    	
    	
    	
    	   	
    	buttonGetCovidData.setBounds(20,260,150,40);
    	buttonGetCovidData.setActionCommand("GetCovidData");
        frame.add(buttonGetCovidData);
        buttonGetCovidData.addActionListener(this);
        
    	buttonClose.setBounds(200,260,150,40);
    	buttonClose.setActionCommand("Close");
        frame.add(buttonClose);
        buttonClose.addActionListener(this);
        
        textFieldOut.setBackground(Color.WHITE);
    	textFieldOut.setBounds(20,350,800,100);
    	frame.add(textFieldOut);
    	
    	frame.setVisible(true);
	}
	
	public void setTodayDate() {
		
		System.out.println("in setDate");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now));  
		strTodayDate = dtf.format(now);
		
	}
	
	public void getCovidData() throws IOException {
		
		   URL dataURL = new URL (strCovidDataURL);
			 
	 		// Get the input stream through URL Connection
	       URLConnection dataConnection = dataURL.openConnection();
	       InputStream inStream = dataConnection.getInputStream();

	       BufferedReader inBFR = new BufferedReader(new InputStreamReader(inStream));

	       String inLine = null;

	       int lineCnt = 0;
	       while ((inLine = inBFR.readLine()) != null) {
	    	   lineCnt = lineCnt + 1;
	           System.out.println(inLine);
	           textFieldOut.setText (inLine);
	           
	           strCovidData = inLine;
	           //if (lineCnt == 2) {
	        // 	   setDateData(inLine);
	          // }
	       }
	       
	       
	       System.out.println("   ---   DONE AGAIN  ---   ");
			
		}
	
	public void setCovidDataInDisplay() {
		int iCountryPosition = 7;
		int iLastUpdatePosition = 11;
		int iNewCasesPosition=15;
		int iNewDeathsPosition=19;  
		int iTotalCasesPosition = 23;
		int iTotalDeathsPosition = 27;
		
		String[] stringarray = strCovidData.split("\"");
		
		System.out.println(stringarray[iCountryPosition].trim());
		textThisCountry.setText(stringarray[iCountryPosition].trim());
		textLastUpdate.setText(stringarray[iLastUpdatePosition].trim());
		textNewCases.setText(stringarray[iNewCasesPosition].trim());
		textNewDeaths.setText(stringarray[iNewDeathsPosition].trim());
		textTotalCases.setText(stringarray[iTotalCasesPosition].trim());
		textTotalDeaths.setText(stringarray[iTotalDeathsPosition].trim());
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		String action = evt.getActionCommand();
		 
		 switch (action) {
		 
		 case "Close":
			 frame.dispose();
			 break;
			 
		 case "GetCovidData":
			 //setURL();
			 //setURL(textFieldIn.getText());
			 
			 strCovidDataURL = strRootCovidDataURL + textFieldInputCountry.getText();
			 System.out.println(strCovidDataURL);
			 try {
				getCovidData();
				setCovidDataInDisplay();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
		 }
		
	}
	
}
public class myCovidApp {

	public static void main(String[] args) {
		System.out.println("in myCovidApp");
		
		try {
			new ActionEventCovidApp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
