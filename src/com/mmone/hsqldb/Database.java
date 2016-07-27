/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.hsqldb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.hsqldb.jdbc.JDBCDataSource;

/**
 *
 * @author mauro.larese
 */
public class Database {
    private Connection conn;
    private QueryRunner qr;

    public QueryRunner getQueryRunner() {
        return qr;
    }
 
    private String dbParh;
    
    public Database(String dbPath) {
        this.dbParh = dbPath;
        try {
            setup();
        } catch (SQLException ex) {
            System.out.println("Error - " + ex.getMessage());
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setup() throws SQLException{
        JDBCDataSource ds = new JDBCDataSource();
        ds.setDatabase("jdbc:hsqldb:file:" + dbParh);
        conn = ds.getConnection("sa", "");
        if(conn==null){
            System.out.println("conn not created");
        }
        qr = new QueryRunner(ds);
          
    }
    
    public void rollback() throws SQLException{
        if(conn!=null){
            conn.rollback();
        }
    }
    public void commit() throws SQLException{
        if(conn!=null){
            conn.commit();
        }
    }
    public void shutDown() throws SQLException{
        if(conn!=null){
            qr.update("SHUTDOWN");
            DbUtils.close(conn);
        }else{
            System.out.println("conn alredy closed");
        }
        
    }
}
