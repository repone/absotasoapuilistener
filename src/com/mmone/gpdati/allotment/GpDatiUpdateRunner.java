/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment; 

import com.mmone.gpdati.allotment.record.AllotmentRecord;
import com.mmone.gpdati.allotment.record.GpDatiAllotmentFileNotFoundException;
import com.mmone.gpdati.allotment.writer.XmlrpcAllotmentWriter;
import com.mmone.gpdati.config.GpDatiDataProvvider;
import com.mmone.gpdati.config.GpDatiProperties;
import com.mmone.gpdati.config.GpDatiObjectsFactory;
import com.mmone.otasoapui.MissingParametersException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mauro.larese
 */
public class GpDatiUpdateRunner {
    private GpDatiProperties properties; 
    private GpDatiDataProvvider dataProvvider;
    private XmlrpcAllotmentWriter allotmentWriter ;
    private static Logger logger=Logger.getLogger(GpDatiUpdateRunner.class.getName());
    public GpDatiUpdateRunner(GpDatiProperties properties) {
       this.properties = properties;   
    }
    
    private void startup() throws MissingParametersException{ 
        GpDatiObjectsFactory f = GpDatiObjectsFactory.getInstance(properties);
        dataProvvider = new GpDatiDataProvvider( f ); 
        allotmentWriter = f.getXmlrpcAllotmentWriter(); 
    }
    
    private void cleanup(){
        dataProvvider.cleanUp();
        
    }
 
    public void run() throws UpdateRunException, GpDatiAllotmentFileNotFoundException, MissingParametersException{ 
        startup(); 
        
        try {
            //lettura file GPDATI    
            logger.log(Level.INFO, "getAllotmentRecordList");
            List<AllotmentRecord> gpdAvail = dataProvvider.getAllotmentRecordList();  
            
            logger.log(Level.INFO, "gpdAvail.size= "+gpdAvail.size());
            allotmentWriter.writeAllotments(gpdAvail);
             
        } catch (FileNotFoundException efnf) {
            cleanup();
            logger.log(Level.SEVERE, "run", efnf.getMessage());
            throw new GpDatiAllotmentFileNotFoundException(efnf.getClass().getName() + " "+ efnf.getMessage()); 
        } catch (Exception ex) {
            cleanup();
            logger.log(Level.SEVERE, "run", ex.getMessage());
            throw new UpdateRunException(ex.getClass().getName() + " "+ ex.getMessage()); 
        }
          
        
        cleanup();
    }
}
