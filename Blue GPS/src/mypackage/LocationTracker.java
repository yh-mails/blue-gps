package mypackage;

import java.util.Date;

import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;

import net.rim.device.api.i18n.SimpleDateFormat;


class LocationTracker {
	
	private LocationProvider provider;
	private String lastGPSRead;
	private BTServer bt;
	private String log;
	
	public LocationTracker(BTServer bt) {
		this.bt=bt;
		log = "Starting GPS...";
		
		setupGPS();
	}
	
	public String getLog() {return this.log;};
	
	public void setupGPS() {
		try {
			if(provider != null) {
				provider.setLocationListener(null, 0, 0, 0);
				provider.reset();
				provider = null;
			}
			
			Criteria criteria = new Criteria();
			
			criteria.setHorizontalAccuracy(100);
			criteria.setVerticalAccuracy(100);
			criteria.setCostAllowed(false);
			criteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_MEDIUM);
			
			provider = LocationProvider.getInstance(criteria);
			
			// reset the gps counter so we wait another 5 mins before a reset
			provider.setLocationListener(new MyLocationListener(), -1, -1, -1);
			//provider.setLocationListener(listener, interval, timeout, maxAge)
			log = "GPS initialized. Awaiting coordinates...";
		} catch(Exception e) { }
	}
	
	private class MyLocationListener implements LocationListener {
		public void locationUpdated(LocationProvider provider, Location location) {
			SimpleDateFormat timingFormat = new SimpleDateFormat("ddMMyy,HHmmss");
			lastGPSRead = timingFormat.format(new Date());
			if(location != null && location.isValid())
			{				
				//log = location.getExtraInfo("application/X-jsr179-location-nmea");
				//location.getQualifiedCoordinates()
				//log = postprocess(log);
				BBNmea nmea = new BBNmea(location,lastGPSRead);
				log = nmea.getGGA() + "\n\r" + nmea.getRMC() + "\n\r" + nmea.getGSV() + "\n\r" + nmea.getGSA() + "\n\r" + nmea.getVTG() + "\n\r";
				bt.sendData(log);
/*
				String d =
					"$GPGGA,203115.438,5555.11516,N,-309.6039,W,1,5,,105.0,M,,M,,0000*5D\n\r"+
					"$GPGLL,5555.11516,N,-309.6039,W,203115.438,A*34\n\r"+
					"$GPRMC,203115.438,A,5555.11516,N,-309.6039,W,0.6031129,90.04944,011011,,,A*54\n\r"+
					"$GPGSA,A,3,14,11,22,03,19,06,18,28,,,,,2.1,1.1,1.8*11\n\r"+//33
					"$GPGSV,1,1,12,14,21,103,25,11,58,261,40,22,44,067,32,03,31,146,32*11\n\r";//41
				String d1 =
					"$GPGLL,5555.1121,N,-309.60108,W,191744.994,A*37\r\n"+
					"$GPRMC,191745.955,A,5555.11234,N,-309.60054,W,1.2062258,336.9452,011011,,,A*6B\r\n"+
					"$GPGGA,191745.955,5555.11234,N,-309.60054,W,1,7,,117.0,M,,M,,0000*6D\r\n"+
					"$GPGSA,A,3,14,11,22,03,19,06,18,28,,,,,2.1,1.1,1.8*33\r\n"+
					"$GPGSV,1,1,12,14,21,103,25,11,58,261,40,22,44,067,32,03,31,146,32*00\r\n"; //required checksum = 
				
				//bt.sendData(d);
	*/	
			/*
				String d =
				"$GPRMC,165944.323,A,5555.1111,N,00309.5978,W, 000.0    ,000.0    ,011011,   ,,A*7C\r\n"+
				 $GPRMC,191745.955,A,5555.11234,N,-309.60054,W,1.2062258,336.9452,011011,    ,,A*6B
				//"$GPVTG,000.0,T,,M,000.0,N,000.0,K,A*0D\r\n"+ //not required
				"$GPGGA,165945.323,5555.1110, N,00309.5978,W,1,08,1.1,74.7,M,52.2,M,,0000*77\r\n"+
				$GPGGA, 191745.955,5555.11234,N,-309.60054,W,1,7 ,   ,117.0,M,,M,,0000*6D
				"$GPGSA,A,3,14,11,22,03,19,06,18,28,,,,,2.1,1.1,1.8*33\r\n"+
				"$GPGSV,1,1,12,14,21,103,25,11,58,261,40,22,44,067,32,03,31,146,32*41\r\n"; //required
				bt.sendData(d);
			*/
			}
			else
			{
				log = "GPS location is not valid";
			}
			timingFormat = new SimpleDateFormat("HH:mm:ss");
			log = timingFormat.format(new Date()) + "\n" + log;
		}

		public void providerStateChanged(LocationProvider provider, int newState) {
			// TODO: if provider was disabled, then disable reporting
			log = "GPS state changed: " + newState;
			
			LocationTracker.this.setupGPS();
			
//			try {
//				provider.setLocationListener(null, 0, 0, 0);
//				provider.reset();
//				provider = null;
//				provider = LocationProvider.getInstance(new Criteria());
//				provider.setLocationListener(new MyLocationListener(), 10, -1, -1);
//			} catch(Exception e) { log = e.toString() + log; }
		}
	}

}