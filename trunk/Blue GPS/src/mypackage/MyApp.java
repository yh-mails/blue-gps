package mypackage;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class MyApp extends UiApplication
{
	public DataContext dc;
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MyApp theApp = new MyApp();
        theApp.enterEventDispatcher();
    }
    

    /**
     * Creates a new MyApp object
     */
    public MyApp()
    {      
    	dc = new DataContext();
        // Push a screen onto the UI stack for rendering.
    	MainScreen ms = new MyScreen();
        pushScreen(ms);
    }
    
    
}
