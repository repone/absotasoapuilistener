/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.reader;
 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger; 
import org.apache.commons.lang3.time.DateUtils;
import org.apache.xmlrpc.XmlRpcClient;

/**
 *
 * @author mauro.larese
 */
public class AvailCrud {
    public static final String AVAIL_ACTION_SET = "set";
    public static final int XRPC_SET_ALLOTMENT_RESULT_ERROR = -1;
    public static final String[] dateParsers = new String[]{"yyyyMMdd", "yyyy-MM-dd"};
    
    public static void saveAllotment(XmlRpcClient client, Integer hotelCode,String startDt, String endDt, Integer roomId ,int bookingLimit)throws Exception{
        Date sdt = DateUtils.parseDate(startDt, dateParsers);
        Date edt = DateUtils.parseDate(endDt, dateParsers);
        
        saveAllotment(client, hotelCode, sdt, edt, roomId, bookingLimit);
    }
    
    public static void saveAllotment(XmlRpcClient client, Integer hotelCode,java.util.Date startDt, java.util.Date endDt, Integer roomId ,int bookingLimit)throws Exception{
        
        int iBookingLimit = new Integer(bookingLimit);

        int xrpcresult = modifyAllotment(client,startDt,endDt, AVAIL_ACTION_SET,iBookingLimit,0,roomId,hotelCode);             
        Logger.getLogger("AvailCrud") .log(Level.INFO, "xrpcresult="+xrpcresult );    
           
    }
    private static int modifyAllotment(
            XmlRpcClient client,
            java.util.Date dateStart,
            java.util.Date dateEnd,
            String action,
            int availability,
            int reservation,
            Integer invCode,
            Integer hotelCode){
        
        Vector parameters=new Vector();   
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        parameters.add(new Integer(hotelCode)); //1
        parameters.add(new Integer(invCode)); //2
        //todo gestire con inventario unico -1
        int rate = 1; ///fisso nr da verificare per iu
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
            Logger.getLogger("AvailCrud").log(Level.INFO, logData);
            
        try { 
            result = (Vector) client.execute("backend.modifyAllotment", parameters); 
            
            
            
        } catch (Exception e) {
            Logger.getLogger("AvailCrud").log(Level.SEVERE, "", e);
            // addError(ResponseBuilder.EWT_UNKNOWN, ResponseBuilder.ERR_SYSTEM_ERROR, "Error on updating  allotment (modifyAllotment)");
            return ret ;
        }
        
        try{
            Map hret = (Map)result.get(0); 
            ret = new Integer(  (String)hret.get("unique_allotment_service_response") );  
            
        }catch(Exception e){
                        

        }
         
        Map hret = (Map)result.get(0); 
        ret = new Integer(  (String)hret.get("unique_allotment_service_response") );  
        Logger.getLogger("AvailCrud").log(Level.INFO, "Xrpc done " );
   
        return ret;
    }
    
}
