package mypackage;

import java.util.Timer;

import javax.microedition.location.Location;

import net.rim.device.api.command.Command;
import net.rim.device.api.system.Application;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.util.StringProvider;

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
	private Location loc; 
	private long lastGPSRead;
	public MenuItem MenuStart;
	
    public MyScreen()
    {        
        // Set the displayed title of the screen       
        setTitle("Blue GPS");
        setBackground(BackgroundFactory.createSolidBackground(0));
        
        MenuStart = new MenuItem(new StringProvider("Start"), 0x230000, 0);    
        MenuStart.setCommandContext(this);
        StartCommandHandler sch = new StartCommandHandler();
        MenuStart.setCommand(new Command(sch));
        addMenuItem(MenuStart);

        this.logf = new LogField();
        
        this.add(logf);
        this.logf.setText("Press start...");
    }
    
    
    public String getName() {
    	return "MyScreen";
    }
}
