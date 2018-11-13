/*
 * Main.java
 *
 * Created on May 7, 2006, 6:21 PM
 *
 */

package net.javanetwork.xml.fop;

import net.javanetwork.xml.fop.view.RenderControl;

/**
 *
 * @author John Yeary <jyeary@javanetwork.net>
 * @version 1.0
 */
public class Main {
    
    /**
     * Creates a new instance of Main
     */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RenderControl().setVisible(true);
            }
        });
    } 
}
