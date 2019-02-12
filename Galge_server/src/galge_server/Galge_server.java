
package galge_server;

import java.rmi.Naming;

public class Galge_server {
    

    public static void main(String[] args) throws Exception {
        
        //java.rmi.registry.LocateRegistry.createRegistry(5477); // start i server-JVM
        //Naming.rebind("rmi://130.225.170.204:5477/s165477", g);
        //rmi://javabog.dk/brugeradmin

        java.rmi.registry.LocateRegistry.createRegistry(1234);
        GalgeInterface g = new GalgeImplementation();
        Naming.rebind("rmi://localhost:1234/galge", g);
        System.out.println("galge serveren er startet");
        
        
        /*
        java.rmi.registry.LocateRegistry.createRegistry(1234); // start i server-JVM                
        GalgeInterface g = new GalgeImplementation();
        Naming.rebind("rmi://localhost:1234/galge", g);
        System.out.println("galge serveren er startet");
        */
        
        
    }
    
}
