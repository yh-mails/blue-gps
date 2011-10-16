package mypackage;

import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
public class AppMenu extends MenuItem {
	private int Type;
	
	public AppMenu(String text, int ordinal, int priority, int t, Object context) {
		super(text,ordinal,priority);
		Type = t;
	}

	public void run() {
    	switch (Type) {
    	case 1://options
    		//UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
    		OptionsScreen oscreen = new OptionsScreen();
    		UiApplication.getUiApplication().pushScreen(oscreen);
    	break;	
    	case 2:  //help
    		//UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
    		HelpScreen hscreen = new HelpScreen();
    		UiApplication.getUiApplication().pushScreen(hscreen);
    	break;
    	case 3: 
    		Dialog.inform("Gregory Dymarek\r\n\r\ngregd72002@gmail.com\r\n\r\n16th October 2011");
    	break;
    	case 4: 
    		if (((MyUIApp)UiApplication.getUiApplication()).dc.serviceProcessID!=0) {
    			ApplicationManager.getApplicationManager().postGlobalEvent(((MyUIApp)UiApplication.getUiApplication()).dc.serviceProcessID, ((MyUIApp)UiApplication.getUiApplication()).dc.GUID, 12, 0, null, null);			
    			Dialog.inform("Service restarted.");
    		} else {
    			Dialog.inform("Service is not running!\r\nPlease ensure service is enabled in the options and restart your Blackberry.");
    		}
    	break;
    	case 10://save and back to main from options
    		((MyUIApp)UiApplication.getUiApplication()).dc.saveOptions();
    		UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
    	break;
    	case 100: //back to main screen
    		UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
    	break;
    	default:
    		Dialog.alert("Unknown command!");
    	}
	}
}
