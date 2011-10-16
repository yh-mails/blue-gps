package mypackage;

import java.util.Hashtable;

import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.component.Dialog;

public class DataContext {
	private PersistentObject persistentObject;
	private Hashtable settingsTable;
	
	public boolean enabled;
	public boolean gprmc;
	public boolean gpgll;
	public boolean gpgga;
	public boolean gpgsa;
	public int frequency;
	public float dopm;
	public String btservicename;
	public int debug;
	
	public int serviceProcessID;
	public int guiProcessID=0; //0 - no GUI process connected
	
	public long GUID=0x17293a46212aae03L;
	
	public DataContext() {
    	persistentObject = PersistentStore.getPersistentObject(GUID);
    	synchronized(persistentObject) {
    			settingsTable = (Hashtable)persistentObject.getContents();
    			if (null == settingsTable) {
    				settingsTable = new Hashtable();
    				persistentObject.setContents(settingsTable);
    				persistentObject.commit();
    			}
    	}
    	
    	load();
        
	}
	
	public void load() {
        String _s = (String)settingsTable.get("serviceProcessID");
        serviceProcessID = str2int(_s,0);
        
        _s = (String)settingsTable.get("enabled");
        enabled = str2bool(_s,true);
    	_s = (String)settingsTable.get("GPRMC");
        gprmc = str2bool(_s,true);
        _s = (String)settingsTable.get("GPGLL");
        gpgll = str2bool(_s,true);
        _s = (String)settingsTable.get("GPGGA"); 
        gpgga = str2bool(_s,true);
        _s = (String)settingsTable.get("GPGSA"); 
        gpgsa = str2bool(_s,true);
        _s = (String)settingsTable.get("updateFreq");
        frequency = str2int(_s,1);
        _s = (String)settingsTable.get("DOPM");
        dopm = str2float(_s,3.0f);
        _s = (String)settingsTable.get("btServiceName");
        if (_s!=null && _s.length()>0) btservicename = _s;
        else btservicename = new String("GPS");
        _s = (String)settingsTable.get("debug");          
        debug = str2int(_s,0);		
	}
	
    public void saveOptions() {
    	int severirty = 0;
    	if (valueChanged("GPGLL",bool2str(gpgll))) {
        	settingsTable.put("GPGLL", bool2str(gpgll));
        	severirty = 1;
    	}
    	if (valueChanged("GPGGA",bool2str(gpgga))) {
        	settingsTable.put("GPGGA", bool2str(gpgga));
        	severirty = 1;
    	}
    	if (valueChanged("GPGSA",bool2str(gpgsa))) {
        	settingsTable.put("GPGSA", bool2str(gpgsa));
        	severirty = 1;
    	}
    	if (valueChanged("updateFreq",Integer.toString(frequency))) {
        	settingsTable.put("updateFreq", Integer.toString(frequency));
        	severirty = 1;
    	}
    	if (valueChanged("DOPM",Float.toString(dopm))) {
        	settingsTable.put("DOPM", Float.toString(dopm));
        	severirty = 1;
    	}
    	if (valueChanged("debug",Integer.toString(debug))) {
        	settingsTable.put("debug", Integer.toString(debug));
        	severirty = 1;
    	}
    	if (valueChanged("enabled",bool2str(enabled))) {
    		settingsTable.put("enabled", bool2str(enabled));
    		severirty = 2;
    	}
    	if (valueChanged("btServiceName",btservicename)) {
        	settingsTable.put("btServiceName", btservicename);
        	severirty = 1;
    	}
    	if (valueChanged("GPRMC",bool2str(gprmc))) {
    		settingsTable.put("GPRMC", bool2str(gprmc));
    		severirty = 1;
    	}
		if (severirty==2) {
			persistentObject.commit();
			Dialog.inform("Options saved.\r\nIn order to apply changes please restart your Blackberry.");
		} else if (severirty==1) {
			persistentObject.commit();
        	ApplicationManager.getApplicationManager().postGlobalEvent(serviceProcessID, GUID, 2, 0, null, null);
			Dialog.inform("Options have been applied.\r\n");
		}
    }
    
    public void writeServiceID() {
    	settingsTable.put("serviceProcessID", Integer.toString(serviceProcessID));
    	persistentObject.commit();
    }
    
    private boolean valueChanged(String key, String val) {
    	//we will be comparing v (derived from key) against val
    	String v = (String)settingsTable.get(key);
	
    	if (v==null) v=new String();
    	if (val==null) val=new String();
    	
    	if (v.equalsIgnoreCase(val)) //inner value check
    		return false;
    	return true;
    }
    
    static public String bool2str(boolean v) {
    	if (v) return new String("TRUE");
    	return new String("FALSE"); 
    }
 
    static public int str2int(String s, int def) {
    	if (s==null) return def;
    	try {
    		return Integer.parseInt(s);
    	} catch (NumberFormatException e) {
    		return def;
    	}
    }

    static public float str2float(String s, float def) {
    	if (s==null) return def;
    	try {
    		return Float.parseFloat(s);
    	} catch (NumberFormatException e) {
    		return def;
    	}
    }
    
    static public boolean str2bool(String s, boolean def) {
    	if (s==null) return def;
    	if (s.equalsIgnoreCase("true")) return true;
    	if (s.equalsIgnoreCase("false")) return false;
    	return def;
    }
    	
}
