package mypackage;

import java.io.UnsupportedEncodingException;

import javax.microedition.location.Location;
import javax.microedition.location.QualifiedCoordinates;

public class BBNmea {
	private Location location;
	public QualifiedCoordinates coordinates;
	private String FixTime;
	private String FixDate;
	private String latitude;
	private String longitude;
	private String altitude;
	private float hdop;
	private float vdop;	
	private float pdop;
	private DataContext dc;
	
	public BBNmea(Location loc, String fixtimestamp, DataContext dc) {
		this.dc = dc;
		location = loc;
		coordinates = location.getQualifiedCoordinates();
		FixDate = fixtimestamp.substring(0, 6);
		FixTime = fixtimestamp.substring(7,13)+".000";
		
		hdop = coordinates.getHorizontalAccuracy();
		vdop = coordinates.getVerticalAccuracy();
		pdop = (float)Math.sqrt(hdop*hdop+vdop*vdop);
		hdop = m_to_dop(hdop);
		vdop = m_to_dop(vdop);
		pdop = m_to_dop(pdop);
		
		double _latitude = coordinates.getLatitude(); //N,S
		if (_latitude<0) latitude = deg_to_dms(Math.abs(_latitude))+",S";
		else latitude = deg_to_dms(_latitude)+",N";
		
		double _longitude = coordinates.getLongitude(); //E,W
		if (_longitude<0) longitude = deg_to_dms(Math.abs(_longitude))+",W";
		else longitude = deg_to_dms(_longitude)+",E";
		
		float _altitude = coordinates.getAltitude();
		if (_altitude==Float.NaN) altitude = "0.0,M";
		else altitude = _altitude+",M";		
	}
	
	public String getGGA() {
		String ret = "GPGGA,";

		ret += FixTime+","+latitude+","+longitude+",1,00,"+hdop+","+altitude+",0.0,M,,0000";

		if (this.dc.debug>2) {
			//ret = "GPGGA,085247.000,1291.7150,N,10134.3113,E,1,04,2.1,94.7,M,8.5,M,,0000";
			//ret = "GPGGA,125030.31,1234.567890,S,01234.567890,E,1,04,2.1,94.7,M,8.5,M,,0000";
			ret = "GPGGA,085247.000,1291.7150,N,10134.3113,E,1,00,2.1,94.7,M,8.5,M,,0000";
		}
		return "$"+ret+"*"+checksum(ret)+"\r\n";		
	}
	
	public String getGLL() {
		String ret = "GPGLL,";

		ret += latitude+","+longitude+","+FixTime+",A";

		if (this.dc.debug>2) {
			ret = "GPGLL,1291.7150,N,10134.3113,E,085247.000,A";
		}
		
		return "$"+ret+"*"+checksum(ret)+"\r\n";	
	}
	
	public String getRMC() {
		String ret = "GPRMC,";
		
		float speed = location.getSpeed()/(float)0.514;
		int ispeed = (int)(speed*1000.0f);
		speed = ispeed/1000.0f;
		
		float tangle = location.getCourse(); //can be 0.0?
		int itangle = (int)(tangle*1000.0f);
		tangle = itangle/1000.0f;
		
		ret += FixTime+",A,"+latitude+","+longitude+","+speed+","+tangle+","+FixDate+",,,A";
		if (this.dc.debug>2) {
			//ret = "GPRMC,085246.000,A,1291.7150,N,10134.3113,E,0.00,58.57,110511,,,A";
			//ret = "GPRMC,125030.31,V,1234.567890,S,01234.567890,E,,,270110,,,N";
			ret = "GPRMC,085246.000,A,1291.7150,N,10134.3113,E,0.00,58.57,110511,,,A";			
		}
		return "$"+ret+"*"+checksum(ret)+"\r\n";
	}
	
	/* dummy data */
	public String getGSV() {
		String ret = "GPGSV,";

		//ret += "3,1,12,04,52,207,29,10,72,273,37,13,70,080,33,07,31,157,20";
		
		return "$"+ret+"*"+checksum(ret)+"\r\n";
	}
	
	/* dummy data */
	public String getGSA() {
		String ret = "GPGSA,";
		
		//ret += "A,3,04,10,13,02,05,23,,,,,,,3.0,1.9,2.3";
		ret += "A,3,,,,,,,,,,,,,"+pdop+","+hdop+","+vdop;
		
		return "$"+ret+"*"+checksum(ret)+"\r\n";		
	}

	/* dummy data */
	public String getVTG() {
		String ret = "GPVTG,";
		
		//ret += "000.0,T,,M,000.0,N,000.0,K,A";

		return "$"+ret+"*"+checksum(ret)+"\r\n";		
	}
	
	private String checksum(String s) {
		int chksum = 0;
		int i;
		byte[] b;
		try {
			b = s.getBytes("UTF8");

			chksum = 0;
			for (i=0;i<b.length;i++) {
				chksum = chksum ^ b[i];
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String ret = Integer.toHexString(chksum).toUpperCase();
		if (ret.length()<2) ret = "0"+ret;
		return ret;
	}
	
	private String deg_to_dms(double _deg) {
		int deg = (int)_deg;
		int _min = (int)((_deg-deg)*60.0*1000.0);
		float min = _min/1000.0f;
		
		String sdeg = Integer.toString(deg);
		while (sdeg.length()<2) sdeg = "0"+sdeg;
		
		String smin = Float.toString(min);
		if (min<10) smin = "0"+smin;
		if (min<1) smin = "0"+smin;

		return sdeg+smin;
	}
	
	private float m_to_dop(float m) {
		int _ret;
		if (m==Float.NaN) return 10.0f;
		_ret = (int)(m*dc.dopm);
		if (_ret<10) _ret=10;
		return _ret/10.0f;
	}
	/*
	private String[] split(String original,String separator) {
		Vector nodes = new Vector();
		// Parse nodes into vector
		int index = original.indexOf(separator);
		while(index>=0) {
		nodes.addElement( original.substring(0, index) );
		original = original.substring(index+separator.length());
		index = original.indexOf(separator);
		}
		// Get the last node
		nodes.addElement( original );

		// Create splitted string array
		String[] result = new String[ nodes.size() ];
		if( nodes.size()>0 ) {
		for(int loop=0; loop<nodes.size(); loop++)
		{
		result[loop] = (String)nodes.elementAt(loop);
		}

		}

		return result;
		}
		*/
}
