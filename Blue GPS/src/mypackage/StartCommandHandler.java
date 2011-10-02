package mypackage;

import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
import net.rim.device.api.ui.component.Dialog;
public class StartCommandHandler extends CommandHandler {
	public BTServer bt;
	public LocationTracker lt;
	private boolean isRunning = false;
    public void execute(ReadOnlyCommandMetadata metadata, Object context)
    {
    	if (isRunning) {
    		Dialog.alert("Blue GPS had been already started!");
    		return;
    	}
    	isRunning = true;
    	//((MyScreen) context).logf.setText("Starting...");
    	//((MyScreen) context).removeMenuItem(((MyScreen) context).MenuStart);
    	bt = new BTServer();
    	lt = new LocationTracker(bt);
    	new UIUpdater(((MyScreen) context),bt,lt);
    }          
}

/*
BT GPS:
$GPGGA,231315.079,5555.1110,N,00309.5998,W,1,06,1.9,72.9,M,52.2,M,,0000*76
$GPGSA,A,3,04,10,13,02,05,23,,,,,,,3.0,1.9,2.3*39
$GPRMC,231315.079,A,5555.1110,N,00309.5998,W,000.0,000.0,011011,,,A*73
$GPVTG,000.0,T,,M,000.0,N,000.0,K,A*0D
$GPGGA,231316.079,5555.1110,N,00309.5998,W,1,06,1.9,72.9,M,52.2,M,,0000*75
$GPGSA,A,3,04,10,13,02,05,23,,,,,,,3.0,1.9,2.3*39
$GPGSV,3,1,12,04,52,207,29,10,72,273,37,13,70,080,33,07,31,157,20*7A
$GPGSV,3,2,12,02,47,287,34,16,09,070,24,05,15,286,38,29,09,343,20*7D
$GPGSV,3,3,12,23,43,067,35,08,09,173,,20,08,111,,30,00,349,*79
$GPRMC,231316.079,A,5555.1110,N,00309.5998,W,000.0,000.0,011011,,,A*70
$GPVTG,000.0,T,,M,000.0,N,000.0,K,A*0D
$GPGGA,231317.079,5555.1110,N,00309.5999,W,1,06,1.9,72.9,M,52.2,M,,0000*75
$GPGSA,A,3,04,10,13,02,05,23,,,,,,,3.0,1.9,2.3*39
$GPRMC,231317.079,A,5555.1110,N,00309.5999,W,000.0,000.0,011011,,,A*70
$GPVTG,000.0,T,,M,000.0,N,000.0,K,A*0D

BB:
$GPGGA,001432,5555.6,N,39.36,W,1,5,,125.0,M,,M,,*5E
$GPGLL,5555.6,N,39.36,W,001432,A*35
$GPRMC,001432,A,5555.6,N,39.36,W,2.1789885,315.19775,021011,,,A*66
*/