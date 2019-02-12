/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author alexander
 */
public class GalgeImpl extends UnicastRemoteObject implements GalgeInterf {
    
    private GalgeLogik gl = new GalgeLogik();
    
    public GalgeImpl() throws RemoteException{
        
    }

    /**
     *
     * @throws Exception
     */
    @Override
    public void startSpil() throws Exception {
        gl.hentOrdFraDr();
        gl.nulstil();
        System.out.println("der er lige blevet startet et spil");
    }

    @Override
    public String getOrd() throws java.rmi.RemoteException {
        return gl.getSynligtOrd();
    }

    @Override
    public void gætBogstav(String s) throws java.rmi.RemoteException {
        gl.gætBogstav(s);
        System.out.println(gl.logStatus());
    }

    @Override
    public String gæt(String s) throws RemoteException {
        gl.gætBogstav(s);
        System.out.println("der er lige blevet gættet på bogstav :" + s);
        return gl.getSynligtOrd();


    }

    @Override
    public boolean varRigtigt() {
        return gl.erSidsteBogstavKorrekt();
    }

    @Override
    public int antalFejl() throws RemoteException {
        System.out.println("spilleren har " + gl.getAntalForkerteBogstaver() + " forkerte gæt.");
        return gl.getAntalForkerteBogstaver();
    }

    @Override
    public boolean erSpilletVundet() throws RemoteException {
        if (gl.erSpilletVundet()) {
            System.out.println("spillet er vundet");
        }
        return gl.erSpilletVundet();
    }

    @Override
    public boolean erSpilletSlut() throws RemoteException {
        if (gl.erSpilletVundet()) {
            if (gl.erSpilletVundet()) {
                System.out.println("spillet er vundet");
            }
            if (gl.erSpilletTabt()) {
                System.out.println("spillet er tabt");
            }
        }
        return gl.erSpilletSlut();
    }

    @Override
    public void startIgen() throws RemoteException {
        gl.nulstil();
        System.out.println("spillet er genstartet");
    }

}
