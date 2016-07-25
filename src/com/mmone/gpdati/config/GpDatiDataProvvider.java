/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.config;

import com.mmone.gpdati.allotment.record.AllotmentRecord;
import java.util.List;

/**
 *
 * @author mauro.larese
 */
public class GpDatiDataProvvider {
    GpDatiObjectsFactory componentsFactory;
    
    public GpDatiDataProvvider(GpDatiProperties p) {
        this.componentsFactory= new GpDatiObjectsFactory(p);
    }

    public GpDatiDataProvvider(GpDatiObjectsFactory componentsFactory) {
        this.componentsFactory = componentsFactory;
    }
    
    public List<AllotmentRecord> getAllotmentRecordList() throws Exception{
        return componentsFactory.getAllotmentRecordsListBuilder().getRecords();
    }
    
    public GpdDbRoomMap getRoomMap() throws Exception{
        return componentsFactory.getRoomMap();
    }
    
    public void cleanUp(){
        componentsFactory.cleanUp();
    }
}
