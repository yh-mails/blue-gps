package mypackage;

import java.io.IOException;
import java.util.Date;

import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;

import net.rim.device.api.bluetooth.BluetoothSerialPort;
import net.rim.device.api.bluetooth.BluetoothSerialPortListener;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.GlobalEventListener;

public class MyService extends Application {
	public DataContext dc;
	private MyGlobalEventListener mgel;
	
	private BluetoothSerialPort bsp;
	private LocationProvider lp;
	
	int sentBytes;
    /**
     * Creates a new MyApp object
     */
    public MyService()
    {      
    	dc = new DataContext();
    	
    	if (!dc.enabled) {
        	dc.serviceProcessID = 0;
        	dc.writeServiceID();    		
    		System.exit(0);
    	}
    	
    	dc.serviceProcessID = getApplication().getProcessId();
    	dc.writeServiceID();
    	
    	mgel = new MyGlobalEventListener();
    	getApplication().addGlobalEventListener(mgel);
    	
    	initStage1();
    }

    /* to be used for simulator with not BT support
     * 

    public void initStage1_() {
    	if (bsp!=null) {
    		bsp.close();
    		bsp = null;
    	}
    	
    	if (lp!=null) {
    		lp.reset();
    		lp = null;
    	}
		
    	sentBytes = 0;
    	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 0, 0, null, null);	   	
    }
      */
    
    public void initStage1() { //bt
    	if (bsp!=null) {
    		bsp.close();
    		bsp = null;
    	}
    	
    	if (lp!=null) {
    		lp.reset();   		
    		lp = null;
			if (dc.guiProcessID!=0) {
		       	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 13, 0, null, null);
			}    		
    	}
		
    	sentBytes = 0;
    	
		try {
			bsp = new BluetoothSerialPort(dc.btservicename,BluetoothSerialPort.BAUD_4800,BluetoothSerialPort.DATA_FORMAT_PARITY_NONE ^ BluetoothSerialPort.DATA_FORMAT_DATA_BITS_8 ^ BluetoothSerialPort.DATA_FORMAT_STOP_BITS_1,BluetoothSerialPort.FLOW_CONTROL_NONE,2048,2048,new MyBTListener());
			if (dc.guiProcessID!=0) {
			       	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 0, 0, null, null);			
			}
		} catch (IOException e) {
			if (dc.guiProcessID!=0) {
				ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, -1, 0, null, null);			
			}
			e.printStackTrace();
		}		
    	
    }
    
    public void initStage2() { //gps					
    	Criteria criteria = new Criteria();
			
		criteria.setHorizontalAccuracy(100);
		criteria.setVerticalAccuracy(100);
		criteria.setCostAllowed(false);
		criteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_MEDIUM);
			
		try {
			lp = LocationProvider.getInstance(criteria);
		} catch (LocationException e) {
			if (dc.guiProcessID!=0) {
		       	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, -2, 0, null, null);			
			}			
			e.printStackTrace();
		}

		lp.setLocationListener(new MyLocationListener(), this.dc.frequency, -1, -1);
		if (dc.guiProcessID!=0) {
	       	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 7, 0, null, null);			
		}
    }
 
    public void forwardData(String s) {
		try {
			byte [] b = s.getBytes("UTF8");
			bsp.write(b);
			sentBytes+=b.length;
		} catch (IOException e) {
			if (dc.guiProcessID!=0) {
		       	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 10, 0, null, null);			
			}		
		}		 	
    }
    
	private class MyLocationListener implements LocationListener {
		public void locationUpdated(LocationProvider provider, Location location) {
			SimpleDateFormat timingFormat = new SimpleDateFormat("ddMMyy,HHmmss");
			String lastGPSRead = timingFormat.format(new Date()); //hopefully it is UTC? This possibly could be taken from BB NMEA sentence. Though does it have UTC?
			if(location != null && location.isValid())
			{				
				BBNmea nmea = new BBNmea(location,lastGPSRead,dc);
				String msg = "";
				if (dc.gpgga) msg += nmea.getGGA();
				if (dc.gprmc) msg += nmea.getRMC();
				if (dc.gpgll) msg += nmea.getGLL();
				if (dc.gpgsa) msg += nmea.getGSA();

				if (dc.guiProcessID!=0) {
					ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 9, 0, location, null);
				} else if (dc.guiProcessID!=0 && dc.debug>1) {
					ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 9, 0, location, msg);
				}
				forwardData(msg);
			}
			else
			{
				if (dc.guiProcessID!=0) {
					ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 8, 0, null, null);
				}
			}
		}

		public void providerStateChanged(LocationProvider provider, int newState) {
		}
	}    
    
	private class MyBTListener implements BluetoothSerialPortListener {

		public void dataReceived(int length) {
			byte [] buf = new byte[1024];
			String s;
			try {
				bsp.read(buf);
				s = new String(buf);
				if (dc.guiProcessID!=0 && dc.debug>1)
			       	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 3, 0, s, null);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void dataSent() {
			if (dc.guiProcessID!=0 && dc.debug>1)
		       	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 4, sentBytes, null, null);
			
		}

		public void deviceConnected(boolean success) {
			if (dc.guiProcessID!=0) {
				int _success = 0;
				if (success) _success = 1;
		       	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 5, _success, null, null);
			}
			if (success)
				initStage2();
		}

		public void deviceDisconnected() {
			if (dc.guiProcessID!=0) {
		       	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 6, 0, null, null);
			}
			lp.reset();
			lp = null;
			if (dc.guiProcessID!=0) {
		       	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 13, 0, null, null);
			}
		}

		public void dtrStateChange(boolean high) {
			// TODO Auto-generated method stub
			
		}
	}    
    
    class MyGlobalEventListener implements GlobalEventListener {
    	/*
    	   -1 - bt failed to initialised
    	   -2 - gps failed to initialised
    	   
    	    0 - bt initialised
			1 - gui connected
			2 - gui process id (data 1)
			3 - bt data received (object 0)
			4 - bt data sent (total bytes sent: data 1)
			5 - bt device connected (success: data 1)
			6 - bt device disconnected
			7 - gps initialised
			8 - awaiting better gps
			9 - nmea information (location: object 0, nmea string: object 1)
			10 - bt send error
			11 - hello from service (sent after gui connects to it)
			12 - reset service
			13 - gps stopped
			100 - msg from service
    	 */
		public void eventOccurred(long guid, int data0, int data1,
				Object object0, Object object1) {
			if (guid!=dc.GUID) return;
			
			if (data0 == 1) { //GUI connected, object0 is an int of gui process id
				dc.guiProcessID = data1;
		       	ApplicationManager.getApplicationManager().postGlobalEvent(dc.guiProcessID, dc.GUID, 11, 0, null, null);				
			} else if (data0 == 2) { //settings have changed
				dc.load();
				initStage1();
			} else if (data0 == 12) {
				initStage1();
			}
			
		}
    	
    }
}
