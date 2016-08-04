/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.record;

import java.text.ParseException;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mauro.larese
 */
public class CompareAllotmentRecordByDate implements Comparator<AllotmentRecord> {

    @Override
    public int compare(AllotmentRecord o1, AllotmentRecord o2) {
        try { 
            
            int compResult = o1.getCompareString().compareTo(o2.getCompareString()) ;
            return compResult ;
        } catch (Exception ex) { 
            System.out.println("Comparing " + o1.getCompareString() + " and " + o2.getCompareString());
            System.out.println("compare Exception "+ex.getClass().getName()+" "+ex.getMessage());
            return 0;
        }
    }
    
}
