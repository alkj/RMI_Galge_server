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
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author alexander
 */
public class GalgeImpl extends UnicastRemoteObject implements GalgeInterf {
    
    private HashMap<String, GalgeLogik> galgeSpil = new HashMap<String, GalgeLogik>();
    
    public GalgeImpl() throws RemoteException{
        
    }
    
    /**
     *
     * @throws Exception
     */
    @Override
    public void startSpil(String brugerNavn, String kodeOrd) throws Exception {
        if (logInd(brugerNavn, kodeOrd)) {
        galgeSpil.get(brugerNavn).nulstil();
        System.out.println("der er lige blevet startet et spil");
        }
        System.out.println("startSpil() blev kaldt med forkert bruger eller kode");
    }
    
    @Override
    public String getOrd(String brugerNavn, String kodeOrd) throws java.rmi.RemoteException {
        if (logInd(brugerNavn, kodeOrd)) {
            return galgeSpil.get(brugerNavn).getSynligtOrd();
        } else {
            return "getOrd(), men ikke logget ind";
        }
    }
    
    
    @Override
    public String gæt(String brugerNavn, String kodeOrd, String s) throws RemoteException {
        
        if (logInd(brugerNavn, kodeOrd)) {
            
            if (galgeSpil.containsKey(brugerNavn)) {
                galgeSpil.get(brugerNavn).logStatus();
                galgeSpil.get(brugerNavn).gætBogstav(s);
                return galgeSpil.get(brugerNavn).getSynligtOrd();
            } else {
                galgeSpil.put(brugerNavn, new GalgeLogik(brugerNavn, kodeOrd));
                galgeSpil.get(brugerNavn).gætBogstav(s);
                return galgeSpil.get(brugerNavn).getSynligtOrd();
            }
        } else {
            return "forkert brugernavn eller kode";
        }
        
    }
    
    @Override
    public boolean varRigtigt(String brugerNavn, String kodeOrd) throws RemoteException {
        
        if (logInd(brugerNavn, kodeOrd)) {
        return galgeSpil.get(brugerNavn).erSidsteBogstavKorrekt();            
        }
        return false;
    }
    
    @Override
    public int antalFejl(String brugerNavn, String kodeOrd) throws RemoteException {
        if (logInd(brugerNavn, kodeOrd)) {
            System.out.println("spiller " + brugerNavn + " har antal fejl: " + galgeSpil.get(brugerNavn).getAntalForkerteBogstaver());
            return galgeSpil.get(brugerNavn).getAntalForkerteBogstaver();
        } else {
            return -1;
        }
    }
    
    @Override
    public boolean erSpilletVundet(String brugerNavn, String kodeOrd) throws RemoteException {
        if (logInd(brugerNavn, kodeOrd)) {
            if (galgeSpil.get(brugerNavn).erSpilletVundet()) {
                System.out.println("spillet er vundet for " + brugerNavn);
                return true;
            } else return false;
        }
        return false;
    }
    
    @Override
    public boolean erSpilletSlut(String brugerNavn, String kodeOrd) throws RemoteException {
        if (logInd(brugerNavn, kodeOrd)) {
            return galgeSpil.get(brugerNavn).erSpilletSlut();
        }
        return false;
    }
    
    @Override
    public void startIgen(String brugerNavn, String kodeOrd) throws RemoteException {
        if (logInd(brugerNavn, kodeOrd)) {
            
            if (galgeSpil.containsKey(brugerNavn)) {
                try {
                galgeSpil.get(brugerNavn).hentOrdFraDr();                    
                } catch (Exception e) {
                    System.out.println("kunne ikke hente ord");
                }
                System.out.println("spillet er startet");
            } else {
                galgeSpil.put(brugerNavn, new GalgeLogik(brugerNavn, kodeOrd));
                galgeSpil.get(brugerNavn).nulstil();
            }

        } else {
            System.out.println("forkert brugernavn eller kodeord");
        }

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
            return true;
        } catch (Exception e) {
            System.err.println("ikke logget ind");
            System.err.println("forkert brugernavn/kode");
            return false;
            
        }
        
        
        
    }
    
    
}
