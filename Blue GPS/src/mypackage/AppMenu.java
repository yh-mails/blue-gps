package mypackage;

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
public class AppMenu extends MenuItem {
	public BTServer bt;
	public LocationTracker lt;
	private boolean isRunning = false;
	private int Type;
	private Object Context;
	
	public AppMenu(String text, int ordinal, int priority, int t, Object context) {
		super(text,ordinal,priority);
		Type = t;
		this.Context = context;
	}

	public void run() {
    	switch (Type) {
    	case 0:
	    	if (isRunning) {
	    		Dialog.alert("Blue GPS had been already started!");
	    		return;
	    	}
	    	isRunning = true;
	    	bt = new BTServer(((MyApp) UiApplication.getUiApplication()).dc);
	    	lt = new LocationTracker(bt,((MyApp) UiApplication.getUiApplication()).dc);
	    	new UIUpdater(((MyScreen) Context),bt,lt);
	    break;
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
    		Dialog.inform("Gregory Dymarek\r\n\r\ngregd72002@gmail.com\r\n\r\n5th October 2011");
    	break;
    	case 10://save and back to main from options
    		((MyApp)UiApplication.getUiApplication()).dc.saveOptions();
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
