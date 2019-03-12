
package server;

public interface GalgeInterf extends java.rmi.Remote{
    
    void startSpil()            throws Exception;
    String getOrd()             throws java.rmi.RemoteException;
    String gæt(String s)        throws java.rmi.RemoteException;
    boolean varRigtigt()        throws java.rmi.RemoteException;
    int antalFejl()             throws java.rmi.RemoteException;
    boolean erSpilletVundet()   throws java.rmi.RemoteException;
    boolean erSpilletSlut()     throws java.rmi.RemoteException;
    void startIgen()            throws java.rmi.RemoteException;
    boolean logInd(String brugerNavn, String kodeOrd)            throws java.rmi.RemoteException;
}
