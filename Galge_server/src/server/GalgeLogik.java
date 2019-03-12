package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;




public class GalgeLogik {
    
  /** AHT afprøvning er muligeOrd synlig på pakkeniveau */
  ArrayList<String> muligeOrd = new ArrayList<String>();
  private String ordet;
  private ArrayList<String> brugteBogstaver = new ArrayList<String>();
  private String synligtOrd;
  private int antalForkerteBogstaver;
  private boolean sidsteBogstavVarKorrekt;
  private boolean spilletErVundet;
  private boolean spilletErTabt;
  public String brugerNavn;
  public String kodeOrd;


  public ArrayList<String> getBrugteBogstaver() {
    return brugteBogstaver;
  }

  public String getSynligtOrd() {
    return synligtOrd;
  }

  public String getOrdet() {
    return ordet;
  }

  public int getAntalForkerteBogstaver() {
    return antalForkerteBogstaver;
  }

  public boolean erSidsteBogstavKorrekt() {
    return sidsteBogstavVarKorrekt;
  }

  public boolean erSpilletVundet() {
    return spilletErVundet;
  }

  public boolean erSpilletTabt() {
    return spilletErTabt;
  }

  public boolean erSpilletSlut() {
    return spilletErTabt || spilletErVundet;
  }


  public GalgeLogik(String brugerNavn, String kodeOrd) {
      this.brugerNavn=brugerNavn;
      this.kodeOrd=kodeOrd;
      muligeOrd.add("bil");
      muligeOrd.add("computer");
      muligeOrd.add("programmering");
      muligeOrd.add("motorvej");
      muligeOrd.add("busrute");
      muligeOrd.add("gangsti");
      muligeOrd.add("skovsnegl");
      muligeOrd.add("solsort");
      muligeOrd.add("seksten");
      muligeOrd.add("sytten");
      muligeOrd.add("atten");
      nulstil();
  }

  public void nulstil() {
    brugteBogstaver.clear();
    antalForkerteBogstaver = 0;
    spilletErVundet = false;
    spilletErTabt = false;
    ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
    opdaterSynligtOrd();
  }


  private void opdaterSynligtOrd() {
    synligtOrd = "";
    spilletErVundet = true;
    for (int n = 0; n < ordet.length(); n++) {
      String bogstav = ordet.substring(n, n + 1);
      if (brugteBogstaver.contains(bogstav)) {
        synligtOrd = synligtOrd + bogstav;
      } else {
        synligtOrd = synligtOrd + "*";
        spilletErVundet = false;
      }
    }
  }

  public void gætBogstav(String bogstav) {
    if (bogstav.length() != 1) return;
    System.out.println("Der gættes på bogstavet: " + bogstav);
    if (brugteBogstaver.contains(bogstav)) return;
    if (spilletErVundet || spilletErTabt) return;

    brugteBogstaver.add(bogstav);

    if (ordet.contains(bogstav)) {
      sidsteBogstavVarKorrekt = true;
      System.out.println("Bogstavet var korrekt: " + bogstav);
    } else {
      // Vi gættede på et bogstav der ikke var i ordet.
      sidsteBogstavVarKorrekt = false;
      System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
      antalForkerteBogstaver = antalForkerteBogstaver + 1;
      if (antalForkerteBogstaver > 6) {
        spilletErTabt = true;
      }
    }
    opdaterSynligtOrd();
  }

  public String logStatus() {
    String str = "";
    str += "---------- \n";
    str += "- ordet (skult) = " + ordet + "\n";
    str += "- synligtOrd = " + synligtOrd + "\n";
    str += "- forkerteBogstaver = " + antalForkerteBogstaver + "\n";
    str += "- brugeBogstaver = " + brugteBogstaver + "\n";
    if (spilletErTabt) str += "- SPILLET ER TABT\n";
    if (spilletErVundet) str += "- SPILLET ER VUNDET\n";
    str += ("---------- \n\n");
    System.out.println(str);
    return str;
  }


  public static String hentUrl(String url) throws IOException {
    System.out.println("Henter data fra " + url);
    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    StringBuilder sb = new StringBuilder();
    String linje = br.readLine();
    while (linje != null) {
      sb.append(linje + "\n");
      linje = br.readLine();
    }
    return sb.toString();
  }


  public void hentOrdFraDr() throws Exception {
      
      /*
    String data = hentUrl("https://dr.dk");
    //System.out.println("data = " + data);

    data = data.substring(data.indexOf("<body")). // fjern headere
            replaceAll("<.+?>", " ").toLowerCase(). // fjern tags
            replaceAll("&#198;", "æ"). // erstat HTML-tegn
            replaceAll("&#230;", "æ"). // erstat HTML-tegn
            replaceAll("&#216;", "ø"). // erstat HTML-tegn
            replaceAll("&#248;", "ø"). // erstat HTML-tegn
            replaceAll("&oslash;", "ø"). // erstat HTML-tegn
            replaceAll("&#229;", "å"). // erstat HTML-tegn
            replaceAll("[^a-zæøå]", " "). // fjern tegn der ikke er bogstaver
            replaceAll(" [a-zæøå] "," "). // fjern 1-bogstavsord
            replaceAll(" [a-zæøå][a-zæøå] "," "); // fjern 2-bogstavsord

    System.out.println("data = " + data);
    System.out.println("data = " + Arrays.asList(data.split("\\s+")));
    muligeOrd.clear();
    muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

    System.out.println("muligeOrd = " + muligeOrd);
    nulstil();
    */
      
      
      Client cl = ClientBuilder.newClient();
      Response rs = cl.target("https://www.dr.dk/mu-online/api/1.4/schedule/nownext/dr1").request(MediaType.APPLICATION_JSON).get();
      String stri = rs.readEntity(String.class);
      JSONObject json = new JSONObject(stri);
      
      
      ArrayList<String> galgeOrd = new ArrayList<String>();
      
      String str = json.getJSONObject("Now").getString("Description");
      str = str.replace(",", "");
      str = str.replace(".", "");
      str = str.toLowerCase();
      String[] temp = str.split(" ");
      
      for (int i = 0; i < temp.length; i++) {
          temp[i] = temp[i].trim();
          if (temp[i].length()>5) {
              galgeOrd.add(temp[i]);
              System.out.println( temp[i] );
          }
      }
      
      muligeOrd.clear();
      muligeOrd.addAll(galgeOrd);
      System.out.println(muligeOrd);
      nulstil();
    
    
  }
}
