package mypackage;

import java.util.Hashtable;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.component.Dialog;

public class DataContext {
	private PersistentObject persistentObject;
	private Hashtable settingsTable;
	
	public boolean gprmc;
	public boolean gpgll;
	public boolean gpgga;
	public boolean gpgsa;
	public int frequency;
	public float dopm;
	public boolean debug;
	
	public DataContext() {
		long KEY = 0x17293a46212aae03L;
    	persistentObject = PersistentStore.getPersistentObject(KEY);
    	synchronized(persistentObject) {
    			settingsTable = (Hashtable)persistentObject.getContents();
    			if (null == settingsTable) {
    				settingsTable = new Hashtable();
    				persistentObject.setContents(settingsTable);
    				persistentObject.commit();
    			}
    	}
    	
    	//return;
        String _s = (String)settingsTable.get("GPRMC");
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
        _s = (String)settingsTable.get("debug");          
        debug = str2bool(_s,false);        
	}
	
    public void saveOptions() {
    	boolean valueChanged = false;
    	if (valueChanged("GPRMC",bool2str(gprmc))) {
    		settingsTable.put("GPRMC", bool2str(gprmc));
    		valueChanged = true;
    	}
    	if (valueChanged("GPGLL",bool2str(gpgll))) {
        	settingsTable.put("GPGLL", bool2str(gpgll));
        	valueChanged = true;
    	}
    	if (valueChanged("GPGGA",bool2str(gpgga))) {
        	settingsTable.put("GPGGA", bool2str(gpgga));
        	valueChanged = true;
    	}
    	if (valueChanged("GPGSA",bool2str(gpgsa))) {
        	settingsTable.put("GPGSA", bool2str(gpgsa));
        	valueChanged = true;
    	}
    	if (valueChanged("updateFreq",Integer.toString(frequency))) {
        	settingsTable.put("updateFreq", Integer.toString(frequency));
        	valueChanged = true;
    	}
    	if (valueChanged("DOPM",Float.toString(dopm))) {
        	settingsTable.put("DOPM", Float.toString(dopm));
        	valueChanged = true;
    	}
    	if (valueChanged("debug",bool2str(debug))) {
        	settingsTable.put("debug", bool2str(debug));
        	valueChanged = true;
    	}
		if (valueChanged) {
			persistentObject.commit();
			Dialog.inform("Options saved.\r\nApplication will now quit.\r\nPlease restart.");
			System.exit(0);
		} else {
			persistentObject.commit();
		}
		
    }
    
    private boolean valueChanged(String key, String val) {
    	Object o = settingsTable.get(key);
    	String v;
    	if (o==null) v=new String();
    	else v = o.toString();
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
