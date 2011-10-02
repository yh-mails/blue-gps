package mypackage;

import java.io.UnsupportedEncodingException;

import javax.microedition.location.Location;
import javax.microedition.location.QualifiedCoordinates;

public class BBNmea {
	private Location location;
	private QualifiedCoordinates coordinates;
	private String FixTime;
	private String FixDate;
	private String latitude;
	private String longitude;
	private String altitude;
	
	public BBNmea(Location loc, String fixtimestamp) {
		location = loc;
		coordinates = location.getQualifiedCoordinates();
		FixDate = fixtimestamp.substring(0, 6);
		FixTime = fixtimestamp.substring(7,13);
		
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

		ret += FixTime+","+latitude+","+longitude+",1,06,1.9,"+altitude+",0.0,M,,0000";
		//06 satelites
		//ret += "231315.079,5555.1110,N,00309.5998,W,1,06,1.9,72.9,M,52.2,M,,0000";
		return "$"+ret+"*"+checksum(ret);		
	}
	
	public String getGLL() {
		String ret = "GPGLL,";

		ret += latitude+","+longitude+","+FixTime+",A";

		return "$"+ret+"*"+checksum(ret);	
	}
	
	public String getRMC() {
		String ret = "GPRMC,";
		
		float speed = location.getSpeed()/(float)0.514;
		int ispeed = (int)speed*1000;
		speed = ispeed/1000;
		
		float tangle = location.getCourse(); //can be 0.0?
		int itangle = (int)tangle*1000;
		tangle = itangle/1000;
		
		tangle=0;
		
		ret += FixTime+",A,"+latitude+","+longitude+","+speed+","+tangle+","+FixDate+",,,A";
		//ret += "231315.079,A,5555.1110,N,00309.5998,W,000.0,000.0,011011,,,A";
		
		return "$"+ret+"*"+checksum(ret);
	}
	
	/* dummy data */
	public String getGSV() {
		String ret = "GPGSV,";

		ret += "3,1,12,04,52,207,29,10,72,273,37,13,70,080,33,07,31,157,20";
		
		return "$"+ret+"*"+checksum(ret);
	}
	
	/* dummy data */
	public String getGSA() {
		String ret = "GPGSA,";
		
		ret += "A,3,04,10,13,02,05,23,,,,,,,3.0,1.9,2.3";
		
		return "$"+ret+"*"+checksum(ret);		
	}

	/* dummy data */
	public String getVTG() {
		String ret = "GPVTG,";
		
		ret += "000.0,T,,M,000.0,N,000.0,K,A";

		return "$"+ret+"*"+checksum(ret);		
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
		double _min = (_deg-deg)*60;
		int min = (int)_min;
		String smin = Integer.toString(min);
		if (min<10) smin="0"+min;
		double _sec = (_min-min)*60;
		int sec = (int)_sec;
		String ssec = Integer.toString(sec);
		if (sec<10) ssec="0"+sec;		
		return deg+""+smin+"."+ssec;
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
