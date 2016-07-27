/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.config;

import com.mmone.gpdati.allotment.record.AllotmentRecord;
import com.mmone.otasoapui.AllotmentUpdatePropertiesCollector;
import com.mmone.otasoapui.SoapUiPropertiesCollector;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;

/**
 *
 * @author mauro.larese
 */
public class GpDatiDataProvvider {
    GpDatiObjectsFactory componentsFactory;
 
    public GpDatiDataProvvider(AllotmentUpdatePropertiesCollector ap) {
        this(new GpDatiProperties(ap));
    }

    
    public GpDatiDataProvvider(GpDatiProperties p) {
        this(new GpDatiObjectsFactory(p));
    }

    public GpDatiDataProvvider(GpDatiObjectsFactory componentsFactory) {
        this.componentsFactory = componentsFactory;
    }
    
    public List<AllotmentRecord> getAllotmentRecordList() throws Exception{
        return componentsFactory.getAllotmentRecordsListBuilder().getRecords();
    }
    
    public GpDatiDbRoomMap getRoomMap() throws Exception{
        return componentsFactory.getRoomMap();
    }
    
    public void cleanUp(){
        componentsFactory.cleanUp();
    }
     
}
