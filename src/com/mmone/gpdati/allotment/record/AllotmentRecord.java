/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.record;

import java.text.ParseException;
import java.util.Date; 
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private int roomId=0;
    private int allotment;
    private String hotel;
    private int hotelId=0; 
    private String dateFrom;
    private String dateTo;
    
    public int getRoomId() {
        return roomId;
    }

    public AllotmentRecord setRoomId(int roomId) {
        this.roomId = roomId;
        return this;
    }

    public int getHotelId() {
        return hotelId;
    }

    public AllotmentRecord setHotelId(int hotelId) {
        this.hotelId = hotelId;
        return this;
    }
    

    public AllotmentRecord() {
    }
     
    public String getCompleteKey(){
        return hotel+"|"+rcode+"|"+dateFrom+"|"+dateTo+"|"+allotment;
    }
    public String getLongCompleteKey(){
        return hotel+"|"+rcode+"|"+dateFrom+"|"+dateTo+"|"+allotment+"|"+hotelId+"|"+roomId;
    }
    public String getCompareString(){
        try {
            return hotel+"|"+rcode+"|"+getDateYMD();
        } catch (ParseException ex) {
            Logger.getLogger(AllotmentRecord.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String getLongGroupKey(){
        return hotel+"|"+rcode+"|"+dateFrom+"|"+allotment;
    }
    public String getGroupKey(){
        return hotel+"|"+rcode+"|"+dateFrom;
    }
    public String getSmallGroupKey(){
        return hotel+"|"+rcode ;
    }
    public boolean isValidRecord(){ 
        if(hotel==null || hotel.equals("0")) return false;
        if(rcode==null || rcode.equals("0")) return false;
        if(dateFrom==null  ) return false;
        if(dateTo==null  ) return false;
        return true;
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

    public String getDateTo() {
        return dateTo;
    }
    public String getDateToYMD() throws ParseException {
        return DateFormatUtils.format( getJDateTo()  , "yyyyMMdd");
    }

    public String getDateTo(String dtFormat) throws ParseException {
        return DateFormatUtils.format( getJDateTo()  , dtFormat);
    }

    public String getDateToY_M_D() throws ParseException {
        String dtFormat="yyyy-MM-dd";
        return DateFormatUtils.format( getJDateTo(), dtFormat);
    }
    public String getDateToD_M_Y() throws ParseException {
        String dtFormat="dd-MM-yyyy";
        return DateFormatUtils.format( getJDateTo(), dtFormat);
    }

    public Date getJDateTo() throws ParseException {
        return  DateUtils.parseDate(dateTo, "ddMMyyyy")     ;
    }

    public AllotmentRecord setDateTo(String date) {
        this.dateTo = date;
        return this;
    }

    public String getDate() {
        return dateFrom;
    }
    public String getDateYMD() throws ParseException { 
        return DateFormatUtils.format( getJDate()  , "yyyyMMdd");
    }

    public String getDate(String dtFormat) throws ParseException { 
        return DateFormatUtils.format( getJDate()  , dtFormat);
    }
    
    public String getDateY_M_D() throws ParseException { 
        String dtFormat="yyyy-MM-dd";
        return DateFormatUtils.format( getJDate()  , dtFormat);
    }
    public String getDateD_M_Y() throws ParseException { 
        String dtFormat="dd-MM-yyyy";
        return DateFormatUtils.format( getJDate()  , dtFormat);
    }
    
    public Date getJDate() throws ParseException {
        return  DateUtils.parseDate(dateFrom, "ddMMyyyy")     ;
    }
    
    public AllotmentRecord setDate(String date) {
        this.dateFrom = date;
        this.dateTo = date;
        return this;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE); 
    }
    
}
