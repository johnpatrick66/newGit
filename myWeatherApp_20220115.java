//
// see for guidance in fetching weather data
// https://www.ncei.noaa.gov/support/access-data-service-api-user-documentation
//
// the Reston weather station is:  US1VAFX0076   (but use Dulles:  USW00093738)
//                    latitude:  38.978422
//                    longitude:  -77.346219
//
// Example query:
// https://www.ncei.noaa.gov/access/services/data/v1?dataset=global-marine&dataTypes=WIND_DIR,WIND_SPEED&stations=AUCE&startDate=2016-01-01&endDate=2016-01-02&boundingBox=90,-180,-90,180
// https://www.ncei.noaa.gov/access/services/data/v1?dataset=global-summary-of-the-year&dataTypes=DP01,DP05,DP10,DSND,DSNW,DT00,DT32,DX32,DX70,DX90,SNOW,PRCP&stations=ASN00084027&startDate=1952-01-01&endDate=1970-12-31&includeAttributes=true&format=pdf
// URL url = new URL ("https://www.ncei.noaa.gov/access/services/data/v1?dataset=daily-summaries&dataTypes=DP01,DP05,DP10,DSND,DSNW,DT00,DT32,DX32,DX70,DX90,SNOW,PRCP&stations=USW00093738&startDate=2020-12-01&endDate=2020-12-31&includeAttributes=true");
//
//
// This is what we want to use:
// URL url = new URL ("https://www.ncei.noaa.gov/access/services/data/v1?dataset=daily-summaries&stations=USW00093738&dataTypes=TMIN,TMAX&startDate=2020-12-01&endDate=2020-12-31&includeAttributes=true&includeStationName=true");
//
// Stations:
// https://www1.ncdc.noaa.gov/pub/data/ghcn/daily/ghcnd-stations.txt
//
// NOTE:
// TMAX and TMIN are tenths of C



package myWeatherApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class ActionEventDemo implements ActionListener {
	
	JFrame frame=new JFrame();
	
	JLabel lblTodayDate=new JLabel();
	JTextField textTodayDate=new JTextField();
	
	JTextField textFieldOut=new JTextField();
        
	JLabel lblWhatDate=new JLabel();
	JTextField textFieldIn=new JTextField();
	
	JLabel lblC=new JLabel();
	JLabel lblF=new JLabel();
	
	JLabel lblDayHigh=new JLabel();
	JLabel lblDayLow=new JLabel();
	
	JTextField textDayHighC=new JTextField();
	JTextField textDayHighF=new JTextField();
	JTextField textDayLowC=new JTextField();
	JTextField textDayLowF=new JTextField();
		
	JButton buttonGetWeather=new JButton("Get Weather");
	JButton buttonClose=new JButton("CLOSE");
	
	String strWeatherURL="";
	String strWeatherDate="";
	String strTodayDate="";
	String strDayHigh="";
	String strDayLow="";
	
	ActionEventDemo() throws IOException{
        prepareGUI();
        //setURL();
        //getWeather();
	}

	public void setURL(String inDate) {
		
		//strWeatherDate = textFieldIn.getText();
		strWeatherDate = inDate;
		strWeatherURL = "https://www.ncei.noaa.gov/access/services/data/v1?dataset=daily-summaries&stations=USW00093738&dataTypes=TMIN,TMAX&includeAttributes=true&includeStationName=true";
		strWeatherURL = strWeatherURL + "&startDate=" + strWeatherDate + "&endDate=" + strWeatherDate;
		System.out.println("https://www.ncei.noaa.gov/access/services/data/v1?dataset=daily-summaries&stations=USW00093738&dataTypes=TMIN,TMAX&includeAttributes=true&includeStationName=true&startDate=2021-11-10&endDate=2021-11-18");
		System.out.println(strWeatherURL);
		
	}
	
	public void setTodayDate() {
		
		System.out.println("in setDate");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now));  
		strTodayDate = dtf.format(now);
		
	}
	
	public void getWeather() throws IOException {
		
	   URL weatherURL = new URL (strWeatherURL);
		 
 		// Get the input stream through URL Connection
       URLConnection weatherConnection = weatherURL.openConnection();
       InputStream inStream = weatherConnection.getInputStream();

       BufferedReader inBFR = new BufferedReader(new InputStreamReader(inStream));

       String inLine = null;

       // read each line and write to System.out
       // first line is header
       // second line is data, quotation mark separated, with quotation marks around each data point
       int lineCnt = 0;
       while ((inLine = inBFR.readLine()) != null) {
    	   lineCnt = lineCnt + 1;
           System.out.println(inLine);
           textFieldOut.setText (inLine);
           if (lineCnt == 2) {
        	   setDateData(inLine);
           }
       }
       
       System.out.println("   ---   DONE AGAIN  ---   ");
		
	}
	
	public void setDateData(String strInData) {
		final int highTempPosition = 7;
		final int lowTempPosition = 11;
		double dHighC=0;
		double dLowC=0;
		double dHighF=0;
		double dLowF=0;
		
		System.out.println("in setDateData");
		System.out.println(strInData);
		
		String[] stringarray = strInData.split("\"");
		System.out.println(stringarray[highTempPosition]);
		System.out.println(stringarray[lowTempPosition]);
				
		strDayHigh = stringarray[highTempPosition].trim();
		dHighC = Double.parseDouble(strDayHigh);
		System.out.println(strDayHigh);
		dHighC = dHighC * .10;
		dHighF = getCtoF(dHighC);
		
		textDayHighC.setText(String.format("%.1f", dHighC)); 
		textDayHighF.setText(String.format("%.1f", dHighF)); 
		System.out.println(dHighC);
		System.out.println("c2f-->" + dHighF);
		
		strDayLow = stringarray[lowTempPosition].trim();
		dLowC = Double.parseDouble(strDayLow);
		System.out.println(strDayLow);
		dLowC = dLowC * .10;
		dLowF = getCtoF(dLowC);
		
		textDayLowC.setText(String.format("%.1f", dLowC)); 
		textDayLowF.setText(String.format("%.1f", dLowF)); 
		System.out.println(dLowC);
		System.out.println("c2f-->" + dLowF);
		
	}
	
	public Double getCtoF(Double inC) {
		//fahrenheit=(celsius*1.8)+32
		
		return (inC * 1.8) + 32;
		
	}
	
 	public void prepareGUI() {
		setTodayDate();
		
		frame.setTitle("My Weather App");
        frame.getContentPane().setLayout(null);
        //frame.setVisible(true);
        frame.setBounds(200,200,1000,700);
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
    	
    	lblWhatDate.setOpaque(true);
        lblWhatDate.setBounds(30,60,120,30);
        lblWhatDate.setText("What Date:  ");
        frame.add(lblWhatDate);
                
        textFieldIn.setBackground(Color.WHITE);
    	textFieldIn.setBounds(170,60,200,30);
    	frame.add(textFieldIn);
    	
    	lblC.setOpaque(false);
    	lblC.setBounds(210,120,30,30);
    	lblC.setText("C");
        frame.add(lblC);
        
        lblF.setOpaque(false);
        lblF.setBounds(300,120,30,30);
        lblF.setText("F");
        frame.add(lblF);
        
    	lblDayHigh.setOpaque(true);
    	lblDayHigh.setBounds(60,160,80,30);
    	lblDayHigh.setText("Day High:  ");
        frame.add(lblDayHigh);
        
        textDayHighC.setBackground(Color.WHITE);
        textDayHighC.setBounds(190,160,50,30);
        textDayHighC.setEditable(false);
    	frame.add(textDayHighC);
    	
    	textDayHighF.setBackground(Color.WHITE);
    	textDayHighF.setBounds(280,160,50,30);
    	textDayHighF.setEditable(false);
    	frame.add(textDayHighF);
        
        lblDayLow.setOpaque(true);
        lblDayLow.setBounds(60,200,80,30);
        lblDayLow.setText("Day Low:  ");
        frame.add(lblDayLow);
        
        textDayLowC.setBackground(Color.WHITE);
        textDayLowC.setBounds(190,200,50,30);
        textDayLowC.setEditable(false);
    	frame.add(textDayLowC);
    	
    	textDayLowF.setBackground(Color.WHITE);
    	textDayLowF.setBounds(280,200,50,30);
    	textDayLowF.setEditable(false);
    	frame.add(textDayLowF);
        
        textFieldOut.setBackground(Color.WHITE);
    	textFieldOut.setBounds(20,450,800,100);
    	frame.add(textFieldOut);
    	
    	buttonGetWeather.setBounds(20,600,150,40);
    	buttonGetWeather.setActionCommand("GetWeather");
        frame.add(buttonGetWeather);
        buttonGetWeather.addActionListener(this);
        
    	buttonClose.setBounds(200,600,150,40);
    	buttonClose.setActionCommand("Close");
        frame.add(buttonClose);
        buttonClose.addActionListener(this);
		
        frame.setVisible(true);
	}

	 @Override
	public void actionPerformed(ActionEvent evt) {
		 
		 String action = evt.getActionCommand();
		 
		 switch (action) {
		 
		 case "Close":
			 frame.dispose();
			 break;
			 
		 case "GetWeather":
			 //setURL();
			 setURL(textFieldIn.getText());
			 
			 try {
				getWeather();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
		 }
		
	}
}


public class myWeatherApp {

	public static void main(String[] args) throws IOException {
		System.out.println("in here");
		 // Make a URL to the web page
 //		URL url = new URL ("https://www.ncei.noaa.gov/access/services/data/v1?dataset=daily-summaries&stations=USW00093738&dataTypes=TMIN,TMAX&includeAttributes=true&includeStationName=true&startDate=2021-11-10&endDate=2021-11-18");
 //
 //		// Get the input stream through URL Connection
 //      URLConnection con = url.openConnection();
 //      InputStream is =con.getInputStream();
//
//       BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//       String line = null;
//
//       // read each line and write to System.out
//       while ((line = br.readLine()) != null) {
//           System.out.println(line);
//       }
       
       System.out.println("   ---   DONE   ---   ");
       
       new ActionEventDemo();
	}

}
