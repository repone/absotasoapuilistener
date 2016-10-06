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
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;
/**
 *
 * @author mauro.larese
 */
public class AllotmentUpdateListener implements  RequestFilter{ 
    @Override
    public void filterRequest(SubmitContext sc, Request rqst) { 
        GpDatiProperties gpDatiProperties =new GpDatiProperties(new SoapUiPropertiesCollector());
                
        FileAppender fa = new FileAppender();
        fa.setName("UpdateFileLogger");
        fa.setFile(gpDatiProperties.getAvailFileName()+".log" );
        fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
        fa.setThreshold(org.apache.log4j.Level.INFO);
        fa.setAppend(true);
        fa.activateOptions();

        //add appender to any Logger (here is root)
        SoapUI.log.addAppender(fa);
        
        System.out.println( "AllotmentUpdateListener called by Listener ---------> " + rqst.getName() ); 
  
        System.out.println("Allotment update start");
         
        String p = (String)SoapUI.getGlobalProperties().getPropertyValue("update.done");
        if(p==null){ 
            try {  
                System.out.println("update can start"); 
                SoapUI.getGlobalProperties().setPropertyValue("update.done","true"); 
                GpDatiObjectsFactory
                        .getInstance(gpDatiProperties)
                        .getUpdateRunner().run();
                System.out.println("update run end");
                
                File fdispo = new File(gpDatiProperties.getAvailFileName());
                fdispo.renameTo(new File(gpDatiProperties.getAvailFileName()+".done"));
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
