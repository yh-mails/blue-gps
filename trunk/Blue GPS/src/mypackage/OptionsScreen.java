package mypackage;

import net.rim.device.api.system.Characters;
import net.rim.device.api.system.KeyListener;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.BackgroundFactory;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class OptionsScreen extends MainScreen
{
    /**
     * Creates a new MyScreen object
     */

	private CheckboxField gprmcf;
	private CheckboxField gpgllf;
	private CheckboxField gpggaf;
	private CheckboxField gpgsaf;
	private TextField updatefreqf;
	private TextField dopmf;
	private CheckboxField debugf;
	
	private MenuItem MenuBack;
	public MyApp app;

	
    public OptionsScreen()
    {   
    	app = (MyApp)UiApplication.getUiApplication();
    	this.addKeyListener(new OptionsKeyListener());
        // Set the displayed title of the screen       
        setTitle("Blue GPS - Options");
        setBackground(BackgroundFactory.createSolidBackground(0));
        
        MenuBack = new AppMenu("Back", 0x230000, 0,10,this);    
        addMenuItem(MenuBack);
        
        MyFieldChangeListener mfcl = new MyFieldChangeListener();
        gprmcf = new CheckboxField("Include GPRMC", app.dc.gprmc);
        gpgllf = new CheckboxField("Include GPGLL", app.dc.gpgll);
        gpggaf = new CheckboxField("Include GPGGA", app.dc.gpgga);
        gpgsaf = new CheckboxField("Include GPGSA", app.dc.gpgsa);
        
        LabelField lf = new LabelField("\r\n\r\nEnter Update frequency in seconds. Value -1 specifies to update only if location changes. Only full seconds are valid. Minimum value is to update every second. Default: 1\r\n");
        updatefreqf = new TextField();
        updatefreqf.setLabel("Update frequency: ");
        updatefreqf.setText(Integer.toString(app.dc.frequency));
        
        LabelField lf1 = new LabelField("\r\n\r\nEnter dilution of precision (DOP) modifier. Blackberry does not provide DOP information and therefore DOP needs to be calculated based on accuracy (in meters) and given modifier. DOP = accuracy*modifier/10. Default: 3.0\r\n");
        dopmf = new TextField();
        dopmf.setLabel("DOP modifier: ");
        dopmf.setText(Float.toString(app.dc.dopm));        

        LabelField lf2 = new LabelField("\r\n\r\nDeveloper option only. Make sure this is unchecked for normal use!\r\n");
        debugf = new CheckboxField("DEBUG?", app.dc.debug);
        
        gprmcf.setChangeListener(mfcl);
        gpgllf.setChangeListener(mfcl);
        gpggaf.setChangeListener(mfcl);
        gpgsaf.setChangeListener(mfcl);
        updatefreqf.setChangeListener(mfcl);
        dopmf.setChangeListener(mfcl);
        debugf.setChangeListener(mfcl);

        this.add(gprmcf);
        this.add(gpgllf);
        this.add(gpggaf);
        this.add(gpgsaf);
        this.add(lf);
        this.add(updatefreqf);
        this.add(lf1);
        this.add(dopmf);
        this.add(lf2); 
        this.add(debugf);     
    }
    
    private void updateDataContext() {
    	this.app.dc.gprmc = this.gprmcf.getChecked();
    	this.app.dc.gpgll = this.gpgllf.getChecked();
    	this.app.dc.gpgga = this.gpggaf.getChecked();
    	this.app.dc.gpgsa = this.gpgsaf.getChecked();
    	this.app.dc.frequency = DataContext.str2int(this.updatefreqf.getText(),1);
    	this.app.dc.dopm = DataContext.str2float(this.dopmf.getText(),3.0f);
    	this.app.dc.debug = this.debugf.getChecked();
    }
    
    private class MyFieldChangeListener implements FieldChangeListener {

		public void fieldChanged(Field field, int context) {
			updateDataContext();
		}
    
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
