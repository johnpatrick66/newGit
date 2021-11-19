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

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class ActionEventDemo implements ActionListener {
	
	JFrame frame=new JFrame();
	
	ActionEventDemo(){
        prepareGUI();
	}

	public void prepareGUI() {
		frame.setTitle("My Weather App");
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setBounds(200,200,400,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.cyan);
		
	}

	 @Override
	public void actionPerformed(ActionEvent evt) {
		
	}
}


public class myWeatherApp {

	public static void main(String[] args) throws IOException {
		System.out.println("in here");
		 // Make a URL to the web page
 		URL url = new URL ("https://www.ncei.noaa.gov/access/services/data/v1?dataset=daily-summaries&stations=USW00093738&dataTypes=TMIN,TMAX&startDate=2021-11-10&endDate=2021-11-18&includeAttributes=true&includeStationName=true");
 
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
