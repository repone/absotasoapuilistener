/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.record;

import com.mmone.gpdati.allotment.reader.AllotmentFileReader;
import com.mmone.gpdati.allotment.reader.AllotmentLineProvvider;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mauro.larese
 */
public class AllotmentRecordsListBuilder {
    private AllotmentLineProvvider linePrv;
    private List<AllotmentRecord> records=null;
    
    public AllotmentRecordsListBuilder(AllotmentLineProvvider linePrv) {
        this.linePrv = linePrv;
    }
    
    private void buildRecordList() throws Exception{
        List<String> lines = linePrv.getLines();
        records = new ArrayList<AllotmentRecord>();
        
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
            
            r
                .setHotel( h )
                .setRcode( rc )
                .setDate(dt)
                .setAllotment( al )
            ;
            records.add(r);
        }
         
    }
    public List<AllotmentRecord> getRecords() throws Exception{
        if(records == null)
            buildRecordList();
        return records;
    }
    
    public static void main(String[] args) {
        AllotmentLineProvvider afr = new AllotmentFileReader("C:/svnprjects/mauro_netbprj/abs-ota-soapui-listener/test/FILE_DISPO__20160616.txt"); 
        AllotmentRecordsListBuilder arlb = new AllotmentRecordsListBuilder(afr);
        try {
            List<AllotmentRecord>records= arlb.getRecords();
            System.out.println( "Records in list " + records.size()  );
            for (AllotmentRecord record : records) { 
                System.out.println( record );
                System.out.println( "date YMD=" + record.getDateYMD() );
            }
        } catch (Exception ex) {
            Logger.getLogger(AllotmentRecordsListBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
