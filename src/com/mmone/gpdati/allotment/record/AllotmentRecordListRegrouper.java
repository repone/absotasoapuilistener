/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.record;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mauro.larese
 */
public class AllotmentRecordListRegrouper extends Hashtable<String, AllotmentRecord >{
    private Map<String,List<AllotmentRecord>> recordsMap;
    public AllotmentRecordListRegrouper(  Map<String,List<AllotmentRecord>> recordsMap ) {
        this.recordsMap=recordsMap;
        doGrouping();
    }
    public AllotmentRecordListRegrouper( AllotmentRecordsListBuilder bld ) {
        try {
            this.recordsMap=bld.getMappedRecords();
        } catch (Exception ex) {
            this.recordsMap = new Hashtable<String, List<AllotmentRecord>>();
            Logger.getLogger(AllotmentRecordListRegrouper.class.getName()).log(Level.SEVERE, null, ex);
        }
        doGrouping();
    }
    
    private void doGrouping(){
        for ( Entry<String, List<AllotmentRecord>> entry : recordsMap.entrySet()) {
            String key = entry.getKey();
            List<AllotmentRecord> records = entry.getValue();
            //System.out.println("k=" + key  );
            
            AllotmentRecord lastRecord=null;
            for (AllotmentRecord record : records) {  
                //System.out.println(lastRecord.getSmallGroupKey()+"-"+record.getSmallGroupKey());
                if( lastRecord!=null && lastRecord.getSmallGroupKey().equals(record.getSmallGroupKey()) && record.getAllotment()==lastRecord.getAllotment()){ 
                    lastRecord.setDateTo(record.getDate() );
                    //System.out.println("----- grouping " + lastRecord.getCompleteKey()+ " -- " + record.getDate()); 
                }else{
                    this.put(record.getGroupKey(), record);
                    lastRecord=record;
                    //System.out.println("----- adding " + lastRecord.getSmallGroupKey()+" lk "+ lastRecord.getCompleteKey() + " count="+this.size()); 
                }
                
            }
            
        }
    }
}
