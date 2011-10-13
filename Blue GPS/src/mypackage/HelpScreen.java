package mypackage;


import net.rim.device.api.system.Characters;
import net.rim.device.api.system.KeyListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.BackgroundFactory;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class HelpScreen extends MainScreen
{
    /**
     * Creates a new MyScreen object
     */

	private MenuItem MenuBack;
	public MyApp app;

	
    public HelpScreen()
    {   
    	app = (MyApp)UiApplication.getUiApplication();
    	this.addKeyListener(new OptionsKeyListener());
        // Set the displayed title of the screen       
        setTitle("Blue GPS - Help");
        setBackground(BackgroundFactory.createSolidBackground(0));
        
        MenuBack = new AppMenu("Back", 0x230000, 0,100,this);    
        addMenuItem(MenuBack);
        
        LabelField lf = new LabelField("Before switching on the application please ensure you have blootooth enabled on the device the application is running.\r\nPair your device only if the application is up running and started.\r\n\r\nThere is not guarantee that this will be working. It has been tested with iOS 4.X running AirBlue application v.0.1.0.\r\n\r\nIf your device does not recognize Blue GPS as a GPS receiver this is most likely due to fact that your device cannot handle a GPS receiver that feature more than one Serial Port profile. Please try switching off all default Serial Port profiles on your device and re-try (hint: go to bluetooth options on your Blackberry).\r\n\r\nIf you like the application or would like to contribute to its development please do not hesitate to get touch with me: gregd72002@gmail.com.");
        this.add(lf);
    }
    
     private class OptionsKeyListener implements KeyListener {

		public boolean keyChar(char key, int status, int time) {
			if (key==Characters.ESCAPE) {
				MenuBack.run();
				return true;
			}
			return false;
		}

		public boolean keyDown(int keycode, int time) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean keyRepeat(int keycode, int time) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean keyStatus(int keycode, int time) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean keyUp(int keycode, int time) {
			// TODO Auto-generated method stub
			return false;
		}
    	
    }
}
