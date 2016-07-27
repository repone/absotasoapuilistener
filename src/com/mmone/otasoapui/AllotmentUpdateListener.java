package com.mmone.otasoapui;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.wsdl.submit.RequestFilter;
import com.eviware.soapui.model.iface.Request;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.model.iface.SubmitContext;
import com.mmone.gpdati.allotment.GpDatiUpdateRunner;
import com.mmone.gpdati.allotment.UpdateRunException;
import com.mmone.gpdati.allotment.record.GpDatiAllotmentFileNotFoundException;
import com.mmone.gpdati.config.GpDatiObjectsFactory;
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
  
        System.out.println("update start");
         
        String p = (String)SoapUI.getGlobalProperties().getPropertyValue("update.done");
        if(p==null){ 
            try {  
                System.out.println("update can start");
                GpDatiObjectsFactory.getInstance().getUpdateRunner().run();
                SoapUI.getGlobalProperties().setPropertyValue("update.done","true"); 
            } catch (MissingParametersException ex) {
                Logger.getLogger(AllotmentUpdateListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UpdateRunException ex) {
                Logger.getLogger(AllotmentUpdateListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GpDatiAllotmentFileNotFoundException ex) {
                Logger.getLogger(AllotmentUpdateListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println( "update end");
        }else{
            System.out.println("update alredy started");
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
