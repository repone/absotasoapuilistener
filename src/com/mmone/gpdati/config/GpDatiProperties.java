/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.config;

/**
 *
 * @author mauro.larese
 */
public class GpDatiProperties {
    private String rpcUser;
    private String rpcPassword; 
    private String rpcUrl; 
    private String dbPath; 
    
    public String getRpcUser() {
        return rpcUser;
    }

    public GpDatiProperties(String rpcUser, String rpcPassword, String rpcUrl, String dbPath) {
        this.rpcUser = rpcUser;
        this.rpcPassword = rpcPassword;
        this.rpcUrl = rpcUrl;
        this.dbPath = dbPath;
    }

    public GpDatiProperties(String rpcUser, String rpcPassword, String rpcUrl) {
        this.rpcUser = rpcUser;
        this.rpcPassword = rpcPassword;
        this.rpcUrl = rpcUrl;
    }

    public GpDatiProperties() {
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public void setRpcUser(String rpcUser) {
        this.rpcUser = rpcUser;
    }

    public String getRpcPassword() {
        return rpcPassword;
    }

    public void setRpcPassword(String rpcPassword) {
        this.rpcPassword = rpcPassword;
    }

    public String getRpcUrl() {
        return rpcUrl;
    }

    public void setRpcUrl(String rpcUrl) {
        this.rpcUrl = rpcUrl;
    }
    
}
