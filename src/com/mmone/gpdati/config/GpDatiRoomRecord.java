/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.config;

import java.util.Hashtable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author mauro.larese
 */
public class GpDatiRoomRecord {
    private int id;
    private String gpstru;
    private String gproom;

    public GpDatiRoomRecord() {
    }

    public GpDatiRoomRecord(String gpstru, String gproom) {
        this(gpstru,gproom,0,0,0); 
    }

    public GpDatiRoomRecord(String gpstru, String gproom, int absstru, int absroom, int perc) {
        this.gpstru = gpstru;
        this.gproom = gproom;
        this.absstru = absstru;
        this.absroom = absroom;
        this.perc = perc;
    }
    private int absstru;
    private int absroom;
    private int perc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGpstru() {
        return gpstru;
    }

    public void setGpstru(String gpstru) {
        this.gpstru = gpstru;
    }

    public String getGproom() {
        return gproom;
    }

    public void setGproom(String gproom) {
        this.gproom = gproom;
    }

    public int getAbsstru() {
        return absstru;
    }

    public void setAbsstru(int absstru) {
        this.absstru = absstru;
    }

    public int getAbsroom() {
        return absroom;
    }

    public void setAbsroom(int absroom) {
        this.absroom = absroom;
    }

    public int getPerc() {
        return perc;
    }

    public void setPerc(int perc) {
        this.perc = perc;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE); 
    }
}
