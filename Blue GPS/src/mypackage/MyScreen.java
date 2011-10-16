package mypackage;

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.BackgroundFactory;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class MyScreen extends MainScreen
{
    /**
     * Creates a new MyScreen object
     */
	public LabelField info;
	public LabelField gps;
	public LabelField bt;
	private MenuItem MenuOptions;
	private MenuItem MenuHelp;
	private MenuItem MenuAbout;
	private MenuItem MenuRestart;
	
    public MyScreen()
    {        
        // Set the displayed title of the screen       
        setTitle("Blue GPS");
        setBackground(BackgroundFactory.createSolidBackground(0));

        MenuAbout = new AppMenu("About", 0x230000, 1,3, this);    
        addMenuItem(MenuAbout);

        MenuRestart = new AppMenu("Restart service", 0x230000, 2,4, this);    
        addMenuItem(MenuRestart);
        
        MenuOptions = new AppMenu("Options", 0x230000, 3, 1, this);    
        addMenuItem(MenuOptions);
        
        MenuHelp = new AppMenu("Help", 0x230000, 4,2, this);
        addMenuItem(MenuHelp);
        
        this.info = new LabelField();
        this.gps = new LabelField();
        this.bt = new LabelField();
        
        this.add(info);
        this.add(gps);
        this.add(bt);
        
        this.info.setText("Awaiting information from the service.\r\nPlease ensure service is enabled in the options.");
    }
    
}
