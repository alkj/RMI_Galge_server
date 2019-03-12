/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package server;

import brugerautorisation.transport.rmi.Brugeradmin;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author alexander
 */
public class GalgeImpl extends UnicastRemoteObject implements GalgeInterf {
    
    private GalgeLogik gl = new GalgeLogik();
    private boolean erLoggetInd = false;
    
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
        if (erLoggetInd) {            
        return gl.getSynligtOrd();
        } else {
            return "ikke logget ind";
        }
    }
    
    
    @Override
    public String gæt(String s) throws RemoteException {
        
        if (erLoggetInd) {            
        gl.gætBogstav(s);
        System.out.println("der er lige blevet gættet på bogstav :" + s);
        return gl.getSynligtOrd();
        }
        return "du er ikke logget ind";
        
        
    }
    
    @Override
    public boolean varRigtigt() {
        if (erLoggetInd) {
        return gl.erSidsteBogstavKorrekt();            
        }
        return false;
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
    
    @Override
    public boolean logInd(String brugerNavn, String kodeOrd) throws RemoteException {
        
        Brugeradmin ba;
        String br = brugerNavn;
        String ko = kodeOrd;

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
            ba.hentBruger(br, ko);
            System.out.println("--------logget ind--------");
            this.erLoggetInd = true;
            return this.erLoggetInd;
        } catch (Exception e) {
            System.err.println("ikke logget ind");
            System.err.println("forkert brugernavn/kode");
            this.erLoggetInd=false;
            return this.erLoggetInd;
            
        }
        
    }
    
    
}
