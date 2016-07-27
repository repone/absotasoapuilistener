/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.config;

import com.mmone.gpdati.allotment.GpDatiUpdateRunner;
import com.mmone.gpdati.allotment.reader.AllotmentFileReader;
import com.mmone.gpdati.allotment.reader.AllotmentLineProvvider;
import com.mmone.gpdati.allotment.record.AllotmentRecordsListBuilder;
import com.mmone.hsqldb.Database;
import com.mmone.otasoapui.AllotmentUpdatePropertiesCollector;
import com.mmone.otasoapui.MissingParametersException;
import com.mmone.otasoapui.SoapUiPropertiesCollector;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcClient;

/**
 *
 * @author mauro.larese
 */
public class GpDatiObjectsFactory {
    private GpDatiXmlRpcConfigurator xmlRpcConfigurator=null;
    private Database database=null; 
    private GpDatiProperties properties=null;
    private GpDatiDbRoomMap roomMap=null;
    private XmlRpcClient xmlRpcClient=null; 
    private AllotmentRecordsListBuilder allotmentRecordsListBuilder = null;
    private GpDatiUpdateRunner gpdUpdateRunner=null;
    private static GpDatiObjectsFactory instance=null;
    
    public static GpDatiObjectsFactory getInstance(){
        if(instance==null)
            instance = new GpDatiObjectsFactory();
        return instance;
    }
    
    public GpDatiUpdateRunner getUpdateRunner() throws MissingParametersException{
        if(gpdUpdateRunner==null){
            gpdUpdateRunner= new GpDatiUpdateRunner( getProperties());
        }     
        return gpdUpdateRunner;
    }
    
    public AllotmentRecordsListBuilder getAllotmentRecordsListBuilder() throws MissingParametersException {
        if(allotmentRecordsListBuilder==null){
            AllotmentLineProvvider afr = new AllotmentFileReader(getProperties().getAvailFileName()); 
            allotmentRecordsListBuilder = new AllotmentRecordsListBuilder(afr,this.getRoomMap());
        }
        return allotmentRecordsListBuilder;
    }
            
    public AllotmentUpdatePropertiesCollector getAllotmentUpdatePropertiesCollector() 
            throws MissingParametersException {
        AllotmentUpdatePropertiesCollector prp = new SoapUiPropertiesCollector(); 
        if(prp.hasError()){
            throw new MissingParametersException(prp.getErrorMessage() );
        }
        
        return prp;
    }
    
    public GpDatiDbRoomMap getRoomMap() throws MissingParametersException {
        if(roomMap==null)
            roomMap = new GpDatiDbRoomMap( getDatabase());
        return roomMap;
    }

    public GpDatiProperties getProperties() throws MissingParametersException {
        if(properties==null)
            properties = new GpDatiProperties( this.getAllotmentUpdatePropertiesCollector() );
        return properties;
    }
 
    public void cleanUp(){
        try {
            if(database!=null){
                database.shutDown();
                database=null;
            }
            
            xmlRpcClient=null;
        } catch (SQLException ex) {
            Logger.getLogger(GpDatiObjectsFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public GpDatiObjectsFactory(  ) {  
         
    }
    
    public GpDatiObjectsFactory( GpDatiProperties prp ) {  
        this.properties = prp; 
    }
    
    public GpDatiObjectsFactory(AllotmentUpdatePropertiesCollector updProperties ) {  
        this(new GpDatiProperties( updProperties ));
    }
    
    public GpDatiXmlRpcConfigurator getGpDatiXmlRpcConfigurator() throws MissingParametersException  {
        if(xmlRpcConfigurator==null)
            xmlRpcConfigurator=new GpDatiXmlRpcConfigurator( this.getProperties() );
        return xmlRpcConfigurator;
    }
    
    public XmlRpcClient getXmlRpcClient() throws XmlRpcConfigErrorException{
        if(xmlRpcClient==null)
            xmlRpcClient = xmlRpcConfigurator.configureRpcClient();
        
        return xmlRpcClient;
    }
     
    public Database getDatabase() throws MissingParametersException {
        if(database==null){
            database=new Database(this.getProperties().getDbPath()); 
        }    
        return database;
    }
    
}
