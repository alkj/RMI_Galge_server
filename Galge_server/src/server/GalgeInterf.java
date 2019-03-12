
package server;

public interface GalgeInterf extends java.rmi.Remote{
    
    void startSpil(String brugerNavn, String kodeOrd)            throws Exception;
    String getOrd(String brugerNavn, String kodeOrd)             throws java.rmi.RemoteException;
    String gæt(String brugerNavn, String kodeOrd, String s)        throws java.rmi.RemoteException;
    boolean varRigtigt(String brugerNavn, String kodeOrd)        throws java.rmi.RemoteException;
    int antalFejl(String brugerNavn, String kodeOrd)             throws java.rmi.RemoteException;
    boolean erSpilletVundet(String brugerNavn, String kodeOrd)   throws java.rmi.RemoteException;
    boolean erSpilletSlut(String brugerNavn, String kodeOrd)     throws java.rmi.RemoteException;
    void startIgen(String brugerNavn, String kodeOrd)            throws java.rmi.RemoteException;
    boolean logInd(String brugerNavn, String kodeOrd)            throws java.rmi.RemoteException;
}
