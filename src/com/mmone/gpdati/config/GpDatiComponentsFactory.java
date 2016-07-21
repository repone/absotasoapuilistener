/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.config;

import com.mmone.hsqldb.Database;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcClient;

/**
 *
 * @author mauro.larese
 */
public class GpDatiComponentsFactory {
    GpDatiXmlRpcConfigurator xmlRpcConfigurator=null;
    Database database=null; 
    GpDatiProperties properties;
    GpdDbRoomMap roomMap=null;
    XmlRpcClient xmlRpcClient=null;

    public GpdDbRoomMap getRoomMap() {
        if(roomMap==null)
            roomMap = new GpdDbRoomMap(database);
        return roomMap;
    }

     
    public void cleanUp(){
        try {
            database.shutDown();
            xmlRpcClient=null;
        } catch (SQLException ex) {
            Logger.getLogger(GpDatiComponentsFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public GpDatiComponentsFactory(GpDatiProperties properties ) {  
        this.properties=properties;
    }
    
    public GpDatiXmlRpcConfigurator getGpDatiXmlRpcConfigurator()  {
        if(xmlRpcConfigurator==null)
            xmlRpcConfigurator=new GpDatiXmlRpcConfigurator( properties );
        return xmlRpcConfigurator;
    }
    
    public XmlRpcClient getXmlRpcClient() throws XmlRpcConfigErrorException{
        if(xmlRpcClient==null)
            xmlRpcClient = xmlRpcConfigurator.configureRpcClient();
        
        return xmlRpcClient;
    }
    
    public Database getDatabase() {
        if(database==null)
            database=new Database(properties.getDbPath());
        return database;
    }
    
}
