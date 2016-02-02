package com.mmone.otasoapui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.io.FileNotFoundException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXParseException;


/**
 *
 * @author daniele.fioretto
 */
public class DOMParser {
    
    private Document d;
    private Element r;
    private String directoryIn;
    private String directoryOut;
    private String file;
    private InputStream is;
    String  outfile = "error_writing_file";
    
    private String hotelReservationID="" ;
    private String echoToken="" ;

    /**
    *
    * @author daniele.fioretto
    * 
    * Costruttore
    * - directoryIn: directory dove si trova l'xml con la risposta SOAP da processare (terminata con /)
    * - directoryOut: directory dove vengono scritti i file in output
    */
    public DOMParser (String directoryIn, String directoryOut) {
        this.directoryIn = directoryIn;
        this.directoryOut = directoryOut;
    }
    
    
    /**
    *
    * @author daniele.fioretto
    * 
    * Chiamare questo metodo se la risposta xml si trova su un file
    */
    public void AnalisiDOM(String file) {
        try {
                this.file = file;
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder domParser = dbf.newDocumentBuilder();

                d = domParser.parse(new File(directoryIn + file));
                r = d.getDocumentElement();
                esaminaNodo(r);
        }
        catch(SAXParseException e) {
            System.out.println("Errore di parsing: "+ e.getMessage());
            //System.exit(1);
            return;
        }
        catch(FileNotFoundException e) {
            System.out.println("File "+ directoryIn + file + " non trovato");
            //System.exit(1);
            return;
        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("getHotelReservationID = " + getHotelReservationID());
        System.out.println("getEchoToken = " + getEchoToken());
    }// AnalisiDOM
    
    
    /**
    *
    * @author daniele.fioretto
    * 
    * Chiamare questo metodo se la risposta xml si trova su un file
    */
    public void AnalisiDOM(InputStream is) {
        try {
                this.is = is;
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder domParser = dbf.newDocumentBuilder();

                d = domParser.parse(this.is);
                r = d.getDocumentElement();
                esaminaNodo(r);
        }
        catch(SAXParseException e) {
            System.out.println("Errore di parsing: "+ e.getMessage());
            return;
        }
        catch(FileNotFoundException e) {
            System.out.println("File "+ directoryIn + file + " non trovato");
            return;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("getHotelReservationID = " + getHotelReservationID());
        System.out.println("getEchoToken = " + getEchoToken());
    }// AnalisiDOM
    
    
    // versione standard
    /*public void esaminaNodo(Node nodo) {
		switch(nodo.getNodeType()) {
			case Node.ELEMENT_NODE:
				System.out.println("Elemento: " + nodo.getNodeName());
				NamedNodeMap attributi = nodo.getAttributes();
				if(attributi.getLength() > 0) {
					System.out.println("\tAttributi: ");
					for(int i=0; i<attributi.getLength(); i++) {
						Attr attributo = (Attr) attributi.item(i);
						System.out.print(attributo.getNodeName() + "=" + attributo.getNodeValue() + " ");
					}
					System.out.println();
				}
				esaminaFigli(nodo.getChildNodes());
				System.out.println("Fine elemento: " + nodo.getNodeName());
				break;
		case Node.CDATA_SECTION_NODE:
		case Node.TEXT_NODE:
			Text testo = (Text)nodo;
			if(!testo.getNodeValue().trim().equals(""))
				System.out.println("\tTesto; " + testo.getNodeValue());
			break;
		}
    }// esaminaNodo
    */
    
    // versione mia
    public String esaminaNodo(Node nodo) {
        String echoT = "";        
        String hotelResID = "";        
        StringBuffer hotelReservation = new StringBuffer();
                
		switch(nodo.getNodeType()) {
			case Node.ELEMENT_NODE:
                                    //System.out.println("node: " + nodo.getNodeName());
                                    hotelReservation.append("<").append(nodo.getNodeName());
                                    
                                    NamedNodeMap attributi = nodo.getAttributes();
                                    if(attributi.getLength() > 0) {
					for(int i=0; i<attributi.getLength(); i++) {
						Attr attributo = (Attr) attributi.item(i);
                                                hotelReservation.append(" ").append(attributo.getNodeName()).append("=\"").append(attributo.getNodeValue()).append("\"");
                                                if (attributo.getNodeName().equals("ResID_Value")) {
                                                    outfile = attributo.getNodeValue().replaceAll("/", "_");
                                                    hotelResID = attributo.getNodeValue();
                                                }else if (attributo.getNodeName().equals("EchoToken")) {
                                                    echoT = attributo.getNodeValue();
                                                }
					}
                                    }
                                    hotelReservation.append(">");
                                    String figli = esaminaFigli(nodo.getChildNodes());
                                    if (!figli.equals(""))
                                        hotelReservation.append(figli+"\n");
                                    hotelReservation.append("</").append(nodo.getNodeName()).append(">");
                                    
                                    if (nodo.getNodeName().equals("HotelReservationID")) {
                                        this.hotelReservationID = hotelResID; 
                                    } else if (nodo.getNodeName().equals("OTA_ResRetrieveRS")) {
                                        this.echoToken = echoT;
                                    } else if (nodo.getNodeName().equals("HotelReservation")) {
                                        //System.out.println(hotelReservation.toString());
                                        try{
                                            // Create file
                                            //System.out.println(outfile);
                                            //FileWriter fstream = new FileWriter(directoryOut + outfile);
                                            FileWriter fstream = new FileWriter(directoryOut );
                                            BufferedWriter out = new BufferedWriter(fstream);
                                            out.write(hotelReservation.toString());
                                            //Close the output stream
                                            out.close();
                                            } catch (Exception e){//Catch exception if any
                                                System.err.println("Error: " + e.getMessage());
                                            }
                                        
                                        
                                    }
                                    break;
                            
		case Node.CDATA_SECTION_NODE:
		case Node.TEXT_NODE:
			Text testo = (Text)nodo;
			if(!testo.getNodeValue().trim().equals(""))
                                hotelReservation.append(testo.getNodeValue());
                        break;
                        
		}
                
                return hotelReservation.toString();
    }// esaminaNodo
    
    
    public String esaminaFigli(NodeList figli) {
        StringBuffer xmlFigli = new StringBuffer();
		for(int i=0; i<figli.getLength(); i++)
			xmlFigli.append(esaminaNodo(figli.item(i)));
        return xmlFigli.toString();
    }// esaminaFigli

    /**
     * @return the hotelReservationID
     */
    public String getHotelReservationID() {
        return hotelReservationID;
    }

    /**
     * @return the echoToken
     */
    public String getEchoToken() {
        return echoToken;
    }
    
}
