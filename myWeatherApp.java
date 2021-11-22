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
    JTextField textFieldIn=new JTextField();
	JLabel lblWhatDate=new JLabel();
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

	public void setURL() {
		
		strWeatherDate = textFieldIn.getText();
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
		double dHigh=0;
		double dLow=0;
		System.out.println("in setDateData");
		System.out.println(strInData);
		
		String[] stringarray = strInData.split("\"");
		System.out.println(stringarray[highTempPosition]);
		System.out.println(stringarray[lowTempPosition]);
		
		
		strDayHigh = stringarray[highTempPosition].trim();
		dHigh = Integer.parseInt(strDayHigh);
		System.out.println(strDayHigh);
		dHigh = dHigh * .10;
		System.out.println(dHigh);
		
		strDayLow = stringarray[lowTempPosition].trim();
		dLow = Integer.parseInt(strDayLow);
		System.out.println(strDayLow);
		dLow = dLow * .10;
		System.out.println(dLow);
		
	}
	public void prepareGUI() {
		setTodayDate();
		
		frame.setTitle("My Weather App");
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setBounds(200,200,1000,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.cyan);
        
        lblTodayDate.setBounds(30,20,150,30);
        lblTodayDate.setText("Today's Date:  ");
        frame.add(lblTodayDate);
        
        textTodayDate.setBackground(Color.cyan);
        textTodayDate.setBorder(null);
        textTodayDate.setBounds(170,20,200,30);
        textTodayDate.setText(strTodayDate);
    	frame.add(textTodayDate);
    	
        
        lblWhatDate.setBounds(30,50,150,30);
        lblWhatDate.setText("What Date:  ");
        frame.add(lblWhatDate);
        
        textFieldIn.setBackground(Color.WHITE);
    	textFieldIn.setBounds(170,50,200,30);
    	frame.add(textFieldIn);
        
        textFieldOut.setBackground(Color.WHITE);
    	textFieldOut.setBounds(20,200,800,100);
    	frame.add(textFieldOut);
    	
    	buttonGetWeather.setBounds(20,400,150,40);
    	buttonGetWeather.setActionCommand("GetWeather");
        frame.add(buttonGetWeather);
        buttonGetWeather.addActionListener(this);
        
    	buttonClose.setBounds(200,400,150,40);
    	buttonClose.setActionCommand("Close");
        frame.add(buttonClose);
        buttonClose.addActionListener(this);
		
	}

	 @Override
	public void actionPerformed(ActionEvent evt) {
		 
		 String action = evt.getActionCommand();
		 
		 switch (action) {
		 
		 case "Close":
			 frame.dispose();
			 break;
			 
		 case "GetWeather":
			 setURL();
			 
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
 		URL url = new URL ("https://www.ncei.noaa.gov/access/services/data/v1?dataset=daily-summaries&stations=USW00093738&dataTypes=TMIN,TMAX&includeAttributes=true&includeStationName=true&startDate=2021-11-10&endDate=2021-11-18");
 
 		// Get the input stream through URL Connection
       URLConnection con = url.openConnection();
       InputStream is =con.getInputStream();

       BufferedReader br = new BufferedReader(new InputStreamReader(is));

       String line = null;

       // read each line and write to System.out
       while ((line = br.readLine()) != null) {
           System.out.println(line);
       }
       
       System.out.println("   ---   DONE   ---   ");
       
       new ActionEventDemo();
	}

}
