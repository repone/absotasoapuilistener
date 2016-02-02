/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.otasoapui ;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.iface.Request;
import com.eviware.soapui.model.iface.Request.SubmitException;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.model.support.TestRunListenerAdapter;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.impl.wsdl.submit.RequestFilter;
import com.eviware.soapui.impl.wsdl.submit.transports.http.BaseHttpRequestTransport;
import com.eviware.soapui.impl.wsdl.submit.transports.http.support.attachments.WsdlSinglePartHttpResponse;
import com.eviware.soapui.impl.wsdl.submit.transports.http.support.methods.ExtendedPostMethod;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mauro.larese
 */
public class AbsOtaListener implements  RequestFilter{
    private int counter = 0;
    private String savePath = null;
    @Override
    public void filterRequest(SubmitContext sc, Request rqst) {
       
        if(savePath==null)
            savePath = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.save.path");
         
    }

    @Override
    public void afterRequest(SubmitContext sc, Request rqst) {
       
      if( rqst.getName().equals("OTA_NotifReport")) return;
      
      System.out.println( "Request ---------> " + rqst.getName() );
      WsdlSinglePartHttpResponse response = ( WsdlSinglePartHttpResponse) sc.getProperty(BaseHttpRequestTransport.RESPONSE);
      String hotel = (String)SoapUI.getGlobalProperties().getPropertyValue("abs.name");
      String path = savePath+hotel+"/res_"; 
      File file = new File(path);
      System.out.println("File path = " + path); 
      if (!file.exists())   file.mkdirs();
          
      try{   
         //System.out.println(  response.getContentAsXml()  );
      }catch(Exception e){  }
      
      DOMParser domParser = new DOMParser("", path); 
       
      try{   
        ByteArrayInputStream b = new  ByteArrayInputStream(  response.getContentAsXml().getBytes("UTF-8")  );
        domParser.AnalisiDOM(b);
      }catch(Exception e){
          System.out.println( " response " + response.getContentAsXml() ); 
          System.out.println( " request " +  rqst.toString()   ); 
          e.printStackTrace();
      }
      SoapUI.getGlobalProperties().setPropertyValue("abs.hotelReservationID",domParser.getHotelReservationID());
      SoapUI.getGlobalProperties().setPropertyValue("abs.echoToken",domParser.getEchoToken());
      
      System.out.println( SoapUI.getGlobalProperties().getPropertyValue("abs.hotelReservationID" ) );
      System.out.println( SoapUI.getGlobalProperties().getPropertyValue("abs.echoToken" ) );
    }

    @Override
    public void afterRequest(SubmitContext sc, Response rspns) { 
    }

 
     
}
