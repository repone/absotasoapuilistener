/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.writer;

import com.mmone.gpdati.allotment.record.AllotmentRecord;
import com.mmone.gpdati.config.GpDatiProperties;
import com.mmone.gpdati.config.GpDatiXmlRpcConfigurator;
import com.mmone.gpdati.config.XmlRpcConfigErrorException;
import com.mmone.gpdati.config.XmlRpcConfigurator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcClient;

/**
 *
 * @author mauro.larese
 */
public class XmlrpcAllotmentWriter implements AllotmentWriter{
    private XmlRpcClient rpcClient;
    private XmlRpcConfigurator configurator;
    
    public XmlrpcAllotmentWriter(XmlRpcConfigurator configurator ) {
        try {
            this.rpcClient=configurator.configureRpcClient();
        } catch (XmlRpcConfigErrorException ex) {
            Logger.getLogger(XmlrpcAllotmentWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void writeAllotment(List<AllotmentRecord>allotments) throws ErrorWritingAllotmentException {
        for (AllotmentRecord allotment : allotments) {
            
            /*
            try {
                
                
                AvailCrud.saveAllotment(
                    rpcClient,
                    allotment.getHotel(),
                    allotment.getJDate(),
                    allotment.getJDate(),
                    allotment.getRcode(), 
                    allotment.getAllotment()
                );
            } catch (ParseException ex) {
                Logger.getLogger(XmlrpcAllotmentWriter.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
    }
    
    public static void main(String[] args) {
          
        try {
            GpDatiProperties prop = new GpDatiProperties("otauser","8eWruyEN","http://reservation.cmsone.it/backend/manager/xmlrpc/ser.php");
            XmlRpcConfigurator configurator = new GpDatiXmlRpcConfigurator(prop);
            XmlRpcClient rpc = configurator.configureRpcClient();
        } catch (XmlRpcConfigErrorException ex) {
            Logger.getLogger(XmlrpcAllotmentWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}
