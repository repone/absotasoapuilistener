/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.otasoapui;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.wsdl.submit.RequestFilter;
import com.eviware.soapui.model.iface.Request;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.model.iface.SubmitContext;
import com.mmone.gpdati.allotment.GpdUpdateRunner;
import com.mmone.gpdati.allotment.UpdateRunException;
import com.mmone.gpdati.config.GpDatiProperties;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.time.DateFormatUtils;
/**
 *
 * @author mauro.larese
 */
public class AllotmentUpdateListener implements  RequestFilter{
    private GpdUpdateRunner updateRunner=null;
    @Override
    public void filterRequest(SubmitContext sc, Request rqst) {
        //(String)SoapUI.getGlobalProperties().getPropertyValue("abs.save.path");
         
        String p = (String)SoapUI.getGlobalProperties().getPropertyValue("update.done");
        if(p==null){
            SoapUI.getGlobalProperties().setPropertyValue("update.done","true");
            try {
                doUpdate();
            } catch (MissingParametersException ex) {
                Logger.getLogger(AllotmentUpdateListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UpdateRunException ex) {
                Logger.getLogger(AllotmentUpdateListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }

    private void doUpdate() throws MissingParametersException, UpdateRunException{
        if(updateRunner==null){ 
            String dbPath = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.db");
            String rpcUser = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.rpc.user");
            String rpcPwd = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.rpc.pwd");
            String rpcUrl = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.rpc.url");
            String availFile = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.avail.file");
             
            String errorMessage = "";
            boolean hasError = false;
             
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
            
            if(hasError){
                throw new MissingParametersException(errorMessage);
            }
             
            GpDatiProperties p = new GpDatiProperties( rpcUser,rpcPwd,rpcUrl,dbPath,availFile); 
              
            updateRunner=new GpdUpdateRunner(p);
            updateRunner.run();
        }
    }
    
    @Override
    public void afterRequest(SubmitContext sc, Request rqst) {
        //code
        
    }

    @Override
    public void afterRequest(SubmitContext sc, Response rspns) {
         
    }
}
