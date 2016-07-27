/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.config;

import com.mmone.otasoapui.AllotmentUpdatePropertiesCollector;
import java.util.Date;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author mauro.larese
 */
public class GpDatiProperties {
    private String rpcUser;
    private String rpcPassword; 
    private String rpcUrl; 
    private String dbPath; 
    private String availFileName; 
    private boolean availUpdated; 
    
    public GpDatiProperties(AllotmentUpdatePropertiesCollector p) {
        this( p.getRpcUser(),p.getRpcPwd(),p.getRpcUrl(),p.getDbPath(),p.getAvailFile());
    }
    public GpDatiProperties(String rpcUser, String rpcPassword, String rpcUrl, String dbPath, String availFileName) {
        this(rpcUser,rpcPassword,rpcUrl,dbPath);
        this.availFileName = availFileName;
    }
     
    public GpDatiProperties(String rpcUser, String rpcPassword, String rpcUrl, String dbPath) {
        this(rpcUser,rpcPassword,rpcUrl); 
        this.dbPath = dbPath;
    }

    public GpDatiProperties(String rpcUser, String rpcPassword, String rpcUrl) {
        this(); 
        this.rpcUser = rpcUser;
        this.rpcPassword = rpcPassword;
        this.rpcUrl = rpcUrl;
    }

    public GpDatiProperties() {
    }
    
     public String getAvailFileName() {
        return availFileName;
    }
  
    public String getRpcUser() {
        return rpcUser;
    }
    public String getDbPath() {
        return dbPath;
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
 
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE); 
    }
    
    public static void main(String[] args) {
        String s = "C:/svnprjects/mauro_netbprj/abs-ota-soapui-listener/test/FILE_DISPO__%s.txt";
        String td = DateFormatUtils.format(new Date(), "yyyyMMdd");
        System.out.println( String.format(s, td)    );
    }
}
