package com.mmone.otasoapui;

import com.eviware.soapui.SoapUI;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 *
 * @author mauro.larese
 */
public class SoapUiPropertiesCollector implements AllotmentUpdatePropertiesCollector{
    private String dbPath  ;
    private String rpcUser  ;
    private String rpcPwd  ;
    private String rpcUrl ;
    private String availFile  ;
    private String errorMessage = "";
    private boolean hasError = false;
        
    @Override
    public String getDbPath() {
        return dbPath;
    }

    @Override
    public String getRpcUser() {
        return rpcUser;
    }

    @Override
    public String getRpcPwd() {
        return rpcPwd;
    }

    @Override
    public String getRpcUrl() {
        return rpcUrl;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean hasError() {
        return hasError;
    }

    @Override
    public String getAvailFile() {
        return availFile;
    }
    
    public SoapUiPropertiesCollector() {
        dbPath = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.db");
        rpcUser = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.rpc.user");
        rpcPwd = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.rpc.pwd");
        rpcUrl = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.rpc.url");
        availFile = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.avail.file");
 
        if(dbPath==null){
            hasError=true;
            errorMessage+=" dbPath mandatory\n";
        }
 
        if(rpcUser==null){
            hasError=true;
            errorMessage+=" rpcUser mandatory\n";
        }

        if(rpcPwd==null){
            hasError=true;
            errorMessage+=" rpcPwd mandatory\n";
        }

        if(rpcUrl==null){
            hasError=true;
            errorMessage+=" rpcUrl mandatory\n";
        }

        if(availFile==null){
            hasError=true;
            errorMessage+=" availFile mandatory\n";
        }else{
            String td = DateFormatUtils.format(new Date(), "yyyyMMdd");
            availFile=String.format(availFile, td) ;
        }     
    }    
    
}
