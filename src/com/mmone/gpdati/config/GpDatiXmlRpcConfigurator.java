/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.config;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcClient;

/**
 *
 * @author mauro.larese
 */
public class GpDatiXmlRpcConfigurator implements XmlRpcConfigurator { 
    GpDatiProperties properties;

    public GpDatiXmlRpcConfigurator(GpDatiProperties properties) {
        this.properties = properties; 
    }
   
    
    @Override
    public XmlRpcClient configureRpcClient() throws XmlRpcConfigErrorException {
        try {
            XmlRpcClient client = new XmlRpcClient(properties.getRpcUrl());
            client.setBasicAuthentication(properties.getRpcUser() , properties.getRpcPassword());
            return client;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GpDatiXmlRpcConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            throw new XmlRpcConfigErrorException("Error configuring client "+ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        try {
            XmlRpcClient client = new XmlRpcClient("");
            client.setBasicAuthentication("" , "");
        } catch (MalformedURLException ex) {
            Logger.getLogger(GpDatiXmlRpcConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
