/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.record;

import com.mmone.gpdati.allotment.reader.AllotmentFileReader;
import com.mmone.gpdati.allotment.reader.AllotmentLineProvvider;
import com.mmone.gpdati.config.GpDatiDbRoomMap;
import com.mmone.gpdati.config.GpDatiRoomRecord;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author mauro.larese
 */
public class AllotmentRecordsListBuilder {
    private AllotmentLineProvvider linePrv;
    private List<AllotmentRecord> records=null;
    private List<AllotmentRecord> grpRecords=null;
    private Map<String,List<AllotmentRecord>> recordsMap=null;
    private GpDatiDbRoomMap roomMap=null;
    
    public AllotmentRecordsListBuilder(AllotmentLineProvvider linePrv) {
        this.linePrv = linePrv;
    }
    public AllotmentRecordsListBuilder(AllotmentLineProvvider linePrv,GpDatiDbRoomMap roomMap) {
        this(linePrv);
        this.roomMap=roomMap; 
    }
    private void buildRecordList() throws Exception{
        List<String> lines = linePrv.getLines();
        records = new ArrayList<AllotmentRecord>();
        recordsMap=new Hashtable<String, List<AllotmentRecord>>();
        
        for (String line : lines) {
            AllotmentRecord r = new AllotmentRecord();
            String h =  line.substring(0, 5).trim() ;
            String rc = line.substring(13,18).trim();
            String dt = line.substring(5,13).trim();
            Integer al =  0;
            
            try {
               al = new Integer( line.substring(18,23).trim() )    ; 
               if(al<0)  al = 0;
            } catch (Exception e) { }
            
            if(roomMap!=null){
                 GpDatiRoomRecord room= roomMap.get(h, rc); 
                 int sid=0;
                 int roomId=0;
                 if(room!=null){ 
                     sid= new Integer(room.getAbsstru())  ;
                     roomId= new Integer(room.getAbsroom())  ;
                     
                 }else{
                     room=roomMap.insert(h, rc);
                 }
                 if(sid!=0 && roomId!=0){
                     int allotment =  al * room.getPerc()/100;
                     allotment=(int)Math.round(allotment*10000   )/10000;
                     
                     r
                        .setHotel( h )
                        .setRcode( rc )
                        .setDate(dt)
                        .setAllotment( allotment )
                        .setHotelId(sid)
                        .setRoomId(roomId) ;
                    ;
                    records.add(r);
                 }
                 
            }else{
                r
                        .setHotel( h )
                        .setRcode( rc )
                        .setDate(dt)
                        .setAllotment( al );
                
                records.add(r);
            }
            
            String smallKey = r.getSmallGroupKey() ;
            List<AllotmentRecord>lr;
            if(  recordsMap.containsKey(  smallKey  )  ){
                lr=recordsMap.get(smallKey) ;
            }else{
                lr = new ArrayList<AllotmentRecord>(); 
                recordsMap.put(smallKey,lr);
            }
            
            lr.add(r);
        }
         
    }
    public List<AllotmentRecord> getRecords() throws Exception{
        if(records == null)
            buildRecordList();
        return records;
    }
    
    public List<AllotmentRecord> getGroupedRecords() throws Exception{
        if(grpRecords == null){
            grpRecords = new ArrayList<AllotmentRecord>();
            buildRecordList();
            AllotmentRecordListRegrouper rgp = new AllotmentRecordListRegrouper(recordsMap);
             
            for (Map.Entry<String, AllotmentRecord> entry : rgp.entrySet()) { 
                AllotmentRecord rec = entry.getValue();
                
                if(rec.isValidRecord())
                    grpRecords.add( rec ); 
            } 
            Collections.sort(grpRecords,new CompareAllotmentRecordByDate());
            //grpRecords.sort(new CompareAllotmentRecordByDate() );
        }    
        return grpRecords;
    }
    
    public Map<String,List<AllotmentRecord>> getMappedRecords() throws Exception{
        if(recordsMap == null)
            buildRecordList();
         
        return recordsMap;
    }
    
    public static void main(String[] args) {
         
        AllotmentLineProvvider afr = new AllotmentFileReader("D:/tmp_desktop/scrigno-gpdati/FILE_DISPO__20160802.txt"); 
        AllotmentRecordsListBuilder arlb = new AllotmentRecordsListBuilder(afr); 
        
         
        try {
            List<AllotmentRecord>records= arlb.getGroupedRecords();
            System.out.println( "Records in list " + records.size()  );
             
            if(true)
                for (AllotmentRecord record : records) {  
                    System.out.println( "---- " + record.getCompleteKey()); 
                }
             
            //MapUtils.debugPrint(System.out , "MAP", arlb.getMappedRecords());
            //MapUtils.debugPrint(System.out , "MAP", reg);
        } catch (Exception ex) {
            Logger.getLogger(AllotmentRecordsListBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
