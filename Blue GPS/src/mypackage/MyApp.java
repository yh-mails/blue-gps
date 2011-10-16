package mypackage;


/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class MyApp
{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
    	if ( args != null && args.length > 0 && args[0].equals("service") ){
    		MyService theApp = new MyService();
    		theApp.enterEventDispatcher();  
    	} else {
            // code to launch the background thread }
    		MyUIApp theApp = new MyUIApp();
            theApp.enterEventDispatcher();  	   
       } 
    }
}
