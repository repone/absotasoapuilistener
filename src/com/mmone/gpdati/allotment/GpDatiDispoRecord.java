/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment;

import java.util.StringTokenizer;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.text.StrTokenizer;

/**
 *
 * @author mauro.larese
 */
public class GpDatiDispoRecord {

    public GpDatiDispoRecord() {
    }
    
    private String key;
    private String dateFrom;
    private String dateTo;
    private String structureId;
    private String roomId;
    private String allotment;
    
    public String getKey() { 
        return key;
    }

    public GpDatiDispoRecord(String key) {
        setKey(key) ;
    }

    public void setKey(String key) { 
        this.key = key;
        detokenizeValues(key);
    }
    
    private void detokenizeValues (String value){
        // '1|1|13|2016-10-27|2016-10-28'
        StrTokenizer tokenizer =  new StrTokenizer(key,"|");
        String[] ta = tokenizer.getTokenArray();
        
        structureId = ta[0];
        roomId = ta[1];
        allotment = ta[2];
        dateFrom = ta[3];
        dateTo = ta[4];
        
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getStructureId() {
        return structureId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getAllotment() {
        return allotment;
    }
     
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE); 
    }
    
    public static void main(String[] args) {
        String tk = "1|1|13|2016-10-27|2016-10-28";
        GpDatiDispoRecord d = new GpDatiDispoRecord(tk);
        
        System.out.println(d);
    }
}
