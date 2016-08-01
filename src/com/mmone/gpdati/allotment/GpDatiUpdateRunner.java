/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment;

import com.mmone.gpdati.allotment.reader.AllotmentFileReader;
import com.mmone.gpdati.allotment.record.AllotmentRecord;
import com.mmone.gpdati.allotment.record.GpDatiAllotmentFileNotFoundException;
import com.mmone.gpdati.config.GpDatiDataProvvider;
import com.mmone.gpdati.config.GpDatiProperties;
import com.mmone.gpdati.config.GpDatiDbRoomMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author mauro.larese
 */
public class GpDatiUpdateRunner {
    private GpDatiProperties properties; 
    private GpDatiDataProvvider dataProvvider;
    
    public GpDatiUpdateRunner(GpDatiProperties properties) {
       this.properties = properties;   
    }
    
    private void startup(){ 
        dataProvvider = new GpDatiDataProvvider(properties);
    }
    private void cleanup(){
        dataProvvider.cleanUp();
    }
    public void run() throws UpdateRunException, GpDatiAllotmentFileNotFoundException{ 
        startup(); 
        
        try {
            //lettura file GPDATI    
            List<AllotmentRecord> gpdAvail = dataProvvider.getAllotmentRecordList(); 
            for (AllotmentRecord allotmentRecord : gpdAvail) {
                
            }
 
            
        } catch (FileNotFoundException efnf) {
            cleanup();
            throw new GpDatiAllotmentFileNotFoundException(efnf.getClass().getName() + " "+ efnf.getMessage()); 
        } catch (Exception ex) {
            cleanup();
            throw new UpdateRunException(ex.getClass().getName() + " "+ ex.getMessage()); 
        }
          
        
        cleanup();
    }
}
