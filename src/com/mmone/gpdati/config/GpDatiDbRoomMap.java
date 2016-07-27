/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.config;

import com.mmone.hsqldb.Database;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable; 
import java.util.Map.Entry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author mauro.larese
 */
public class GpDatiDbRoomMap extends Hashtable<String, GpDatiRoomRecord> implements IGpDatiRoomMap {
    Database database;
    public GpDatiDbRoomMap(Database database) {
        this.database = database;
        try {
            loadAll();
        } catch (SQLException ex) {
            Logger.getLogger(GpDatiDbRoomMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private void loadAll() throws SQLException{
        String query = "SELECT * FROM PUBLIC.RELATION_GPDATI";
        ResultSetHandler<List<GpDatiRoomRecord>> h = 
                new BeanListHandler<GpDatiRoomRecord>(GpDatiRoomRecord.class);

        List<GpDatiRoomRecord>recs=database.getQueryRunner().query(query,h);
        
        for (GpDatiRoomRecord rec : recs) {
            
            //System.out.println( rec.toString());
            if(rec!=null && rec.getGpstru()!=null && rec.getGpstru()!=null)
                this.put(rec.getGpstru(), rec.getGproom(), rec);
            
        } 
    }
    
    private String makeKey(String gpstructure, String gproom){
        return "##"+gpstructure+"##"+gproom+"##" ;
    }
    @Override
    public void put(String gpstructure, String gproom,GpDatiRoomRecord record) {
        this.put(  makeKey(gpstructure,gproom),record);
    }

    @Override
    public GpDatiRoomRecord get(String gpstructure, String gproom) {
        return get( makeKey(gpstructure,gproom)  );
    }
 
    public void debugPrint(){ 
        MapUtils.debugPrint(System.out   , "GpDatiRoomRecords", this);
    }
    
    public void put(String gpstructure, String gproom) {
        GpDatiRoomRecord record = new GpDatiRoomRecord(gpstructure,gproom);
        
        this.put(  makeKey(gpstructure,gproom),record);
    }
    
    public GpDatiRoomRecord insert(String gpStru,String gpRoom) throws SQLException{
        if(this.containsKey(makeKey(gpStru,gpRoom) )) {
            Logger.getLogger(GpDatiDbRoomMap.class.getName()).log(Level.SEVERE,   "already exsist");
            return this.get(gpStru, gpRoom);
        }
            
        String sql = "INSERT INTO relation_gpdati (  GPSTRU , GPROOM ,ABSSTRU ,ABSROOM  ,PERC) "+
                                         " VALUES (  ?      , ?      , ?      ,?        , 0  );";
        
        database.getQueryRunner().update(sql,gpStru,gpRoom,0,0); 
        this.put(gpStru, gpRoom);
        
        return this.get(gpStru, gpRoom);
    }
    
    public static void main(String[] args) {
        Database db = new Database("D:/tmp_desktop/scrigno-gpdati/data/gpdati.db");
        GpDatiDbRoomMap m = new GpDatiDbRoomMap(db); 
         
        //MapUtils.debugPrint(System.out, "Rooms map", m);
    
        try {
            m.insert("zzzs1e", "zezzs1");
        } catch (SQLException ex) {
            Logger.getLogger(GpDatiDbRoomMap.class.getName()).log(Level.SEVERE,   ex.getMessage());
            //Logger.getLogger(GpDatiDbRoomMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            db.shutDown();
        } catch (SQLException ex) {
            Logger.getLogger(GpDatiDbRoomMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
