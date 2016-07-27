/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author mauro.larese
 */
public class AllotmentFileReader implements AllotmentLineProvvider {
    private String fileName;
    private List<String> lines=null;
    
    public AllotmentFileReader(String fileName) {
        this.fileName = fileName;
        
    }
     
    private void loadData() throws IOException{
        File file = new File(fileName);
        lines = FileUtils.readLines(file, "UTF-8");    
    }
    
    public List<String> getLines() throws IOException{
        if(lines==null)
            loadData();
        return lines;
    }
     
    public static void main(String[] args) {
        try {
            AllotmentLineProvvider afr = new AllotmentFileReader("C:/svnprjects/mauro_netbprj/abs-ota-soapui-listener/test/FILE_DISPO__20160616.txt");
            
            List<String>l = afr.getLines();
            for (String s : l) {
                System.out.println(s);
            }
        } catch (Exception ex) {
            Logger.getLogger(AllotmentFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
