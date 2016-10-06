/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.writer;

import com.eviware.soapui.SoapUI;
import com.mmone.gpdati.allotment.record.AllotmentRecord;
import com.mmone.gpdati.config.GpDatiProperties;
import com.mmone.gpdati.config.GpDatiXmlRpcConfigurator;
import com.mmone.gpdati.config.XmlRpcConfigErrorException;
import com.mmone.gpdati.config.XmlRpcConfigurator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.FileAppender;
import org.apache.xmlrpc.XmlRpcClient;

/**
 *
 * @author mauro.larese
 */
public class XmlrpcAllotmentWriter implements AllotmentWriter{
    private XmlRpcClient rpcClient;
    private XmlRpcConfigurator configurator;
    public static final String AVAIL_ACTION_SET = "set";
    public static final int XRPC_SET_ALLOTMENT_RESULT_ERROR = -1;
    
    public XmlrpcAllotmentWriter(XmlRpcConfigurator configurator ) {
        try {
            this.rpcClient=configurator.configureRpcClient();
        } catch (XmlRpcConfigErrorException ex) {
            Logger.getLogger(XmlrpcAllotmentWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void writeAllotments(List<AllotmentRecord>allotments) throws ErrorWritingAllotmentException {
        for (AllotmentRecord allotment : allotments) {
            if(allotment.isValidRecord())
                writeAllotment(allotment);
        }
    }
    
    public int writeAllotment(AllotmentRecord record) throws ErrorWritingAllotmentException {
        try { 
            if(!record.isValidRecord()) return -2; 
            //System.out.println("--- " + record.getLongCompleteKey());if(true) return;
            
            return modifyAllotment( record , "set");
        } catch (ParseException ex) {
            Logger.getLogger(XmlrpcAllotmentWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    private int modifyAllotment(AllotmentRecord record , String action) throws ParseException{
        java.util.Date dateStart=record.getJDate();
        java.util.Date dateEnd=record.getJDateTo(); 
        int availability=record.getAllotment();
        int reservation=0;
        Integer invCode=record.getRoomId();
        Integer hotelCode=record.getHotelId();
        
        Vector parameters=new Vector();   
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        parameters.add(new Integer(hotelCode)); //1
        parameters.add(new Integer(invCode)); //2
     
        int rate = 1; 
        parameters.add(new Integer(rate)); //3 offerta
        parameters.add(new Integer(availability)); //4 disponibilità
        parameters.add(new Integer(reservation)); //5 prenotazione
        parameters.add(action); //6  Azione : set,increase,decrease
        parameters.add(df.format(dateStart).toString());  //7
        parameters.add(df.format(dateEnd).toString());  //8
        Vector result = new Vector();
        int ret = XRPC_SET_ALLOTMENT_RESULT_ERROR;
     
        String logData = 
                    "hotelCode="+hotelCode
                +   " - invCode="+invCode
                +   " - offerta="+rate
                +   " - availability="+availability
                +   " - reservation="+reservation
                +   " - action="+action
                +   " - dateStart="+df.format(dateStart).toString()
                +   " - dateEnd="+df.format(dateEnd).toString() 
        ;         
             
        try { 
            result = (Vector) rpcClient.execute("backend.modifyAllotment", parameters); 
            SoapUI.log.info("logger availability updated "+record.getLongCompleteKey());  
        } catch (Exception e) {
            SoapUI.log.info("----------------------------");
            SoapUI.log.info("Error record " + record.getLongCompleteKey());
            SoapUI.log.info(e.getClass().getName() + " "+e.getMessage() );
            SoapUI.log.info("----------------------------");
            return ret ;
        }
        
        try{
            Map hret = (Map)result.get(0); 
            ret = new Integer(  (String)hret.get("unique_allotment_service_response") );  
        }catch(Exception e){   }
         
        Map hret = (Map)result.get(0); 
        ret = new Integer(  (String)hret.get("unique_allotment_service_response") );  
        Logger.getLogger("AvailCrud").log(Level.INFO, "Xrpc done " );
        
        SoapUI.log.info("Xrpc result ="+ret);
        return ret;
    }
    
    public static void main(String[] args) {
          
        GpDatiProperties prop = new GpDatiProperties("otauser","8eWruyEN","http://reservation.cmsone.it/backend/manager/xmlrpc/ser.php");
        XmlRpcConfigurator configurator = new GpDatiXmlRpcConfigurator(prop); 
        XmlrpcAllotmentWriter aw = new XmlrpcAllotmentWriter(configurator);
         
         
    }
}
