/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment;

import com.mmone.gpdati.config.GpDatiDbRoomMap;
import com.mmone.gpdati.config.GpDatiRoomRecord;
import com.mmone.hsqldb.Database;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author mauro.larese
 */
public class GpDatiDbDispoMap extends Hashtable<String, GpDatiDispoRecord>{
    Database database;
    public GpDatiDbDispoMap(Database database) {
        this.database = database;
        try {
            loadAll();
        } catch (SQLException ex) {
            Logger.getLogger(GpDatiDispoRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadAll() throws SQLException{
        String query = "SELECT * FROM PUBLIC.GPDATI_DISPO";
        ResultSetHandler<List<GpDatiDispoRecord>> h = 
                new BeanListHandler<GpDatiDispoRecord>(GpDatiDispoRecord.class);

        List<GpDatiDispoRecord>recs=database.getQueryRunner().query(query,h);
        
        for (GpDatiDispoRecord rec : recs) {
            this.put(rec.getKey(),rec);
        } 
    }
}
