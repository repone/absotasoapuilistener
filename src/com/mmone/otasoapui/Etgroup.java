package com.mmone.otasoapui;

import java.io.ByteArrayInputStream;


/**
 *
 * @author daniele.fioretto
 */
public class Etgroup {
    
 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Inizio elaborazione documento");
        String pathIN = "//FILESERVER/webdesign/danielefioretto/java/";
        String pathOUT = "";
        System.out.println("Percorso file = "+pathIN);
        
        // Avvio l'elaborazione della risposta
        try{
            DOMParser domParser = new DOMParser(pathIN, pathOUT);
            domParser.AnalisiDOM("XMLSoapResponse.xml");
        }catch(Exception e){
            e.printStackTrace();
        }
       // ByteArrayInputStream b = new  ByteArrayInputStream( "ddd".getBytes());
       // domParser.AnalisiDOM(b);
         
    }
    
}
