/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.record;

import java.text.ParseException;
import java.util.Date; 
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author mauro.larese
 */
public class AllotmentRecord {
    private String rcode;
    private int allotment;
    private String hotel;
    private String date;

    public AllotmentRecord() {
    }

    public String getRcode() {
        return rcode;
    }

    public AllotmentRecord setRcode(String rcode) {
        this.rcode = rcode;
        return this;
    }

    public int getAllotment() {
        return allotment;
    }

    public AllotmentRecord setAllotment(int allotment) { 
        this.allotment = allotment;
        return this;
    }

    public String getHotel() {
        return hotel;
    }

    public AllotmentRecord setHotel(String hotel) {
        this.hotel = hotel;
        return this;
    }

    public String getDate() {
        return date;
    }
    public String getDateYMD() throws ParseException { 
        return DateFormatUtils.format( getJDate()  , "yyyyMMdd");
    }

    public Date getJDate() throws ParseException {
        return  DateUtils.parseDate(date, "ddMMyyyy")     ;
    }
    
    public AllotmentRecord setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE); 
    }
    
}