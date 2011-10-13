package mypackage;

import net.rim.device.api.ui.MenuItem;
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
	public LogField logf;
	private MenuItem MenuStart;
	private MenuItem MenuOptions;
	private MenuItem MenuHelp;
	private MenuItem MenuAbout;
	
    public MyScreen()
    {        
        // Set the displayed title of the screen       
        setTitle("Blue GPS");
        setBackground(BackgroundFactory.createSolidBackground(0));
        
        MenuStart = new AppMenu("Start", 0x230000, 0,0,this);    
        addMenuItem(MenuStart);

        MenuOptions = new AppMenu("Options", 0x230000, 1, 1, this);    
        addMenuItem(MenuOptions);
        
        MenuHelp = new AppMenu("Help", 0x230000, 2,2, this);
        addMenuItem(MenuHelp);
        
        MenuAbout = new AppMenu("About", 0x230000, 3,3, this);    
        addMenuItem(MenuAbout);
        
        this.logf = new LogField();
        
        this.add(logf);
        this.logf.setText("Press start...");
     
    }
    
}
