package mypackage;

import javax.microedition.location.Location;

import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.GlobalEventListener;
import net.rim.device.api.ui.UiApplication;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class MyUIApp extends UiApplication
{
	public DataContext dc;
	public MyScreen ms;
    /**
     * Creates a new MyApp object
     */
    public MyUIApp()
    {      
    	dc = new DataContext();
    	dc.guiProcessID = getApplication().getProcessId();
    	
    	MyGlobalEventListener mgel = new MyGlobalEventListener();
    	getApplication().addGlobalEventListener(mgel);
    	
        // Push a screen onto the UI stack for rendering.
    	this.ms = new MyScreen();
        pushScreen(this.ms);

        ApplicationManager.getApplicationManager().postGlobalEvent(dc.serviceProcessID, dc.GUID, 1, dc.guiProcessID, null, null);			

    }
    
    public void updateScreen(String info, String gps, String bt) {
    	if (info!=null) this.ms.info.setText(info+"\r\n\r\n");
    	if (gps!=null) this.ms.gps.setText(gps+"\r\n\r\n");
    	if (bt!=null) this.ms.bt.setText(bt+"\r\n");
    }
    
    class MyGlobalEventListener implements GlobalEventListener {
    	/*
    	   -1 - bt failed to initialised
    	   -2 - gps failed to initialised
    	   
    	    0 - bt initialised
			1 - gui connected (data 1) //service targeted
			2 - settings changed //service targeted
			3 - bt data received (object 0)
			4 - bt data sent (total bytes sent: data 1)
			5 - bt device connected (success: data 1)
			6 - bt device disconnected
			7 - gps initialised
			8 - awaiting better gps
			9 - nmea information (location: object 0, nmea string: object 1)
			10 - bt send error
			11 - hello from service
    	 */
		public void eventOccurred(long guid, int data0, int data1,
				Object object0, Object object1) {
			if (guid!=dc.GUID) return;

			switch (data0) {
				case -1:
					updateScreen(null,"","Bluetooth Port failed to initialised!\r\nIs Bluetooth switched on? Have you entered a valid Bluetooth Service Name in the options?");
					break;
				case -2:
					updateScreen(null,"GPS failed to initialised!\r\nPlease make sure location information is available on your Blackberry.",null);
					break;
				case 0:
					updateScreen(null,null,"Bluetooth Port initialised.");
					break;
				case 3:
					updateScreen(null,null,"BT received data:\r\n"+object0);
					break;
				case 4:
					updateScreen(null,null,"BT total sent data: "+data1);
					break;
				case 5:
					updateScreen(null,null,"Bluetooth device connected.");
					break;
				case 6:
					updateScreen(null,null,"No bluetooth device connected.");
					break;
				case 7:
					updateScreen(null,"GPS Initialised.",null);
					break;
				case 8:
					updateScreen(null,"GPS: Poor signal.",null);
					break;
				case 9:
					Location loc = (Location)object0;
					String nmea = (String)object1;
					String s = "Latitude: "+loc.getQualifiedCoordinates().getLatitude()+"\r\n";
					s+="Longitude: "+loc.getQualifiedCoordinates().getLongitude()+"\r\n";
					s+="Altitude: "+loc.getQualifiedCoordinates().getAltitude()+"\r\n";
					s+="Horizontal accuracy: "+loc.getQualifiedCoordinates().getHorizontalAccuracy()+"\r\n";
					s+="Vertical accuracy: "+loc.getQualifiedCoordinates().getVerticalAccuracy()+"\r\n";
					s+="Speed: "+loc.getSpeed()+"\r\n";
					s+="Course: "+loc.getCourse()+"\r\n\r\n";
					if (nmea!=null) s+=nmea;
					updateScreen(null,s,null);
					break;
				case 10:
					updateScreen(null,null,"Error sending data over bluetooth.");
					break;
				case 11:
					updateScreen("Connected to the service (application is running).","","");
					break;
				case 13:
					updateScreen(null,"",null);
					break;
				default:
			}
			
		}
    	
    }    
}
