/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package galge_server;

import java.rmi.Naming;

/**
 *
 * @author alexander
 */
public class Galge_server {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        java.rmi.registry.LocateRegistry.createRegistry(5477); // start i server-JVM
        galge_server.GalgeInterf g = new galge_server.GalgeImpl();
        Naming.rebind("rmi://130.225.170.204:5477/s165477", g);
        //rmi://javabog.dk/brugeradmin
        //Naming.rebind("rmi://localhost:1234/galge", g);
        System.out.println("galge serveren er startet");
        
    }
    
}
