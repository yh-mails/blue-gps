package mypackage;

import java.util.Timer;
import java.util.TimerTask;

import net.rim.device.api.ui.UiApplication;

public class UIUpdater extends TimerTask {
	private Timer timer;
	private MyScreen ms;
	private BTServer bt;
	private LocationTracker lt;
	public UIUpdater(MyScreen ms, BTServer bt, LocationTracker lt) {
		this.ms = ms;
		this.bt = bt;
		this.lt = lt;
        timer = new Timer();
        timer.schedule(this, 0, 1000);		
	}
	
	public void run() {
		String gps = lt.getLog();
		String blue = bt.getLog();
		_Updater updater = new _Updater(ms,gps+"\n\n"+blue);
		UiApplication.getUiApplication().invokeLater(updater);
		
	}

	private class _Updater implements Runnable{
		private MyScreen ms;
		private String s;
		_Updater(MyScreen ms,String s) {
			this.ms = ms;
			this.s = s;
		}

		public void run() {
			this.ms.logf.setText(this.s);	
		}
		
	}
}

