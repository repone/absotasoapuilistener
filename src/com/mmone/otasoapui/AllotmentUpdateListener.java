package com.mmone.otasoapui;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.wsdl.submit.RequestFilter;
import com.eviware.soapui.model.iface.Request;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.model.iface.SubmitContext;
import com.mmone.gpdati.allotment.GpDatiUpdateRunner;
import com.mmone.gpdati.allotment.UpdateRunException;
import com.mmone.gpdati.allotment.reader.AllotmentFileReader;
import com.mmone.gpdati.allotment.record.GpDatiAllotmentFileNotFoundException;
import com.mmone.gpdati.config.GpDatiObjectsFactory;
import com.mmone.gpdati.config.GpDatiObjectsFactoryNullException;
import com.mmone.gpdati.config.GpDatiProperties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author mauro.larese
 */
public class AllotmentUpdateListener implements  RequestFilter{ 
    @Override
    public void filterRequest(SubmitContext sc, Request rqst) { 
        System.out.println( "AllotmentUpdateListener called by Listener ---------> " + rqst.getName() ); 
  
        System.out.println("Allotment update start");
         
        String p = (String)SoapUI.getGlobalProperties().getPropertyValue("update.done");
        if(p==null){ 
            try {  
                System.out.println("update can start"); 
                SoapUI.getGlobalProperties().setPropertyValue("update.done","true"); 
                GpDatiObjectsFactory
                        .getInstance(new GpDatiProperties(new SoapUiPropertiesCollector()))
                        .getUpdateRunner().run();
                System.out.println("update run end");
                
            } catch (Exception ex) {
                Logger.getLogger(AllotmentFileReader.class.getName()).log(Level.INFO, "******************************************************************");
                Logger.getLogger(AllotmentFileReader.class.getName()).log(Level.INFO, "skipping sync "+ ex.getMessage());
                Logger.getLogger(AllotmentFileReader.class.getName()).log(Level.INFO, "******************************************************************");
                //System.out.println("update run exception " + ex.getClass().getName() +ex.getMessage());
                System.out.println("Stopping Allotment update runner ..."); 
            }finally{
                System.out.println( "Allotment update end");
            }   
            
        }else{
            System.out.println("Allotment update alredy started");
        } 
    }
 
    
    @Override
    public void afterRequest(SubmitContext sc, Request rqst) {
        //code
        
    }

    @Override
    public void afterRequest(SubmitContext sc, Response rspns) {
         
    }
}
