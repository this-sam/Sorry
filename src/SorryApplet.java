import javax.swing.*;


public class SorryApplet extends JApplet {
	//Called when this applet is loaded into the browser.
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
            		new SorryGame();
                }
            });
        } catch (Exception e) {
            System.err.println("SorryGame didn't complete successfully");
        }
    }
    
    public void start() {
    	
    }
    
    public void stop() {
    	
    	
    	
    }
    
    public void destroy() {
    	
    }
}
