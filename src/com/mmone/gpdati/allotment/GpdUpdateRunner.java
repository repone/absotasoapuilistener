/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment;

import com.mmone.gpdati.allotment.record.AllotmentRecord;
import com.mmone.gpdati.config.GpDatiObjectsFactory;
import com.mmone.gpdati.config.GpDatiDataProvvider;
import com.mmone.gpdati.config.GpDatiProperties;
import com.mmone.gpdati.config.GpdDbRoomMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mauro.larese
 */
public class GpdUpdateRunner {
    private GpDatiProperties properties; 
    private GpDatiDataProvvider dataProvvider;
    
    public GpdUpdateRunner(GpDatiProperties properties) {
       this.properties = properties;   
    }
    
    private void startup(){ 
        dataProvvider = new GpDatiDataProvvider(properties);
    }
    private void cleanup(){
        dataProvvider.cleanUp();
    }
    public void run() throws UpdateRunException{
        startup(); 
        try {
            //lettura file GPDATI e parse file
            List<AllotmentRecord> gpdAvail = dataProvvider.getAllotmentRecordList();
        } catch (Exception ex) {
            cleanup();
            throw new UpdateRunException(ex.getClass().getName() + " "+ ex.getMessage()); 
        }
        
        try {
            //recupero le room per hotel  e popolamento mappatura
            GpdDbRoomMap roomMap = dataProvvider.getRoomMap();
        } catch (Exception ex) {
            cleanup();
            throw new UpdateRunException(ex.getClass().getName() + " "+ ex.getMessage());
        }
         
        //Recupero configurazione mappatura
        //leggo indice precedente delle modifiche
        //AGGIORNAMENTO ALLOTMENT
        //svuoto il db con gli indici 
        //creo gli indici
        cleanup();
    }
}
