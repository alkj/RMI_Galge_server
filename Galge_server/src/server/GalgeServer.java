/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.Remote;

/**
 *
 * @author alexander
 */
public class GalgeServer {
    
    public static void main(String[] arg) throws Exception {
        
		java.rmi.registry.LocateRegistry.createRegistry(1234); // start i server-JVM                
                GalgeInterf g = new GalgeImpl();
		Naming.rebind("rmi://localhost:1234/galge", g);
                System.out.println("galge serveren er startet");
                
    }

}

