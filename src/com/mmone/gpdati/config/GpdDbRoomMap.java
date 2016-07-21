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
public class GpdDbRoomMap extends Hashtable<String, GpdRoomRecord> implements IGpdRoomMap {
    Database database;
    public GpdDbRoomMap(Database database) {
        this.database = database;
        try {
            loadAll();
        } catch (SQLException ex) {
            Logger.getLogger(GpdDbRoomMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private void loadAll() throws SQLException{
        String query = "SELECT * FROM PUBLIC.RELATION_GPDATI";
        ResultSetHandler<List<GpdRoomRecord>> h = 
                new BeanListHandler<GpdRoomRecord>(GpdRoomRecord.class);

        List<GpdRoomRecord>recs=database.getQueryRunner().query(query,h);
        
        for (GpdRoomRecord rec : recs) {
            
            //System.out.println( rec.toString());
            if(rec!=null && rec.getGpstru()!=null && rec.getGpstru()!=null)
                this.put(rec.getGpstru(), rec.getGproom(), rec);
            
        } 
    }
    
    private String makeKey(String gpstructure, String gproom){
        return "##"+gpstructure+"##"+gproom+"##" ;
    }
    @Override
    public void put(String gpstructure, String gproom,GpdRoomRecord record) {
        this.put(  makeKey(gpstructure,gproom),record);
    }

    @Override
    public GpdRoomRecord get(String gpstructure, String gproom) {
        return get( makeKey(gpstructure,gproom)  );
    }
 
    public static void main(String[] args) {
        Database db = new Database("D:/tmp_desktop/scrigno-gpdati/data/gpdati.db");
        GpdDbRoomMap m = new GpdDbRoomMap(db); 
         
        MapUtils.debugPrint(System.out, "records", m);
         
        try {
            db.shutDown();
        } catch (SQLException ex) {
            Logger.getLogger(GpdDbRoomMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
